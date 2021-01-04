/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.SurfaceHolder
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$id
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.Throwable
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.Util;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.journeyapps.barcodescanner.camera.CameraSurface;
import com.journeyapps.barcodescanner.camera.CameraThread;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;
import com.journeyapps.barcodescanner.camera.PreviewCallback;

public class CameraInstance {
    private static final String TAG = CameraInstance.class.getSimpleName();
    private boolean cameraClosed = true;
    private CameraManager cameraManager;
    private CameraSettings cameraSettings = new CameraSettings();
    private CameraThread cameraThread;
    private Runnable closer = new Runnable(){

        public void run() {
            try {
                Log.d((String)TAG, (String)"Closing camera");
                CameraInstance.this.cameraManager.stopPreview();
                CameraInstance.this.cameraManager.close();
            }
            catch (Exception exception) {
                Log.e((String)TAG, (String)"Failed to close camera", (Throwable)exception);
            }
            CameraInstance.this.cameraClosed = true;
            CameraInstance.this.readyHandler.sendEmptyMessage(R.id.zxing_camera_closed);
            CameraInstance.this.cameraThread.decrementInstances();
        }
    };
    private Runnable configure = new Runnable(){

        public void run() {
            try {
                Log.d((String)TAG, (String)"Configuring camera");
                CameraInstance.this.cameraManager.configure();
                if (CameraInstance.this.readyHandler != null) {
                    CameraInstance.this.readyHandler.obtainMessage(R.id.zxing_prewiew_size_ready, (Object)CameraInstance.this.getPreviewSize()).sendToTarget();
                }
                return;
            }
            catch (Exception exception) {
                CameraInstance.this.notifyError(exception);
                Log.e((String)TAG, (String)"Failed to configure camera", (Throwable)exception);
                return;
            }
        }
    };
    private DisplayConfiguration displayConfiguration;
    private Handler mainHandler;
    private boolean open = false;
    private Runnable opener = new Runnable(){

        public void run() {
            try {
                Log.d((String)TAG, (String)"Opening camera");
                CameraInstance.this.cameraManager.open();
                return;
            }
            catch (Exception exception) {
                CameraInstance.this.notifyError(exception);
                Log.e((String)TAG, (String)"Failed to open camera", (Throwable)exception);
                return;
            }
        }
    };
    private Runnable previewStarter = new Runnable(){

        public void run() {
            try {
                Log.d((String)TAG, (String)"Starting preview");
                CameraInstance.this.cameraManager.setPreviewDisplay(CameraInstance.this.surface);
                CameraInstance.this.cameraManager.startPreview();
                return;
            }
            catch (Exception exception) {
                CameraInstance.this.notifyError(exception);
                Log.e((String)TAG, (String)"Failed to start preview", (Throwable)exception);
                return;
            }
        }
    };
    private Handler readyHandler;
    private CameraSurface surface;

    public CameraInstance(Context context) {
        CameraManager cameraManager;
        Util.validateMainThread();
        this.cameraThread = CameraThread.getInstance();
        this.cameraManager = cameraManager = new CameraManager(context);
        cameraManager.setCameraSettings(this.cameraSettings);
        this.mainHandler = new Handler();
    }

    public CameraInstance(CameraManager cameraManager) {
        Util.validateMainThread();
        this.cameraManager = cameraManager;
    }

    private Size getPreviewSize() {
        return this.cameraManager.getPreviewSize();
    }

    private void notifyError(Exception exception) {
        Handler handler = this.readyHandler;
        if (handler != null) {
            handler.obtainMessage(R.id.zxing_camera_error, (Object)exception).sendToTarget();
        }
    }

    private void validateOpen() {
        if (this.open) {
            return;
        }
        throw new IllegalStateException("CameraInstance is not open");
    }

    public void changeCameraParameters(final CameraParametersCallback cameraParametersCallback) {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(new Runnable(){

                public void run() {
                    CameraInstance.this.cameraManager.changeCameraParameters(cameraParametersCallback);
                }
            });
        }
    }

    public void close() {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(this.closer);
        } else {
            this.cameraClosed = true;
        }
        this.open = false;
    }

    public void configureCamera() {
        Util.validateMainThread();
        this.validateOpen();
        this.cameraThread.enqueue(this.configure);
    }

    protected CameraManager getCameraManager() {
        return this.cameraManager;
    }

    public int getCameraRotation() {
        return this.cameraManager.getCameraRotation();
    }

    public CameraSettings getCameraSettings() {
        return this.cameraSettings;
    }

    protected CameraThread getCameraThread() {
        return this.cameraThread;
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.displayConfiguration;
    }

    protected CameraSurface getSurface() {
        return this.surface;
    }

    public boolean isCameraClosed() {
        return this.cameraClosed;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void open() {
        Util.validateMainThread();
        this.open = true;
        this.cameraClosed = false;
        this.cameraThread.incrementAndEnqueue(this.opener);
    }

    public void requestPreview(final PreviewCallback previewCallback) {
        this.mainHandler.post(new Runnable(){

            public void run() {
                if (!CameraInstance.this.open) {
                    Log.d((String)TAG, (String)"Camera is closed, not requesting preview");
                    return;
                }
                CameraInstance.this.cameraThread.enqueue(new Runnable(){

                    public void run() {
                        CameraInstance.this.cameraManager.requestPreviewFrame(previewCallback);
                    }
                });
            }

        });
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        if (!this.open) {
            this.cameraSettings = cameraSettings;
            this.cameraManager.setCameraSettings(cameraSettings);
        }
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.displayConfiguration = displayConfiguration;
        this.cameraManager.setDisplayConfiguration(displayConfiguration);
    }

    public void setReadyHandler(Handler handler) {
        this.readyHandler = handler;
    }

    public void setSurface(CameraSurface cameraSurface) {
        this.surface = cameraSurface;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.setSurface(new CameraSurface(surfaceHolder));
    }

    public void setTorch(final boolean bl) {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(new Runnable(){

                public void run() {
                    CameraInstance.this.cameraManager.setTorch(bl);
                }
            });
        }
    }

    public void startPreview() {
        Util.validateMainThread();
        this.validateOpen();
        this.cameraThread.enqueue(this.previewStarter);
    }

}

