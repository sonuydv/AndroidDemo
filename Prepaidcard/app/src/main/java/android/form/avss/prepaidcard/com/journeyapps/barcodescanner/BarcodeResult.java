/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  com.google.zxing.BarcodeFormat
 *  com.google.zxing.Result
 *  com.google.zxing.ResultMetadataType
 *  com.google.zxing.ResultPoint
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.SourceData;
import java.util.Map;

public class BarcodeResult {
    private static final float PREVIEW_DOT_WIDTH = 10.0f;
    private static final float PREVIEW_LINE_WIDTH = 4.0f;
    protected Result mResult;
    private final int mScaleFactor = 2;
    protected SourceData sourceData;

    public BarcodeResult(Result result, SourceData sourceData) {
        this.mResult = result;
        this.sourceData = sourceData;
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint resultPoint, ResultPoint resultPoint2, int n) {
        if (resultPoint != null && resultPoint2 != null) {
            canvas.drawLine(resultPoint.getX() / (float)n, resultPoint.getY() / (float)n, resultPoint2.getX() / (float)n, resultPoint2.getY() / (float)n, paint);
        }
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.mResult.getBarcodeFormat();
    }

    public Bitmap getBitmap() {
        return this.sourceData.getBitmap(2);
    }

    public int getBitmapScaleFactor() {
        return 2;
    }

    public Bitmap getBitmapWithResultPoints(int n) {
        Bitmap bitmap;
        Bitmap bitmap2 = bitmap = this.getBitmap();
        ResultPoint[] arrresultPoint = this.mResult.getResultPoints();
        if (arrresultPoint != null && arrresultPoint.length > 0 && bitmap != null) {
            bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
            Paint paint = new Paint();
            paint.setColor(n);
            int n2 = arrresultPoint.length;
            if (n2 == 2) {
                paint.setStrokeWidth(4.0f);
                BarcodeResult.drawLine(canvas, paint, arrresultPoint[0], arrresultPoint[1], 2);
                return bitmap2;
            }
            if (arrresultPoint.length == 4 && (this.mResult.getBarcodeFormat() == BarcodeFormat.UPC_A || this.mResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
                BarcodeResult.drawLine(canvas, paint, arrresultPoint[0], arrresultPoint[1], 2);
                BarcodeResult.drawLine(canvas, paint, arrresultPoint[2], arrresultPoint[3], 2);
                return bitmap2;
            }
            paint.setStrokeWidth(10.0f);
            for (ResultPoint resultPoint : arrresultPoint) {
                if (resultPoint == null) continue;
                canvas.drawPoint(resultPoint.getX() / 2.0f, resultPoint.getY() / 2.0f, paint);
            }
        }
        return bitmap2;
    }

    public byte[] getRawBytes() {
        return this.mResult.getRawBytes();
    }

    public Result getResult() {
        return this.mResult;
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.mResult.getResultMetadata();
    }

    public ResultPoint[] getResultPoints() {
        return this.mResult.getResultPoints();
    }

    public String getText() {
        return this.mResult.getText();
    }

    public long getTimestamp() {
        return this.mResult.getTimestamp();
    }

    public String toString() {
        return this.mResult.getText();
    }
}

