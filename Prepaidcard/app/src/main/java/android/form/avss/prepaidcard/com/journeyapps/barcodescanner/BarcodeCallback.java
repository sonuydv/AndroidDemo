/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.zxing.ResultPoint
 *  java.lang.Object
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeResult;
import java.util.List;

public interface BarcodeCallback {
    public void barcodeResult(BarcodeResult var1);

    public void possibleResultPoints(List<ResultPoint> var1);
}

