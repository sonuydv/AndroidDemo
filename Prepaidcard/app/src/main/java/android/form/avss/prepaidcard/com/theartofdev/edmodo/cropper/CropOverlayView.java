/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.View
 *  android.view.ViewParent
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.Arrays
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import com.theartofdev.edmodo.cropper.BitmapUtils;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropWindowHandler;
import com.theartofdev.edmodo.cropper.CropWindowMoveHandler;
import java.util.Arrays;

public class CropOverlayView
extends View {
    private boolean initializedCropWindow;
    private int mAspectRatioX;
    private int mAspectRatioY;
    private Paint mBackgroundPaint;
    private float mBorderCornerLength;
    private float mBorderCornerOffset;
    private Paint mBorderCornerPaint;
    private Paint mBorderPaint;
    private final float[] mBoundsPoints = new float[8];
    private final RectF mCalcBounds = new RectF();
    private CropImageView.CropShape mCropShape;
    private CropWindowChangeListener mCropWindowChangeListener;
    private final CropWindowHandler mCropWindowHandler = new CropWindowHandler();
    private final RectF mDrawRect = new RectF();
    private boolean mFixAspectRatio;
    private Paint mGuidelinePaint;
    private CropImageView.Guidelines mGuidelines;
    private float mInitialCropWindowPaddingRatio;
    private final Rect mInitialCropWindowRect = new Rect();
    private CropWindowMoveHandler mMoveHandler;
    private boolean mMultiTouchEnabled;
    private Integer mOriginalLayerType;
    private Path mPath = new Path();
    private ScaleGestureDetector mScaleDetector;
    private float mSnapRadius;
    private float mTargetAspectRatio = (float)this.mAspectRatioX / (float)this.mAspectRatioY;
    private float mTouchRadius;
    private int mViewHeight;
    private int mViewWidth;

    public CropOverlayView(Context context) {
        this(context, null);
    }

    public CropOverlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean calculateBounds(RectF rectF) {
        float f = BitmapUtils.getRectLeft(this.mBoundsPoints);
        float f2 = BitmapUtils.getRectTop(this.mBoundsPoints);
        float f3 = BitmapUtils.getRectRight(this.mBoundsPoints);
        float f4 = BitmapUtils.getRectBottom(this.mBoundsPoints);
        if (!this.isNonStraightAngleRotated()) {
            this.mCalcBounds.set(f, f2, f3, f4);
            return false;
        }
        float[] arrf = this.mBoundsPoints;
        float f5 = arrf[0];
        float f6 = arrf[1];
        float f7 = arrf[4];
        float f8 = arrf[5];
        float f9 = arrf[6];
        float f10 = arrf[7];
        if (arrf[7] < arrf[1]) {
            if (arrf[1] < arrf[3]) {
                f5 = arrf[6];
                f6 = arrf[7];
                f7 = arrf[2];
                f8 = arrf[3];
                f9 = arrf[4];
                f10 = arrf[5];
            } else {
                f5 = arrf[4];
                f6 = arrf[5];
                f7 = arrf[0];
                f8 = arrf[1];
                f9 = arrf[2];
                f10 = arrf[3];
            }
        } else if (arrf[1] > arrf[3]) {
            f5 = arrf[2];
            f6 = arrf[3];
            f7 = arrf[6];
            f8 = arrf[7];
            f9 = arrf[0];
            f10 = arrf[1];
        }
        float f11 = (f10 - f6) / (f9 - f5);
        float f12 = -1.0f / f11;
        float f13 = f6 - f11 * f5;
        float f14 = f6 - f12 * f5;
        float f15 = f8 - f11 * f7;
        float f16 = f8 - f12 * f7;
        float f17 = rectF.centerY() - rectF.top;
        float f18 = rectF.centerX();
        float f19 = f17 / (f18 - rectF.left);
        float f20 = -f19;
        float f21 = rectF.top;
        float f22 = f21 - f19 * rectF.left;
        float f23 = rectF.top;
        float f24 = f23 - f20 * rectF.right;
        float f25 = (f22 - f13) / (f11 - f19) < rectF.right ? (f22 - f13) / (f11 - f19) : f;
        float f26 = Math.max((float)f, (float)f25);
        float f27 = (f22 - f14) / (f12 - f19) < rectF.right ? (f22 - f14) / (f12 - f19) : f26;
        float f28 = Math.max((float)f26, (float)f27);
        float f29 = (f24 - f16) / (f12 - f20) < rectF.right ? (f24 - f16) / (f12 - f20) : f28;
        float f30 = Math.max((float)f28, (float)f29);
        float f31 = (f24 - f14) / (f12 - f20) > rectF.left ? (f24 - f14) / (f12 - f20) : f3;
        float f32 = Math.min((float)f3, (float)f31);
        float f33 = (f24 - f15) / (f11 - f20) > rectF.left ? (f24 - f15) / (f11 - f20) : f32;
        float f34 = Math.min((float)f32, (float)f33);
        float f35 = (f22 - f15) / (f11 - f19) > rectF.left ? (f22 - f15) / (f11 - f19) : f34;
        float f36 = Math.min((float)f34, (float)f35);
        float f37 = Math.max((float)f2, (float)Math.max((float)(f13 + f11 * f30), (float)(f14 + f12 * f36)));
        float f38 = Math.min((float)f4, (float)Math.min((float)(f16 + f12 * f30), (float)(f15 + f11 * f36)));
        this.mCalcBounds.left = f30;
        this.mCalcBounds.top = f37;
        this.mCalcBounds.right = f36;
        this.mCalcBounds.bottom = f38;
        return true;
    }

    private void callOnCropWindowChanged(boolean bl) {
        try {
            if (this.mCropWindowChangeListener != null) {
                this.mCropWindowChangeListener.onCropWindowChanged(bl);
            }
            return;
        }
        catch (Exception exception) {
            Log.e((String)"AIC", (String)"Exception in crop window changed", (Throwable)exception);
            return;
        }
    }

    private void drawBackground(Canvas canvas) {
        RectF rectF = this.mCropWindowHandler.getRect();
        float f = Math.max((float)BitmapUtils.getRectLeft(this.mBoundsPoints), (float)0.0f);
        float f2 = Math.max((float)BitmapUtils.getRectTop(this.mBoundsPoints), (float)0.0f);
        float f3 = Math.min((float)BitmapUtils.getRectRight(this.mBoundsPoints), (float)this.getWidth());
        float f4 = Math.min((float)BitmapUtils.getRectBottom(this.mBoundsPoints), (float)this.getHeight());
        if (this.mCropShape == CropImageView.CropShape.RECTANGLE) {
            if (this.isNonStraightAngleRotated() && Build.VERSION.SDK_INT > 17) {
                this.mPath.reset();
                Path path = this.mPath;
                float[] arrf = this.mBoundsPoints;
                path.moveTo(arrf[0], arrf[1]);
                Path path2 = this.mPath;
                float[] arrf2 = this.mBoundsPoints;
                path2.lineTo(arrf2[2], arrf2[3]);
                Path path3 = this.mPath;
                float[] arrf3 = this.mBoundsPoints;
                path3.lineTo(arrf3[4], arrf3[5]);
                Path path4 = this.mPath;
                float[] arrf4 = this.mBoundsPoints;
                path4.lineTo(arrf4[6], arrf4[7]);
                this.mPath.close();
                canvas.save();
                canvas.clipPath(this.mPath, Region.Op.INTERSECT);
                canvas.clipRect(rectF, Region.Op.XOR);
                canvas.drawRect(f, f2, f3, f4, this.mBackgroundPaint);
                canvas.restore();
                return;
            }
            canvas.drawRect(f, f2, f3, rectF.top, this.mBackgroundPaint);
            canvas.drawRect(f, rectF.bottom, f3, f4, this.mBackgroundPaint);
            canvas.drawRect(f, rectF.top, rectF.left, rectF.bottom, this.mBackgroundPaint);
            canvas.drawRect(rectF.right, rectF.top, f3, rectF.bottom, this.mBackgroundPaint);
            return;
        }
        this.mPath.reset();
        if (Build.VERSION.SDK_INT <= 17 && this.mCropShape == CropImageView.CropShape.OVAL) {
            this.mDrawRect.set(2.0f + rectF.left, 2.0f + rectF.top, rectF.right - 2.0f, rectF.bottom - 2.0f);
        } else {
            this.mDrawRect.set(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
        this.mPath.addOval(this.mDrawRect, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(this.mPath, Region.Op.XOR);
        canvas.drawRect(f, f2, f3, f4, this.mBackgroundPaint);
        canvas.restore();
    }

    private void drawBorders(Canvas canvas) {
        Paint paint = this.mBorderPaint;
        if (paint != null) {
            float f = paint.getStrokeWidth();
            RectF rectF = this.mCropWindowHandler.getRect();
            rectF.inset(f / 2.0f, f / 2.0f);
            if (this.mCropShape == CropImageView.CropShape.RECTANGLE) {
                canvas.drawRect(rectF, this.mBorderPaint);
                return;
            }
            canvas.drawOval(rectF, this.mBorderPaint);
        }
    }

    private void drawCorners(Canvas canvas) {
        if (this.mBorderCornerPaint != null) {
            Paint paint = this.mBorderPaint;
            float f = paint != null ? paint.getStrokeWidth() : 0.0f;
            float f2 = this.mBorderCornerPaint.getStrokeWidth();
            float f3 = f2 / 2.0f;
            CropImageView.CropShape cropShape = this.mCropShape;
            CropImageView.CropShape cropShape2 = CropImageView.CropShape.RECTANGLE;
            float f4 = 0.0f;
            if (cropShape == cropShape2) {
                f4 = this.mBorderCornerOffset;
            }
            float f5 = f3 + f4;
            RectF rectF = this.mCropWindowHandler.getRect();
            rectF.inset(f5, f5);
            float f6 = (f2 - f) / 2.0f;
            float f7 = f6 + f2 / 2.0f;
            canvas.drawLine(rectF.left - f6, rectF.top - f7, rectF.left - f6, rectF.top + this.mBorderCornerLength, this.mBorderCornerPaint);
            canvas.drawLine(rectF.left - f7, rectF.top - f6, rectF.left + this.mBorderCornerLength, rectF.top - f6, this.mBorderCornerPaint);
            canvas.drawLine(f6 + rectF.right, rectF.top - f7, f6 + rectF.right, rectF.top + this.mBorderCornerLength, this.mBorderCornerPaint);
            canvas.drawLine(f7 + rectF.right, rectF.top - f6, rectF.right - this.mBorderCornerLength, rectF.top - f6, this.mBorderCornerPaint);
            canvas.drawLine(rectF.left - f6, f7 + rectF.bottom, rectF.left - f6, rectF.bottom - this.mBorderCornerLength, this.mBorderCornerPaint);
            canvas.drawLine(rectF.left - f7, f6 + rectF.bottom, rectF.left + this.mBorderCornerLength, f6 + rectF.bottom, this.mBorderCornerPaint);
            canvas.drawLine(f6 + rectF.right, f7 + rectF.bottom, f6 + rectF.right, rectF.bottom - this.mBorderCornerLength, this.mBorderCornerPaint);
            canvas.drawLine(f7 + rectF.right, f6 + rectF.bottom, rectF.right - this.mBorderCornerLength, f6 + rectF.bottom, this.mBorderCornerPaint);
        }
    }

    private void drawGuidelines(Canvas canvas) {
        if (this.mGuidelinePaint != null) {
            Paint paint = this.mBorderPaint;
            float f = paint != null ? paint.getStrokeWidth() : 0.0f;
            RectF rectF = this.mCropWindowHandler.getRect();
            rectF.inset(f, f);
            float f2 = rectF.width() / 3.0f;
            float f3 = rectF.height() / 3.0f;
            if (this.mCropShape == CropImageView.CropShape.OVAL) {
                float f4 = rectF.width() / 2.0f - f;
                float f5 = rectF.height() / 2.0f - f;
                float f6 = f2 + rectF.left;
                float f7 = rectF.right - f2;
                float f8 = (float)((double)f5 * Math.sin((double)Math.acos((double)((f4 - f2) / f4))));
                canvas.drawLine(f6, f5 + rectF.top - f8, f6, f8 + (rectF.bottom - f5), this.mGuidelinePaint);
                canvas.drawLine(f7, f5 + rectF.top - f8, f7, f8 + (rectF.bottom - f5), this.mGuidelinePaint);
                float f9 = f3 + rectF.top;
                float f10 = rectF.bottom - f3;
                float f11 = (float)((double)f4 * Math.cos((double)Math.asin((double)((f5 - f3) / f5))));
                canvas.drawLine(f4 + rectF.left - f11, f9, f11 + (rectF.right - f4), f9, this.mGuidelinePaint);
                canvas.drawLine(f4 + rectF.left - f11, f10, f11 + (rectF.right - f4), f10, this.mGuidelinePaint);
                return;
            }
            float f12 = f2 + rectF.left;
            float f13 = rectF.right - f2;
            canvas.drawLine(f12, rectF.top, f12, rectF.bottom, this.mGuidelinePaint);
            canvas.drawLine(f13, rectF.top, f13, rectF.bottom, this.mGuidelinePaint);
            float f14 = f3 + rectF.top;
            float f15 = rectF.bottom - f3;
            canvas.drawLine(rectF.left, f14, rectF.right, f14, this.mGuidelinePaint);
            canvas.drawLine(rectF.left, f15, rectF.right, f15, this.mGuidelinePaint);
        }
    }

    private void fixCropWindowRectByRules(RectF rectF) {
        if (rectF.width() < this.mCropWindowHandler.getMinCropWidth()) {
            float f = (this.mCropWindowHandler.getMinCropWidth() - rectF.width()) / 2.0f;
            rectF.left -= f;
            rectF.right = f + rectF.right;
        }
        if (rectF.height() < this.mCropWindowHandler.getMinCropHeight()) {
            float f = (this.mCropWindowHandler.getMinCropHeight() - rectF.height()) / 2.0f;
            rectF.top -= f;
            rectF.bottom = f + rectF.bottom;
        }
        if (rectF.width() > this.mCropWindowHandler.getMaxCropWidth()) {
            float f = (rectF.width() - this.mCropWindowHandler.getMaxCropWidth()) / 2.0f;
            rectF.left = f + rectF.left;
            rectF.right -= f;
        }
        if (rectF.height() > this.mCropWindowHandler.getMaxCropHeight()) {
            float f = (rectF.height() - this.mCropWindowHandler.getMaxCropHeight()) / 2.0f;
            rectF.top = f + rectF.top;
            rectF.bottom -= f;
        }
        this.calculateBounds(rectF);
        if (this.mCalcBounds.width() > 0.0f && this.mCalcBounds.height() > 0.0f) {
            float f = Math.max((float)this.mCalcBounds.left, (float)0.0f);
            float f2 = Math.max((float)this.mCalcBounds.top, (float)0.0f);
            float f3 = Math.min((float)this.mCalcBounds.right, (float)this.getWidth());
            float f4 = Math.min((float)this.mCalcBounds.bottom, (float)this.getHeight());
            if (rectF.left < f) {
                rectF.left = f;
            }
            if (rectF.top < f2) {
                rectF.top = f2;
            }
            if (rectF.right > f3) {
                rectF.right = f3;
            }
            if (rectF.bottom > f4) {
                rectF.bottom = f4;
            }
        }
        if (this.mFixAspectRatio && (double)Math.abs((float)(rectF.width() - rectF.height() * this.mTargetAspectRatio)) > 0.1) {
            if (rectF.width() > rectF.height() * this.mTargetAspectRatio) {
                float f = Math.abs((float)(rectF.height() * this.mTargetAspectRatio - rectF.width())) / 2.0f;
                rectF.left = f + rectF.left;
                rectF.right -= f;
                return;
            }
            float f = Math.abs((float)(rectF.width() / this.mTargetAspectRatio - rectF.height())) / 2.0f;
            rectF.top = f + rectF.top;
            rectF.bottom -= f;
        }
    }

    private static Paint getNewPaint(int n) {
        Paint paint = new Paint();
        paint.setColor(n);
        return paint;
    }

    private static Paint getNewPaintOrNull(float f, int n) {
        if (f > 0.0f) {
            Paint paint = new Paint();
            paint.setColor(n);
            paint.setStrokeWidth(f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            return paint;
        }
        return null;
    }

    private void initCropWindow() {
        float f = Math.max((float)BitmapUtils.getRectLeft(this.mBoundsPoints), (float)0.0f);
        float f2 = Math.max((float)BitmapUtils.getRectTop(this.mBoundsPoints), (float)0.0f);
        float f3 = Math.min((float)BitmapUtils.getRectRight(this.mBoundsPoints), (float)this.getWidth());
        float f4 = Math.min((float)BitmapUtils.getRectBottom(this.mBoundsPoints), (float)this.getHeight());
        if (!(f3 <= f)) {
            if (f4 <= f2) {
                return;
            }
            RectF rectF = new RectF();
            this.initializedCropWindow = true;
            float f5 = this.mInitialCropWindowPaddingRatio;
            float f6 = f5 * (f3 - f);
            float f7 = f5 * (f4 - f2);
            if (this.mInitialCropWindowRect.width() > 0 && this.mInitialCropWindowRect.height() > 0) {
                rectF.left = f + (float)this.mInitialCropWindowRect.left / this.mCropWindowHandler.getScaleFactorWidth();
                rectF.top = f2 + (float)this.mInitialCropWindowRect.top / this.mCropWindowHandler.getScaleFactorHeight();
                rectF.right = rectF.left + (float)this.mInitialCropWindowRect.width() / this.mCropWindowHandler.getScaleFactorWidth();
                rectF.bottom = rectF.top + (float)this.mInitialCropWindowRect.height() / this.mCropWindowHandler.getScaleFactorHeight();
                rectF.left = Math.max((float)f, (float)rectF.left);
                rectF.top = Math.max((float)f2, (float)rectF.top);
                rectF.right = Math.min((float)f3, (float)rectF.right);
                rectF.bottom = Math.min((float)f4, (float)rectF.bottom);
            } else if (this.mFixAspectRatio && f3 > f && f4 > f2) {
                if ((f3 - f) / (f4 - f2) > this.mTargetAspectRatio) {
                    rectF.top = f2 + f7;
                    rectF.bottom = f4 - f7;
                    float f8 = (float)this.getWidth() / 2.0f;
                    this.mTargetAspectRatio = (float)this.mAspectRatioX / (float)this.mAspectRatioY;
                    float f9 = Math.max((float)this.mCropWindowHandler.getMinCropWidth(), (float)(rectF.height() * this.mTargetAspectRatio)) / 2.0f;
                    rectF.left = f8 - f9;
                    rectF.right = f8 + f9;
                } else {
                    rectF.left = f + f6;
                    rectF.right = f3 - f6;
                    float f10 = (float)this.getHeight() / 2.0f;
                    float f11 = Math.max((float)this.mCropWindowHandler.getMinCropHeight(), (float)(rectF.width() / this.mTargetAspectRatio)) / 2.0f;
                    rectF.top = f10 - f11;
                    rectF.bottom = f10 + f11;
                }
            } else {
                rectF.left = f + f6;
                rectF.top = f2 + f7;
                rectF.right = f3 - f6;
                rectF.bottom = f4 - f7;
            }
            this.fixCropWindowRectByRules(rectF);
            this.mCropWindowHandler.setRect(rectF);
            return;
        }
    }

    private boolean isNonStraightAngleRotated() {
        float[] arrf = this.mBoundsPoints;
        float f = arrf[0] FCMPL arrf[6];
        boolean bl = false;
        if (f != false) {
            float f2 = arrf[1] FCMPL arrf[7];
            bl = false;
            if (f2 != false) {
                bl = true;
            }
        }
        return bl;
    }

    private void onActionDown(float f, float f2) {
        CropWindowMoveHandler cropWindowMoveHandler;
        this.mMoveHandler = cropWindowMoveHandler = this.mCropWindowHandler.getMoveHandler(f, f2, this.mTouchRadius, this.mCropShape);
        if (cropWindowMoveHandler != null) {
            this.invalidate();
        }
    }

    private void onActionMove(float f, float f2) {
        if (this.mMoveHandler != null) {
            float f3 = this.mSnapRadius;
            RectF rectF = this.mCropWindowHandler.getRect();
            if (this.calculateBounds(rectF)) {
                f3 = 0.0f;
            }
            CropWindowMoveHandler cropWindowMoveHandler = this.mMoveHandler;
            RectF rectF2 = this.mCalcBounds;
            int n = this.mViewWidth;
            int n2 = this.mViewHeight;
            boolean bl = this.mFixAspectRatio;
            float f4 = this.mTargetAspectRatio;
            cropWindowMoveHandler.move(rectF, f, f2, rectF2, n, n2, f3, bl, f4);
            this.mCropWindowHandler.setRect(rectF);
            this.callOnCropWindowChanged(true);
            this.invalidate();
        }
    }

    private void onActionUp() {
        if (this.mMoveHandler != null) {
            this.mMoveHandler = null;
            this.callOnCropWindowChanged(false);
            this.invalidate();
        }
    }

    public void fixCurrentCropWindowRect() {
        RectF rectF = this.getCropWindowRect();
        this.fixCropWindowRectByRules(rectF);
        this.mCropWindowHandler.setRect(rectF);
    }

    public int getAspectRatioX() {
        return this.mAspectRatioX;
    }

    public int getAspectRatioY() {
        return this.mAspectRatioY;
    }

    public CropImageView.CropShape getCropShape() {
        return this.mCropShape;
    }

    public RectF getCropWindowRect() {
        return this.mCropWindowHandler.getRect();
    }

    public CropImageView.Guidelines getGuidelines() {
        return this.mGuidelines;
    }

    public Rect getInitialCropWindowRect() {
        return this.mInitialCropWindowRect;
    }

    public boolean isFixAspectRatio() {
        return this.mFixAspectRatio;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawBackground(canvas);
        if (this.mCropWindowHandler.showGuidelines()) {
            if (this.mGuidelines == CropImageView.Guidelines.ON) {
                this.drawGuidelines(canvas);
            } else if (this.mGuidelines == CropImageView.Guidelines.ON_TOUCH && this.mMoveHandler != null) {
                this.drawGuidelines(canvas);
            }
        }
        this.drawBorders(canvas);
        this.drawCorners(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isEnabled()) {
            int n;
            if (this.mMultiTouchEnabled) {
                this.mScaleDetector.onTouchEvent(motionEvent);
            }
            if ((n = motionEvent.getAction()) != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return false;
                        }
                    } else {
                        this.onActionMove(motionEvent.getX(), motionEvent.getY());
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }
                this.getParent().requestDisallowInterceptTouchEvent(false);
                this.onActionUp();
                return true;
            }
            this.onActionDown(motionEvent.getX(), motionEvent.getY());
            return true;
        }
        return false;
    }

    public void resetCropOverlayView() {
        if (this.initializedCropWindow) {
            this.setCropWindowRect(BitmapUtils.EMPTY_RECT_F);
            this.initCropWindow();
            this.invalidate();
        }
    }

    public void resetCropWindowRect() {
        if (this.initializedCropWindow) {
            this.initCropWindow();
            this.invalidate();
            this.callOnCropWindowChanged(false);
        }
    }

    public void setAspectRatioX(int n) {
        if (n > 0) {
            if (this.mAspectRatioX != n) {
                this.mAspectRatioX = n;
                this.mTargetAspectRatio = (float)n / (float)this.mAspectRatioY;
                if (this.initializedCropWindow) {
                    this.initCropWindow();
                    this.invalidate();
                }
            }
            return;
        }
        throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
    }

    public void setAspectRatioY(int n) {
        if (n > 0) {
            if (this.mAspectRatioY != n) {
                this.mAspectRatioY = n;
                this.mTargetAspectRatio = (float)this.mAspectRatioX / (float)n;
                if (this.initializedCropWindow) {
                    this.initCropWindow();
                    this.invalidate();
                }
            }
            return;
        }
        throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
    }

    public void setBounds(float[] arrf, int n, int n2) {
        if (arrf == null || !Arrays.equals((float[])this.mBoundsPoints, (float[])arrf)) {
            if (arrf == null) {
                Arrays.fill((float[])this.mBoundsPoints, (float)0.0f);
            } else {
                System.arraycopy((Object)arrf, (int)0, (Object)this.mBoundsPoints, (int)0, (int)arrf.length);
            }
            this.mViewWidth = n;
            this.mViewHeight = n2;
            RectF rectF = this.mCropWindowHandler.getRect();
            if (rectF.width() == 0.0f || rectF.height() == 0.0f) {
                this.initCropWindow();
            }
        }
    }

    public void setCropShape(CropImageView.CropShape cropShape) {
        if (this.mCropShape != cropShape) {
            this.mCropShape = cropShape;
            if (Build.VERSION.SDK_INT <= 17) {
                if (this.mCropShape == CropImageView.CropShape.OVAL) {
                    Integer n;
                    this.mOriginalLayerType = n = Integer.valueOf((int)this.getLayerType());
                    if (n != 1) {
                        this.setLayerType(1, null);
                    } else {
                        this.mOriginalLayerType = null;
                    }
                } else {
                    Integer n = this.mOriginalLayerType;
                    if (n != null) {
                        this.setLayerType(n.intValue(), null);
                        this.mOriginalLayerType = null;
                    }
                }
            }
            this.invalidate();
        }
    }

    public void setCropWindowChangeListener(CropWindowChangeListener cropWindowChangeListener) {
        this.mCropWindowChangeListener = cropWindowChangeListener;
    }

    public void setCropWindowLimits(float f, float f2, float f3, float f4) {
        this.mCropWindowHandler.setCropWindowLimits(f, f2, f3, f4);
    }

    public void setCropWindowRect(RectF rectF) {
        this.mCropWindowHandler.setRect(rectF);
    }

    public void setFixedAspectRatio(boolean bl) {
        if (this.mFixAspectRatio != bl) {
            this.mFixAspectRatio = bl;
            if (this.initializedCropWindow) {
                this.initCropWindow();
                this.invalidate();
            }
        }
    }

    public void setGuidelines(CropImageView.Guidelines guidelines) {
        if (this.mGuidelines != guidelines) {
            this.mGuidelines = guidelines;
            if (this.initializedCropWindow) {
                this.invalidate();
            }
        }
    }

    public void setInitialAttributeValues(CropImageOptions cropImageOptions) {
        this.mCropWindowHandler.setInitialAttributeValues(cropImageOptions);
        this.setCropShape(cropImageOptions.cropShape);
        this.setSnapRadius(cropImageOptions.snapRadius);
        this.setGuidelines(cropImageOptions.guidelines);
        this.setFixedAspectRatio(cropImageOptions.fixAspectRatio);
        this.setAspectRatioX(cropImageOptions.aspectRatioX);
        this.setAspectRatioY(cropImageOptions.aspectRatioY);
        this.setMultiTouchEnabled(cropImageOptions.multiTouchEnabled);
        this.mTouchRadius = cropImageOptions.touchRadius;
        this.mInitialCropWindowPaddingRatio = cropImageOptions.initialCropWindowPaddingRatio;
        this.mBorderPaint = CropOverlayView.getNewPaintOrNull(cropImageOptions.borderLineThickness, cropImageOptions.borderLineColor);
        this.mBorderCornerOffset = cropImageOptions.borderCornerOffset;
        this.mBorderCornerLength = cropImageOptions.borderCornerLength;
        this.mBorderCornerPaint = CropOverlayView.getNewPaintOrNull(cropImageOptions.borderCornerThickness, cropImageOptions.borderCornerColor);
        this.mGuidelinePaint = CropOverlayView.getNewPaintOrNull(cropImageOptions.guidelinesThickness, cropImageOptions.guidelinesColor);
        this.mBackgroundPaint = CropOverlayView.getNewPaint(cropImageOptions.backgroundColor);
    }

    public void setInitialCropWindowRect(Rect rect) {
        Rect rect2 = this.mInitialCropWindowRect;
        Rect rect3 = rect != null ? rect : BitmapUtils.EMPTY_RECT;
        rect2.set(rect3);
        if (this.initializedCropWindow) {
            this.initCropWindow();
            this.invalidate();
            this.callOnCropWindowChanged(false);
        }
    }

    public void setMaxCropResultSize(int n, int n2) {
        this.mCropWindowHandler.setMaxCropResultSize(n, n2);
    }

    public void setMinCropResultSize(int n, int n2) {
        this.mCropWindowHandler.setMinCropResultSize(n, n2);
    }

    public boolean setMultiTouchEnabled(boolean bl) {
        if (this.mMultiTouchEnabled != bl) {
            this.mMultiTouchEnabled = bl;
            if (bl && this.mScaleDetector == null) {
                this.mScaleDetector = new ScaleGestureDetector(this.getContext(), (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener());
            }
            return true;
        }
        return false;
    }

    public void setSnapRadius(float f) {
        this.mSnapRadius = f;
    }

    public static interface CropWindowChangeListener {
        public void onCropWindowChanged(boolean var1);
    }

    private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            RectF rectF = CropOverlayView.this.mCropWindowHandler.getRect();
            float f = scaleGestureDetector.getFocusX();
            float f2 = scaleGestureDetector.getFocusY();
            float f3 = scaleGestureDetector.getCurrentSpanY() / 2.0f;
            float f4 = scaleGestureDetector.getCurrentSpanX() / 2.0f;
            float f5 = f2 - f3;
            float f6 = f - f4;
            float f7 = f + f4;
            float f8 = f2 + f3;
            if (f6 < f7 && f5 <= f8 && f6 >= 0.0f && f7 <= CropOverlayView.this.mCropWindowHandler.getMaxCropWidth() && f5 >= 0.0f && f8 <= CropOverlayView.this.mCropWindowHandler.getMaxCropHeight()) {
                rectF.set(f6, f5, f7, f8);
                CropOverlayView.this.mCropWindowHandler.setRect(rectF);
                CropOverlayView.this.invalidate();
            }
            return true;
        }
    }

}

