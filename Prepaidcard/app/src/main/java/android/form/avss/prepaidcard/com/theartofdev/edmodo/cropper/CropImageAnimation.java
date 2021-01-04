/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.Interpolator
 *  android.view.animation.Transformation
 *  android.widget.ImageView
 *  java.lang.Object
 *  java.lang.System
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import com.theartofdev.edmodo.cropper.CropOverlayView;

final class CropImageAnimation
extends Animation
implements Animation.AnimationListener {
    private final float[] mAnimMatrix = new float[9];
    private final float[] mAnimPoints = new float[8];
    private final RectF mAnimRect = new RectF();
    private final CropOverlayView mCropOverlayView;
    private final float[] mEndBoundPoints = new float[8];
    private final RectF mEndCropWindowRect = new RectF();
    private final float[] mEndImageMatrix = new float[9];
    private final ImageView mImageView;
    private final float[] mStartBoundPoints = new float[8];
    private final RectF mStartCropWindowRect = new RectF();
    private final float[] mStartImageMatrix = new float[9];

    public CropImageAnimation(ImageView imageView, CropOverlayView cropOverlayView) {
        this.mImageView = imageView;
        this.mCropOverlayView = cropOverlayView;
        this.setDuration(300L);
        this.setFillAfter(true);
        this.setInterpolator((Interpolator)new AccelerateDecelerateInterpolator());
        this.setAnimationListener((AnimationListener)this);
    }

    protected void applyTransformation(float f, Transformation transformation) {
        float[] arrf;
        float[] arrf2;
        this.mAnimRect.left = this.mStartCropWindowRect.left + f * (this.mEndCropWindowRect.left - this.mStartCropWindowRect.left);
        this.mAnimRect.top = this.mStartCropWindowRect.top + f * (this.mEndCropWindowRect.top - this.mStartCropWindowRect.top);
        this.mAnimRect.right = this.mStartCropWindowRect.right + f * (this.mEndCropWindowRect.right - this.mStartCropWindowRect.right);
        this.mAnimRect.bottom = this.mStartCropWindowRect.bottom + f * (this.mEndCropWindowRect.bottom - this.mStartCropWindowRect.bottom);
        this.mCropOverlayView.setCropWindowRect(this.mAnimRect);
        for (int i = 0; i < (arrf = this.mAnimPoints).length; ++i) {
            float[] arrf3 = this.mStartBoundPoints;
            arrf[i] = arrf3[i] + f * (this.mEndBoundPoints[i] - arrf3[i]);
        }
        this.mCropOverlayView.setBounds(arrf, this.mImageView.getWidth(), this.mImageView.getHeight());
        for (int i = 0; i < (arrf2 = this.mAnimMatrix).length; ++i) {
            float[] arrf4 = this.mStartImageMatrix;
            arrf2[i] = arrf4[i] + f * (this.mEndImageMatrix[i] - arrf4[i]);
        }
        Matrix matrix = this.mImageView.getImageMatrix();
        matrix.setValues(this.mAnimMatrix);
        this.mImageView.setImageMatrix(matrix);
        this.mImageView.invalidate();
        this.mCropOverlayView.invalidate();
    }

    public void onAnimationEnd(Animation animation) {
        this.mImageView.clearAnimation();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }

    public void setEndState(float[] arrf, Matrix matrix) {
        System.arraycopy((Object)arrf, (int)0, (Object)this.mEndBoundPoints, (int)0, (int)8);
        this.mEndCropWindowRect.set(this.mCropOverlayView.getCropWindowRect());
        matrix.getValues(this.mEndImageMatrix);
    }

    public void setStartState(float[] arrf, Matrix matrix) {
        this.reset();
        System.arraycopy((Object)arrf, (int)0, (Object)this.mStartBoundPoints, (int)0, (int)8);
        this.mStartCropWindowRect.set(this.mCropOverlayView.getCropWindowRect());
        matrix.getValues(this.mStartImageMatrix);
    }
}

