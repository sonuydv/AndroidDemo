/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Pair
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.ProgressBar
 *  androidx.exifinterface.media.ExifInterface
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Void
 *  java.lang.ref.WeakReference
 *  java.util.UUID
 *  java.util.concurrent.Executor
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.exifinterface.media.ExifInterface;
import com.theartofdev.edmodo.cropper.BitmapCroppingWorkerTask;
import com.theartofdev.edmodo.cropper.BitmapLoadingWorkerTask;
import com.theartofdev.edmodo.cropper.BitmapUtils;
import com.theartofdev.edmodo.cropper.CropImageAnimation;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropOverlayView;
import com.theartofdev.edmodo.cropper.R;
import java.lang.ref.WeakReference;
import java.util.UUID;
import java.util.concurrent.Executor;

public class CropImageView
extends FrameLayout {
    private CropImageAnimation mAnimation;
    private boolean mAutoZoomEnabled = true;
    private Bitmap mBitmap;
    private WeakReference<BitmapCroppingWorkerTask> mBitmapCroppingWorkerTask;
    private WeakReference<BitmapLoadingWorkerTask> mBitmapLoadingWorkerTask;
    private final CropOverlayView mCropOverlayView;
    private int mDegreesRotated;
    private boolean mFlipHorizontally;
    private boolean mFlipVertically;
    private final Matrix mImageInverseMatrix = new Matrix();
    private final Matrix mImageMatrix = new Matrix();
    private final float[] mImagePoints = new float[8];
    private int mImageResource;
    private final ImageView mImageView;
    private int mInitialDegreesRotated;
    private int mLayoutHeight;
    private int mLayoutWidth;
    private Uri mLoadedImageUri;
    private int mLoadedSampleSize = 1;
    private int mMaxZoom;
    private OnCropImageCompleteListener mOnCropImageCompleteListener;
    private OnSetCropOverlayReleasedListener mOnCropOverlayReleasedListener;
    private OnSetCropOverlayMovedListener mOnSetCropOverlayMovedListener;
    private OnSetCropWindowChangeListener mOnSetCropWindowChangeListener;
    private OnSetImageUriCompleteListener mOnSetImageUriCompleteListener;
    private final ProgressBar mProgressBar;
    private RectF mRestoreCropWindowRect;
    private int mRestoreDegreesRotated;
    private boolean mSaveBitmapToInstanceState = false;
    private Uri mSaveInstanceStateBitmapUri;
    private final float[] mScaleImagePoints = new float[8];
    private ScaleType mScaleType;
    private boolean mShowCropOverlay = true;
    private boolean mShowProgressBar = true;
    private boolean mSizeChanged;
    private float mZoom = 1.0f;
    private float mZoomOffsetX;
    private float mZoomOffsetY;

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        CropOverlayView cropOverlayView;
        ImageView imageView;
        CropImageOptions cropImageOptions;
        super(context, attributeSet);
        Intent intent = context instanceof Activity ? ((Activity)context).getIntent() : null;
        cropImageOptions = null;
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("CROP_IMAGE_EXTRA_BUNDLE");
            cropImageOptions = null;
            if (bundle != null) {
                cropImageOptions = (CropImageOptions)bundle.getParcelable("CROP_IMAGE_EXTRA_OPTIONS");
            }
        }
        if (cropImageOptions == null) {
            cropImageOptions = new CropImageOptions();
            if (attributeSet != null) {
                TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CropImageView, 0, 0);
                try {
                    cropImageOptions.fixAspectRatio = typedArray.getBoolean(R.styleable.CropImageView_cropFixAspectRatio, cropImageOptions.fixAspectRatio);
                    cropImageOptions.aspectRatioX = typedArray.getInteger(R.styleable.CropImageView_cropAspectRatioX, cropImageOptions.aspectRatioX);
                    cropImageOptions.aspectRatioY = typedArray.getInteger(R.styleable.CropImageView_cropAspectRatioY, cropImageOptions.aspectRatioY);
                    cropImageOptions.scaleType = ScaleType.values()[typedArray.getInt(R.styleable.CropImageView_cropScaleType, cropImageOptions.scaleType.ordinal())];
                    cropImageOptions.autoZoomEnabled = typedArray.getBoolean(R.styleable.CropImageView_cropAutoZoomEnabled, cropImageOptions.autoZoomEnabled);
                    cropImageOptions.multiTouchEnabled = typedArray.getBoolean(R.styleable.CropImageView_cropMultiTouchEnabled, cropImageOptions.multiTouchEnabled);
                    cropImageOptions.maxZoom = typedArray.getInteger(R.styleable.CropImageView_cropMaxZoom, cropImageOptions.maxZoom);
                    cropImageOptions.cropShape = CropShape.values()[typedArray.getInt(R.styleable.CropImageView_cropShape, cropImageOptions.cropShape.ordinal())];
                    cropImageOptions.guidelines = Guidelines.values()[typedArray.getInt(R.styleable.CropImageView_cropGuidelines, cropImageOptions.guidelines.ordinal())];
                    cropImageOptions.snapRadius = typedArray.getDimension(R.styleable.CropImageView_cropSnapRadius, cropImageOptions.snapRadius);
                    cropImageOptions.touchRadius = typedArray.getDimension(R.styleable.CropImageView_cropTouchRadius, cropImageOptions.touchRadius);
                    cropImageOptions.initialCropWindowPaddingRatio = typedArray.getFloat(R.styleable.CropImageView_cropInitialCropWindowPaddingRatio, cropImageOptions.initialCropWindowPaddingRatio);
                    cropImageOptions.borderLineThickness = typedArray.getDimension(R.styleable.CropImageView_cropBorderLineThickness, cropImageOptions.borderLineThickness);
                    cropImageOptions.borderLineColor = typedArray.getInteger(R.styleable.CropImageView_cropBorderLineColor, cropImageOptions.borderLineColor);
                    cropImageOptions.borderCornerThickness = typedArray.getDimension(R.styleable.CropImageView_cropBorderCornerThickness, cropImageOptions.borderCornerThickness);
                    cropImageOptions.borderCornerOffset = typedArray.getDimension(R.styleable.CropImageView_cropBorderCornerOffset, cropImageOptions.borderCornerOffset);
                    cropImageOptions.borderCornerLength = typedArray.getDimension(R.styleable.CropImageView_cropBorderCornerLength, cropImageOptions.borderCornerLength);
                    cropImageOptions.borderCornerColor = typedArray.getInteger(R.styleable.CropImageView_cropBorderCornerColor, cropImageOptions.borderCornerColor);
                    cropImageOptions.guidelinesThickness = typedArray.getDimension(R.styleable.CropImageView_cropGuidelinesThickness, cropImageOptions.guidelinesThickness);
                    cropImageOptions.guidelinesColor = typedArray.getInteger(R.styleable.CropImageView_cropGuidelinesColor, cropImageOptions.guidelinesColor);
                    cropImageOptions.backgroundColor = typedArray.getInteger(R.styleable.CropImageView_cropBackgroundColor, cropImageOptions.backgroundColor);
                    cropImageOptions.showCropOverlay = typedArray.getBoolean(R.styleable.CropImageView_cropShowCropOverlay, this.mShowCropOverlay);
                    cropImageOptions.showProgressBar = typedArray.getBoolean(R.styleable.CropImageView_cropShowProgressBar, this.mShowProgressBar);
                    cropImageOptions.borderCornerThickness = typedArray.getDimension(R.styleable.CropImageView_cropBorderCornerThickness, cropImageOptions.borderCornerThickness);
                    cropImageOptions.minCropWindowWidth = (int)typedArray.getDimension(R.styleable.CropImageView_cropMinCropWindowWidth, (float)cropImageOptions.minCropWindowWidth);
                    cropImageOptions.minCropWindowHeight = (int)typedArray.getDimension(R.styleable.CropImageView_cropMinCropWindowHeight, (float)cropImageOptions.minCropWindowHeight);
                    cropImageOptions.minCropResultWidth = (int)typedArray.getFloat(R.styleable.CropImageView_cropMinCropResultWidthPX, (float)cropImageOptions.minCropResultWidth);
                    cropImageOptions.minCropResultHeight = (int)typedArray.getFloat(R.styleable.CropImageView_cropMinCropResultHeightPX, (float)cropImageOptions.minCropResultHeight);
                    cropImageOptions.maxCropResultWidth = (int)typedArray.getFloat(R.styleable.CropImageView_cropMaxCropResultWidthPX, (float)cropImageOptions.maxCropResultWidth);
                    cropImageOptions.maxCropResultHeight = (int)typedArray.getFloat(R.styleable.CropImageView_cropMaxCropResultHeightPX, (float)cropImageOptions.maxCropResultHeight);
                    cropImageOptions.flipHorizontally = typedArray.getBoolean(R.styleable.CropImageView_cropFlipHorizontally, cropImageOptions.flipHorizontally);
                    cropImageOptions.flipVertically = typedArray.getBoolean(R.styleable.CropImageView_cropFlipHorizontally, cropImageOptions.flipVertically);
                    this.mSaveBitmapToInstanceState = typedArray.getBoolean(R.styleable.CropImageView_cropSaveBitmapToInstanceState, this.mSaveBitmapToInstanceState);
                    if (typedArray.hasValue(R.styleable.CropImageView_cropAspectRatioX) && typedArray.hasValue(R.styleable.CropImageView_cropAspectRatioX) && !typedArray.hasValue(R.styleable.CropImageView_cropFixAspectRatio)) {
                        cropImageOptions.fixAspectRatio = true;
                    }
                }
                finally {
                    typedArray.recycle();
                }
            }
        }
        cropImageOptions.validate();
        this.mScaleType = cropImageOptions.scaleType;
        this.mAutoZoomEnabled = cropImageOptions.autoZoomEnabled;
        this.mMaxZoom = cropImageOptions.maxZoom;
        this.mShowCropOverlay = cropImageOptions.showCropOverlay;
        this.mShowProgressBar = cropImageOptions.showProgressBar;
        this.mFlipHorizontally = cropImageOptions.flipHorizontally;
        this.mFlipVertically = cropImageOptions.flipVertically;
        View view = LayoutInflater.from((Context)context).inflate(R.layout.crop_image_view, (ViewGroup)this, true);
        this.mImageView = imageView = (ImageView)view.findViewById(R.id.ImageView_image);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        this.mCropOverlayView = cropOverlayView = (CropOverlayView)view.findViewById(R.id.CropOverlayView);
        cropOverlayView.setCropWindowChangeListener(new CropOverlayView.CropWindowChangeListener(){

            @Override
            public void onCropWindowChanged(boolean bl) {
                OnSetCropOverlayMovedListener onSetCropOverlayMovedListener;
                CropImageView.this.handleCropWindowChanged(bl, true);
                OnSetCropOverlayReleasedListener onSetCropOverlayReleasedListener = CropImageView.this.mOnCropOverlayReleasedListener;
                if (onSetCropOverlayReleasedListener != null && !bl) {
                    onSetCropOverlayReleasedListener.onCropOverlayReleased(CropImageView.this.getCropRect());
                }
                if ((onSetCropOverlayMovedListener = CropImageView.this.mOnSetCropOverlayMovedListener) != null && bl) {
                    onSetCropOverlayMovedListener.onCropOverlayMoved(CropImageView.this.getCropRect());
                }
            }
        });
        this.mCropOverlayView.setInitialAttributeValues(cropImageOptions);
        this.mProgressBar = (ProgressBar)view.findViewById(R.id.CropProgressBar);
        this.setProgressBarVisibility();
    }

    private void applyImageMatrix(float f, float f2, boolean bl, boolean bl2) {
        if (this.mBitmap != null && f > 0.0f && f2 > 0.0f) {
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            RectF rectF = this.mCropOverlayView.getCropWindowRect();
            this.mImageInverseMatrix.mapRect(rectF);
            this.mImageMatrix.reset();
            this.mImageMatrix.postTranslate((f - (float)this.mBitmap.getWidth()) / 2.0f, (f2 - (float)this.mBitmap.getHeight()) / 2.0f);
            this.mapImagePointsByImageMatrix();
            int n = this.mDegreesRotated;
            if (n > 0) {
                this.mImageMatrix.postRotate((float)n, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                this.mapImagePointsByImageMatrix();
            }
            float f3 = Math.min((float)(f / BitmapUtils.getRectWidth(this.mImagePoints)), (float)(f2 / BitmapUtils.getRectHeight(this.mImagePoints)));
            if (this.mScaleType == ScaleType.FIT_CENTER || this.mScaleType == ScaleType.CENTER_INSIDE && f3 < 1.0f || f3 > 1.0f && this.mAutoZoomEnabled) {
                this.mImageMatrix.postScale(f3, f3, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
                this.mapImagePointsByImageMatrix();
            }
            float f4 = this.mFlipHorizontally ? -this.mZoom : this.mZoom;
            float f5 = this.mFlipVertically ? -this.mZoom : this.mZoom;
            this.mImageMatrix.postScale(f4, f5, BitmapUtils.getRectCenterX(this.mImagePoints), BitmapUtils.getRectCenterY(this.mImagePoints));
            this.mapImagePointsByImageMatrix();
            this.mImageMatrix.mapRect(rectF);
            if (bl) {
                float f6 = f > BitmapUtils.getRectWidth(this.mImagePoints) ? 0.0f : Math.max((float)Math.min((float)(f / 2.0f - rectF.centerX()), (float)(-BitmapUtils.getRectLeft(this.mImagePoints))), (float)((float)this.getWidth() - BitmapUtils.getRectRight(this.mImagePoints))) / f4;
                this.mZoomOffsetX = f6;
                float f7 = f2 > BitmapUtils.getRectHeight(this.mImagePoints) ? 0.0f : Math.max((float)Math.min((float)(f2 / 2.0f - rectF.centerY()), (float)(-BitmapUtils.getRectTop(this.mImagePoints))), (float)((float)this.getHeight() - BitmapUtils.getRectBottom(this.mImagePoints))) / f5;
                this.mZoomOffsetY = f7;
            } else {
                this.mZoomOffsetX = Math.min((float)Math.max((float)(f4 * this.mZoomOffsetX), (float)(-rectF.left)), (float)(f + -rectF.right)) / f4;
                this.mZoomOffsetY = Math.min((float)Math.max((float)(f5 * this.mZoomOffsetY), (float)(-rectF.top)), (float)(f2 + -rectF.bottom)) / f5;
            }
            this.mImageMatrix.postTranslate(f4 * this.mZoomOffsetX, f5 * this.mZoomOffsetY);
            rectF.offset(f4 * this.mZoomOffsetX, f5 * this.mZoomOffsetY);
            this.mCropOverlayView.setCropWindowRect(rectF);
            this.mapImagePointsByImageMatrix();
            this.mCropOverlayView.invalidate();
            if (bl2) {
                this.mAnimation.setEndState(this.mImagePoints, this.mImageMatrix);
                this.mImageView.startAnimation((Animation)this.mAnimation);
            } else {
                this.mImageView.setImageMatrix(this.mImageMatrix);
            }
            this.updateImageBounds(false);
        }
    }

    private void clearImageInt() {
        if (this.mBitmap != null && (this.mImageResource > 0 || this.mLoadedImageUri != null)) {
            this.mBitmap.recycle();
        }
        this.mBitmap = null;
        this.mImageResource = 0;
        this.mLoadedImageUri = null;
        this.mLoadedSampleSize = 1;
        this.mDegreesRotated = 0;
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mImageMatrix.reset();
        this.mSaveInstanceStateBitmapUri = null;
        this.mImageView.setImageBitmap(null);
        this.setCropOverlayVisibility();
    }

    private static int getOnMeasureSpec(int n, int n2, int n3) {
        if (n == 1073741824) {
            return n2;
        }
        if (n == Integer.MIN_VALUE) {
            return Math.min((int)n3, (int)n2);
        }
        return n3;
    }

    private void handleCropWindowChanged(boolean bl, boolean bl2) {
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (this.mBitmap != null && n > 0 && n2 > 0) {
            OnSetCropWindowChangeListener onSetCropWindowChangeListener;
            RectF rectF = this.mCropOverlayView.getCropWindowRect();
            if (bl) {
                if (rectF.left < 0.0f || rectF.top < 0.0f || rectF.right > (float)n || rectF.bottom > (float)n2) {
                    this.applyImageMatrix(n, n2, false, false);
                }
            } else if (this.mAutoZoomEnabled || this.mZoom > 1.0f) {
                float f = this.mZoom FCMPG (float)this.mMaxZoom;
                float f2 = 0.0f;
                if (f < 0) {
                    float f3 = rectF.width() FCMPG 0.5f * (float)n;
                    f2 = 0.0f;
                    if (f3 < 0) {
                        float f4 = rectF.height() FCMPG 0.5f * (float)n2;
                        f2 = 0.0f;
                        if (f4 < 0) {
                            f2 = Math.min((float)this.mMaxZoom, (float)Math.min((float)((float)n / (rectF.width() / this.mZoom / 0.64f)), (float)((float)n2 / (rectF.height() / this.mZoom / 0.64f))));
                        }
                    }
                }
                if (this.mZoom > 1.0f && (rectF.width() > 0.65f * (float)n || rectF.height() > 0.65f * (float)n2)) {
                    f2 = Math.max((float)1.0f, (float)Math.min((float)((float)n / (rectF.width() / this.mZoom / 0.51f)), (float)((float)n2 / (rectF.height() / this.mZoom / 0.51f))));
                }
                if (!this.mAutoZoomEnabled) {
                    f2 = 1.0f;
                }
                if (f2 > 0.0f && f2 != this.mZoom) {
                    if (bl2) {
                        if (this.mAnimation == null) {
                            this.mAnimation = new CropImageAnimation(this.mImageView, this.mCropOverlayView);
                        }
                        this.mAnimation.setStartState(this.mImagePoints, this.mImageMatrix);
                    }
                    this.mZoom = f2;
                    this.applyImageMatrix(n, n2, true, bl2);
                }
            }
            if ((onSetCropWindowChangeListener = this.mOnSetCropWindowChangeListener) != null && !bl) {
                onSetCropWindowChangeListener.onCropWindowChanged();
            }
        }
    }

    private void mapImagePointsByImageMatrix() {
        float[] arrf = this.mImagePoints;
        arrf[0] = 0.0f;
        arrf[1] = 0.0f;
        arrf[2] = this.mBitmap.getWidth();
        float[] arrf2 = this.mImagePoints;
        arrf2[3] = 0.0f;
        arrf2[4] = this.mBitmap.getWidth();
        this.mImagePoints[5] = this.mBitmap.getHeight();
        float[] arrf3 = this.mImagePoints;
        arrf3[6] = 0.0f;
        arrf3[7] = this.mBitmap.getHeight();
        this.mImageMatrix.mapPoints(this.mImagePoints);
        float[] arrf4 = this.mScaleImagePoints;
        arrf4[0] = 0.0f;
        arrf4[1] = 0.0f;
        arrf4[2] = 100.0f;
        arrf4[3] = 0.0f;
        arrf4[4] = 100.0f;
        arrf4[5] = 100.0f;
        arrf4[6] = 0.0f;
        arrf4[7] = 100.0f;
        this.mImageMatrix.mapPoints(arrf4);
    }

    private void setBitmap(Bitmap bitmap, int n, Uri uri, int n2, int n3) {
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 == null || !bitmap2.equals((Object)bitmap)) {
            this.mImageView.clearAnimation();
            this.clearImageInt();
            this.mBitmap = bitmap;
            this.mImageView.setImageBitmap(bitmap);
            this.mLoadedImageUri = uri;
            this.mImageResource = n;
            this.mLoadedSampleSize = n2;
            this.mDegreesRotated = n3;
            this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
            CropOverlayView cropOverlayView = this.mCropOverlayView;
            if (cropOverlayView != null) {
                cropOverlayView.resetCropOverlayView();
                this.setCropOverlayVisibility();
            }
        }
    }

    private void setCropOverlayVisibility() {
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView != null) {
            int n = this.mShowCropOverlay && this.mBitmap != null ? 0 : 4;
            cropOverlayView.setVisibility(n);
        }
    }

    private void setProgressBarVisibility() {
        boolean bl = this.mShowProgressBar && (this.mBitmap == null && this.mBitmapLoadingWorkerTask != null || this.mBitmapCroppingWorkerTask != null);
        ProgressBar progressBar = this.mProgressBar;
        int n = bl ? 0 : 4;
        progressBar.setVisibility(n);
    }

    private void updateImageBounds(boolean bl) {
        if (this.mBitmap != null && !bl) {
            float f = 100.0f * (float)this.mLoadedSampleSize / BitmapUtils.getRectWidth(this.mScaleImagePoints);
            float f2 = 100.0f * (float)this.mLoadedSampleSize / BitmapUtils.getRectHeight(this.mScaleImagePoints);
            this.mCropOverlayView.setCropWindowLimits(this.getWidth(), this.getHeight(), f, f2);
        }
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        float[] arrf = bl ? null : this.mImagePoints;
        cropOverlayView.setBounds(arrf, this.getWidth(), this.getHeight());
    }

    public void clearAspectRatio() {
        this.mCropOverlayView.setAspectRatioX(1);
        this.mCropOverlayView.setAspectRatioY(1);
        this.setFixedAspectRatio(false);
    }

    public void clearImage() {
        this.clearImageInt();
        this.mCropOverlayView.setInitialCropWindowRect(null);
    }

    public void flipImageHorizontally() {
        this.mFlipHorizontally = true ^ this.mFlipHorizontally;
        this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
    }

    public void flipImageVertically() {
        this.mFlipVertically = true ^ this.mFlipVertically;
        this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
    }

    public Pair<Integer, Integer> getAspectRatio() {
        return new Pair((Object)this.mCropOverlayView.getAspectRatioX(), (Object)this.mCropOverlayView.getAspectRatioY());
    }

    public float[] getCropPoints() {
        RectF rectF = this.mCropOverlayView.getCropWindowRect();
        float[] arrf = new float[]{rectF.left, rectF.top, rectF.right, rectF.top, rectF.right, rectF.bottom, rectF.left, rectF.bottom};
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapPoints(arrf);
        for (int i = 0; i < arrf.length; ++i) {
            arrf[i] = arrf[i] * (float)this.mLoadedSampleSize;
        }
        return arrf;
    }

    public Rect getCropRect() {
        int n = this.mLoadedSampleSize;
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        return BitmapUtils.getRectFromPoints(this.getCropPoints(), n * bitmap.getWidth(), n * bitmap.getHeight(), this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY());
    }

    public CropShape getCropShape() {
        return this.mCropOverlayView.getCropShape();
    }

    public RectF getCropWindowRect() {
        CropOverlayView cropOverlayView = this.mCropOverlayView;
        if (cropOverlayView == null) {
            return null;
        }
        return cropOverlayView.getCropWindowRect();
    }

    public Bitmap getCroppedImage() {
        return this.getCroppedImage(0, 0, RequestSizeOptions.NONE);
    }

    public Bitmap getCroppedImage(int n, int n2) {
        return this.getCroppedImage(n, n2, RequestSizeOptions.RESIZE_INSIDE);
    }

    public Bitmap getCroppedImage(int n, int n2, RequestSizeOptions requestSizeOptions) {
        if (this.mBitmap != null) {
            Bitmap bitmap;
            this.mImageView.clearAnimation();
            int n3 = requestSizeOptions != RequestSizeOptions.NONE ? n : 0;
            RequestSizeOptions requestSizeOptions2 = RequestSizeOptions.NONE;
            int n4 = 0;
            if (requestSizeOptions != requestSizeOptions2) {
                n4 = n2;
            }
            if (this.mLoadedImageUri != null && (this.mLoadedSampleSize > 1 || requestSizeOptions == RequestSizeOptions.SAMPLING)) {
                int n5 = this.mBitmap.getWidth() * this.mLoadedSampleSize;
                int n6 = this.mBitmap.getHeight() * this.mLoadedSampleSize;
                Context context = this.getContext();
                Uri uri = this.mLoadedImageUri;
                float[] arrf = this.getCropPoints();
                int n7 = this.mDegreesRotated;
                boolean bl = this.mCropOverlayView.isFixAspectRatio();
                int n8 = this.mCropOverlayView.getAspectRatioX();
                int n9 = this.mCropOverlayView.getAspectRatioY();
                boolean bl2 = this.mFlipHorizontally;
                boolean bl3 = this.mFlipVertically;
                bitmap = BitmapUtils.cropBitmap((Context)context, (Uri)uri, (float[])arrf, (int)n7, (int)n5, (int)n6, (boolean)bl, (int)n8, (int)n9, (int)n3, (int)n4, (boolean)bl2, (boolean)bl3).bitmap;
            } else {
                bitmap = BitmapUtils.cropBitmapObjectHandleOOM((Bitmap)this.mBitmap, (float[])this.getCropPoints(), (int)this.mDegreesRotated, (boolean)this.mCropOverlayView.isFixAspectRatio(), (int)this.mCropOverlayView.getAspectRatioX(), (int)this.mCropOverlayView.getAspectRatioY(), (boolean)this.mFlipHorizontally, (boolean)this.mFlipVertically).bitmap;
            }
            return BitmapUtils.resizeBitmap(bitmap, n3, n4, requestSizeOptions);
        }
        return null;
    }

    public void getCroppedImageAsync() {
        this.getCroppedImageAsync(0, 0, RequestSizeOptions.NONE);
    }

    public void getCroppedImageAsync(int n, int n2) {
        this.getCroppedImageAsync(n, n2, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void getCroppedImageAsync(int n, int n2, RequestSizeOptions requestSizeOptions) {
        if (this.mOnCropImageCompleteListener != null) {
            this.startCropWorkerTask(n, n2, requestSizeOptions, null, null, 0);
            return;
        }
        throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
    }

    public Guidelines getGuidelines() {
        return this.mCropOverlayView.getGuidelines();
    }

    public int getImageResource() {
        return this.mImageResource;
    }

    public Uri getImageUri() {
        return this.mLoadedImageUri;
    }

    public int getMaxZoom() {
        return this.mMaxZoom;
    }

    public int getRotatedDegrees() {
        return this.mDegreesRotated;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public Rect getWholeImageRect() {
        int n = this.mLoadedSampleSize;
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return null;
        }
        return new Rect(0, 0, n * bitmap.getWidth(), n * bitmap.getHeight());
    }

    public boolean isAutoZoomEnabled() {
        return this.mAutoZoomEnabled;
    }

    public boolean isFixAspectRatio() {
        return this.mCropOverlayView.isFixAspectRatio();
    }

    public boolean isFlippedHorizontally() {
        return this.mFlipHorizontally;
    }

    public boolean isFlippedVertically() {
        return this.mFlipVertically;
    }

    public boolean isSaveBitmapToInstanceState() {
        return this.mSaveBitmapToInstanceState;
    }

    public boolean isShowCropOverlay() {
        return this.mShowCropOverlay;
    }

    public boolean isShowProgressBar() {
        return this.mShowProgressBar;
    }

    void onImageCroppingAsyncComplete(BitmapCroppingWorkerTask.Result result) {
        this.mBitmapCroppingWorkerTask = null;
        this.setProgressBarVisibility();
        OnCropImageCompleteListener onCropImageCompleteListener = this.mOnCropImageCompleteListener;
        if (onCropImageCompleteListener != null) {
            CropResult cropResult = new CropResult(this.mBitmap, this.mLoadedImageUri, result.bitmap, result.uri, result.error, this.getCropPoints(), this.getCropRect(), this.getWholeImageRect(), this.getRotatedDegrees(), result.sampleSize);
            onCropImageCompleteListener.onCropImageComplete(this, cropResult);
        }
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mLayoutWidth > 0 && this.mLayoutHeight > 0) {
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.width = this.mLayoutWidth;
            layoutParams.height = this.mLayoutHeight;
            this.setLayoutParams(layoutParams);
            if (this.mBitmap != null) {
                this.applyImageMatrix(n3 - n, n4 - n2, true, false);
                if (this.mRestoreCropWindowRect != null) {
                    int n5 = this.mRestoreDegreesRotated;
                    if (n5 != this.mInitialDegreesRotated) {
                        this.mDegreesRotated = n5;
                        this.applyImageMatrix(n3 - n, n4 - n2, true, false);
                    }
                    this.mImageMatrix.mapRect(this.mRestoreCropWindowRect);
                    this.mCropOverlayView.setCropWindowRect(this.mRestoreCropWindowRect);
                    this.handleCropWindowChanged(false, false);
                    this.mCropOverlayView.fixCurrentCropWindowRect();
                    this.mRestoreCropWindowRect = null;
                } else if (this.mSizeChanged) {
                    this.mSizeChanged = false;
                    this.handleCropWindowChanged(false, false);
                }
            } else {
                this.updateImageBounds(true);
            }
            return;
        }
        this.updateImageBounds(true);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = MeasureSpec.getMode((int)n);
        int n4 = MeasureSpec.getSize((int)n);
        int n5 = MeasureSpec.getMode((int)n2);
        int n6 = MeasureSpec.getSize((int)n2);
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            int n7;
            int n8;
            if (n6 == 0) {
                n6 = bitmap.getHeight();
            }
            double d = Double.POSITIVE_INFINITY;
            double d2 = Double.POSITIVE_INFINITY;
            if (n4 < this.mBitmap.getWidth()) {
                d = (double)n4 / (double)this.mBitmap.getWidth();
            }
            if (n6 < this.mBitmap.getHeight()) {
                d2 = (double)n6 / (double)this.mBitmap.getHeight();
            }
            if (d == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY) {
                n7 = this.mBitmap.getWidth();
                n8 = this.mBitmap.getHeight();
            } else if (d <= d2) {
                n7 = n4;
                n8 = (int)(d * (double)this.mBitmap.getHeight());
            } else {
                n8 = n6;
                n7 = (int)(d2 * (double)this.mBitmap.getWidth());
            }
            int n9 = CropImageView.getOnMeasureSpec(n3, n4, n7);
            int n10 = CropImageView.getOnMeasureSpec(n5, n6, n8);
            this.mLayoutWidth = n9;
            this.mLayoutHeight = n10;
            this.setMeasuredDimension(n9, n10);
            return;
        }
        this.setMeasuredDimension(n4, n6);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle)parcelable;
            if (this.mBitmapLoadingWorkerTask == null && this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource == 0) {
                RectF rectF;
                int n;
                Uri uri = (Uri)bundle.getParcelable("LOADED_IMAGE_URI");
                if (uri != null) {
                    String string2 = bundle.getString("LOADED_IMAGE_STATE_BITMAP_KEY");
                    if (string2 != null) {
                        Bitmap bitmap = BitmapUtils.mStateBitmap != null && ((String)BitmapUtils.mStateBitmap.first).equals((Object)string2) ? (Bitmap)((WeakReference)BitmapUtils.mStateBitmap.second).get() : null;
                        Bitmap bitmap2 = bitmap;
                        BitmapUtils.mStateBitmap = null;
                        if (bitmap2 != null && !bitmap2.isRecycled()) {
                            this.setBitmap(bitmap2, 0, uri, bundle.getInt("LOADED_SAMPLE_SIZE"), 0);
                        }
                    }
                    if (this.mLoadedImageUri == null) {
                        this.setImageUriAsync(uri);
                    }
                } else {
                    int n2 = bundle.getInt("LOADED_IMAGE_RESOURCE");
                    if (n2 > 0) {
                        this.setImageResource(n2);
                    } else {
                        Uri uri2 = (Uri)bundle.getParcelable("LOADING_IMAGE_URI");
                        if (uri2 != null) {
                            this.setImageUriAsync(uri2);
                        }
                    }
                }
                this.mRestoreDegreesRotated = n = bundle.getInt("DEGREES_ROTATED");
                this.mDegreesRotated = n;
                Rect rect = (Rect)bundle.getParcelable("INITIAL_CROP_RECT");
                if (rect != null && (rect.width() > 0 || rect.height() > 0)) {
                    this.mCropOverlayView.setInitialCropWindowRect(rect);
                }
                if ((rectF = (RectF)bundle.getParcelable("CROP_WINDOW_RECT")) != null && (rectF.width() > 0.0f || rectF.height() > 0.0f)) {
                    this.mRestoreCropWindowRect = rectF;
                }
                this.mCropOverlayView.setCropShape(CropShape.valueOf(bundle.getString("CROP_SHAPE")));
                this.mAutoZoomEnabled = bundle.getBoolean("CROP_AUTO_ZOOM_ENABLED");
                this.mMaxZoom = bundle.getInt("CROP_MAX_ZOOM");
                this.mFlipHorizontally = bundle.getBoolean("CROP_FLIP_HORIZONTALLY");
                this.mFlipVertically = bundle.getBoolean("CROP_FLIP_VERTICALLY");
            }
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        BitmapLoadingWorkerTask bitmapLoadingWorkerTask;
        WeakReference<BitmapLoadingWorkerTask> weakReference;
        if (this.mLoadedImageUri == null && this.mBitmap == null && this.mImageResource < 1) {
            return super.onSaveInstanceState();
        }
        Bundle bundle = new Bundle();
        Uri uri = this.mLoadedImageUri;
        if (this.mSaveBitmapToInstanceState && uri == null && this.mImageResource < 1) {
            Uri uri2;
            uri = uri2 = BitmapUtils.writeTempStateStoreBitmap(this.getContext(), this.mBitmap, this.mSaveInstanceStateBitmapUri);
            this.mSaveInstanceStateBitmapUri = uri2;
        }
        if (uri != null && this.mBitmap != null) {
            String string2 = UUID.randomUUID().toString();
            BitmapUtils.mStateBitmap = new Pair((Object)string2, (Object)new WeakReference((Object)this.mBitmap));
            bundle.putString("LOADED_IMAGE_STATE_BITMAP_KEY", string2);
        }
        if ((weakReference = this.mBitmapLoadingWorkerTask) != null && (bitmapLoadingWorkerTask = (BitmapLoadingWorkerTask)((Object)weakReference.get())) != null) {
            bundle.putParcelable("LOADING_IMAGE_URI", (Parcelable)bitmapLoadingWorkerTask.getUri());
        }
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("LOADED_IMAGE_URI", (Parcelable)uri);
        bundle.putInt("LOADED_IMAGE_RESOURCE", this.mImageResource);
        bundle.putInt("LOADED_SAMPLE_SIZE", this.mLoadedSampleSize);
        bundle.putInt("DEGREES_ROTATED", this.mDegreesRotated);
        bundle.putParcelable("INITIAL_CROP_RECT", (Parcelable)this.mCropOverlayView.getInitialCropWindowRect());
        BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
        this.mImageMatrix.invert(this.mImageInverseMatrix);
        this.mImageInverseMatrix.mapRect(BitmapUtils.RECT);
        bundle.putParcelable("CROP_WINDOW_RECT", (Parcelable)BitmapUtils.RECT);
        bundle.putString("CROP_SHAPE", this.mCropOverlayView.getCropShape().name());
        bundle.putBoolean("CROP_AUTO_ZOOM_ENABLED", this.mAutoZoomEnabled);
        bundle.putInt("CROP_MAX_ZOOM", this.mMaxZoom);
        bundle.putBoolean("CROP_FLIP_HORIZONTALLY", this.mFlipHorizontally);
        bundle.putBoolean("CROP_FLIP_VERTICALLY", this.mFlipVertically);
        return bundle;
    }

    void onSetImageUriAsyncComplete(BitmapLoadingWorkerTask.Result result) {
        OnSetImageUriCompleteListener onSetImageUriCompleteListener;
        this.mBitmapLoadingWorkerTask = null;
        this.setProgressBarVisibility();
        if (result.error == null) {
            this.mInitialDegreesRotated = result.degreesRotated;
            this.setBitmap(result.bitmap, 0, result.uri, result.loadSampleSize, result.degreesRotated);
        }
        if ((onSetImageUriCompleteListener = this.mOnSetImageUriCompleteListener) != null) {
            onSetImageUriCompleteListener.onSetImageUriComplete(this, result.uri, result.error);
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        boolean bl = n3 > 0 && n4 > 0;
        this.mSizeChanged = bl;
    }

    public void resetCropRect() {
        this.mZoom = 1.0f;
        this.mZoomOffsetX = 0.0f;
        this.mZoomOffsetY = 0.0f;
        this.mDegreesRotated = this.mInitialDegreesRotated;
        this.mFlipHorizontally = false;
        this.mFlipVertically = false;
        this.applyImageMatrix(this.getWidth(), this.getHeight(), false, false);
        this.mCropOverlayView.resetCropWindowRect();
    }

    public void rotateImage(int n) {
        if (this.mBitmap != null) {
            float f;
            int n2 = n < 0 ? 360 + n % 360 : n % 360;
            boolean bl = !this.mCropOverlayView.isFixAspectRatio() && (n2 > 45 && n2 < 135 || n2 > 215 && n2 < 305);
            BitmapUtils.RECT.set(this.mCropOverlayView.getCropWindowRect());
            RectF rectF = BitmapUtils.RECT;
            float f2 = bl ? rectF.height() : rectF.width();
            float f3 = f2 / 2.0f;
            RectF rectF2 = BitmapUtils.RECT;
            float f4 = bl ? rectF2.width() : rectF2.height();
            float f5 = f4 / 2.0f;
            if (bl) {
                boolean bl2 = this.mFlipHorizontally;
                this.mFlipHorizontally = this.mFlipVertically;
                this.mFlipVertically = bl2;
            }
            this.mImageMatrix.invert(this.mImageInverseMatrix);
            BitmapUtils.POINTS[0] = BitmapUtils.RECT.centerX();
            BitmapUtils.POINTS[1] = BitmapUtils.RECT.centerY();
            BitmapUtils.POINTS[2] = 0.0f;
            BitmapUtils.POINTS[3] = 0.0f;
            BitmapUtils.POINTS[4] = 1.0f;
            BitmapUtils.POINTS[5] = 0.0f;
            this.mImageInverseMatrix.mapPoints(BitmapUtils.POINTS);
            this.mDegreesRotated = (n2 + this.mDegreesRotated) % 360;
            this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            this.mZoom = f = (float)((double)this.mZoom / Math.sqrt((double)(Math.pow((double)(BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2]), (double)2.0) + Math.pow((double)(BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3]), (double)2.0))));
            this.mZoom = Math.max((float)f, (float)1.0f);
            this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
            this.mImageMatrix.mapPoints(BitmapUtils.POINTS2, BitmapUtils.POINTS);
            double d = Math.sqrt((double)(Math.pow((double)(BitmapUtils.POINTS2[4] - BitmapUtils.POINTS2[2]), (double)2.0) + Math.pow((double)(BitmapUtils.POINTS2[5] - BitmapUtils.POINTS2[3]), (double)2.0)));
            float f6 = (float)(d * (double)f3);
            float f7 = (float)(d * (double)f5);
            BitmapUtils.RECT.set(BitmapUtils.POINTS2[0] - f6, BitmapUtils.POINTS2[1] - f7, f6 + BitmapUtils.POINTS2[0], f7 + BitmapUtils.POINTS2[1]);
            this.mCropOverlayView.resetCropOverlayView();
            this.mCropOverlayView.setCropWindowRect(BitmapUtils.RECT);
            this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
            this.handleCropWindowChanged(false, false);
            this.mCropOverlayView.fixCurrentCropWindowRect();
        }
    }

    public void saveCroppedImageAsync(Uri uri) {
        this.saveCroppedImageAsync(uri, Bitmap.CompressFormat.JPEG, 90, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri uri, Bitmap.CompressFormat compressFormat, int n) {
        this.saveCroppedImageAsync(uri, compressFormat, n, 0, 0, RequestSizeOptions.NONE);
    }

    public void saveCroppedImageAsync(Uri uri, Bitmap.CompressFormat compressFormat, int n, int n2, int n3) {
        this.saveCroppedImageAsync(uri, compressFormat, n, n2, n3, RequestSizeOptions.RESIZE_INSIDE);
    }

    public void saveCroppedImageAsync(Uri uri, Bitmap.CompressFormat compressFormat, int n, int n2, int n3, RequestSizeOptions requestSizeOptions) {
        if (this.mOnCropImageCompleteListener != null) {
            this.startCropWorkerTask(n2, n3, requestSizeOptions, uri, compressFormat, n);
            return;
        }
        throw new IllegalArgumentException("mOnCropImageCompleteListener is not set");
    }

    public void setAspectRatio(int n, int n2) {
        this.mCropOverlayView.setAspectRatioX(n);
        this.mCropOverlayView.setAspectRatioY(n2);
        this.setFixedAspectRatio(true);
    }

    public void setAutoZoomEnabled(boolean bl) {
        if (this.mAutoZoomEnabled != bl) {
            this.mAutoZoomEnabled = bl;
            this.handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setCropRect(Rect rect) {
        this.mCropOverlayView.setInitialCropWindowRect(rect);
    }

    public void setCropShape(CropShape cropShape) {
        this.mCropOverlayView.setCropShape(cropShape);
    }

    public void setFixedAspectRatio(boolean bl) {
        this.mCropOverlayView.setFixedAspectRatio(bl);
    }

    public void setFlippedHorizontally(boolean bl) {
        if (this.mFlipHorizontally != bl) {
            this.mFlipHorizontally = bl;
            this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
        }
    }

    public void setFlippedVertically(boolean bl) {
        if (this.mFlipVertically != bl) {
            this.mFlipVertically = bl;
            this.applyImageMatrix(this.getWidth(), this.getHeight(), true, false);
        }
    }

    public void setGuidelines(Guidelines guidelines) {
        this.mCropOverlayView.setGuidelines(guidelines);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mCropOverlayView.setInitialCropWindowRect(null);
        this.setBitmap(bitmap, 0, null, 1, 0);
    }

    public void setImageBitmap(Bitmap bitmap, ExifInterface exifInterface) {
        Bitmap bitmap2;
        int n = 0;
        if (bitmap != null && exifInterface != null) {
            BitmapUtils.RotateBitmapResult rotateBitmapResult = BitmapUtils.rotateBitmapByExif(bitmap, exifInterface);
            bitmap2 = rotateBitmapResult.bitmap;
            n = rotateBitmapResult.degrees;
            this.mInitialDegreesRotated = rotateBitmapResult.degrees;
        } else {
            bitmap2 = bitmap;
        }
        this.mCropOverlayView.setInitialCropWindowRect(null);
        this.setBitmap(bitmap2, 0, null, 1, n);
    }

    public void setImageResource(int n) {
        if (n != 0) {
            this.mCropOverlayView.setInitialCropWindowRect(null);
            this.setBitmap(BitmapFactory.decodeResource((Resources)this.getResources(), (int)n), n, null, 1, 0);
        }
    }

    public void setImageUriAsync(Uri uri) {
        if (uri != null) {
            WeakReference weakReference;
            WeakReference<BitmapLoadingWorkerTask> weakReference2 = this.mBitmapLoadingWorkerTask;
            BitmapLoadingWorkerTask bitmapLoadingWorkerTask = weakReference2 != null ? (BitmapLoadingWorkerTask)((Object)weakReference2.get()) : null;
            if (bitmapLoadingWorkerTask != null) {
                bitmapLoadingWorkerTask.cancel(true);
            }
            this.clearImageInt();
            this.mRestoreCropWindowRect = null;
            this.mRestoreDegreesRotated = 0;
            this.mCropOverlayView.setInitialCropWindowRect(null);
            this.mBitmapLoadingWorkerTask = weakReference = new WeakReference((Object)new BitmapLoadingWorkerTask(this, uri));
            ((BitmapLoadingWorkerTask)((Object)weakReference.get())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
            this.setProgressBarVisibility();
        }
    }

    public void setMaxCropResultSize(int n, int n2) {
        this.mCropOverlayView.setMaxCropResultSize(n, n2);
    }

    public void setMaxZoom(int n) {
        if (this.mMaxZoom != n && n > 0) {
            this.mMaxZoom = n;
            this.handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setMinCropResultSize(int n, int n2) {
        this.mCropOverlayView.setMinCropResultSize(n, n2);
    }

    public void setMultiTouchEnabled(boolean bl) {
        if (this.mCropOverlayView.setMultiTouchEnabled(bl)) {
            this.handleCropWindowChanged(false, false);
            this.mCropOverlayView.invalidate();
        }
    }

    public void setOnCropImageCompleteListener(OnCropImageCompleteListener onCropImageCompleteListener) {
        this.mOnCropImageCompleteListener = onCropImageCompleteListener;
    }

    public void setOnCropWindowChangedListener(OnSetCropWindowChangeListener onSetCropWindowChangeListener) {
        this.mOnSetCropWindowChangeListener = onSetCropWindowChangeListener;
    }

    public void setOnSetCropOverlayMovedListener(OnSetCropOverlayMovedListener onSetCropOverlayMovedListener) {
        this.mOnSetCropOverlayMovedListener = onSetCropOverlayMovedListener;
    }

    public void setOnSetCropOverlayReleasedListener(OnSetCropOverlayReleasedListener onSetCropOverlayReleasedListener) {
        this.mOnCropOverlayReleasedListener = onSetCropOverlayReleasedListener;
    }

    public void setOnSetImageUriCompleteListener(OnSetImageUriCompleteListener onSetImageUriCompleteListener) {
        this.mOnSetImageUriCompleteListener = onSetImageUriCompleteListener;
    }

    public void setRotatedDegrees(int n) {
        int n2 = this.mDegreesRotated;
        if (n2 != n) {
            this.rotateImage(n - n2);
        }
    }

    public void setSaveBitmapToInstanceState(boolean bl) {
        this.mSaveBitmapToInstanceState = bl;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType != this.mScaleType) {
            this.mScaleType = scaleType;
            this.mZoom = 1.0f;
            this.mZoomOffsetY = 0.0f;
            this.mZoomOffsetX = 0.0f;
            this.mCropOverlayView.resetCropOverlayView();
            this.requestLayout();
        }
    }

    public void setShowCropOverlay(boolean bl) {
        if (this.mShowCropOverlay != bl) {
            this.mShowCropOverlay = bl;
            this.setCropOverlayVisibility();
        }
    }

    public void setShowProgressBar(boolean bl) {
        if (this.mShowProgressBar != bl) {
            this.mShowProgressBar = bl;
            this.setProgressBarVisibility();
        }
    }

    public void setSnapRadius(float f) {
        if (f >= 0.0f) {
            this.mCropOverlayView.setSnapRadius(f);
        }
    }

    public void startCropWorkerTask(int n, int n2, RequestSizeOptions requestSizeOptions, Uri uri, Bitmap.CompressFormat compressFormat, int n3) {
        block1 : {
            CropImageView cropImageView;
            block5 : {
                Bitmap bitmap;
                int n4;
                int n5;
                block4 : {
                    Bitmap bitmap2;
                    block2 : {
                        int n6;
                        int n7;
                        block3 : {
                            bitmap2 = this.mBitmap;
                            if (bitmap2 == null) break block1;
                            this.mImageView.clearAnimation();
                            WeakReference<BitmapCroppingWorkerTask> weakReference = this.mBitmapCroppingWorkerTask;
                            BitmapCroppingWorkerTask bitmapCroppingWorkerTask = weakReference != null ? (BitmapCroppingWorkerTask)((Object)weakReference.get()) : null;
                            BitmapCroppingWorkerTask bitmapCroppingWorkerTask2 = bitmapCroppingWorkerTask;
                            if (bitmapCroppingWorkerTask2 != null) {
                                bitmapCroppingWorkerTask2.cancel(true);
                            }
                            n4 = requestSizeOptions != RequestSizeOptions.NONE ? n : 0;
                            n5 = requestSizeOptions != RequestSizeOptions.NONE ? n2 : 0;
                            n7 = bitmap2.getWidth() * this.mLoadedSampleSize;
                            int n8 = bitmap2.getHeight();
                            int n9 = this.mLoadedSampleSize;
                            n6 = n8 * n9;
                            if (this.mLoadedImageUri == null) break block2;
                            if (n9 > 1 || requestSizeOptions == RequestSizeOptions.SAMPLING) break block3;
                            bitmap = bitmap2;
                            cropImageView = this;
                            break block4;
                        }
                        BitmapCroppingWorkerTask bitmapCroppingWorkerTask = new BitmapCroppingWorkerTask(this, this.mLoadedImageUri, this.getCropPoints(), this.mDegreesRotated, n7, n6, this.mCropOverlayView.isFixAspectRatio(), this.mCropOverlayView.getAspectRatioX(), this.mCropOverlayView.getAspectRatioY(), n4, n5, this.mFlipHorizontally, this.mFlipVertically, requestSizeOptions, uri, compressFormat, n3);
                        WeakReference weakReference = new WeakReference((Object)bitmapCroppingWorkerTask);
                        cropImageView = this;
                        cropImageView.mBitmapCroppingWorkerTask = weakReference;
                        break block5;
                    }
                    bitmap = bitmap2;
                    cropImageView = this;
                }
                float[] arrf = this.getCropPoints();
                int n10 = cropImageView.mDegreesRotated;
                boolean bl = cropImageView.mCropOverlayView.isFixAspectRatio();
                int n11 = cropImageView.mCropOverlayView.getAspectRatioX();
                int n12 = cropImageView.mCropOverlayView.getAspectRatioY();
                boolean bl2 = cropImageView.mFlipHorizontally;
                boolean bl3 = cropImageView.mFlipVertically;
                BitmapCroppingWorkerTask bitmapCroppingWorkerTask = new BitmapCroppingWorkerTask(this, bitmap, arrf, n10, bl, n11, n12, n4, n5, bl2, bl3, requestSizeOptions, uri, compressFormat, n3);
                cropImageView.mBitmapCroppingWorkerTask = new WeakReference((Object)bitmapCroppingWorkerTask);
            }
            ((BitmapCroppingWorkerTask)((Object)cropImageView.mBitmapCroppingWorkerTask.get())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
            this.setProgressBarVisibility();
            return;
        }
    }

    public static class CropResult {
        private final Bitmap mBitmap;
        private final float[] mCropPoints;
        private final Rect mCropRect;
        private final Exception mError;
        private final Bitmap mOriginalBitmap;
        private final Uri mOriginalUri;
        private final int mRotation;
        private final int mSampleSize;
        private final Uri mUri;
        private final Rect mWholeImageRect;

        CropResult(Bitmap bitmap, Uri uri, Bitmap bitmap2, Uri uri2, Exception exception, float[] arrf, Rect rect, Rect rect2, int n, int n2) {
            this.mOriginalBitmap = bitmap;
            this.mOriginalUri = uri;
            this.mBitmap = bitmap2;
            this.mUri = uri2;
            this.mError = exception;
            this.mCropPoints = arrf;
            this.mCropRect = rect;
            this.mWholeImageRect = rect2;
            this.mRotation = n;
            this.mSampleSize = n2;
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public float[] getCropPoints() {
            return this.mCropPoints;
        }

        public Rect getCropRect() {
            return this.mCropRect;
        }

        public Exception getError() {
            return this.mError;
        }

        public Bitmap getOriginalBitmap() {
            return this.mOriginalBitmap;
        }

        public Uri getOriginalUri() {
            return this.mOriginalUri;
        }

        public int getRotation() {
            return this.mRotation;
        }

        public int getSampleSize() {
            return this.mSampleSize;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public Rect getWholeImageRect() {
            return this.mWholeImageRect;
        }

        public boolean isSuccessful() {
            return this.mError == null;
        }
    }

    public static final class CropShape
    extends Enum<CropShape> {
        private static final /* synthetic */ CropShape[] $VALUES;
        public static final /* enum */ CropShape OVAL;
        public static final /* enum */ CropShape RECTANGLE;

        static {
            CropShape cropShape;
            RECTANGLE = new CropShape();
            OVAL = cropShape = new CropShape();
            CropShape[] arrcropShape = new CropShape[]{RECTANGLE, cropShape};
            $VALUES = arrcropShape;
        }

        public static CropShape valueOf(String string2) {
            return (CropShape)Enum.valueOf(CropShape.class, (String)string2);
        }

        public static CropShape[] values() {
            return (CropShape[])$VALUES.clone();
        }
    }

    public static final class Guidelines
    extends Enum<Guidelines> {
        private static final /* synthetic */ Guidelines[] $VALUES;
        public static final /* enum */ Guidelines OFF;
        public static final /* enum */ Guidelines ON;
        public static final /* enum */ Guidelines ON_TOUCH;

        static {
            Guidelines guidelines;
            OFF = new Guidelines();
            ON_TOUCH = new Guidelines();
            ON = guidelines = new Guidelines();
            Guidelines[] arrguidelines = new Guidelines[]{OFF, ON_TOUCH, guidelines};
            $VALUES = arrguidelines;
        }

        public static Guidelines valueOf(String string2) {
            return (Guidelines)Enum.valueOf(Guidelines.class, (String)string2);
        }

        public static Guidelines[] values() {
            return (Guidelines[])$VALUES.clone();
        }
    }

    public static interface OnCropImageCompleteListener {
        public void onCropImageComplete(CropImageView var1, CropResult var2);
    }

    public static interface OnSetCropOverlayMovedListener {
        public void onCropOverlayMoved(Rect var1);
    }

    public static interface OnSetCropOverlayReleasedListener {
        public void onCropOverlayReleased(Rect var1);
    }

    public static interface OnSetCropWindowChangeListener {
        public void onCropWindowChanged();
    }

    public static interface OnSetImageUriCompleteListener {
        public void onSetImageUriComplete(CropImageView var1, Uri var2, Exception var3);
    }

    public static final class RequestSizeOptions
    extends Enum<RequestSizeOptions> {
        private static final /* synthetic */ RequestSizeOptions[] $VALUES;
        public static final /* enum */ RequestSizeOptions NONE;
        public static final /* enum */ RequestSizeOptions RESIZE_EXACT;
        public static final /* enum */ RequestSizeOptions RESIZE_FIT;
        public static final /* enum */ RequestSizeOptions RESIZE_INSIDE;
        public static final /* enum */ RequestSizeOptions SAMPLING;

        static {
            RequestSizeOptions requestSizeOptions;
            NONE = new RequestSizeOptions();
            SAMPLING = new RequestSizeOptions();
            RESIZE_INSIDE = new RequestSizeOptions();
            RESIZE_FIT = new RequestSizeOptions();
            RESIZE_EXACT = requestSizeOptions = new RequestSizeOptions();
            RequestSizeOptions[] arrrequestSizeOptions = new RequestSizeOptions[]{NONE, SAMPLING, RESIZE_INSIDE, RESIZE_FIT, requestSizeOptions};
            $VALUES = arrrequestSizeOptions;
        }

        public static RequestSizeOptions valueOf(String string2) {
            return (RequestSizeOptions)Enum.valueOf(RequestSizeOptions.class, (String)string2);
        }

        public static RequestSizeOptions[] values() {
            return (RequestSizeOptions[])$VALUES.clone();
        }
    }

    public static final class ScaleType
    extends Enum<ScaleType> {
        private static final /* synthetic */ ScaleType[] $VALUES;
        public static final /* enum */ ScaleType CENTER;
        public static final /* enum */ ScaleType CENTER_CROP;
        public static final /* enum */ ScaleType CENTER_INSIDE;
        public static final /* enum */ ScaleType FIT_CENTER;

        static {
            ScaleType scaleType;
            FIT_CENTER = new ScaleType();
            CENTER = new ScaleType();
            CENTER_CROP = new ScaleType();
            CENTER_INSIDE = scaleType = new ScaleType();
            ScaleType[] arrscaleType = new ScaleType[]{FIT_CENTER, CENTER, CENTER_CROP, scaleType};
            $VALUES = arrscaleType;
        }

        public static ScaleType valueOf(String string2) {
            return (ScaleType)Enum.valueOf(ScaleType.class, (String)string2);
        }

        public static ScaleType[] values() {
            return (ScaleType[])$VALUES.clone();
        }
    }

}

