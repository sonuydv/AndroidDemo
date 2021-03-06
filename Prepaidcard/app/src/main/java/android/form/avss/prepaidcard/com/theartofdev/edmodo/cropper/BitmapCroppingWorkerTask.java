/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.net.Uri
 *  android.os.AsyncTask
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Void
 *  java.lang.ref.WeakReference
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import com.theartofdev.edmodo.cropper.BitmapUtils;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.lang.ref.WeakReference;

final class BitmapCroppingWorkerTask
extends AsyncTask<Void, Void, Result> {
    private final int mAspectRatioX;
    private final int mAspectRatioY;
    private final Bitmap mBitmap;
    private final Context mContext;
    private final WeakReference<CropImageView> mCropImageViewReference;
    private final float[] mCropPoints;
    private final int mDegreesRotated;
    private final boolean mFixAspectRatio;
    private final boolean mFlipHorizontally;
    private final boolean mFlipVertically;
    private final int mOrgHeight;
    private final int mOrgWidth;
    private final int mReqHeight;
    private final CropImageView.RequestSizeOptions mReqSizeOptions;
    private final int mReqWidth;
    private final Bitmap.CompressFormat mSaveCompressFormat;
    private final int mSaveCompressQuality;
    private final Uri mSaveUri;
    private final Uri mUri;

    BitmapCroppingWorkerTask(CropImageView cropImageView, Bitmap bitmap, float[] arrf, int n, boolean bl, int n2, int n3, int n4, int n5, boolean bl2, boolean bl3, CropImageView.RequestSizeOptions requestSizeOptions, Uri uri, Bitmap.CompressFormat compressFormat, int n6) {
        this.mCropImageViewReference = new WeakReference((Object)cropImageView);
        this.mContext = cropImageView.getContext();
        this.mBitmap = bitmap;
        this.mCropPoints = arrf;
        this.mUri = null;
        this.mDegreesRotated = n;
        this.mFixAspectRatio = bl;
        this.mAspectRatioX = n2;
        this.mAspectRatioY = n3;
        this.mReqWidth = n4;
        this.mReqHeight = n5;
        this.mFlipHorizontally = bl2;
        this.mFlipVertically = bl3;
        this.mReqSizeOptions = requestSizeOptions;
        this.mSaveUri = uri;
        this.mSaveCompressFormat = compressFormat;
        this.mSaveCompressQuality = n6;
        this.mOrgWidth = 0;
        this.mOrgHeight = 0;
    }

    BitmapCroppingWorkerTask(CropImageView cropImageView, Uri uri, float[] arrf, int n, int n2, int n3, boolean bl, int n4, int n5, int n6, int n7, boolean bl2, boolean bl3, CropImageView.RequestSizeOptions requestSizeOptions, Uri uri2, Bitmap.CompressFormat compressFormat, int n8) {
        this.mCropImageViewReference = new WeakReference((Object)cropImageView);
        this.mContext = cropImageView.getContext();
        this.mUri = uri;
        this.mCropPoints = arrf;
        this.mDegreesRotated = n;
        this.mFixAspectRatio = bl;
        this.mAspectRatioX = n4;
        this.mAspectRatioY = n5;
        this.mOrgWidth = n2;
        this.mOrgHeight = n3;
        this.mReqWidth = n6;
        this.mReqHeight = n7;
        this.mFlipHorizontally = bl2;
        this.mFlipVertically = bl3;
        this.mReqSizeOptions = requestSizeOptions;
        this.mSaveUri = uri2;
        this.mSaveCompressFormat = compressFormat;
        this.mSaveCompressQuality = n8;
        this.mBitmap = null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected /* varargs */ Result doInBackground(Void ... arrvoid) {
        BitmapUtils.BitmapSampled bitmapSampled;
        Bitmap bitmap;
        int n = 1;
        try {
            if (this.isCancelled()) return null;
            if (this.mUri != null) {
                bitmapSampled = BitmapUtils.cropBitmap(this.mContext, this.mUri, this.mCropPoints, this.mDegreesRotated, this.mOrgWidth, this.mOrgHeight, this.mFixAspectRatio, this.mAspectRatioX, this.mAspectRatioY, this.mReqWidth, this.mReqHeight, this.mFlipHorizontally, this.mFlipVertically);
            } else {
                if (this.mBitmap == null) return new Result((Bitmap)null, n);
                bitmapSampled = BitmapUtils.cropBitmapObjectHandleOOM(this.mBitmap, this.mCropPoints, this.mDegreesRotated, this.mFixAspectRatio, this.mAspectRatioX, this.mAspectRatioY, this.mFlipHorizontally, this.mFlipVertically);
            }
            bitmap = BitmapUtils.resizeBitmap(bitmapSampled.bitmap, this.mReqWidth, this.mReqHeight, this.mReqSizeOptions);
            if (this.mSaveUri == null) {
                return new Result(bitmap, bitmapSampled.sampleSize);
            }
            BitmapUtils.writeBitmapToUri(this.mContext, bitmap, this.mSaveUri, this.mSaveCompressFormat, this.mSaveCompressQuality);
            if (bitmap == null) return new Result(this.mSaveUri, bitmapSampled.sampleSize);
        }
        catch (Exception exception) {
            if (this.mSaveUri != null) return new Result(exception, (boolean)n);
            n = 0;
            return new Result(exception, (boolean)n);
        }
        bitmap.recycle();
        return new Result(this.mSaveUri, bitmapSampled.sampleSize);
    }

    public Uri getUri() {
        return this.mUri;
    }

    protected void onPostExecute(Result result) {
        if (result != null) {
            boolean bl = this.isCancelled();
            boolean bl2 = false;
            if (!bl) {
                CropImageView cropImageView = (CropImageView)((Object)this.mCropImageViewReference.get());
                bl2 = false;
                if (cropImageView != null) {
                    bl2 = true;
                    cropImageView.onImageCroppingAsyncComplete(result);
                }
            }
            if (!bl2 && result.bitmap != null) {
                result.bitmap.recycle();
            }
        }
    }

    static final class Result {
        public final Bitmap bitmap;
        final Exception error;
        final boolean isSave;
        final int sampleSize;
        public final Uri uri;

        Result(Bitmap bitmap, int n) {
            this.bitmap = bitmap;
            this.uri = null;
            this.error = null;
            this.isSave = false;
            this.sampleSize = n;
        }

        Result(Uri uri, int n) {
            this.bitmap = null;
            this.uri = uri;
            this.error = null;
            this.isSave = true;
            this.sampleSize = n;
        }

        Result(Exception exception, boolean bl) {
            this.bitmap = null;
            this.uri = null;
            this.error = exception;
            this.isSave = bl;
            this.sampleSize = 1;
        }
    }

}

