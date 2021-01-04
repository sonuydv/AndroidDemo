/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.util.DisplayMetrics
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Void
 *  java.lang.ref.WeakReference
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import com.theartofdev.edmodo.cropper.BitmapUtils;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.lang.ref.WeakReference;

final class BitmapLoadingWorkerTask
extends AsyncTask<Void, Void, Result> {
    private final Context mContext;
    private final WeakReference<CropImageView> mCropImageViewReference;
    private final int mHeight;
    private final Uri mUri;
    private final int mWidth;

    public BitmapLoadingWorkerTask(CropImageView cropImageView, Uri uri) {
        this.mUri = uri;
        this.mCropImageViewReference = new WeakReference((Object)cropImageView);
        this.mContext = cropImageView.getContext();
        DisplayMetrics displayMetrics = cropImageView.getResources().getDisplayMetrics();
        double d = displayMetrics.density > 1.0f ? (double)(1.0f / displayMetrics.density) : 1.0;
        this.mWidth = (int)(d * (double)displayMetrics.widthPixels);
        this.mHeight = (int)(d * (double)displayMetrics.heightPixels);
    }

    protected /* varargs */ Result doInBackground(Void ... arrvoid) {
        try {
            if (!this.isCancelled()) {
                BitmapUtils.BitmapSampled bitmapSampled = BitmapUtils.decodeSampledBitmap(this.mContext, this.mUri, this.mWidth, this.mHeight);
                if (!this.isCancelled()) {
                    BitmapUtils.RotateBitmapResult rotateBitmapResult = BitmapUtils.rotateBitmapByExif(bitmapSampled.bitmap, this.mContext, this.mUri);
                    Result result = new Result(this.mUri, rotateBitmapResult.bitmap, bitmapSampled.sampleSize, rotateBitmapResult.degrees);
                    return result;
                }
            }
            return null;
        }
        catch (Exception exception) {
            return new Result(this.mUri, exception);
        }
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
                    cropImageView.onSetImageUriAsyncComplete(result);
                }
            }
            if (!bl2 && result.bitmap != null) {
                result.bitmap.recycle();
            }
        }
    }

    public static final class Result {
        public final Bitmap bitmap;
        public final int degreesRotated;
        public final Exception error;
        public final int loadSampleSize;
        public final Uri uri;

        Result(Uri uri, Bitmap bitmap, int n, int n2) {
            this.uri = uri;
            this.bitmap = bitmap;
            this.loadSampleSize = n;
            this.degreesRotated = n2;
            this.error = null;
        }

        Result(Uri uri, Exception exception) {
            this.uri = uri;
            this.bitmap = null;
            this.loadSampleSize = 0;
            this.degreesRotated = 0;
            this.error = exception;
        }
    }

}

