/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  java.lang.Math
 *  java.lang.Object
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.graphics.RectF;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropWindowMoveHandler;

final class CropWindowHandler {
    private final RectF mEdges = new RectF();
    private final RectF mGetEdges = new RectF();
    private float mMaxCropResultHeight;
    private float mMaxCropResultWidth;
    private float mMaxCropWindowHeight;
    private float mMaxCropWindowWidth;
    private float mMinCropResultHeight;
    private float mMinCropResultWidth;
    private float mMinCropWindowHeight;
    private float mMinCropWindowWidth;
    private float mScaleFactorHeight = 1.0f;
    private float mScaleFactorWidth = 1.0f;

    CropWindowHandler() {
    }

    private boolean focusCenter() {
        return true ^ this.showGuidelines();
    }

    private CropWindowMoveHandler.Type getOvalPressedMoveType(float f, float f2) {
        float f3 = this.mEdges.width() / 6.0f;
        float f4 = f3 + this.mEdges.left;
        float f5 = this.mEdges.left + f3 * 5.0f;
        float f6 = this.mEdges.height() / 6.0f;
        float f7 = f6 + this.mEdges.top;
        float f8 = this.mEdges.top + 5.0f * f6;
        if (f < f4) {
            if (f2 < f7) {
                return CropWindowMoveHandler.Type.TOP_LEFT;
            }
            if (f2 < f8) {
                return CropWindowMoveHandler.Type.LEFT;
            }
            return CropWindowMoveHandler.Type.BOTTOM_LEFT;
        }
        if (f < f5) {
            if (f2 < f7) {
                return CropWindowMoveHandler.Type.TOP;
            }
            if (f2 < f8) {
                return CropWindowMoveHandler.Type.CENTER;
            }
            return CropWindowMoveHandler.Type.BOTTOM;
        }
        if (f2 < f7) {
            return CropWindowMoveHandler.Type.TOP_RIGHT;
        }
        if (f2 < f8) {
            return CropWindowMoveHandler.Type.RIGHT;
        }
        return CropWindowMoveHandler.Type.BOTTOM_RIGHT;
    }

    private CropWindowMoveHandler.Type getRectanglePressedMoveType(float f, float f2, float f3) {
        if (CropWindowHandler.isInCornerTargetZone(f, f2, this.mEdges.left, this.mEdges.top, f3)) {
            return CropWindowMoveHandler.Type.TOP_LEFT;
        }
        if (CropWindowHandler.isInCornerTargetZone(f, f2, this.mEdges.right, this.mEdges.top, f3)) {
            return CropWindowMoveHandler.Type.TOP_RIGHT;
        }
        if (CropWindowHandler.isInCornerTargetZone(f, f2, this.mEdges.left, this.mEdges.bottom, f3)) {
            return CropWindowMoveHandler.Type.BOTTOM_LEFT;
        }
        if (CropWindowHandler.isInCornerTargetZone(f, f2, this.mEdges.right, this.mEdges.bottom, f3)) {
            return CropWindowMoveHandler.Type.BOTTOM_RIGHT;
        }
        if (CropWindowHandler.isInCenterTargetZone(f, f2, this.mEdges.left, this.mEdges.top, this.mEdges.right, this.mEdges.bottom) && this.focusCenter()) {
            return CropWindowMoveHandler.Type.CENTER;
        }
        if (CropWindowHandler.isInHorizontalTargetZone(f, f2, this.mEdges.left, this.mEdges.right, this.mEdges.top, f3)) {
            return CropWindowMoveHandler.Type.TOP;
        }
        if (CropWindowHandler.isInHorizontalTargetZone(f, f2, this.mEdges.left, this.mEdges.right, this.mEdges.bottom, f3)) {
            return CropWindowMoveHandler.Type.BOTTOM;
        }
        if (CropWindowHandler.isInVerticalTargetZone(f, f2, this.mEdges.left, this.mEdges.top, this.mEdges.bottom, f3)) {
            return CropWindowMoveHandler.Type.LEFT;
        }
        if (CropWindowHandler.isInVerticalTargetZone(f, f2, this.mEdges.right, this.mEdges.top, this.mEdges.bottom, f3)) {
            return CropWindowMoveHandler.Type.RIGHT;
        }
        boolean bl = CropWindowHandler.isInCenterTargetZone(f, f2, this.mEdges.left, this.mEdges.top, this.mEdges.right, this.mEdges.bottom);
        CropWindowMoveHandler.Type type = null;
        if (bl) {
            boolean bl2 = this.focusCenter();
            type = null;
            if (!bl2) {
                type = CropWindowMoveHandler.Type.CENTER;
            }
        }
        return type;
    }

    private static boolean isInCenterTargetZone(float f, float f2, float f3, float f4, float f5, float f6) {
        return f > f3 && f < f5 && f2 > f4 && f2 < f6;
    }

    private static boolean isInCornerTargetZone(float f, float f2, float f3, float f4, float f5) {
        return Math.abs((float)(f - f3)) <= f5 && Math.abs((float)(f2 - f4)) <= f5;
    }

    private static boolean isInHorizontalTargetZone(float f, float f2, float f3, float f4, float f5, float f6) {
        return f > f3 && f < f4 && Math.abs((float)(f2 - f5)) <= f6;
    }

    private static boolean isInVerticalTargetZone(float f, float f2, float f3, float f4, float f5, float f6) {
        return Math.abs((float)(f - f3)) <= f6 && f2 > f4 && f2 < f5;
    }

    public float getMaxCropHeight() {
        return Math.min((float)this.mMaxCropWindowHeight, (float)(this.mMaxCropResultHeight / this.mScaleFactorHeight));
    }

    public float getMaxCropWidth() {
        return Math.min((float)this.mMaxCropWindowWidth, (float)(this.mMaxCropResultWidth / this.mScaleFactorWidth));
    }

    public float getMinCropHeight() {
        return Math.max((float)this.mMinCropWindowHeight, (float)(this.mMinCropResultHeight / this.mScaleFactorHeight));
    }

    public float getMinCropWidth() {
        return Math.max((float)this.mMinCropWindowWidth, (float)(this.mMinCropResultWidth / this.mScaleFactorWidth));
    }

    public CropWindowMoveHandler getMoveHandler(float f, float f2, float f3, CropImageView.CropShape cropShape) {
        CropWindowMoveHandler.Type type = cropShape == CropImageView.CropShape.OVAL ? this.getOvalPressedMoveType(f, f2) : this.getRectanglePressedMoveType(f, f2, f3);
        if (type != null) {
            return new CropWindowMoveHandler(type, this, f, f2);
        }
        return null;
    }

    public RectF getRect() {
        this.mGetEdges.set(this.mEdges);
        return this.mGetEdges;
    }

    public float getScaleFactorHeight() {
        return this.mScaleFactorHeight;
    }

    public float getScaleFactorWidth() {
        return this.mScaleFactorWidth;
    }

    public void setCropWindowLimits(float f, float f2, float f3, float f4) {
        this.mMaxCropWindowWidth = f;
        this.mMaxCropWindowHeight = f2;
        this.mScaleFactorWidth = f3;
        this.mScaleFactorHeight = f4;
    }

    public void setInitialAttributeValues(CropImageOptions cropImageOptions) {
        this.mMinCropWindowWidth = cropImageOptions.minCropWindowWidth;
        this.mMinCropWindowHeight = cropImageOptions.minCropWindowHeight;
        this.mMinCropResultWidth = cropImageOptions.minCropResultWidth;
        this.mMinCropResultHeight = cropImageOptions.minCropResultHeight;
        this.mMaxCropResultWidth = cropImageOptions.maxCropResultWidth;
        this.mMaxCropResultHeight = cropImageOptions.maxCropResultHeight;
    }

    public void setMaxCropResultSize(int n, int n2) {
        this.mMaxCropResultWidth = n;
        this.mMaxCropResultHeight = n2;
    }

    public void setMinCropResultSize(int n, int n2) {
        this.mMinCropResultWidth = n;
        this.mMinCropResultHeight = n2;
    }

    public void setRect(RectF rectF) {
        this.mEdges.set(rectF);
    }

    public boolean showGuidelines() {
        return !(this.mEdges.width() < 100.0f) && !(this.mEdges.height() < 100.0f);
    }
}

