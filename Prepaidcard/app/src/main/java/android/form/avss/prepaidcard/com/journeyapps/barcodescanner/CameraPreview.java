/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.SurfaceTexture
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.Display
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.TextureView
 *  android.view.TextureView$SurfaceTextureListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.WindowManager
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$id
 *  com.google.zxing.client.android.R$styleable
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.InterruptedException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.RotationCallback;
import com.journeyapps.barcodescanner.RotationListener;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.Util;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.journeyapps.barcodescanner.camera.CameraSurface;
import com.journeyapps.barcodescanner.camera.CenterCropStrategy;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;
import com.journeyapps.barcodescanner.camera.FitCenterStrategy;
import com.journeyapps.barcodescanner.camera.FitXYStrategy;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CameraPreview
extends ViewGroup {
    private static final int ROTATION_LISTENER_DELAY_MS = 250;
    private static final String TAG = CameraPreview.class.getSimpleName();
    private CameraInstance cameraInstance;
    private CameraSettings cameraSettings = new CameraSettings();
    private Size containerSize;
    private Size currentSurfaceSize;
    private DisplayConfiguration displayConfiguration;
    private final StateListener fireState = new StateListener(){

        @Override
        public void cameraClosed() {
            Iterator iterator = CameraPreview.this.stateListeners.iterator();
            while (iterator.hasNext()) {
                ((StateListener)iterator.next()).cameraClosed();
            }
        }

        @Override
        public void cameraError(Exception exception) {
            Iterator iterator = CameraPreview.this.stateListeners.iterator();
            while (iterator.hasNext()) {
                ((StateListener)iterator.next()).cameraError(exception);
            }
        }

        @Override
        public void previewSized() {
            Iterator iterator = CameraPreview.this.stateListeners.iterator();
            while (iterator.hasNext()) {
                ((StateListener)iterator.next()).previewSized();
            }
        }

        @Override
        public void previewStarted() {
            Iterator iterator = CameraPreview.this.stateListeners.iterator();
            while (iterator.hasNext()) {
                ((StateListener)iterator.next()).previewStarted();
            }
        }

        @Override
        public void previewStopped() {
            Iterator iterator = CameraPreview.this.stateListeners.iterator();
            while (iterator.hasNext()) {
                ((StateListener)iterator.next()).previewStopped();
            }
        }
    };
    private Rect framingRect = null;
    private Size framingRectSize = null;
    private double marginFraction = 0.1;
    private int openedOrientation = -1;
    private boolean previewActive = false;
    private Rect previewFramingRect = null;
    private PreviewScalingStrategy previewScalingStrategy = null;
    private Size previewSize;
    private RotationCallback rotationCallback = new RotationCallback(){

        @Override
        public void onRotationChanged(int n) {
            CameraPreview.this.stateHandler.postDelayed(new Runnable(){

                public void run() {
                    CameraPreview.this.rotationChanged();
                }
            }, 250L);
        }

    };
    private RotationListener rotationListener;
    private final Handler.Callback stateCallback = new Handler.Callback(){

        public boolean handleMessage(Message message) {
            block2 : {
                block1 : {
                    if (message.what == R.id.zxing_prewiew_size_ready) {
                        CameraPreview.this.previewSized((Size)message.obj);
                        return true;
                    }
                    if (message.what != R.id.zxing_camera_error) break block1;
                    Exception exception = (Exception)((Object)message.obj);
                    if (!CameraPreview.this.isActive()) break block2;
                    CameraPreview.this.pause();
                    CameraPreview.this.fireState.cameraError(exception);
                    break block2;
                }
                if (message.what != R.id.zxing_camera_closed) break block2;
                CameraPreview.this.fireState.cameraClosed();
            }
            return false;
        }
    };
    private Handler stateHandler;
    private List<StateListener> stateListeners = new ArrayList();
    private final SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback(){

        public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
            if (surfaceHolder == null) {
                Log.e((String)TAG, (String)"*** WARNING *** surfaceChanged() gave us a null surface!");
                return;
            }
            CameraPreview.this.currentSurfaceSize = new Size(n2, n3);
            CameraPreview.this.startPreviewIfReady();
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            CameraPreview.this.currentSurfaceSize = null;
        }
    };
    private Rect surfaceRect;
    private SurfaceView surfaceView;
    private TextureView textureView;
    private boolean torchOn = false;
    private boolean useTextureView = false;
    private WindowManager windowManager;

    public CameraPreview(Context context) {
        super(context);
        this.initialize(context, null, 0, 0);
    }

    public CameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize(context, attributeSet, 0, 0);
    }

    public CameraPreview(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initialize(context, attributeSet, n, 0);
    }

    private void calculateFrames() {
        Size size;
        if (this.containerSize != null && (size = this.previewSize) != null && this.displayConfiguration != null) {
            Rect rect;
            int n = size.width;
            int n2 = this.previewSize.height;
            int n3 = this.containerSize.width;
            int n4 = this.containerSize.height;
            this.surfaceRect = this.displayConfiguration.scalePreview(this.previewSize);
            this.framingRect = this.calculateFramingRect(new Rect(0, 0, n3, n4), this.surfaceRect);
            Rect rect2 = new Rect(this.framingRect);
            rect2.offset(-this.surfaceRect.left, -this.surfaceRect.top);
            this.previewFramingRect = rect = new Rect(n * rect2.left / this.surfaceRect.width(), n2 * rect2.top / this.surfaceRect.height(), n * rect2.right / this.surfaceRect.width(), n2 * rect2.bottom / this.surfaceRect.height());
            if (rect.width() > 0 && this.previewFramingRect.height() > 0) {
                this.fireState.previewSized();
                return;
            }
            this.previewFramingRect = null;
            this.framingRect = null;
            Log.w((String)TAG, (String)"Preview frame is too small");
            return;
        }
        this.previewFramingRect = null;
        this.framingRect = null;
        this.surfaceRect = null;
        throw new IllegalStateException("containerSize or previewSize is not set yet");
    }

    private void containerSized(Size size) {
        this.containerSize = size;
        CameraInstance cameraInstance = this.cameraInstance;
        if (cameraInstance != null && cameraInstance.getDisplayConfiguration() == null) {
            DisplayConfiguration displayConfiguration;
            this.displayConfiguration = displayConfiguration = new DisplayConfiguration(this.getDisplayRotation(), size);
            displayConfiguration.setPreviewScalingStrategy(this.getPreviewScalingStrategy());
            this.cameraInstance.setDisplayConfiguration(this.displayConfiguration);
            this.cameraInstance.configureCamera();
            boolean bl = this.torchOn;
            if (bl) {
                this.cameraInstance.setTorch(bl);
            }
        }
    }

    private int getDisplayRotation() {
        return this.windowManager.getDefaultDisplay().getRotation();
    }

    private void initCamera() {
        CameraInstance cameraInstance;
        if (this.cameraInstance != null) {
            Log.w((String)TAG, (String)"initCamera called twice");
            return;
        }
        this.cameraInstance = cameraInstance = this.createCameraInstance();
        cameraInstance.setReadyHandler(this.stateHandler);
        this.cameraInstance.open();
        this.openedOrientation = this.getDisplayRotation();
    }

    private void initialize(Context context, AttributeSet attributeSet, int n, int n2) {
        if (this.getBackground() == null) {
            this.setBackgroundColor(-16777216);
        }
        this.initializeAttributes(attributeSet);
        this.windowManager = (WindowManager)context.getSystemService("window");
        this.stateHandler = new Handler(this.stateCallback);
        this.rotationListener = new RotationListener();
    }

    private void previewSized(Size size) {
        this.previewSize = size;
        if (this.containerSize != null) {
            this.calculateFrames();
            this.requestLayout();
            this.startPreviewIfReady();
        }
    }

    private void rotationChanged() {
        if (this.isActive() && this.getDisplayRotation() != this.openedOrientation) {
            this.pause();
            this.resume();
        }
    }

    private void setupSurfaceView() {
        SurfaceView surfaceView;
        if (this.useTextureView) {
            TextureView textureView;
            this.textureView = textureView = new TextureView(this.getContext());
            textureView.setSurfaceTextureListener(this.surfaceTextureListener());
            this.addView((View)this.textureView);
            return;
        }
        this.surfaceView = surfaceView = new SurfaceView(this.getContext());
        surfaceView.getHolder().addCallback(this.surfaceCallback);
        this.addView((View)this.surfaceView);
    }

    private void startCameraPreview(CameraSurface cameraSurface) {
        if (!this.previewActive && this.cameraInstance != null) {
            Log.i((String)TAG, (String)"Starting preview");
            this.cameraInstance.setSurface(cameraSurface);
            this.cameraInstance.startPreview();
            this.previewActive = true;
            this.previewStarted();
            this.fireState.previewStarted();
        }
    }

    private void startPreviewIfReady() {
        Rect rect;
        Size size = this.currentSurfaceSize;
        if (size != null && this.previewSize != null && (rect = this.surfaceRect) != null) {
            if (this.surfaceView != null && size.equals(new Size(rect.width(), this.surfaceRect.height()))) {
                this.startCameraPreview(new CameraSurface(this.surfaceView.getHolder()));
                return;
            }
            TextureView textureView = this.textureView;
            if (textureView != null && textureView.getSurfaceTexture() != null) {
                if (this.previewSize != null) {
                    Matrix matrix = this.calculateTextureTransform(new Size(this.textureView.getWidth(), this.textureView.getHeight()), this.previewSize);
                    this.textureView.setTransform(matrix);
                }
                this.startCameraPreview(new CameraSurface(this.textureView.getSurfaceTexture()));
            }
        }
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener() {
        return new TextureView.SurfaceTextureListener(){

            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int n, int n2) {
                this.onSurfaceTextureSizeChanged(surfaceTexture, n, n2);
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int n, int n2) {
                CameraPreview.this.currentSurfaceSize = new Size(n, n2);
                CameraPreview.this.startPreviewIfReady();
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }
        };
    }

    public void addStateListener(StateListener stateListener) {
        this.stateListeners.add((Object)stateListener);
    }

    protected Rect calculateFramingRect(Rect rect, Rect rect2) {
        Rect rect3 = new Rect(rect);
        rect3.intersect(rect2);
        if (this.framingRectSize != null) {
            rect3.inset(Math.max((int)0, (int)((rect3.width() - this.framingRectSize.width) / 2)), Math.max((int)0, (int)((rect3.height() - this.framingRectSize.height) / 2)));
            return rect3;
        }
        int n = (int)Math.min((double)((double)rect3.width() * this.marginFraction), (double)((double)rect3.height() * this.marginFraction));
        rect3.inset(n, n);
        if (rect3.height() > rect3.width()) {
            rect3.inset(0, (rect3.height() - rect3.width()) / 2);
        }
        return rect3;
    }

    protected Matrix calculateTextureTransform(Size size, Size size2) {
        float f;
        float f2;
        float f3 = (float)size.width / (float)size.height;
        float f4 = (float)size2.width / (float)size2.height;
        if (f3 < f4) {
            f2 = f4 / f3;
            f = 1.0f;
        } else {
            f2 = 1.0f;
            f = f3 / f4;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f);
        float f5 = f2 * (float)size.width;
        float f6 = f * (float)size.height;
        matrix.postTranslate(((float)size.width - f5) / 2.0f, ((float)size.height - f6) / 2.0f);
        return matrix;
    }

    public void changeCameraParameters(CameraParametersCallback cameraParametersCallback) {
        CameraInstance cameraInstance = this.cameraInstance;
        if (cameraInstance != null) {
            cameraInstance.changeCameraParameters(cameraParametersCallback);
        }
    }

    protected CameraInstance createCameraInstance() {
        CameraInstance cameraInstance = new CameraInstance(this.getContext());
        cameraInstance.setCameraSettings(this.cameraSettings);
        return cameraInstance;
    }

    public CameraInstance getCameraInstance() {
        return this.cameraInstance;
    }

    public CameraSettings getCameraSettings() {
        return this.cameraSettings;
    }

    public Rect getFramingRect() {
        return this.framingRect;
    }

    public Size getFramingRectSize() {
        return this.framingRectSize;
    }

    public double getMarginFraction() {
        return this.marginFraction;
    }

    public Rect getPreviewFramingRect() {
        return this.previewFramingRect;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        PreviewScalingStrategy previewScalingStrategy = this.previewScalingStrategy;
        if (previewScalingStrategy != null) {
            return previewScalingStrategy;
        }
        if (this.textureView != null) {
            return new CenterCropStrategy();
        }
        return new FitCenterStrategy();
    }

    protected void initializeAttributes(AttributeSet attributeSet) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.zxing_camera_preview);
        int n = (int)typedArray.getDimension(R.styleable.zxing_camera_preview_zxing_framing_rect_width, -1.0f);
        int n2 = (int)typedArray.getDimension(R.styleable.zxing_camera_preview_zxing_framing_rect_height, -1.0f);
        if (n > 0 && n2 > 0) {
            this.framingRectSize = new Size(n, n2);
        }
        this.useTextureView = typedArray.getBoolean(R.styleable.zxing_camera_preview_zxing_use_texture_view, true);
        int n3 = typedArray.getInteger(R.styleable.zxing_camera_preview_zxing_preview_scaling_strategy, -1);
        if (n3 == 1) {
            this.previewScalingStrategy = new CenterCropStrategy();
        } else if (n3 == 2) {
            this.previewScalingStrategy = new FitCenterStrategy();
        } else if (n3 == 3) {
            this.previewScalingStrategy = new FitXYStrategy();
        }
        typedArray.recycle();
    }

    protected boolean isActive() {
        return this.cameraInstance != null;
    }

    public boolean isCameraClosed() {
        CameraInstance cameraInstance = this.cameraInstance;
        return cameraInstance == null || cameraInstance.isCameraClosed();
        {
        }
    }

    public boolean isPreviewActive() {
        return this.previewActive;
    }

    public boolean isUseTextureView() {
        return this.useTextureView;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setupSurfaceView();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.containerSized(new Size(n3 - n, n4 - n2));
        SurfaceView surfaceView = this.surfaceView;
        if (surfaceView != null) {
            Rect rect = this.surfaceRect;
            if (rect == null) {
                surfaceView.layout(0, 0, this.getWidth(), this.getHeight());
                return;
            }
            surfaceView.layout(rect.left, this.surfaceRect.top, this.surfaceRect.right, this.surfaceRect.bottom);
            return;
        }
        TextureView textureView = this.textureView;
        if (textureView != null) {
            textureView.layout(0, 0, this.getWidth(), this.getHeight());
        }
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle)parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("super"));
        this.setTorch(bundle.getBoolean("torch"));
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", parcelable);
        bundle.putBoolean("torch", this.torchOn);
        return bundle;
    }

    public void pause() {
        SurfaceView surfaceView;
        TextureView textureView;
        Util.validateMainThread();
        Log.d((String)TAG, (String)"pause()");
        this.openedOrientation = -1;
        CameraInstance cameraInstance = this.cameraInstance;
        if (cameraInstance != null) {
            cameraInstance.close();
            this.cameraInstance = null;
            this.previewActive = false;
        } else {
            this.stateHandler.sendEmptyMessage(R.id.zxing_camera_closed);
        }
        if (this.currentSurfaceSize == null && (surfaceView = this.surfaceView) != null) {
            surfaceView.getHolder().removeCallback(this.surfaceCallback);
        }
        if (this.currentSurfaceSize == null && (textureView = this.textureView) != null) {
            textureView.setSurfaceTextureListener(null);
        }
        this.containerSize = null;
        this.previewSize = null;
        this.previewFramingRect = null;
        this.rotationListener.stop();
        this.fireState.previewStopped();
    }

    public void pauseAndWait() {
        CameraInstance cameraInstance = this.getCameraInstance();
        this.pause();
        long l = System.nanoTime();
        while (cameraInstance != null && !cameraInstance.isCameraClosed()) {
            if (System.nanoTime() - l > 2000000000L) {
                return;
            }
            try {
                Thread.sleep((long)1L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
                break;
            }
        }
    }

    protected void previewStarted() {
    }

    public void resume() {
        Util.validateMainThread();
        Log.d((String)TAG, (String)"resume()");
        this.initCamera();
        if (this.currentSurfaceSize != null) {
            this.startPreviewIfReady();
        } else {
            SurfaceView surfaceView = this.surfaceView;
            if (surfaceView != null) {
                surfaceView.getHolder().addCallback(this.surfaceCallback);
            } else {
                TextureView textureView = this.textureView;
                if (textureView != null) {
                    if (textureView.isAvailable()) {
                        this.surfaceTextureListener().onSurfaceTextureAvailable(this.textureView.getSurfaceTexture(), this.textureView.getWidth(), this.textureView.getHeight());
                    } else {
                        this.textureView.setSurfaceTextureListener(this.surfaceTextureListener());
                    }
                }
            }
        }
        this.requestLayout();
        this.rotationListener.listen(this.getContext(), this.rotationCallback);
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.cameraSettings = cameraSettings;
    }

    public void setFramingRectSize(Size size) {
        this.framingRectSize = size;
    }

    public void setMarginFraction(double d) {
        if (!(d >= 0.5)) {
            this.marginFraction = d;
            return;
        }
        throw new IllegalArgumentException("The margin fraction must be less than 0.5");
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.previewScalingStrategy = previewScalingStrategy;
    }

    public void setTorch(boolean bl) {
        this.torchOn = bl;
        CameraInstance cameraInstance = this.cameraInstance;
        if (cameraInstance != null) {
            cameraInstance.setTorch(bl);
        }
    }

    public void setUseTextureView(boolean bl) {
        this.useTextureView = bl;
    }

    public static interface StateListener {
        public void cameraClosed();

        public void cameraError(Exception var1);

        public void previewSized();

        public void previewStarted();

        public void previewStopped();
    }

}

