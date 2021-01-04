/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.ResultPointCallback
 *  java.lang.Object
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.journeyapps.barcodescanner.Decoder;

public class DecoderResultPointCallback
implements ResultPointCallback {
    private Decoder decoder;

    public DecoderResultPointCallback() {
    }

    public DecoderResultPointCallback(Decoder decoder) {
        this.decoder = decoder;
    }

    public void foundPossibleResultPoint(ResultPoint resultPoint) {
        Decoder decoder = this.decoder;
        if (decoder != null) {
            decoder.foundPossibleResultPoint(resultPoint);
        }
    }

    public Decoder getDecoder() {
        return this.decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }
}

