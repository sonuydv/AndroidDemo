/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$color
 *  com.google.zxing.client.android.R$styleable
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.CameraPreview;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewfinderView
extends View {
    protected static final long ANIMATION_DELAY = 80L;
    protected static final int CURRENT_POINT_OPACITY = 160;
    protected static final int MAX_RESULT_POINTS = 20;
    protected static final int POINT_SIZE = 6;
    protected static final int[] SCANNER_ALPHA;
    protected static final String TAG;
    protected CameraPreview cameraPreview;
    protected Rect framingRect;
    protected final int laserColor;
    protected List<ResultPoint> lastPossibleResultPoints;
    protected final int maskColor;
    protected final Paint paint = new Paint(1);
    protected List<ResultPoint> possibleResultPoints;
    protected Rect previewFramingRect;
    protected Bitmap resultBitmap;
    protected final int resultColor;
    protected final int resultPointColor;
    protected int scannerAlpha;

    static {
        TAG = ViewfinderView.class.getSimpleName();
        SCANNER_ALPHA = new int[]{0, 64, 128, 192, 255, 192, 128, 64};
    }

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = this.getResources();
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.zxing_finder);
        this.maskColor = typedArray.getColor(R.styleable.zxing_finder_zxing_viewfinder_mask, resources.getColor(R.color.zxing_viewfinder_mask));
        this.resultColor = typedArray.getColor(R.styleable.zxing_finder_zxing_result_view, resources.getColor(R.color.zxing_result_view));
        this.laserColor = typedArray.getColor(R.styleable.zxing_finder_zxing_viewfinder_laser, resources.getColor(R.color.zxing_viewfinder_laser));
        this.resultPointColor = typedArray.getColor(R.styleable.zxing_finder_zxing_possible_result_points, resources.getColor(R.color.zxing_possible_result_points));
        typedArray.recycle();
        this.scannerAlpha = 0;
        this.possibleResultPoints = new ArrayList(20);
        this.lastPossibleResultPoints = new ArrayList(20);
    }

    public void addPossibleResultPoint(ResultPoint resultPoint) {
        if (this.possibleResultPoints.size() < 20) {
            this.possibleResultPoints.add((Object)resultPoint);
        }
    }

    public void drawResultBitmap(Bitmap bitmap) {
        this.resultBitmap = bitmap;
        this.invalidate();
    }

    public void drawViewfinder() {
        Bitmap bitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.invalidate();
    }

    public void onDraw(Canvas canvas) {
        this.refreshSizes();
        if (this.framingRect != null) {
            if (this.previewFramingRect == null) {
                return;
            }
            Rect rect = this.framingRect;
            Rect rect2 = this.previewFramingRect;
            int n = canvas.getWidth();
            int n2 = canvas.getHeight();
            Paint paint = this.paint;
            int n3 = this.resultBitmap != null ? this.resultColor : this.maskColor;
            paint.setColor(n3);
            canvas.drawRect(0.0f, 0.0f, (float)n, (float)rect.top, this.paint);
            canvas.drawRect(0.0f, (float)rect.top, (float)rect.left, (float)(1 + rect.bottom), this.paint);
            canvas.drawRect((float)(1 + rect.right), (float)rect.top, (float)n, (float)(1 + rect.bottom), this.paint);
            canvas.drawRect(0.0f, (float)(1 + rect.bottom), (float)n, (float)n2, this.paint);
            if (this.resultBitmap != null) {
                this.paint.setAlpha(160);
                canvas.drawBitmap(this.resultBitmap, null, rect, this.paint);
                return;
            }
            this.paint.setColor(this.laserColor);
            this.paint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
            this.scannerAlpha = (1 + this.scannerAlpha) % SCANNER_ALPHA.length;
            int n4 = rect.height() / 2 + rect.top;
            canvas.drawRect((float)(2 + rect.left), (float)(n4 - 1), (float)(-1 + rect.right), (float)(n4 + 2), this.paint);
            float f = (float)rect.width() / (float)rect2.width();
            float f2 = (float)rect.height() / (float)rect2.height();
            int n5 = rect.left;
            int n6 = rect.top;
            if (!this.lastPossibleResultPoints.isEmpty()) {
                this.paint.setAlpha(80);
                this.paint.setColor(this.resultPointColor);
                Iterator iterator = this.lastPossibleResultPoints.iterator();
                while (iterator.hasNext()) {
                    ResultPoint resultPoint = (ResultPoint)iterator.next();
                    float f3 = n5 + (int)(f * resultPoint.getX());
                    float f4 = n6 + (int)(f2 * resultPoint.getY());
                    Iterator iterator2 = iterator;
                    canvas.drawCircle(f3, f4, 3.0f, this.paint);
                    iterator = iterator2;
                }
                this.lastPossibleResultPoints.clear();
            }
            if (!this.possibleResultPoints.isEmpty()) {
                this.paint.setAlpha(160);
                this.paint.setColor(this.resultPointColor);
                Iterator iterator = this.possibleResultPoints.iterator();
                while (iterator.hasNext()) {
                    ResultPoint resultPoint = (ResultPoint)iterator.next();
                    float f5 = n5 + (int)(f * resultPoint.getX());
                    float f6 = n6 + (int)(f2 * resultPoint.getY());
                    Iterator iterator3 = iterator;
                    canvas.drawCircle(f5, f6, 6.0f, this.paint);
                    iterator = iterator3;
                }
                List<ResultPoint> list = this.possibleResultPoints;
                List<ResultPoint> list2 = this.lastPossibleResultPoints;
                this.possibleResultPoints = list2;
                this.lastPossibleResultPoints = list;
                list2.clear();
            }
            this.postInvalidateDelayed(80L, -6 + rect.left, -6 + rect.top, 6 + rect.right, 6 + rect.bottom);
            return;
        }
    }

    protected void refreshSizes() {
        CameraPreview cameraPreview = this.cameraPreview;
        if (cameraPreview == null) {
            return;
        }
        Rect rect = cameraPreview.getFramingRect();
        Rect rect2 = this.cameraPreview.getPreviewFramingRect();
        if (rect != null && rect2 != null) {
            this.framingRect = rect;
            this.previewFramingRect = rect2;
        }
    }

    public void setCameraPreview(CameraPreview cameraPreview) {
        this.cameraPreview = cameraPreview;
        cameraPreview.addStateListener(new CameraPreview.StateListener(){

            @Override
            public void cameraClosed() {
            }

            @Override
            public void cameraError(Exception exception) {
            }

            @Override
            public void previewSized() {
                ViewfinderView.this.refreshSizes();
                ViewfinderView.this.invalidate();
            }

            @Override
            public void previewStarted() {
            }

            @Override
            public void previewStopped() {
            }
        });
    }

}

