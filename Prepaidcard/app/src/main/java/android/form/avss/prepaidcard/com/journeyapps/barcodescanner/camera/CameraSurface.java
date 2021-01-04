/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.SurfaceTexture
 *  android.hardware.Camera
 *  android.view.SurfaceHolder
 *  java.io.IOException
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import java.io.IOException;

public class CameraSurface {
    private SurfaceHolder surfaceHolder;
    private SurfaceTexture surfaceTexture;

    public CameraSurface(SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null) {
            this.surfaceTexture = surfaceTexture;
            return;
        }
        throw new IllegalArgumentException("surfaceTexture may not be null");
    }

    public CameraSurface(SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null) {
            this.surfaceHolder = surfaceHolder;
            return;
        }
        throw new IllegalArgumentException("surfaceHolder may not be null");
    }

    public SurfaceHolder getSurfaceHolder() {
        return this.surfaceHolder;
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.surfaceTexture;
    }

    public void setPreview(Camera camera) throws IOException {
        SurfaceHolder surfaceHolder = this.surfaceHolder;
        if (surfaceHolder != null) {
            camera.setPreviewDisplay(surfaceHolder);
            return;
        }
        camera.setPreviewTexture(this.surfaceTexture);
    }
}

