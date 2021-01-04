/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  java.lang.Enum
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import com.theartofdev.edmodo.cropper.CropWindowHandler;

final class CropWindowMoveHandler {
    private static final Matrix MATRIX = new Matrix();
    private final float mMaxCropHeight;
    private final float mMaxCropWidth;
    private final float mMinCropHeight;
    private final float mMinCropWidth;
    private final PointF mTouchOffset = new PointF();
    private final Type mType;

    public CropWindowMoveHandler(Type type, CropWindowHandler cropWindowHandler, float f, float f2) {
        this.mType = type;
        this.mMinCropWidth = cropWindowHandler.getMinCropWidth();
        this.mMinCropHeight = cropWindowHandler.getMinCropHeight();
        this.mMaxCropWidth = cropWindowHandler.getMaxCropWidth();
        this.mMaxCropHeight = cropWindowHandler.getMaxCropHeight();
        this.calculateTouchOffset(cropWindowHandler.getRect(), f, f2);
    }

    private void adjustBottom(RectF rectF, float f, RectF rectF2, int n, float f2, float f3, boolean bl, boolean bl2) {
        float f4 = f;
        if (f4 > (float)n) {
            f4 = (float)n + (f4 - (float)n) / 1.05f;
            PointF pointF = this.mTouchOffset;
            pointF.y -= (f4 - (float)n) / 1.1f;
        }
        if (f4 > rectF2.bottom) {
            PointF pointF = this.mTouchOffset;
            pointF.y -= (f4 - rectF2.bottom) / 2.0f;
        }
        if (rectF2.bottom - f4 < f2) {
            f4 = rectF2.bottom;
        }
        if (f4 - rectF.top < this.mMinCropHeight) {
            f4 = rectF.top + this.mMinCropHeight;
        }
        if (f4 - rectF.top > this.mMaxCropHeight) {
            f4 = rectF.top + this.mMaxCropHeight;
        }
        if (rectF2.bottom - f4 < f2) {
            f4 = rectF2.bottom;
        }
        if (f3 > 0.0f) {
            float f5 = f3 * (f4 - rectF.top);
            if (f5 < this.mMinCropWidth) {
                f4 = Math.min((float)rectF2.bottom, (float)(rectF.top + this.mMinCropWidth / f3));
                f5 = f3 * (f4 - rectF.top);
            }
            if (f5 > this.mMaxCropWidth) {
                f4 = Math.min((float)rectF2.bottom, (float)(rectF.top + this.mMaxCropWidth / f3));
                f5 = f3 * (f4 - rectF.top);
            }
            if (bl && bl2) {
                f4 = Math.min((float)f4, (float)Math.min((float)rectF2.bottom, (float)(rectF.top + rectF2.width() / f3)));
            } else {
                if (bl && rectF.right - f5 < rectF2.left) {
                    f4 = Math.min((float)rectF2.bottom, (float)(rectF.top + (rectF.right - rectF2.left) / f3));
                    f5 = f3 * (f4 - rectF.top);
                }
                if (bl2 && f5 + rectF.left > rectF2.right) {
                    f4 = Math.min((float)f4, (float)Math.min((float)rectF2.bottom, (float)(rectF.top + (rectF2.right - rectF.left) / f3)));
                }
            }
        }
        rectF.bottom = f4;
    }

    private void adjustBottomByAspectRatio(RectF rectF, float f) {
        rectF.bottom = rectF.top + rectF.width() / f;
    }

    private void adjustLeft(RectF rectF, float f, RectF rectF2, float f2, float f3, boolean bl, boolean bl2) {
        float f4 = f;
        if (f4 < 0.0f) {
            PointF pointF = this.mTouchOffset;
            pointF.x -= (f4 /= 1.05f) / 1.1f;
        }
        if (f4 < rectF2.left) {
            PointF pointF = this.mTouchOffset;
            pointF.x -= (f4 - rectF2.left) / 2.0f;
        }
        if (f4 - rectF2.left < f2) {
            f4 = rectF2.left;
        }
        if (rectF.right - f4 < this.mMinCropWidth) {
            f4 = rectF.right - this.mMinCropWidth;
        }
        if (rectF.right - f4 > this.mMaxCropWidth) {
            f4 = rectF.right - this.mMaxCropWidth;
        }
        if (f4 - rectF2.left < f2) {
            f4 = rectF2.left;
        }
        if (f3 > 0.0f) {
            float f5 = (rectF.right - f4) / f3;
            if (f5 < this.mMinCropHeight) {
                f4 = Math.max((float)rectF2.left, (float)(rectF.right - f3 * this.mMinCropHeight));
                f5 = (rectF.right - f4) / f3;
            }
            if (f5 > this.mMaxCropHeight) {
                f4 = Math.max((float)rectF2.left, (float)(rectF.right - f3 * this.mMaxCropHeight));
                f5 = (rectF.right - f4) / f3;
            }
            if (bl && bl2) {
                f4 = Math.max((float)f4, (float)Math.max((float)rectF2.left, (float)(rectF.right - f3 * rectF2.height())));
            } else {
                if (bl && rectF.bottom - f5 < rectF2.top) {
                    f4 = Math.max((float)rectF2.left, (float)(rectF.right - f3 * (rectF.bottom - rectF2.top)));
                    f5 = (rectF.right - f4) / f3;
                }
                if (bl2 && f5 + rectF.top > rectF2.bottom) {
                    f4 = Math.max((float)f4, (float)Math.max((float)rectF2.left, (float)(rectF.right - f3 * (rectF2.bottom - rectF.top))));
                }
            }
        }
        rectF.left = f4;
    }

    private void adjustLeftByAspectRatio(RectF rectF, float f) {
        rectF.left = rectF.right - f * rectF.height();
    }

    private void adjustLeftRightByAspectRatio(RectF rectF, RectF rectF2, float f) {
        rectF.inset((rectF.width() - f * rectF.height()) / 2.0f, 0.0f);
        if (rectF.left < rectF2.left) {
            rectF.offset(rectF2.left - rectF.left, 0.0f);
        }
        if (rectF.right > rectF2.right) {
            rectF.offset(rectF2.right - rectF.right, 0.0f);
        }
    }

    private void adjustRight(RectF rectF, float f, RectF rectF2, int n, float f2, float f3, boolean bl, boolean bl2) {
        float f4 = f;
        if (f4 > (float)n) {
            f4 = (float)n + (f4 - (float)n) / 1.05f;
            PointF pointF = this.mTouchOffset;
            pointF.x -= (f4 - (float)n) / 1.1f;
        }
        if (f4 > rectF2.right) {
            PointF pointF = this.mTouchOffset;
            pointF.x -= (f4 - rectF2.right) / 2.0f;
        }
        if (rectF2.right - f4 < f2) {
            f4 = rectF2.right;
        }
        if (f4 - rectF.left < this.mMinCropWidth) {
            f4 = rectF.left + this.mMinCropWidth;
        }
        if (f4 - rectF.left > this.mMaxCropWidth) {
            f4 = rectF.left + this.mMaxCropWidth;
        }
        if (rectF2.right - f4 < f2) {
            f4 = rectF2.right;
        }
        if (f3 > 0.0f) {
            float f5 = (f4 - rectF.left) / f3;
            if (f5 < this.mMinCropHeight) {
                f4 = Math.min((float)rectF2.right, (float)(rectF.left + f3 * this.mMinCropHeight));
                f5 = (f4 - rectF.left) / f3;
            }
            if (f5 > this.mMaxCropHeight) {
                f4 = Math.min((float)rectF2.right, (float)(rectF.left + f3 * this.mMaxCropHeight));
                f5 = (f4 - rectF.left) / f3;
            }
            if (bl && bl2) {
                f4 = Math.min((float)f4, (float)Math.min((float)rectF2.right, (float)(rectF.left + f3 * rectF2.height())));
            } else {
                if (bl && rectF.bottom - f5 < rectF2.top) {
                    f4 = Math.min((float)rectF2.right, (float)(rectF.left + f3 * (rectF.bottom - rectF2.top)));
                    f5 = (f4 - rectF.left) / f3;
                }
                if (bl2 && f5 + rectF.top > rectF2.bottom) {
                    f4 = Math.min((float)f4, (float)Math.min((float)rectF2.right, (float)(rectF.left + f3 * (rectF2.bottom - rectF.top))));
                }
            }
        }
        rectF.right = f4;
    }

    private void adjustRightByAspectRatio(RectF rectF, float f) {
        rectF.right = rectF.left + f * rectF.height();
    }

    private void adjustTop(RectF rectF, float f, RectF rectF2, float f2, float f3, boolean bl, boolean bl2) {
        float f4 = f;
        if (f4 < 0.0f) {
            PointF pointF = this.mTouchOffset;
            pointF.y -= (f4 /= 1.05f) / 1.1f;
        }
        if (f4 < rectF2.top) {
            PointF pointF = this.mTouchOffset;
            pointF.y -= (f4 - rectF2.top) / 2.0f;
        }
        if (f4 - rectF2.top < f2) {
            f4 = rectF2.top;
        }
        if (rectF.bottom - f4 < this.mMinCropHeight) {
            f4 = rectF.bottom - this.mMinCropHeight;
        }
        if (rectF.bottom - f4 > this.mMaxCropHeight) {
            f4 = rectF.bottom - this.mMaxCropHeight;
        }
        if (f4 - rectF2.top < f2) {
            f4 = rectF2.top;
        }
        if (f3 > 0.0f) {
            float f5 = f3 * (rectF.bottom - f4);
            if (f5 < this.mMinCropWidth) {
                f4 = Math.max((float)rectF2.top, (float)(rectF.bottom - this.mMinCropWidth / f3));
                f5 = f3 * (rectF.bottom - f4);
            }
            if (f5 > this.mMaxCropWidth) {
                f4 = Math.max((float)rectF2.top, (float)(rectF.bottom - this.mMaxCropWidth / f3));
                f5 = f3 * (rectF.bottom - f4);
            }
            if (bl && bl2) {
                f4 = Math.max((float)f4, (float)Math.max((float)rectF2.top, (float)(rectF.bottom - rectF2.width() / f3)));
            } else {
                if (bl && rectF.right - f5 < rectF2.left) {
                    f4 = Math.max((float)rectF2.top, (float)(rectF.bottom - (rectF.right - rectF2.left) / f3));
                    f5 = f3 * (rectF.bottom - f4);
                }
                if (bl2 && f5 + rectF.left > rectF2.right) {
                    f4 = Math.max((float)f4, (float)Math.max((float)rectF2.top, (float)(rectF.bottom - (rectF2.right - rectF.left) / f3)));
                }
            }
        }
        rectF.top = f4;
    }

    private void adjustTopBottomByAspectRatio(RectF rectF, RectF rectF2, float f) {
        rectF.inset(0.0f, (rectF.height() - rectF.width() / f) / 2.0f);
        if (rectF.top < rectF2.top) {
            rectF.offset(0.0f, rectF2.top - rectF.top);
        }
        if (rectF.bottom > rectF2.bottom) {
            rectF.offset(0.0f, rectF2.bottom - rectF.bottom);
        }
    }

    private void adjustTopByAspectRatio(RectF rectF, float f) {
        rectF.top = rectF.bottom - rectF.width() / f;
    }

    private static float calculateAspectRatio(float f, float f2, float f3, float f4) {
        return (f3 - f) / (f4 - f2);
    }

    private void calculateTouchOffset(RectF rectF, float f, float f2) {
        float f3;
        float f4;
        switch (1.$SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[this.mType.ordinal()]) {
            default: {
                f3 = 0.0f;
                f4 = 0.0f;
                break;
            }
            case 9: {
                f3 = rectF.centerX() - f;
                f4 = rectF.centerY() - f2;
                break;
            }
            case 8: {
                f4 = rectF.bottom - f2;
                f3 = 0.0f;
                break;
            }
            case 7: {
                f3 = rectF.right - f;
                f4 = 0.0f;
                break;
            }
            case 6: {
                f4 = rectF.top - f2;
                f3 = 0.0f;
                break;
            }
            case 5: {
                f3 = rectF.left - f;
                f4 = 0.0f;
                break;
            }
            case 4: {
                f3 = rectF.right - f;
                f4 = rectF.bottom - f2;
                break;
            }
            case 3: {
                f3 = rectF.left - f;
                f4 = rectF.bottom - f2;
                break;
            }
            case 2: {
                f3 = rectF.right - f;
                f4 = rectF.top - f2;
                break;
            }
            case 1: {
                f3 = rectF.left - f;
                f4 = rectF.top - f2;
            }
        }
        this.mTouchOffset.x = f3;
        this.mTouchOffset.y = f4;
    }

    private void moveCenter(RectF rectF, float f, float f2, RectF rectF2, int n, int n2, float f3) {
        float f4 = f - rectF.centerX();
        float f5 = f2 - rectF.centerY();
        if (f4 + rectF.left < 0.0f || f4 + rectF.right > (float)n || f4 + rectF.left < rectF2.left || f4 + rectF.right > rectF2.right) {
            PointF pointF = this.mTouchOffset;
            pointF.x -= (f4 /= 1.05f) / 2.0f;
        }
        if (f5 + rectF.top < 0.0f || f5 + rectF.bottom > (float)n2 || f5 + rectF.top < rectF2.top || f5 + rectF.bottom > rectF2.bottom) {
            PointF pointF = this.mTouchOffset;
            pointF.y -= (f5 /= 1.05f) / 2.0f;
        }
        rectF.offset(f4, f5);
        this.snapEdgesToBounds(rectF, rectF2, f3);
    }

    private void moveSizeWithFixedAspectRatio(RectF rectF, float f, float f2, RectF rectF2, int n, int n2, float f3, float f4) {
        switch (1.$SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[this.mType.ordinal()]) {
            default: {
                return;
            }
            case 8: {
                this.adjustBottom(rectF, f2, rectF2, n2, f3, f4, true, true);
                this.adjustLeftRightByAspectRatio(rectF, rectF2, f4);
                return;
            }
            case 7: {
                this.adjustRight(rectF, f, rectF2, n, f3, f4, true, true);
                this.adjustTopBottomByAspectRatio(rectF, rectF2, f4);
                return;
            }
            case 6: {
                this.adjustTop(rectF, f2, rectF2, f3, f4, true, true);
                this.adjustLeftRightByAspectRatio(rectF, rectF2, f4);
                return;
            }
            case 5: {
                this.adjustLeft(rectF, f, rectF2, f3, f4, true, true);
                this.adjustTopBottomByAspectRatio(rectF, rectF2, f4);
                return;
            }
            case 4: {
                if (CropWindowMoveHandler.calculateAspectRatio(rectF.left, rectF.top, f, f2) < f4) {
                    this.adjustBottom(rectF, f2, rectF2, n2, f3, f4, false, true);
                    this.adjustRightByAspectRatio(rectF, f4);
                    return;
                }
                this.adjustRight(rectF, f, rectF2, n, f3, f4, false, true);
                this.adjustBottomByAspectRatio(rectF, f4);
                return;
            }
            case 3: {
                if (CropWindowMoveHandler.calculateAspectRatio(f, rectF.top, rectF.right, f2) < f4) {
                    this.adjustBottom(rectF, f2, rectF2, n2, f3, f4, true, false);
                    this.adjustLeftByAspectRatio(rectF, f4);
                    return;
                }
                this.adjustLeft(rectF, f, rectF2, f3, f4, false, true);
                this.adjustBottomByAspectRatio(rectF, f4);
                return;
            }
            case 2: {
                if (CropWindowMoveHandler.calculateAspectRatio(rectF.left, f2, f, rectF.bottom) < f4) {
                    this.adjustTop(rectF, f2, rectF2, f3, f4, false, true);
                    this.adjustRightByAspectRatio(rectF, f4);
                    return;
                }
                this.adjustRight(rectF, f, rectF2, n, f3, f4, true, false);
                this.adjustTopByAspectRatio(rectF, f4);
                return;
            }
            case 1: 
        }
        if (CropWindowMoveHandler.calculateAspectRatio(f, f2, rectF.right, rectF.bottom) < f4) {
            this.adjustTop(rectF, f2, rectF2, f3, f4, true, false);
            this.adjustLeftByAspectRatio(rectF, f4);
            return;
        }
        this.adjustLeft(rectF, f, rectF2, f3, f4, true, false);
        this.adjustTopByAspectRatio(rectF, f4);
    }

    private void moveSizeWithFreeAspectRatio(RectF rectF, float f, float f2, RectF rectF2, int n, int n2, float f3) {
        switch (1.$SwitchMap$com$theartofdev$edmodo$cropper$CropWindowMoveHandler$Type[this.mType.ordinal()]) {
            default: {
                return;
            }
            case 8: {
                this.adjustBottom(rectF, f2, rectF2, n2, f3, 0.0f, false, false);
                return;
            }
            case 7: {
                this.adjustRight(rectF, f, rectF2, n, f3, 0.0f, false, false);
                return;
            }
            case 6: {
                this.adjustTop(rectF, f2, rectF2, f3, 0.0f, false, false);
                return;
            }
            case 5: {
                this.adjustLeft(rectF, f, rectF2, f3, 0.0f, false, false);
                return;
            }
            case 4: {
                this.adjustBottom(rectF, f2, rectF2, n2, f3, 0.0f, false, false);
                this.adjustRight(rectF, f, rectF2, n, f3, 0.0f, false, false);
                return;
            }
            case 3: {
                this.adjustBottom(rectF, f2, rectF2, n2, f3, 0.0f, false, false);
                this.adjustLeft(rectF, f, rectF2, f3, 0.0f, false, false);
                return;
            }
            case 2: {
                this.adjustTop(rectF, f2, rectF2, f3, 0.0f, false, false);
                this.adjustRight(rectF, f, rectF2, n, f3, 0.0f, false, false);
                return;
            }
            case 1: 
        }
        this.adjustTop(rectF, f2, rectF2, f3, 0.0f, false, false);
        this.adjustLeft(rectF, f, rectF2, f3, 0.0f, false, false);
    }

    private void snapEdgesToBounds(RectF rectF, RectF rectF2, float f) {
        if (rectF.left < f + rectF2.left) {
            rectF.offset(rectF2.left - rectF.left, 0.0f);
        }
        if (rectF.top < f + rectF2.top) {
            rectF.offset(0.0f, rectF2.top - rectF.top);
        }
        if (rectF.right > rectF2.right - f) {
            rectF.offset(rectF2.right - rectF.right, 0.0f);
        }
        if (rectF.bottom > rectF2.bottom - f) {
            rectF.offset(0.0f, rectF2.bottom - rectF.bottom);
        }
    }

    public void move(RectF rectF, float f, float f2, RectF rectF2, int n, int n2, float f3, boolean bl, float f4) {
        float f5 = f + this.mTouchOffset.x;
        float f6 = f2 + this.mTouchOffset.y;
        if (this.mType == Type.CENTER) {
            this.moveCenter(rectF, f5, f6, rectF2, n, n2, f3);
            return;
        }
        if (bl) {
            this.moveSizeWithFixedAspectRatio(rectF, f5, f6, rectF2, n, n2, f3, f4);
            return;
        }
        this.moveSizeWithFreeAspectRatio(rectF, f5, f6, rectF2, n, n2, f3);
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type BOTTOM;
        public static final /* enum */ Type BOTTOM_LEFT;
        public static final /* enum */ Type BOTTOM_RIGHT;
        public static final /* enum */ Type CENTER;
        public static final /* enum */ Type LEFT;
        public static final /* enum */ Type RIGHT;
        public static final /* enum */ Type TOP;
        public static final /* enum */ Type TOP_LEFT;
        public static final /* enum */ Type TOP_RIGHT;

        static {
            Type type;
            TOP_LEFT = new Type();
            TOP_RIGHT = new Type();
            BOTTOM_LEFT = new Type();
            BOTTOM_RIGHT = new Type();
            LEFT = new Type();
            TOP = new Type();
            RIGHT = new Type();
            BOTTOM = new Type();
            CENTER = type = new Type();
            Type[] arrtype = new Type[]{TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, LEFT, TOP, RIGHT, BOTTOM, type};
            $VALUES = arrtype;
        }

        public static Type valueOf(String string2) {
            return (Type)Enum.valueOf(Type.class, (String)string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

