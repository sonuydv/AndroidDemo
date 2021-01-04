/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Color
 *  android.graphics.Rect
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  java.lang.CharSequence
 *  java.lang.ClassLoader
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropImageOptions
implements Parcelable {
    public static final Creator<CropImageOptions> CREATOR = new Creator<CropImageOptions>(){

        public CropImageOptions createFromParcel(Parcel parcel) {
            return new CropImageOptions(parcel);
        }

        public CropImageOptions[] newArray(int n) {
            return new CropImageOptions[n];
        }
    };
    public int activityMenuIconColor;
    public CharSequence activityTitle;
    public boolean allowCounterRotation;
    public boolean allowFlipping;
    public boolean allowRotation;
    public int aspectRatioX;
    public int aspectRatioY;
    public boolean autoZoomEnabled;
    public int backgroundColor;
    public int borderCornerColor;
    public float borderCornerLength;
    public float borderCornerOffset;
    public float borderCornerThickness;
    public int borderLineColor;
    public float borderLineThickness;
    public int cropMenuCropButtonIcon;
    public CharSequence cropMenuCropButtonTitle;
    public CropImageView.CropShape cropShape;
    public boolean fixAspectRatio;
    public boolean flipHorizontally;
    public boolean flipVertically;
    public CropImageView.Guidelines guidelines;
    public int guidelinesColor;
    public float guidelinesThickness;
    public float initialCropWindowPaddingRatio;
    public Rect initialCropWindowRectangle;
    public int initialRotation;
    public int maxCropResultHeight;
    public int maxCropResultWidth;
    public int maxZoom;
    public int minCropResultHeight;
    public int minCropResultWidth;
    public int minCropWindowHeight;
    public int minCropWindowWidth;
    public boolean multiTouchEnabled;
    public boolean noOutputImage;
    public Bitmap.CompressFormat outputCompressFormat;
    public int outputCompressQuality;
    public int outputRequestHeight;
    public CropImageView.RequestSizeOptions outputRequestSizeOptions;
    public int outputRequestWidth;
    public Uri outputUri;
    public int rotationDegrees;
    public CropImageView.ScaleType scaleType;
    public boolean showCropOverlay;
    public boolean showProgressBar;
    public float snapRadius;
    public float touchRadius;

    public CropImageOptions() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        this.cropShape = CropImageView.CropShape.RECTANGLE;
        this.snapRadius = TypedValue.applyDimension((int)1, (float)3.0f, (DisplayMetrics)displayMetrics);
        this.touchRadius = TypedValue.applyDimension((int)1, (float)24.0f, (DisplayMetrics)displayMetrics);
        this.guidelines = CropImageView.Guidelines.ON_TOUCH;
        this.scaleType = CropImageView.ScaleType.FIT_CENTER;
        this.showCropOverlay = true;
        this.showProgressBar = true;
        this.autoZoomEnabled = true;
        this.multiTouchEnabled = false;
        this.maxZoom = 4;
        this.initialCropWindowPaddingRatio = 0.1f;
        this.fixAspectRatio = false;
        this.aspectRatioX = 1;
        this.aspectRatioY = 1;
        this.borderLineThickness = TypedValue.applyDimension((int)1, (float)3.0f, (DisplayMetrics)displayMetrics);
        this.borderLineColor = Color.argb((int)170, (int)255, (int)255, (int)255);
        this.borderCornerThickness = TypedValue.applyDimension((int)1, (float)2.0f, (DisplayMetrics)displayMetrics);
        this.borderCornerOffset = TypedValue.applyDimension((int)1, (float)5.0f, (DisplayMetrics)displayMetrics);
        this.borderCornerLength = TypedValue.applyDimension((int)1, (float)14.0f, (DisplayMetrics)displayMetrics);
        this.borderCornerColor = -1;
        this.guidelinesThickness = TypedValue.applyDimension((int)1, (float)1.0f, (DisplayMetrics)displayMetrics);
        this.guidelinesColor = Color.argb((int)170, (int)255, (int)255, (int)255);
        this.backgroundColor = Color.argb((int)119, (int)0, (int)0, (int)0);
        this.minCropWindowWidth = (int)TypedValue.applyDimension((int)1, (float)42.0f, (DisplayMetrics)displayMetrics);
        this.minCropWindowHeight = (int)TypedValue.applyDimension((int)1, (float)42.0f, (DisplayMetrics)displayMetrics);
        this.minCropResultWidth = 40;
        this.minCropResultHeight = 40;
        this.maxCropResultWidth = 99999;
        this.maxCropResultHeight = 99999;
        this.activityTitle = "";
        this.activityMenuIconColor = 0;
        this.outputUri = Uri.EMPTY;
        this.outputCompressFormat = Bitmap.CompressFormat.JPEG;
        this.outputCompressQuality = 90;
        this.outputRequestWidth = 0;
        this.outputRequestHeight = 0;
        this.outputRequestSizeOptions = CropImageView.RequestSizeOptions.NONE;
        this.noOutputImage = false;
        this.initialCropWindowRectangle = null;
        this.initialRotation = -1;
        this.allowRotation = true;
        this.allowFlipping = true;
        this.allowCounterRotation = false;
        this.rotationDegrees = 90;
        this.flipHorizontally = false;
        this.flipVertically = false;
        this.cropMenuCropButtonTitle = null;
        this.cropMenuCropButtonIcon = 0;
    }

    protected CropImageOptions(Parcel parcel) {
        this.cropShape = CropImageView.CropShape.values()[parcel.readInt()];
        this.snapRadius = parcel.readFloat();
        this.touchRadius = parcel.readFloat();
        this.guidelines = CropImageView.Guidelines.values()[parcel.readInt()];
        this.scaleType = CropImageView.ScaleType.values()[parcel.readInt()];
        byte by = parcel.readByte();
        boolean bl = true;
        boolean bl2 = by != 0;
        this.showCropOverlay = bl2;
        boolean bl3 = parcel.readByte() != 0;
        this.showProgressBar = bl3;
        boolean bl4 = parcel.readByte() != 0;
        this.autoZoomEnabled = bl4;
        boolean bl5 = parcel.readByte() != 0;
        this.multiTouchEnabled = bl5;
        this.maxZoom = parcel.readInt();
        this.initialCropWindowPaddingRatio = parcel.readFloat();
        boolean bl6 = parcel.readByte() != 0;
        this.fixAspectRatio = bl6;
        this.aspectRatioX = parcel.readInt();
        this.aspectRatioY = parcel.readInt();
        this.borderLineThickness = parcel.readFloat();
        this.borderLineColor = parcel.readInt();
        this.borderCornerThickness = parcel.readFloat();
        this.borderCornerOffset = parcel.readFloat();
        this.borderCornerLength = parcel.readFloat();
        this.borderCornerColor = parcel.readInt();
        this.guidelinesThickness = parcel.readFloat();
        this.guidelinesColor = parcel.readInt();
        this.backgroundColor = parcel.readInt();
        this.minCropWindowWidth = parcel.readInt();
        this.minCropWindowHeight = parcel.readInt();
        this.minCropResultWidth = parcel.readInt();
        this.minCropResultHeight = parcel.readInt();
        this.maxCropResultWidth = parcel.readInt();
        this.maxCropResultHeight = parcel.readInt();
        this.activityTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.activityMenuIconColor = parcel.readInt();
        this.outputUri = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
        this.outputCompressFormat = Bitmap.CompressFormat.valueOf((String)parcel.readString());
        this.outputCompressQuality = parcel.readInt();
        this.outputRequestWidth = parcel.readInt();
        this.outputRequestHeight = parcel.readInt();
        this.outputRequestSizeOptions = CropImageView.RequestSizeOptions.values()[parcel.readInt()];
        boolean bl7 = parcel.readByte() != 0;
        this.noOutputImage = bl7;
        this.initialCropWindowRectangle = (Rect)parcel.readParcelable(Rect.class.getClassLoader());
        this.initialRotation = parcel.readInt();
        boolean bl8 = parcel.readByte() != 0;
        this.allowRotation = bl8;
        boolean bl9 = parcel.readByte() != 0;
        this.allowFlipping = bl9;
        boolean bl10 = parcel.readByte() != 0;
        this.allowCounterRotation = bl10;
        this.rotationDegrees = parcel.readInt();
        boolean bl11 = parcel.readByte() != 0;
        this.flipHorizontally = bl11;
        if (parcel.readByte() == 0) {
            bl = false;
        }
        this.flipVertically = bl;
        this.cropMenuCropButtonTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.cropMenuCropButtonIcon = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void validate() {
        if (this.maxZoom >= 0) {
            if (!(this.touchRadius < 0.0f)) {
                float f = this.initialCropWindowPaddingRatio;
                if (!(f < 0.0f) && !((double)f >= 0.5)) {
                    if (this.aspectRatioX > 0) {
                        if (this.aspectRatioY > 0) {
                            if (!(this.borderLineThickness < 0.0f)) {
                                if (!(this.borderCornerThickness < 0.0f)) {
                                    if (!(this.guidelinesThickness < 0.0f)) {
                                        if (this.minCropWindowHeight >= 0) {
                                            int n = this.minCropResultWidth;
                                            if (n >= 0) {
                                                int n2 = this.minCropResultHeight;
                                                if (n2 >= 0) {
                                                    if (this.maxCropResultWidth >= n) {
                                                        if (this.maxCropResultHeight >= n2) {
                                                            if (this.outputRequestWidth >= 0) {
                                                                if (this.outputRequestHeight >= 0) {
                                                                    int n3 = this.rotationDegrees;
                                                                    if (n3 >= 0 && n3 <= 360) {
                                                                        return;
                                                                    }
                                                                    throw new IllegalArgumentException("Cannot set rotation degrees value to a number < 0 or > 360");
                                                                }
                                                                throw new IllegalArgumentException("Cannot set request height value to a number < 0 ");
                                                            }
                                                            throw new IllegalArgumentException("Cannot set request width value to a number < 0 ");
                                                        }
                                                        throw new IllegalArgumentException("Cannot set max crop result height to smaller value than min crop result height");
                                                    }
                                                    throw new IllegalArgumentException("Cannot set max crop result width to smaller value than min crop result width");
                                                }
                                                throw new IllegalArgumentException("Cannot set min crop result height value to a number < 0 ");
                                            }
                                            throw new IllegalArgumentException("Cannot set min crop result width value to a number < 0 ");
                                        }
                                        throw new IllegalArgumentException("Cannot set min crop window height value to a number < 0 ");
                                    }
                                    throw new IllegalArgumentException("Cannot set guidelines thickness value to a number less than 0.");
                                }
                                throw new IllegalArgumentException("Cannot set corner thickness value to a number less than 0.");
                            }
                            throw new IllegalArgumentException("Cannot set line thickness value to a number less than 0.");
                        }
                        throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
                    }
                    throw new IllegalArgumentException("Cannot set aspect ratio value to a number less than or equal to 0.");
                }
                throw new IllegalArgumentException("Cannot set initial crop window padding value to a number < 0 or >= 0.5");
            }
            throw new IllegalArgumentException("Cannot set touch radius value to a number <= 0 ");
        }
        throw new IllegalArgumentException("Cannot set max zoom to a number < 1");
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.cropShape.ordinal());
        parcel.writeFloat(this.snapRadius);
        parcel.writeFloat(this.touchRadius);
        parcel.writeInt(this.guidelines.ordinal());
        parcel.writeInt(this.scaleType.ordinal());
        parcel.writeByte((byte)(this.showCropOverlay ? 1 : 0));
        parcel.writeByte((byte)(this.showProgressBar ? 1 : 0));
        parcel.writeByte((byte)(this.autoZoomEnabled ? 1 : 0));
        parcel.writeByte((byte)(this.multiTouchEnabled ? 1 : 0));
        parcel.writeInt(this.maxZoom);
        parcel.writeFloat(this.initialCropWindowPaddingRatio);
        parcel.writeByte((byte)(this.fixAspectRatio ? 1 : 0));
        parcel.writeInt(this.aspectRatioX);
        parcel.writeInt(this.aspectRatioY);
        parcel.writeFloat(this.borderLineThickness);
        parcel.writeInt(this.borderLineColor);
        parcel.writeFloat(this.borderCornerThickness);
        parcel.writeFloat(this.borderCornerOffset);
        parcel.writeFloat(this.borderCornerLength);
        parcel.writeInt(this.borderCornerColor);
        parcel.writeFloat(this.guidelinesThickness);
        parcel.writeInt(this.guidelinesColor);
        parcel.writeInt(this.backgroundColor);
        parcel.writeInt(this.minCropWindowWidth);
        parcel.writeInt(this.minCropWindowHeight);
        parcel.writeInt(this.minCropResultWidth);
        parcel.writeInt(this.minCropResultHeight);
        parcel.writeInt(this.maxCropResultWidth);
        parcel.writeInt(this.maxCropResultHeight);
        TextUtils.writeToParcel((CharSequence)this.activityTitle, (Parcel)parcel, (int)n);
        parcel.writeInt(this.activityMenuIconColor);
        parcel.writeParcelable((Parcelable)this.outputUri, n);
        parcel.writeString(this.outputCompressFormat.name());
        parcel.writeInt(this.outputCompressQuality);
        parcel.writeInt(this.outputRequestWidth);
        parcel.writeInt(this.outputRequestHeight);
        parcel.writeInt(this.outputRequestSizeOptions.ordinal());
        parcel.writeInt((int)this.noOutputImage);
        parcel.writeParcelable((Parcelable)this.initialCropWindowRectangle, n);
        parcel.writeInt(this.initialRotation);
        parcel.writeByte((byte)(this.allowRotation ? 1 : 0));
        parcel.writeByte((byte)(this.allowFlipping ? 1 : 0));
        parcel.writeByte((byte)(this.allowCounterRotation ? 1 : 0));
        parcel.writeInt(this.rotationDegrees);
        parcel.writeByte((byte)(this.flipHorizontally ? 1 : 0));
        parcel.writeByte((byte)(this.flipVertically ? 1 : 0));
        TextUtils.writeToParcel((CharSequence)this.cropMenuCropButtonTitle, (Parcel)parcel, (int)n);
        parcel.writeInt(this.cropMenuCropButtonIcon);
    }

}

