/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.Camera
 *  android.hardware.Camera$CameraInfo
 *  android.hardware.Camera$Parameters
 *  android.hardware.Camera$PreviewCallback
 *  android.hardware.Camera$Size
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.SurfaceHolder
 *  com.google.zxing.client.android.AmbientLightManager
 *  com.google.zxing.client.android.camera.CameraConfigurationUtils
 *  com.google.zxing.client.android.camera.open.OpenCameraInterface
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.AmbientLightManager;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;
import com.google.zxing.client.android.camera.open.OpenCameraInterface;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.SourceData;
import com.journeyapps.barcodescanner.camera.AutoFocusManager;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.journeyapps.barcodescanner.camera.CameraSurface;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;
import com.journeyapps.barcodescanner.camera.PreviewCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CameraManager {
    private static final String TAG = CameraManager.class.getSimpleName();
    private AmbientLightManager ambientLightManager;
    private AutoFocusManager autoFocusManager;
    private Camera camera;
    private Camera.CameraInfo cameraInfo;
    private final CameraPreviewCallback cameraPreviewCallback;
    private Context context;
    private String defaultParameters;
    private DisplayConfiguration displayConfiguration;
    private Size previewSize;
    private boolean previewing;
    private Size requestedPreviewSize;
    private int rotationDegrees = -1;
    private CameraSettings settings = new CameraSettings();

    public CameraManager(Context context) {
        this.context = context;
        this.cameraPreviewCallback = new CameraPreviewCallback();
    }

    private int calculateDisplayRotation() {
        int n = this.displayConfiguration.getRotation();
        int n2 = n != 0 ? (n != 1 ? (n != 2 ? (n != 3 ? 0 : 270) : 180) : 90) : 0;
        int n3 = this.cameraInfo.facing == 1 ? (360 - (n2 + this.cameraInfo.orientation) % 360) % 360 : (360 + (this.cameraInfo.orientation - n2)) % 360;
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Camera Display Orientation: ");
        stringBuilder.append(n3);
        Log.i((String)string2, (String)stringBuilder.toString());
        return n3;
    }

    private Camera.Parameters getDefaultCameraParameters() {
        Camera.Parameters parameters = this.camera.getParameters();
        String string2 = this.defaultParameters;
        if (string2 == null) {
            this.defaultParameters = parameters.flatten();
            return parameters;
        }
        parameters.unflatten(string2);
        return parameters;
    }

    private static List<Size> getPreviewSizes(Camera.Parameters parameters) {
        List list = parameters.getSupportedPreviewSizes();
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            Camera.Size size = parameters.getPreviewSize();
            if (size != null) {
                arrayList.add((Object)new Size(size.width, size.height));
            }
            return arrayList;
        }
        for (Camera.Size size : list) {
            arrayList.add((Object)new Size(size.width, size.height));
        }
        return arrayList;
    }

    private void setCameraDisplayOrientation(int n) {
        this.camera.setDisplayOrientation(n);
    }

    private void setDesiredParameters(boolean bl) {
        List<Size> list;
        Camera.Parameters parameters = this.getDefaultCameraParameters();
        if (parameters == null) {
            Log.w((String)TAG, (String)"Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Initial camera parameters: ");
        stringBuilder.append(parameters.flatten());
        Log.i((String)string2, (String)stringBuilder.toString());
        if (bl) {
            Log.w((String)TAG, (String)"In camera config safe mode -- most settings will not be honored");
        }
        CameraConfigurationUtils.setFocus((Camera.Parameters)parameters, (CameraSettings.FocusMode)this.settings.getFocusMode(), (boolean)bl);
        if (!bl) {
            CameraConfigurationUtils.setTorch((Camera.Parameters)parameters, (boolean)false);
            if (this.settings.isScanInverted()) {
                CameraConfigurationUtils.setInvertColor((Camera.Parameters)parameters);
            }
            if (this.settings.isBarcodeSceneModeEnabled()) {
                CameraConfigurationUtils.setBarcodeSceneMode((Camera.Parameters)parameters);
            }
            if (this.settings.isMeteringEnabled() && Build.VERSION.SDK_INT >= 15) {
                CameraConfigurationUtils.setVideoStabilization((Camera.Parameters)parameters);
                CameraConfigurationUtils.setFocusArea((Camera.Parameters)parameters);
                CameraConfigurationUtils.setMetering((Camera.Parameters)parameters);
            }
        }
        if ((list = CameraManager.getPreviewSizes(parameters)).size() == 0) {
            this.requestedPreviewSize = null;
        } else {
            Size size;
            this.requestedPreviewSize = size = this.displayConfiguration.getBestPreviewSize(list, this.isCameraRotated());
            parameters.setPreviewSize(size.width, this.requestedPreviewSize.height);
        }
        if (Build.DEVICE.equals((Object)"glass-1")) {
            CameraConfigurationUtils.setBestPreviewFPS((Camera.Parameters)parameters);
        }
        String string3 = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Final camera parameters: ");
        stringBuilder2.append(parameters.flatten());
        Log.i((String)string3, (String)stringBuilder2.toString());
        this.camera.setParameters(parameters);
    }

    private void setParameters() {
        try {
            int n;
            this.rotationDegrees = n = this.calculateDisplayRotation();
            this.setCameraDisplayOrientation(n);
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Failed to set rotation.");
        }
        try {
            this.setDesiredParameters(false);
        }
        catch (Exception exception) {
            try {
                this.setDesiredParameters(true);
            }
            catch (Exception exception2) {
                Log.w((String)TAG, (String)"Camera rejected even safe-mode parameters! No configuration");
            }
        }
        Camera.Size size = this.camera.getParameters().getPreviewSize();
        this.previewSize = size == null ? this.requestedPreviewSize : new Size(size.width, size.height);
        this.cameraPreviewCallback.setResolution(this.previewSize);
    }

    public void changeCameraParameters(CameraParametersCallback cameraParametersCallback) {
        Camera camera = this.camera;
        if (camera != null) {
            try {
                camera.setParameters(cameraParametersCallback.changeCameraParameters(camera.getParameters()));
                return;
            }
            catch (RuntimeException runtimeException) {
                Log.e((String)TAG, (String)"Failed to change camera parameters", (Throwable)runtimeException);
            }
        }
    }

    public void close() {
        Camera camera = this.camera;
        if (camera != null) {
            camera.release();
            this.camera = null;
        }
    }

    public void configure() {
        if (this.camera != null) {
            this.setParameters();
            return;
        }
        throw new RuntimeException("Camera not open");
    }

    public Camera getCamera() {
        return this.camera;
    }

    public int getCameraRotation() {
        return this.rotationDegrees;
    }

    public CameraSettings getCameraSettings() {
        return this.settings;
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.displayConfiguration;
    }

    public Size getNaturalPreviewSize() {
        return this.previewSize;
    }

    public Size getPreviewSize() {
        if (this.previewSize == null) {
            return null;
        }
        if (this.isCameraRotated()) {
            return this.previewSize.rotate();
        }
        return this.previewSize;
    }

    public boolean isCameraRotated() {
        int n = this.rotationDegrees;
        if (n != -1) {
            return n % 180 != 0;
        }
        throw new IllegalStateException("Rotation not calculated yet. Call configure() first.");
    }

    public boolean isOpen() {
        return this.camera != null;
    }

    public boolean isTorchOn() {
        Camera.Parameters parameters = this.camera.getParameters();
        if (parameters != null) {
            String string2 = parameters.getFlashMode();
            return string2 != null && ("on".equals((Object)string2) || "torch".equals((Object)string2));
        }
        return false;
    }

    public void open() {
        Camera camera;
        this.camera = camera = OpenCameraInterface.open((int)this.settings.getRequestedCameraId());
        if (camera != null) {
            Camera.CameraInfo cameraInfo;
            int n = OpenCameraInterface.getCameraId((int)this.settings.getRequestedCameraId());
            this.cameraInfo = cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo((int)n, (Camera.CameraInfo)cameraInfo);
            return;
        }
        throw new RuntimeException("Failed to open camera");
    }

    public void requestPreviewFrame(PreviewCallback previewCallback) {
        Camera camera = this.camera;
        if (camera != null && this.previewing) {
            this.cameraPreviewCallback.setCallback(previewCallback);
            camera.setOneShotPreviewCallback((Camera.PreviewCallback)this.cameraPreviewCallback);
        }
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.settings = cameraSettings;
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.displayConfiguration = displayConfiguration;
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        this.setPreviewDisplay(new CameraSurface(surfaceHolder));
    }

    public void setPreviewDisplay(CameraSurface cameraSurface) throws IOException {
        cameraSurface.setPreview(this.camera);
    }

    public void setTorch(boolean bl) {
        if (this.camera != null) {
            try {
                if (bl != this.isTorchOn()) {
                    if (this.autoFocusManager != null) {
                        this.autoFocusManager.stop();
                    }
                    Camera.Parameters parameters = this.camera.getParameters();
                    CameraConfigurationUtils.setTorch((Camera.Parameters)parameters, (boolean)bl);
                    if (this.settings.isExposureEnabled()) {
                        CameraConfigurationUtils.setBestExposure((Camera.Parameters)parameters, (boolean)bl);
                    }
                    this.camera.setParameters(parameters);
                    if (this.autoFocusManager != null) {
                        this.autoFocusManager.start();
                    }
                }
                return;
            }
            catch (RuntimeException runtimeException) {
                Log.e((String)TAG, (String)"Failed to set torch", (Throwable)runtimeException);
            }
        }
    }

    public void startPreview() {
        Camera camera = this.camera;
        if (camera != null && !this.previewing) {
            AmbientLightManager ambientLightManager;
            camera.startPreview();
            this.previewing = true;
            this.autoFocusManager = new AutoFocusManager(this.camera, this.settings);
            this.ambientLightManager = ambientLightManager = new AmbientLightManager(this.context, this, this.settings);
            ambientLightManager.start();
        }
    }

    public void stopPreview() {
        Camera camera;
        AmbientLightManager ambientLightManager;
        AutoFocusManager autoFocusManager = this.autoFocusManager;
        if (autoFocusManager != null) {
            autoFocusManager.stop();
            this.autoFocusManager = null;
        }
        if ((ambientLightManager = this.ambientLightManager) != null) {
            ambientLightManager.stop();
            this.ambientLightManager = null;
        }
        if ((camera = this.camera) != null && this.previewing) {
            camera.stopPreview();
            this.cameraPreviewCallback.setCallback(null);
            this.previewing = false;
        }
    }

    private final class CameraPreviewCallback
    implements Camera.PreviewCallback {
        private PreviewCallback callback;
        private Size resolution;

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void onPreviewFrame(byte[] arrby, Camera camera) {
            Size size = this.resolution;
            PreviewCallback previewCallback = this.callback;
            if (size != null && previewCallback != null) {
                if (arrby == null) throw new NullPointerException("No preview data received");
                int n = camera.getParameters().getPreviewFormat();
                SourceData sourceData = new SourceData(arrby, size.width, size.height, n, CameraManager.this.getCameraRotation());
                previewCallback.onPreview(sourceData);
                return;
            }
            Log.d((String)TAG, (String)"Got preview callback, but no handler or resolution available");
            if (previewCallback == null) return;
            previewCallback.onPreviewError(new Exception("No resolution available"));
            return;
            catch (RuntimeException runtimeException) {}
            Log.e((String)TAG, (String)"Camera preview failed", (Throwable)runtimeException);
            previewCallback.onPreviewError((Exception)((Object)runtimeException));
        }

        public void setCallback(PreviewCallback previewCallback) {
            this.callback = previewCallback;
        }

        public void setResolution(Size size) {
            this.resolution = size;
        }
    }

}

