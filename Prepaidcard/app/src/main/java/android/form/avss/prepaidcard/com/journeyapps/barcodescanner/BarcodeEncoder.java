/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  com.google.zxing.BarcodeFormat
 *  com.google.zxing.EncodeHintType
 *  com.google.zxing.MultiFormatWriter
 *  com.google.zxing.WriterException
 *  com.google.zxing.common.BitMatrix
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.util.Map
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public class BarcodeEncoder {
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;

    public Bitmap createBitmap(BitMatrix bitMatrix) {
        int n = bitMatrix.getWidth();
        int n2 = bitMatrix.getHeight();
        int[] arrn = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            int n3 = i * n;
            for (int j = 0; j < n; ++j) {
                int n4 = n3 + j;
                int n5 = bitMatrix.get(j, i) ? -16777216 : -1;
                arrn[n4] = n5;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        bitmap.setPixels(arrn, 0, n, 0, 0, n, n2);
        return bitmap;
    }

    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(string2, barcodeFormat, n, n2);
            return bitMatrix;
        }
        catch (Exception exception) {
            throw new WriterException((Throwable)exception);
        }
        catch (WriterException writerException) {
            throw writerException;
        }
    }

    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(string2, barcodeFormat, n, n2, map);
            return bitMatrix;
        }
        catch (Exception exception) {
            throw new WriterException((Throwable)exception);
        }
        catch (WriterException writerException) {
            throw writerException;
        }
    }

    public Bitmap encodeBitmap(String string2, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.createBitmap(this.encode(string2, barcodeFormat, n, n2));
    }

    public Bitmap encodeBitmap(String string2, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        return this.createBitmap(this.encode(string2, barcodeFormat, n, n2, map));
    }
}

