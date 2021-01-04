/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ResolveInfo
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Xfermode
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  androidx.fragment.app.Fragment
 *  java.io.File
 *  java.io.InputStream
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.R;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CropImage {
    public static final int CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE = 2011;
    public static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203;
    public static final int CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE = 204;
    public static final String CROP_IMAGE_EXTRA_BUNDLE = "CROP_IMAGE_EXTRA_BUNDLE";
    public static final String CROP_IMAGE_EXTRA_OPTIONS = "CROP_IMAGE_EXTRA_OPTIONS";
    public static final String CROP_IMAGE_EXTRA_RESULT = "CROP_IMAGE_EXTRA_RESULT";
    public static final String CROP_IMAGE_EXTRA_SOURCE = "CROP_IMAGE_EXTRA_SOURCE";
    public static final int PICK_IMAGE_CHOOSER_REQUEST_CODE = 200;
    public static final int PICK_IMAGE_PERMISSIONS_REQUEST_CODE = 201;

    private CropImage() {
    }

    public static ActivityBuilder activity() {
        return new ActivityBuilder(null);
    }

    public static ActivityBuilder activity(Uri uri) {
        return new ActivityBuilder(uri);
    }

    public static ActivityResult getActivityResult(Intent intent) {
        if (intent != null) {
            return (ActivityResult)intent.getParcelableExtra(CROP_IMAGE_EXTRA_RESULT);
        }
        return null;
    }

    public static Intent getCameraIntent(Context context, Uri uri) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (uri == null) {
            uri = CropImage.getCaptureImageOutputUri(context);
        }
        intent.putExtra("output", (Parcelable)uri);
        return intent;
    }

    public static List<Intent> getCameraIntents(Context context, PackageManager packageManager) {
        ArrayList arrayList = new ArrayList();
        Uri uri = CropImage.getCaptureImageOutputUri(context);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            intent2.setPackage(resolveInfo.activityInfo.packageName);
            if (uri != null) {
                intent2.putExtra("output", (Parcelable)uri);
            }
            arrayList.add((Object)intent2);
        }
        return arrayList;
    }

    public static Uri getCaptureImageOutputUri(Context context) {
        File file = context.getExternalCacheDir();
        Uri uri = null;
        if (file != null) {
            uri = Uri.fromFile((File)new File(file.getPath(), "pickImageResult.jpeg"));
        }
        return uri;
    }

    public static List<Intent> getGalleryIntents(PackageManager packageManager, String string2, boolean bl) {
        ArrayList arrayList = new ArrayList();
        Intent intent = string2 == "android.intent.action.GET_CONTENT" ? new Intent(string2) : new Intent(string2, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            intent2.setPackage(resolveInfo.activityInfo.packageName);
            arrayList.add((Object)intent2);
        }
        if (!bl) {
            for (Intent intent3 : arrayList) {
                if (!intent3.getComponent().getClassName().equals((Object)"com.android.documentsui.DocumentsActivity")) continue;
                arrayList.remove((Object)intent3);
                return arrayList;
            }
        }
        return arrayList;
    }

    public static Intent getPickImageChooserIntent(Context context) {
        return CropImage.getPickImageChooserIntent(context, context.getString(R.string.pick_image_intent_chooser_title), false, true);
    }

    public static Intent getPickImageChooserIntent(Context context, CharSequence charSequence, boolean bl, boolean bl2) {
        Intent intent;
        List<Intent> list;
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        if (!CropImage.isExplicitCameraPermissionRequired(context) && bl2) {
            arrayList.addAll(CropImage.getCameraIntents(context, packageManager));
        }
        if ((list = CropImage.getGalleryIntents(packageManager, "android.intent.action.GET_CONTENT", bl)).size() == 0) {
            list = CropImage.getGalleryIntents(packageManager, "android.intent.action.PICK", bl);
        }
        arrayList.addAll(list);
        if (arrayList.isEmpty()) {
            intent = new Intent();
        } else {
            intent = (Intent)arrayList.get(-1 + arrayList.size());
            arrayList.remove(-1 + arrayList.size());
        }
        Intent intent2 = Intent.createChooser((Intent)intent, (CharSequence)charSequence);
        intent2.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[])arrayList.toArray((Object[])new Parcelable[arrayList.size()]));
        return intent2;
    }

    public static Uri getPickImageResultUri(Context context, Intent intent) {
        boolean bl = true;
        if (intent != null && intent.getData() != null) {
            String string2 = intent.getAction();
            boolean bl2 = string2 != null && string2.equals((Object)"android.media.action.IMAGE_CAPTURE");
            bl = bl2;
        }
        if (!bl && intent.getData() != null) {
            return intent.getData();
        }
        return CropImage.getCaptureImageOutputUri(context);
    }

    public static boolean hasPermissionInManifest(Context context, String string2) {
        block5 : {
            int n;
            String string3 = context.getPackageName();
            String[] arrstring = context.getPackageManager().getPackageInfo((String)string3, (int)4096).requestedPermissions;
            if (arrstring == null) break block5;
            try {
                if (arrstring.length <= 0) break block5;
                n = arrstring.length;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                boolean bl = arrstring[i].equalsIgnoreCase(string2);
                if (!bl) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isExplicitCameraPermissionRequired(Context context) {
        return Build.VERSION.SDK_INT >= 23 && CropImage.hasPermissionInManifest(context, "android.permission.CAMERA") && context.checkSelfPermission("android.permission.CAMERA") != 0;
    }

    public static boolean isReadExternalStoragePermissionsRequired(Context context, Uri uri) {
        return Build.VERSION.SDK_INT >= 23 && context.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0 && CropImage.isUriRequiresPermissions(context, uri);
    }

    public static boolean isUriRequiresPermissions(Context context, Uri uri) {
        block3 : {
            InputStream inputStream;
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                if (inputStream == null) break block3;
            }
            catch (Exception exception) {
                return true;
            }
            inputStream.close();
        }
        return false;
    }

    public static void startPickImageActivity(Activity activity) {
        activity.startActivityForResult(CropImage.getPickImageChooserIntent((Context)activity), 200);
    }

    public static void startPickImageActivity(Context context, androidx.fragment.app.Fragment fragment) {
        fragment.startActivityForResult(CropImage.getPickImageChooserIntent(context), 200);
    }

    public static Bitmap toOvalBitmap(Bitmap bitmap) {
        int n = bitmap.getWidth();
        int n2 = bitmap.getHeight();
        Bitmap bitmap2 = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawOval(new RectF(0.0f, 0.0f, (float)n, (float)n2), paint);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        bitmap.recycle();
        return bitmap2;
    }

    public static final class ActivityBuilder {
        private final CropImageOptions mOptions;
        private final Uri mSource;

        private ActivityBuilder(Uri uri) {
            this.mSource = uri;
            this.mOptions = new CropImageOptions();
        }

        public Intent getIntent(Context context) {
            return this.getIntent(context, CropImageActivity.class);
        }

        public Intent getIntent(Context context, Class<?> class_) {
            this.mOptions.validate();
            Intent intent = new Intent();
            intent.setClass(context, class_);
            Bundle bundle = new Bundle();
            bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, (Parcelable)this.mSource);
            bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, (Parcelable)this.mOptions);
            intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle);
            return intent;
        }

        public ActivityBuilder setActivityMenuIconColor(int n) {
            this.mOptions.activityMenuIconColor = n;
            return this;
        }

        public ActivityBuilder setActivityTitle(CharSequence charSequence) {
            this.mOptions.activityTitle = charSequence;
            return this;
        }

        public ActivityBuilder setAllowCounterRotation(boolean bl) {
            this.mOptions.allowCounterRotation = bl;
            return this;
        }

        public ActivityBuilder setAllowFlipping(boolean bl) {
            this.mOptions.allowFlipping = bl;
            return this;
        }

        public ActivityBuilder setAllowRotation(boolean bl) {
            this.mOptions.allowRotation = bl;
            return this;
        }

        public ActivityBuilder setAspectRatio(int n, int n2) {
            this.mOptions.aspectRatioX = n;
            this.mOptions.aspectRatioY = n2;
            this.mOptions.fixAspectRatio = true;
            return this;
        }

        public ActivityBuilder setAutoZoomEnabled(boolean bl) {
            this.mOptions.autoZoomEnabled = bl;
            return this;
        }

        public ActivityBuilder setBackgroundColor(int n) {
            this.mOptions.backgroundColor = n;
            return this;
        }

        public ActivityBuilder setBorderCornerColor(int n) {
            this.mOptions.borderCornerColor = n;
            return this;
        }

        public ActivityBuilder setBorderCornerLength(float f) {
            this.mOptions.borderCornerLength = f;
            return this;
        }

        public ActivityBuilder setBorderCornerOffset(float f) {
            this.mOptions.borderCornerOffset = f;
            return this;
        }

        public ActivityBuilder setBorderCornerThickness(float f) {
            this.mOptions.borderCornerThickness = f;
            return this;
        }

        public ActivityBuilder setBorderLineColor(int n) {
            this.mOptions.borderLineColor = n;
            return this;
        }

        public ActivityBuilder setBorderLineThickness(float f) {
            this.mOptions.borderLineThickness = f;
            return this;
        }

        public ActivityBuilder setCropMenuCropButtonIcon(int n) {
            this.mOptions.cropMenuCropButtonIcon = n;
            return this;
        }

        public ActivityBuilder setCropMenuCropButtonTitle(CharSequence charSequence) {
            this.mOptions.cropMenuCropButtonTitle = charSequence;
            return this;
        }

        public ActivityBuilder setCropShape(CropImageView.CropShape cropShape) {
            this.mOptions.cropShape = cropShape;
            return this;
        }

        public ActivityBuilder setFixAspectRatio(boolean bl) {
            this.mOptions.fixAspectRatio = bl;
            return this;
        }

        public ActivityBuilder setFlipHorizontally(boolean bl) {
            this.mOptions.flipHorizontally = bl;
            return this;
        }

        public ActivityBuilder setFlipVertically(boolean bl) {
            this.mOptions.flipVertically = bl;
            return this;
        }

        public ActivityBuilder setGuidelines(CropImageView.Guidelines guidelines) {
            this.mOptions.guidelines = guidelines;
            return this;
        }

        public ActivityBuilder setGuidelinesColor(int n) {
            this.mOptions.guidelinesColor = n;
            return this;
        }

        public ActivityBuilder setGuidelinesThickness(float f) {
            this.mOptions.guidelinesThickness = f;
            return this;
        }

        public ActivityBuilder setInitialCropWindowPaddingRatio(float f) {
            this.mOptions.initialCropWindowPaddingRatio = f;
            return this;
        }

        public ActivityBuilder setInitialCropWindowRectangle(Rect rect) {
            this.mOptions.initialCropWindowRectangle = rect;
            return this;
        }

        public ActivityBuilder setInitialRotation(int n) {
            this.mOptions.initialRotation = (n + 360) % 360;
            return this;
        }

        public ActivityBuilder setMaxCropResultSize(int n, int n2) {
            this.mOptions.maxCropResultWidth = n;
            this.mOptions.maxCropResultHeight = n2;
            return this;
        }

        public ActivityBuilder setMaxZoom(int n) {
            this.mOptions.maxZoom = n;
            return this;
        }

        public ActivityBuilder setMinCropResultSize(int n, int n2) {
            this.mOptions.minCropResultWidth = n;
            this.mOptions.minCropResultHeight = n2;
            return this;
        }

        public ActivityBuilder setMinCropWindowSize(int n, int n2) {
            this.mOptions.minCropWindowWidth = n;
            this.mOptions.minCropWindowHeight = n2;
            return this;
        }

        public ActivityBuilder setMultiTouchEnabled(boolean bl) {
            this.mOptions.multiTouchEnabled = bl;
            return this;
        }

        public ActivityBuilder setNoOutputImage(boolean bl) {
            this.mOptions.noOutputImage = bl;
            return this;
        }

        public ActivityBuilder setOutputCompressFormat(Bitmap.CompressFormat compressFormat) {
            this.mOptions.outputCompressFormat = compressFormat;
            return this;
        }

        public ActivityBuilder setOutputCompressQuality(int n) {
            this.mOptions.outputCompressQuality = n;
            return this;
        }

        public ActivityBuilder setOutputUri(Uri uri) {
            this.mOptions.outputUri = uri;
            return this;
        }

        public ActivityBuilder setRequestedSize(int n, int n2) {
            return this.setRequestedSize(n, n2, CropImageView.RequestSizeOptions.RESIZE_INSIDE);
        }

        public ActivityBuilder setRequestedSize(int n, int n2, CropImageView.RequestSizeOptions requestSizeOptions) {
            this.mOptions.outputRequestWidth = n;
            this.mOptions.outputRequestHeight = n2;
            this.mOptions.outputRequestSizeOptions = requestSizeOptions;
            return this;
        }

        public ActivityBuilder setRotationDegrees(int n) {
            this.mOptions.rotationDegrees = (n + 360) % 360;
            return this;
        }

        public ActivityBuilder setScaleType(CropImageView.ScaleType scaleType) {
            this.mOptions.scaleType = scaleType;
            return this;
        }

        public ActivityBuilder setShowCropOverlay(boolean bl) {
            this.mOptions.showCropOverlay = bl;
            return this;
        }

        public ActivityBuilder setSnapRadius(float f) {
            this.mOptions.snapRadius = f;
            return this;
        }

        public ActivityBuilder setTouchRadius(float f) {
            this.mOptions.touchRadius = f;
            return this;
        }

        public void start(Activity activity) {
            this.mOptions.validate();
            activity.startActivityForResult(this.getIntent((Context)activity), 203);
        }

        public void start(Activity activity, Class<?> class_) {
            this.mOptions.validate();
            activity.startActivityForResult(this.getIntent((Context)activity, class_), 203);
        }

        public void start(Context context, Fragment fragment) {
            fragment.startActivityForResult(this.getIntent(context), 203);
        }

        public void start(Context context, Fragment fragment, Class<?> class_) {
            fragment.startActivityForResult(this.getIntent(context, class_), 203);
        }

        public void start(Context context, androidx.fragment.app.Fragment fragment) {
            fragment.startActivityForResult(this.getIntent(context), 203);
        }

        public void start(Context context, androidx.fragment.app.Fragment fragment, Class<?> class_) {
            fragment.startActivityForResult(this.getIntent(context, class_), 203);
        }
    }

    public static final class ActivityResult
    extends CropImageView.CropResult
    implements Parcelable {
        public static final Creator<ActivityResult> CREATOR = new Creator<ActivityResult>(){

            public ActivityResult createFromParcel(Parcel parcel) {
                return new ActivityResult(parcel);
            }

            public ActivityResult[] newArray(int n) {
                return new ActivityResult[n];
            }
        };

        public ActivityResult(Uri uri, Uri uri2, Exception exception, float[] arrf, Rect rect, int n, Rect rect2, int n2) {
            super(null, uri, null, uri2, exception, arrf, rect, rect2, n, n2);
        }

        protected ActivityResult(Parcel parcel) {
            super(null, (Uri)parcel.readParcelable(Uri.class.getClassLoader()), null, (Uri)parcel.readParcelable(Uri.class.getClassLoader()), (Exception)parcel.readSerializable(), parcel.createFloatArray(), (Rect)parcel.readParcelable(Rect.class.getClassLoader()), (Rect)parcel.readParcelable(Rect.class.getClassLoader()), parcel.readInt(), parcel.readInt());
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeParcelable((Parcelable)this.getOriginalUri(), n);
            parcel.writeParcelable((Parcelable)this.getUri(), n);
            parcel.writeSerializable((Serializable)this.getError());
            parcel.writeFloatArray(this.getCropPoints());
            parcel.writeParcelable((Parcelable)this.getCropRect(), n);
            parcel.writeParcelable((Parcelable)this.getWholeImageRect(), n);
            parcel.writeInt(this.getRotation());
            parcel.writeInt(this.getSampleSize());
        }

    }

}

