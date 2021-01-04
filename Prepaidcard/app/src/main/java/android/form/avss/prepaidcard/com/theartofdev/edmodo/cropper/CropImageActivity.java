/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.Toast
 *  androidx.appcompat.app.ActionBar
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.core.content.ContextCompat
 *  java.io.File
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.R;
import java.io.File;
import java.io.IOException;

public class CropImageActivity
extends AppCompatActivity
implements CropImageView.OnSetImageUriCompleteListener,
CropImageView.OnCropImageCompleteListener {
    private Uri mCropImageUri;
    private CropImageView mCropImageView;
    private CropImageOptions mOptions;

    private void updateMenuItemIconColor(Menu menu2, int n, int n2) {
        Drawable drawable2;
        MenuItem menuItem = menu2.findItem(n);
        if (menuItem != null && (drawable2 = menuItem.getIcon()) != null) {
            try {
                drawable2.mutate();
                drawable2.setColorFilter(n2, PorterDuff.Mode.SRC_ATOP);
                menuItem.setIcon(drawable2);
                return;
            }
            catch (Exception exception) {
                Log.w((String)"AIC", (String)"Failed to update menu item color", (Throwable)exception);
            }
        }
    }

    protected void cropImage() {
        if (this.mOptions.noOutputImage) {
            this.setResult(null, null, 1);
            return;
        }
        Uri uri = this.getOutputUri();
        this.mCropImageView.saveCroppedImageAsync(uri, this.mOptions.outputCompressFormat, this.mOptions.outputCompressQuality, this.mOptions.outputRequestWidth, this.mOptions.outputRequestHeight, this.mOptions.outputRequestSizeOptions);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Uri getOutputUri() {
        Uri uri = this.mOptions.outputUri;
        if (uri != null) {
            if (!uri.equals((Object)Uri.EMPTY)) return uri;
        }
        try {
            String string2 = this.mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG ? ".jpg" : (this.mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp");
            Uri uri2 = Uri.fromFile((File)File.createTempFile((String)"cropped", (String)string2, (File)this.getCacheDir()));
            return uri2;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Failed to create temp file for output image", (Throwable)iOException);
        }
    }

    protected Intent getResultIntent(Uri uri, Exception exception, int n) {
        CropImage.ActivityResult activityResult = new CropImage.ActivityResult(this.mCropImageView.getImageUri(), uri, exception, this.mCropImageView.getCropPoints(), this.mCropImageView.getCropRect(), this.mCropImageView.getRotatedDegrees(), this.mCropImageView.getWholeImageRect(), n);
        Intent intent = new Intent();
        intent.putExtras(this.getIntent());
        intent.putExtra("CROP_IMAGE_EXTRA_RESULT", (Parcelable)activityResult);
        return intent;
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 200) {
            if (n2 == 0) {
                this.setResultCancel();
            }
            if (n2 == -1) {
                Uri uri;
                this.mCropImageUri = uri = CropImage.getPickImageResultUri((Context)this, intent);
                if (CropImage.isReadExternalStoragePermissionsRequired((Context)this, uri)) {
                    this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 201);
                    return;
                }
                this.mCropImageView.setImageUriAsync(this.mCropImageUri);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.setResultCancel();
    }

    public void onCreate(Bundle bundle) {
        ActionBar actionBar;
        super.onCreate(bundle);
        this.setContentView(R.layout.crop_image_activity);
        this.mCropImageView = (CropImageView)this.findViewById(R.id.cropImageView);
        Bundle bundle2 = this.getIntent().getBundleExtra("CROP_IMAGE_EXTRA_BUNDLE");
        this.mCropImageUri = (Uri)bundle2.getParcelable("CROP_IMAGE_EXTRA_SOURCE");
        this.mOptions = (CropImageOptions)bundle2.getParcelable("CROP_IMAGE_EXTRA_OPTIONS");
        if (bundle == null) {
            Uri uri = this.mCropImageUri;
            if (uri != null && !uri.equals((Object)Uri.EMPTY)) {
                if (CropImage.isReadExternalStoragePermissionsRequired((Context)this, this.mCropImageUri)) {
                    this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 201);
                } else {
                    this.mCropImageView.setImageUriAsync(this.mCropImageUri);
                }
            } else if (CropImage.isExplicitCameraPermissionRequired((Context)this)) {
                this.requestPermissions(new String[]{"android.permission.CAMERA"}, 2011);
            } else {
                CropImage.startPickImageActivity((Activity)this);
            }
        }
        if ((actionBar = this.getSupportActionBar()) != null) {
            String string2 = this.mOptions.activityTitle != null && this.mOptions.activityTitle.length() > 0 ? this.mOptions.activityTitle : this.getResources().getString(R.string.crop_image_activity_title);
            actionBar.setTitle((CharSequence)string2);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(R.menu.crop_image_menu, menu2);
        if (!this.mOptions.allowRotation) {
            menu2.removeItem(R.id.crop_image_menu_rotate_left);
            menu2.removeItem(R.id.crop_image_menu_rotate_right);
        } else if (this.mOptions.allowCounterRotation) {
            menu2.findItem(R.id.crop_image_menu_rotate_left).setVisible(true);
        }
        if (!this.mOptions.allowFlipping) {
            menu2.removeItem(R.id.crop_image_menu_flip);
        }
        if (this.mOptions.cropMenuCropButtonTitle != null) {
            menu2.findItem(R.id.crop_image_menu_crop).setTitle(this.mOptions.cropMenuCropButtonTitle);
        }
        Drawable drawable2 = null;
        try {
            int n = this.mOptions.cropMenuCropButtonIcon;
            drawable2 = null;
            if (n != 0) {
                drawable2 = ContextCompat.getDrawable((Context)this, (int)this.mOptions.cropMenuCropButtonIcon);
                menu2.findItem(R.id.crop_image_menu_crop).setIcon(drawable2);
            }
        }
        catch (Exception exception) {
            Log.w((String)"AIC", (String)"Failed to read menu crop drawable", (Throwable)exception);
        }
        if (this.mOptions.activityMenuIconColor != 0) {
            this.updateMenuItemIconColor(menu2, R.id.crop_image_menu_rotate_left, this.mOptions.activityMenuIconColor);
            this.updateMenuItemIconColor(menu2, R.id.crop_image_menu_rotate_right, this.mOptions.activityMenuIconColor);
            this.updateMenuItemIconColor(menu2, R.id.crop_image_menu_flip, this.mOptions.activityMenuIconColor);
            if (drawable2 != null) {
                this.updateMenuItemIconColor(menu2, R.id.crop_image_menu_crop, this.mOptions.activityMenuIconColor);
            }
        }
        return true;
    }

    @Override
    public void onCropImageComplete(CropImageView cropImageView, CropImageView.CropResult cropResult) {
        this.setResult(cropResult.getUri(), cropResult.getError(), cropResult.getSampleSize());
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.crop_image_menu_crop) {
            this.cropImage();
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_rotate_left) {
            this.rotateImage(-this.mOptions.rotationDegrees);
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_rotate_right) {
            this.rotateImage(this.mOptions.rotationDegrees);
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_flip_horizontally) {
            this.mCropImageView.flipImageHorizontally();
            return true;
        }
        if (menuItem.getItemId() == R.id.crop_image_menu_flip_vertically) {
            this.mCropImageView.flipImageVertically();
            return true;
        }
        if (menuItem.getItemId() == 16908332) {
            this.setResultCancel();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        if (n == 201) {
            Uri uri = this.mCropImageUri;
            if (uri != null && arrn.length > 0 && arrn[0] == 0) {
                this.mCropImageView.setImageUriAsync(uri);
            } else {
                Toast.makeText((Context)this, (int)R.string.crop_image_activity_no_permissions, (int)1).show();
                this.setResultCancel();
            }
        }
        if (n == 2011) {
            CropImage.startPickImageActivity((Activity)this);
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView cropImageView, Uri uri, Exception exception) {
        if (exception == null) {
            if (this.mOptions.initialCropWindowRectangle != null) {
                this.mCropImageView.setCropRect(this.mOptions.initialCropWindowRectangle);
            }
            if (this.mOptions.initialRotation > -1) {
                this.mCropImageView.setRotatedDegrees(this.mOptions.initialRotation);
                return;
            }
        } else {
            this.setResult(null, exception, 1);
        }
    }

    protected void onStart() {
        super.onStart();
        this.mCropImageView.setOnSetImageUriCompleteListener(this);
        this.mCropImageView.setOnCropImageCompleteListener(this);
    }

    protected void onStop() {
        super.onStop();
        this.mCropImageView.setOnSetImageUriCompleteListener(null);
        this.mCropImageView.setOnCropImageCompleteListener(null);
    }

    protected void rotateImage(int n) {
        this.mCropImageView.rotateImage(n);
    }

    protected void setResult(Uri uri, Exception exception, int n) {
        int n2 = exception == null ? -1 : 204;
        this.setResult(n2, this.getResultIntent(uri, exception, n));
        this.finish();
    }

    protected void setResultCancel() {
        this.setResult(0);
        this.finish();
    }
}

