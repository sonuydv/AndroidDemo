/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.hardware.Camera
 *  android.hardware.Camera$AutoFocusCallback
 *  android.hardware.Camera$Parameters
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.util.Log
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Collection
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.ArrayList;
import java.util.Collection;

public final class AutoFocusManager {
    private static final long AUTO_FOCUS_INTERVAL_MS = 2000L;
    private static final Collection<String> FOCUS_MODES_CALLING_AF;
    private static final String TAG;
    private int MESSAGE_FOCUS;
    private final Camera.AutoFocusCallback autoFocusCallback;
    private final Camera camera;
    private final Handler.Callback focusHandlerCallback;
    private boolean focusing;
    private Handler handler;
    private boolean stopped;
    private final boolean useAutoFocus;

    static {
        ArrayList arrayList;
        TAG = AutoFocusManager.class.getSimpleName();
        FOCUS_MODES_CALLING_AF = arrayList = new ArrayList(2);
        arrayList.add((Object)"auto");
        FOCUS_MODES_CALLING_AF.add((Object)"macro");
    }

    public AutoFocusManager(Camera camera, CameraSettings cameraSettings) {
        int n;
        this.MESSAGE_FOCUS = n = 1;
        this.focusHandlerCallback = new Handler.Callback(){

            public boolean handleMessage(Message message) {
                if (message.what == AutoFocusManager.this.MESSAGE_FOCUS) {
                    AutoFocusManager.this.focus();
                    return true;
                }
                return false;
            }
        };
        this.autoFocusCallback = new Camera.AutoFocusCallback(){

            public void onAutoFocus(boolean bl, Camera camera) {
                AutoFocusManager.this.handler.post(new Runnable(){

                    public void run() {
                        AutoFocusManager.this.focusing = false;
                        AutoFocusManager.this.autoFocusAgainLater();
                    }
                });
            }

        };
        this.handler = new Handler(this.focusHandlerCallback);
        this.camera = camera;
        String string2 = camera.getParameters().getFocusMode();
        if (!cameraSettings.isAutoFocusEnabled() || !FOCUS_MODES_CALLING_AF.contains((Object)string2)) {
            n = 0;
        }
        this.useAutoFocus = n;
        String string3 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current focus mode '");
        stringBuilder.append(string2);
        stringBuilder.append("'; use auto focus? ");
        stringBuilder.append(this.useAutoFocus);
        Log.i((String)string3, (String)stringBuilder.toString());
        this.start();
    }

    private void autoFocusAgainLater() {
        AutoFocusManager autoFocusManager = this;
        synchronized (autoFocusManager) {
            if (!this.stopped && !this.handler.hasMessages(this.MESSAGE_FOCUS)) {
                this.handler.sendMessageDelayed(this.handler.obtainMessage(this.MESSAGE_FOCUS), 2000L);
            }
            return;
        }
    }

    private void cancelOutstandingTask() {
        this.handler.removeMessages(this.MESSAGE_FOCUS);
    }

    private void focus() {
        if (this.useAutoFocus && !this.stopped && !this.focusing) {
            try {
                this.camera.autoFocus(this.autoFocusCallback);
                this.focusing = true;
                return;
            }
            catch (RuntimeException runtimeException) {
                Log.w((String)TAG, (String)"Unexpected exception while focusing", (Throwable)runtimeException);
                this.autoFocusAgainLater();
            }
        }
    }

    public void start() {
        this.stopped = false;
        this.focus();
    }

    public void stop() {
        this.stopped = true;
        this.focusing = false;
        this.cancelOutstandingTask();
        if (this.useAutoFocus) {
            try {
                this.camera.cancelAutoFocus();
                return;
            }
            catch (RuntimeException runtimeException) {
                Log.w((String)TAG, (String)"Unexpected exception while cancelling focusing", (Throwable)runtimeException);
            }
        }
    }

}

