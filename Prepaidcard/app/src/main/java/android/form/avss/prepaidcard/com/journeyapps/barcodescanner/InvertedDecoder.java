/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.zxing.Binarizer
 *  com.google.zxing.BinaryBitmap
 *  com.google.zxing.LuminanceSource
 *  com.google.zxing.Reader
 *  com.google.zxing.common.HybridBinarizer
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.Decoder;

public class InvertedDecoder
extends Decoder {
    public InvertedDecoder(Reader reader) {
        super(reader);
    }

    @Override
    protected BinaryBitmap toBitmap(LuminanceSource luminanceSource) {
        return new BinaryBitmap((Binarizer)new HybridBinarizer(luminanceSource.invert()));
    }
}

