/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 *  com.google.zxing.LuminanceSource
 *  com.google.zxing.PlanarYUVLuminanceSource
 *  com.google.zxing.Result
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$id
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.zxing.LuminanceSource;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.Decoder;
import com.journeyapps.barcodescanner.SourceData;
import com.journeyapps.barcodescanner.Util;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.PreviewCallback;
import java.util.List;

public class DecoderThread {
    private static final String TAG = DecoderThread.class.getSimpleName();
    private final Object LOCK = new Object();
    private final Handler.Callback callback = new Handler.Callback(){

        public boolean handleMessage(Message message) {
            if (message.what == R.id.zxing_decode) {
                DecoderThread.this.decode((SourceData)message.obj);
            } else if (message.what == R.id.zxing_preview_failed) {
                DecoderThread.this.requestNextPreview();
            }
            return true;
        }
    };
    private CameraInstance cameraInstance;
    private Rect cropRect;
    private Decoder decoder;
    private Handler handler;
    private final PreviewCallback previewCallback = new PreviewCallback(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onPreview(SourceData sourceData) {
            Object object;
            Object object2 = object = DecoderThread.this.LOCK;
            synchronized (object2) {
                if (DecoderThread.this.running) {
                    DecoderThread.this.handler.obtainMessage(R.id.zxing_decode, (Object)sourceData).sendToTarget();
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onPreviewError(Exception exception) {
            Object object;
            Object object2 = object = DecoderThread.this.LOCK;
            synchronized (object2) {
                if (DecoderThread.this.running) {
                    DecoderThread.this.handler.obtainMessage(R.id.zxing_preview_failed).sendToTarget();
                }
                return;
            }
        }
    };
    private Handler resultHandler;
    private boolean running = false;
    private HandlerThread thread;

    public DecoderThread(CameraInstance cameraInstance, Decoder decoder, Handler handler) {
        Util.validateMainThread();
        this.cameraInstance = cameraInstance;
        this.decoder = decoder;
        this.resultHandler = handler;
    }

    private void decode(SourceData sourceData) {
        long l = System.currentTimeMillis();
        sourceData.setCropRect(this.cropRect);
        LuminanceSource luminanceSource = this.createSource(sourceData);
        Result result = null;
        if (luminanceSource != null) {
            result = this.decoder.decode(luminanceSource);
        }
        if (result != null) {
            long l2 = System.currentTimeMillis();
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Found barcode in ");
            stringBuilder.append(l2 - l);
            stringBuilder.append(" ms");
            Log.d((String)string2, (String)stringBuilder.toString());
            if (this.resultHandler != null) {
                BarcodeResult barcodeResult = new BarcodeResult(result, sourceData);
                Message message = Message.obtain((Handler)this.resultHandler, (int)R.id.zxing_decode_succeeded, (Object)barcodeResult);
                message.setData(new Bundle());
                message.sendToTarget();
            }
        } else {
            Handler handler = this.resultHandler;
            if (handler != null) {
                Message.obtain((Handler)handler, (int)R.id.zxing_decode_failed).sendToTarget();
            }
        }
        if (this.resultHandler != null) {
            List<ResultPoint> list = this.decoder.getPossibleResultPoints();
            Message.obtain((Handler)this.resultHandler, (int)R.id.zxing_possible_result_points, list).sendToTarget();
        }
        this.requestNextPreview();
    }

    private void requestNextPreview() {
        this.cameraInstance.requestPreview(this.previewCallback);
    }

    protected LuminanceSource createSource(SourceData sourceData) {
        if (this.cropRect == null) {
            return null;
        }
        return sourceData.createSource();
    }

    public Rect getCropRect() {
        return this.cropRect;
    }

    public Decoder getDecoder() {
        return this.decoder;
    }

    public void setCropRect(Rect rect) {
        this.cropRect = rect;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public void start() {
        HandlerThread handlerThread;
        Util.validateMainThread();
        this.thread = handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.handler = new Handler(this.thread.getLooper(), this.callback);
        this.running = true;
        this.requestNextPreview();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() {
        Object object;
        Util.validateMainThread();
        Object object2 = object = this.LOCK;
        synchronized (object2) {
            this.running = false;
            this.handler.removeCallbacksAndMessages(null);
            this.thread.quit();
            return;
        }
    }

}

