/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 *  android.util.AttributeSet
 *  com.google.zxing.DecodeHintType
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$id
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.Decoder;
import com.journeyapps.barcodescanner.DecoderFactory;
import com.journeyapps.barcodescanner.DecoderResultPointCallback;
import com.journeyapps.barcodescanner.DecoderThread;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.Util;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarcodeView
extends CameraPreview {
    private BarcodeCallback callback = null;
    private DecodeMode decodeMode = DecodeMode.NONE;
    private DecoderFactory decoderFactory;
    private DecoderThread decoderThread;
    private final Handler.Callback resultCallback = new Handler.Callback(){

        public boolean handleMessage(Message message) {
            if (message.what == R.id.zxing_decode_succeeded) {
                BarcodeResult barcodeResult = (BarcodeResult)message.obj;
                if (barcodeResult != null && BarcodeView.this.callback != null && BarcodeView.this.decodeMode != DecodeMode.NONE) {
                    BarcodeView.this.callback.barcodeResult(barcodeResult);
                    if (BarcodeView.this.decodeMode == DecodeMode.SINGLE) {
                        BarcodeView.this.stopDecoding();
                    }
                }
                return true;
            }
            if (message.what == R.id.zxing_decode_failed) {
                return true;
            }
            if (message.what == R.id.zxing_possible_result_points) {
                List list = (List)message.obj;
                if (BarcodeView.this.callback != null && BarcodeView.this.decodeMode != DecodeMode.NONE) {
                    BarcodeView.this.callback.possibleResultPoints((List<ResultPoint>)list);
                }
                return true;
            }
            return false;
        }
    };
    private Handler resultHandler;

    public BarcodeView(Context context) {
        super(context);
        this.initialize();
    }

    public BarcodeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize();
    }

    public BarcodeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initialize();
    }

    private Decoder createDecoder() {
        if (this.decoderFactory == null) {
            this.decoderFactory = this.createDefaultDecoderFactory();
        }
        DecoderResultPointCallback decoderResultPointCallback = new DecoderResultPointCallback();
        HashMap hashMap = new HashMap();
        hashMap.put((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK, (Object)decoderResultPointCallback);
        Decoder decoder = this.decoderFactory.createDecoder((Map<DecodeHintType, ?>)hashMap);
        decoderResultPointCallback.setDecoder(decoder);
        return decoder;
    }

    private void initialize() {
        this.decoderFactory = new DefaultDecoderFactory();
        this.resultHandler = new Handler(this.resultCallback);
    }

    private void startDecoderThread() {
        this.stopDecoderThread();
        if (this.decodeMode != DecodeMode.NONE && this.isPreviewActive()) {
            DecoderThread decoderThread;
            this.decoderThread = decoderThread = new DecoderThread(this.getCameraInstance(), this.createDecoder(), this.resultHandler);
            decoderThread.setCropRect(this.getPreviewFramingRect());
            this.decoderThread.start();
        }
    }

    private void stopDecoderThread() {
        DecoderThread decoderThread = this.decoderThread;
        if (decoderThread != null) {
            decoderThread.stop();
            this.decoderThread = null;
        }
    }

    protected DecoderFactory createDefaultDecoderFactory() {
        return new DefaultDecoderFactory();
    }

    public void decodeContinuous(BarcodeCallback barcodeCallback) {
        this.decodeMode = DecodeMode.CONTINUOUS;
        this.callback = barcodeCallback;
        this.startDecoderThread();
    }

    public void decodeSingle(BarcodeCallback barcodeCallback) {
        this.decodeMode = DecodeMode.SINGLE;
        this.callback = barcodeCallback;
        this.startDecoderThread();
    }

    public DecoderFactory getDecoderFactory() {
        return this.decoderFactory;
    }

    @Override
    public void pause() {
        this.stopDecoderThread();
        super.pause();
    }

    @Override
    protected void previewStarted() {
        super.previewStarted();
        this.startDecoderThread();
    }

    public void setDecoderFactory(DecoderFactory decoderFactory) {
        Util.validateMainThread();
        this.decoderFactory = decoderFactory;
        DecoderThread decoderThread = this.decoderThread;
        if (decoderThread != null) {
            decoderThread.setDecoder(this.createDecoder());
        }
    }

    public void stopDecoding() {
        this.decodeMode = DecodeMode.NONE;
        this.callback = null;
        this.stopDecoderThread();
    }

    private static final class DecodeMode
    extends Enum<DecodeMode> {
        private static final /* synthetic */ DecodeMode[] $VALUES;
        public static final /* enum */ DecodeMode CONTINUOUS;
        public static final /* enum */ DecodeMode NONE;
        public static final /* enum */ DecodeMode SINGLE;

        static {
            DecodeMode decodeMode;
            NONE = new DecodeMode();
            SINGLE = new DecodeMode();
            CONTINUOUS = decodeMode = new DecodeMode();
            DecodeMode[] arrdecodeMode = new DecodeMode[]{NONE, SINGLE, decodeMode};
            $VALUES = arrdecodeMode;
        }

        public static DecodeMode valueOf(String string2) {
            return (DecodeMode)Enum.valueOf(DecodeMode.class, (String)string2);
        }

        public static DecodeMode[] values() {
            return (DecodeMode[])$VALUES.clone();
        }
    }

}

