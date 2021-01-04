/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

class CameraThread {
    private static final String TAG = CameraThread.class.getSimpleName();
    private static CameraThread instance;
    private final Object LOCK = new Object();
    private Handler handler;
    private int openCount = 0;
    private HandlerThread thread;

    private CameraThread() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void checkRunning() {
        Object object;
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            if (this.handler == null) {
                HandlerThread handlerThread;
                if (this.openCount <= 0) {
                    throw new IllegalStateException("CameraThread is not open");
                }
                this.thread = handlerThread = new HandlerThread("CameraThread");
                handlerThread.start();
                this.handler = new Handler(this.thread.getLooper());
            }
            return;
        }
    }

    public static CameraThread getInstance() {
        if (instance == null) {
            instance = new CameraThread();
        }
        return instance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void quit() {
        Object object;
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            this.thread.quit();
            this.thread = null;
            this.handler = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void decrementInstances() {
        Object object;
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            int n;
            this.openCount = n = -1 + this.openCount;
            if (n == 0) {
                this.quit();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void enqueue(Runnable runnable) {
        Object object;
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            this.checkRunning();
            this.handler.post(runnable);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void enqueueDelayed(Runnable runnable, long l) {
        Object object;
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            this.checkRunning();
            this.handler.postDelayed(runnable, l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void incrementAndEnqueue(Runnable runnable) {
        Object object;
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            this.openCount = 1 + this.openCount;
            this.enqueue(runnable);
            return;
        }
    }
}

