/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Display
 *  android.view.OrientationEventListener
 *  android.view.WindowManager
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.content.Context;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import com.journeyapps.barcodescanner.RotationCallback;

public class RotationListener {
    private RotationCallback callback;
    private int lastRotation;
    private OrientationEventListener orientationEventListener;
    private WindowManager windowManager;

    public void listen(Context context, RotationCallback rotationCallback) {
        OrientationEventListener orientationEventListener;
        this.stop();
        Context context2 = context.getApplicationContext();
        this.callback = rotationCallback;
        this.windowManager = (WindowManager)context2.getSystemService("window");
        this.orientationEventListener = orientationEventListener = new OrientationEventListener(context2, 3){

            public void onOrientationChanged(int n) {
                int n2;
                WindowManager windowManager = RotationListener.this.windowManager;
                RotationCallback rotationCallback = RotationListener.this.callback;
                if (RotationListener.this.windowManager != null && rotationCallback != null && (n2 = windowManager.getDefaultDisplay().getRotation()) != RotationListener.this.lastRotation) {
                    RotationListener.this.lastRotation = n2;
                    rotationCallback.onRotationChanged(n2);
                }
            }
        };
        orientationEventListener.enable();
        this.lastRotation = this.windowManager.getDefaultDisplay().getRotation();
    }

    public void stop() {
        OrientationEventListener orientationEventListener = this.orientationEventListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
        this.orientationEventListener = null;
        this.windowManager = null;
        this.callback = null;
    }

}

