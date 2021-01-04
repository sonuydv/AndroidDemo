/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.YuvImage
 *  com.google.zxing.PlanarYUVLuminanceSource
 *  java.io.ByteArrayOutputStream
 *  java.io.OutputStream
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.google.zxing.PlanarYUVLuminanceSource;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class SourceData {
    private Rect cropRect;
    private byte[] data;
    private int dataHeight;
    private int dataWidth;
    private int imageFormat;
    private int rotation;

    public SourceData(byte[] arrby, int n, int n2, int n3, int n4) {
        this.data = arrby;
        this.dataWidth = n;
        this.dataHeight = n2;
        this.rotation = n4;
        this.imageFormat = n3;
        if (n * n2 <= arrby.length) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Image data does not match the resolution. ");
        stringBuilder.append(n);
        stringBuilder.append("x");
        stringBuilder.append(n2);
        stringBuilder.append(" > ");
        stringBuilder.append(arrby.length);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private Bitmap getBitmap(Rect rect, int n) {
        if (this.isRotated()) {
            rect = new Rect(rect.top, rect.left, rect.bottom, rect.right);
        }
        YuvImage yuvImage = new YuvImage(this.data, this.imageFormat, this.dataWidth, this.dataHeight, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(rect, 90, (OutputStream)byteArrayOutputStream);
        byte[] arrby = byteArrayOutputStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = n;
        Bitmap bitmap = BitmapFactory.decodeByteArray((byte[])arrby, (int)0, (int)arrby.length, (BitmapFactory.Options)options);
        if (this.rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate((float)this.rotation);
            int n2 = bitmap.getWidth();
            int n3 = bitmap.getHeight();
            bitmap = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)n2, (int)n3, (Matrix)matrix, (boolean)false);
        }
        return bitmap;
    }

    public static byte[] rotate180(byte[] arrby, int n, int n2) {
        int n3 = n * n2;
        byte[] arrby2 = new byte[n3];
        int n4 = n3 - 1;
        for (int i = 0; i < n3; ++i) {
            arrby2[n4] = arrby[i];
            --n4;
        }
        return arrby2;
    }

    public static byte[] rotateCCW(byte[] arrby, int n, int n2) {
        int n3 = n * n2;
        byte[] arrby2 = new byte[n3];
        int n4 = n3 - 1;
        for (int i = 0; i < n; ++i) {
            for (int j = n2 - 1; j >= 0; --j) {
                arrby2[n4] = arrby[i + j * n];
                --n4;
            }
        }
        return arrby2;
    }

    public static byte[] rotateCW(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n * n2];
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = n2 - 1; j >= 0; --j) {
                arrby2[n3] = arrby[i + j * n];
                ++n3;
            }
        }
        return arrby2;
    }

    public static byte[] rotateCameraPreview(int n, byte[] arrby, int n2, int n3) {
        if (n != 90) {
            if (n != 180) {
                if (n != 270) {
                    return arrby;
                }
                return SourceData.rotateCCW(arrby, n2, n3);
            }
            return SourceData.rotate180(arrby, n2, n3);
        }
        return SourceData.rotateCW(arrby, n2, n3);
    }

    public PlanarYUVLuminanceSource createSource() {
        byte[] arrby = SourceData.rotateCameraPreview(this.rotation, this.data, this.dataWidth, this.dataHeight);
        if (this.isRotated()) {
            PlanarYUVLuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(arrby, this.dataHeight, this.dataWidth, this.cropRect.left, this.cropRect.top, this.cropRect.width(), this.cropRect.height(), false);
            return planarYUVLuminanceSource;
        }
        PlanarYUVLuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(arrby, this.dataWidth, this.dataHeight, this.cropRect.left, this.cropRect.top, this.cropRect.width(), this.cropRect.height(), false);
        return planarYUVLuminanceSource;
    }

    public Bitmap getBitmap() {
        return this.getBitmap(1);
    }

    public Bitmap getBitmap(int n) {
        return this.getBitmap(this.cropRect, n);
    }

    public Rect getCropRect() {
        return this.cropRect;
    }

    public byte[] getData() {
        return this.data;
    }

    public int getDataHeight() {
        return this.dataHeight;
    }

    public int getDataWidth() {
        return this.dataWidth;
    }

    public int getImageFormat() {
        return this.imageFormat;
    }

    public boolean isRotated() {
        return this.rotation % 180 != 0;
    }

    public void setCropRect(Rect rect) {
        this.cropRect = rect;
    }
}

