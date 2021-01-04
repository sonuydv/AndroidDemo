/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.zxing.DecodeHintType
 *  java.lang.Object
 *  java.util.Map
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import com.google.zxing.DecodeHintType;
import com.journeyapps.barcodescanner.Decoder;
import java.util.Map;

public interface DecoderFactory {
    public Decoder createDecoder(Map<DecodeHintType, ?> var1);
}

