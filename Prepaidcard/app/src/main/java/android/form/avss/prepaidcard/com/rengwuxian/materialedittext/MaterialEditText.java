/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  androidx.appcompat.widget.AppCompatEditText
 *  com.nineoldandroids.animation.ArgbEvaluator
 *  com.nineoldandroids.animation.ObjectAnimator
 *  java.lang.CharSequence
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.annotation.Annotation
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package android.form.avss.prepaidcard.com.rengwuxian.materialedittext;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.rengwuxian.materialedittext.Colors;
import com.rengwuxian.materialedittext.Density;
import com.rengwuxian.materialedittext.R;
import com.rengwuxian.materialedittext.validation.METLengthChecker;
import com.rengwuxian.materialedittext.validation.METValidator;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaterialEditText
extends AppCompatEditText {
    public static final int FLOATING_LABEL_HIGHLIGHT = 2;
    public static final int FLOATING_LABEL_NONE = 0;
    public static final int FLOATING_LABEL_NORMAL = 1;
    private Typeface accentTypeface;
    private boolean autoValidate;
    private int baseColor;
    private int bottomEllipsisSize;
    private float bottomLines;
    ObjectAnimator bottomLinesAnimator;
    private int bottomSpacing;
    private int bottomTextSize;
    private boolean charactersCountValid;
    private boolean checkCharactersCountAtBeginning;
    private Bitmap[] clearButtonBitmaps;
    private boolean clearButtonClicking;
    private boolean clearButtonTouched;
    private float currentBottomLines;
    private int errorColor;
    private int extraPaddingBottom;
    private int extraPaddingLeft;
    private int extraPaddingRight;
    private int extraPaddingTop;
    private boolean firstShown;
    private boolean floatingLabelAlwaysShown;
    private boolean floatingLabelAnimating;
    private boolean floatingLabelEnabled;
    private float floatingLabelFraction;
    private int floatingLabelPadding;
    private boolean floatingLabelShown;
    private CharSequence floatingLabelText;
    private int floatingLabelTextColor;
    private int floatingLabelTextSize;
    private ArgbEvaluator focusEvaluator = new ArgbEvaluator();
    private float focusFraction;
    private String helperText;
    private boolean helperTextAlwaysShown;
    private int helperTextColor = -1;
    private boolean hideUnderline;
    private boolean highlightFloatingLabel;
    private Bitmap[] iconLeftBitmaps;
    private int iconOuterHeight;
    private int iconOuterWidth;
    private int iconPadding;
    private Bitmap[] iconRightBitmaps;
    private int iconSize;
    OnFocusChangeListener innerFocusChangeListener;
    private int innerPaddingBottom;
    private int innerPaddingLeft;
    private int innerPaddingRight;
    private int innerPaddingTop;
    ObjectAnimator labelAnimator;
    ObjectAnimator labelFocusAnimator;
    private METLengthChecker lengthChecker;
    private int maxCharacters;
    private int minBottomLines;
    private int minBottomTextLines;
    private int minCharacters;
    OnFocusChangeListener outerFocusChangeListener;
    Paint paint = new Paint(1);
    private int primaryColor;
    private boolean showClearButton;
    private boolean singleLineEllipsis;
    private String tempErrorText;
    private ColorStateList textColorHintStateList;
    private ColorStateList textColorStateList;
    StaticLayout textLayout;
    TextPaint textPaint = new TextPaint(1);
    private Typeface typeface;
    private int underlineColor;
    private boolean validateOnFocusLost;
    private List<METValidator> validators;

    public MaterialEditText(Context context) {
        super(context);
        this.init(context, null);
    }

    public MaterialEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public MaterialEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    private boolean adjustBottomLines() {
        int n;
        if (this.getWidth() == 0) {
            return false;
        }
        this.textPaint.setTextSize((float)this.bottomTextSize);
        if (this.tempErrorText == null && this.helperText == null) {
            n = this.minBottomLines;
        } else {
            StaticLayout staticLayout;
            Layout.Alignment alignment;
            if ((5 & this.getGravity()) != 5 && !this.isRTL()) {
                Layout.Alignment alignment2 = (3 & this.getGravity()) == 3 ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER;
                alignment = alignment2;
            } else {
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
            }
            String string2 = this.tempErrorText;
            if (string2 == null) {
                string2 = this.helperText;
            }
            this.textLayout = staticLayout = new StaticLayout((CharSequence)string2, this.textPaint, this.getWidth() - this.getBottomTextLeftOffset() - this.getBottomTextRightOffset() - this.getPaddingLeft() - this.getPaddingRight(), alignment, 1.0f, 0.0f, true);
            n = Math.max((int)staticLayout.getLineCount(), (int)this.minBottomTextLines);
        }
        if (this.bottomLines != (float)n) {
            this.getBottomLinesAnimator(n).start();
        }
        this.bottomLines = n;
        return true;
    }

    private void checkCharactersCount() {
        int n;
        boolean bl = this.firstShown;
        boolean bl2 = true;
        if (!bl && !this.checkCharactersCountAtBeginning || !this.hasCharactersCounter()) {
            this.charactersCountValid = bl2;
            return;
        }
        Editable editable = this.getText();
        int n2 = editable == null ? 0 : this.checkLength((CharSequence)editable);
        if (n2 < this.minCharacters || (n = this.maxCharacters) > 0 && n2 > n) {
            bl2 = false;
        }
        this.charactersCountValid = bl2;
    }

    private int checkLength(CharSequence charSequence) {
        METLengthChecker mETLengthChecker = this.lengthChecker;
        if (mETLengthChecker == null) {
            return charSequence.length();
        }
        return mETLengthChecker.getLength(charSequence);
    }

    private void correctPaddings() {
        int n;
        int n2 = 0;
        int n3 = this.iconOuterWidth * this.getButtonsCount();
        if (this.isRTL()) {
            n2 = n3;
            n = 0;
        } else {
            n = n3;
        }
        super.setPadding(n2 + (this.innerPaddingLeft + this.extraPaddingLeft), this.innerPaddingTop + this.extraPaddingTop, n + (this.innerPaddingRight + this.extraPaddingRight), this.innerPaddingBottom + this.extraPaddingBottom);
    }

    private Bitmap[] generateIconBitmaps(int n) {
        int n2;
        if (n == -1) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = n2 = 1;
        BitmapFactory.decodeResource((Resources)this.getResources(), (int)n, (BitmapFactory.Options)options);
        int n3 = Math.max((int)options.outWidth, (int)options.outHeight);
        int n4 = this.iconSize;
        if (n3 > n4) {
            n2 = n3 / n4;
        }
        options.inSampleSize = n2;
        options.inJustDecodeBounds = false;
        return this.generateIconBitmaps(BitmapFactory.decodeResource((Resources)this.getResources(), (int)n, (BitmapFactory.Options)options));
    }

    private Bitmap[] generateIconBitmaps(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap[] arrbitmap = new Bitmap[4];
        Bitmap bitmap2 = this.scaleIcon(bitmap);
        arrbitmap[0] = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(arrbitmap[0]);
        int n = this.baseColor;
        int n2 = n & 16777215;
        int n3 = Colors.isLight(n) ? -16777216 : -1979711488;
        canvas.drawColor(n3 | n2, PorterDuff.Mode.SRC_IN);
        arrbitmap[1] = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        new Canvas(arrbitmap[1]).drawColor(this.primaryColor, PorterDuff.Mode.SRC_IN);
        arrbitmap[2] = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas2 = new Canvas(arrbitmap[2]);
        int n4 = this.baseColor;
        int n5 = 16777215 & n4;
        int n6 = Colors.isLight(n4) ? 1275068416 : 1107296256;
        canvas2.drawColor(n6 | n5, PorterDuff.Mode.SRC_IN);
        arrbitmap[3] = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        new Canvas(arrbitmap[3]).drawColor(this.errorColor, PorterDuff.Mode.SRC_IN);
        return arrbitmap;
    }

    private Bitmap[] generateIconBitmaps(Drawable drawable2) {
        if (drawable2 == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap((int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable2.draw(canvas);
        int n = this.iconSize;
        return this.generateIconBitmaps(Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n, (int)n, (boolean)false));
    }

    private int getBottomEllipsisWidth() {
        if (this.singleLineEllipsis) {
            return 5 * this.bottomEllipsisSize + this.getPixel(4);
        }
        return 0;
    }

    private ObjectAnimator getBottomLinesAnimator(float f) {
        ObjectAnimator objectAnimator = this.bottomLinesAnimator;
        if (objectAnimator == null) {
            this.bottomLinesAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (String)"currentBottomLines", (float[])new float[]{f});
        } else {
            objectAnimator.cancel();
            this.bottomLinesAnimator.setFloatValues(new float[]{f});
        }
        return this.bottomLinesAnimator;
    }

    private int getBottomTextLeftOffset() {
        if (this.isRTL()) {
            return this.getCharactersCounterWidth();
        }
        return this.getBottomEllipsisWidth();
    }

    private int getBottomTextRightOffset() {
        if (this.isRTL()) {
            return this.getBottomEllipsisWidth();
        }
        return this.getCharactersCounterWidth();
    }

    private int getButtonsCount() {
        return (int)this.isShowClearButton();
    }

    private String getCharactersCounterText() {
        StringBuilder stringBuilder;
        int n;
        if (this.minCharacters <= 0) {
            StringBuilder stringBuilder2;
            int n2;
            if (this.isRTL()) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.maxCharacters);
                stringBuilder2.append(" / ");
                n2 = this.checkLength((CharSequence)this.getText());
            } else {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(this.checkLength((CharSequence)this.getText()));
                stringBuilder2.append(" / ");
                n2 = this.maxCharacters;
            }
            stringBuilder2.append(n2);
            return stringBuilder2.toString();
        }
        if (this.maxCharacters <= 0) {
            StringBuilder stringBuilder3;
            if (this.isRTL()) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("+");
                stringBuilder3.append(this.minCharacters);
                stringBuilder3.append(" / ");
                stringBuilder3.append(this.checkLength((CharSequence)this.getText()));
            } else {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(this.checkLength((CharSequence)this.getText()));
                stringBuilder3.append(" / ");
                stringBuilder3.append(this.minCharacters);
                stringBuilder3.append("+");
            }
            return stringBuilder3.toString();
        }
        if (this.isRTL()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.maxCharacters);
            stringBuilder.append("-");
            stringBuilder.append(this.minCharacters);
            stringBuilder.append(" / ");
            n = this.checkLength((CharSequence)this.getText());
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.checkLength((CharSequence)this.getText()));
            stringBuilder.append(" / ");
            stringBuilder.append(this.minCharacters);
            stringBuilder.append("-");
            n = this.maxCharacters;
        }
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    private int getCharactersCounterWidth() {
        if (this.hasCharactersCounter()) {
            return (int)this.textPaint.measureText(this.getCharactersCounterText());
        }
        return 0;
    }

    private Typeface getCustomTypeface(String string2) {
        return Typeface.createFromAsset((AssetManager)this.getContext().getAssets(), (String)string2);
    }

    private ObjectAnimator getLabelAnimator() {
        if (this.labelAnimator == null) {
            this.labelAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (String)"floatingLabelFraction", (float[])new float[]{0.0f, 1.0f});
        }
        ObjectAnimator objectAnimator = this.labelAnimator;
        long l = this.floatingLabelAnimating ? 300L : 0L;
        objectAnimator.setDuration(l);
        return this.labelAnimator;
    }

    private ObjectAnimator getLabelFocusAnimator() {
        if (this.labelFocusAnimator == null) {
            this.labelFocusAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (String)"focusFraction", (float[])new float[]{0.0f, 1.0f});
        }
        return this.labelFocusAnimator;
    }

    private int getPixel(int n) {
        return Density.dp2px(this.getContext(), n);
    }

    private boolean hasCharactersCounter() {
        return this.minCharacters > 0 || this.maxCharacters > 0;
        {
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void init(Context context, AttributeSet attributeSet) {
        int n;
        String string2;
        this.iconSize = this.getPixel(32);
        this.iconOuterWidth = this.getPixel(48);
        this.iconOuterHeight = this.getPixel(32);
        this.bottomSpacing = this.getResources().getDimensionPixelSize(R.dimen.inner_components_spacing);
        this.bottomEllipsisSize = this.getResources().getDimensionPixelSize(R.dimen.bottom_ellipsis_height);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialEditText);
        this.textColorStateList = typedArray.getColorStateList(R.styleable.MaterialEditText_met_textColor);
        this.textColorHintStateList = typedArray.getColorStateList(R.styleable.MaterialEditText_met_textColorHint);
        this.baseColor = typedArray.getColor(R.styleable.MaterialEditText_met_baseColor, -16777216);
        TypedValue typedValue = new TypedValue();
        try {
            if (Build.VERSION.SDK_INT < 21) {
                throw new RuntimeException("SDK_INT less than LOLLIPOP");
            }
            context.getTheme().resolveAttribute(16843827, typedValue, true);
            n = typedValue.data;
        }
        catch (Exception exception) {
            int n2 = this.getResources().getIdentifier("colorPrimary", "attr", this.getContext().getPackageName());
            if (n2 == 0) throw new RuntimeException("colorPrimary not found");
            try {
                context.getTheme().resolveAttribute(n2, typedValue, true);
                n = typedValue.data;
            }
            catch (Exception exception2) {
                n = this.baseColor;
            }
        }
        this.primaryColor = typedArray.getColor(R.styleable.MaterialEditText_met_primaryColor, n);
        this.setFloatingLabelInternal(typedArray.getInt(R.styleable.MaterialEditText_met_floatingLabel, 0));
        this.errorColor = typedArray.getColor(R.styleable.MaterialEditText_met_errorColor, Color.parseColor((String)"#e7492E"));
        this.minCharacters = typedArray.getInt(R.styleable.MaterialEditText_met_minCharacters, 0);
        this.maxCharacters = typedArray.getInt(R.styleable.MaterialEditText_met_maxCharacters, 0);
        this.singleLineEllipsis = typedArray.getBoolean(R.styleable.MaterialEditText_met_singleLineEllipsis, false);
        this.helperText = typedArray.getString(R.styleable.MaterialEditText_met_helperText);
        this.helperTextColor = typedArray.getColor(R.styleable.MaterialEditText_met_helperTextColor, -1);
        this.minBottomTextLines = typedArray.getInt(R.styleable.MaterialEditText_met_minBottomTextLines, 0);
        String string3 = typedArray.getString(R.styleable.MaterialEditText_met_accentTypeface);
        if (string3 != null && !this.isInEditMode()) {
            Typeface typeface;
            this.accentTypeface = typeface = this.getCustomTypeface(string3);
            this.textPaint.setTypeface(typeface);
        }
        if ((string2 = typedArray.getString(R.styleable.MaterialEditText_met_typeface)) != null && !this.isInEditMode()) {
            Typeface typeface;
            this.typeface = typeface = this.getCustomTypeface(string2);
            this.setTypeface(typeface);
        }
        String string4 = typedArray.getString(R.styleable.MaterialEditText_met_floatingLabelText);
        this.floatingLabelText = string4;
        if (string4 == null) {
            this.floatingLabelText = this.getHint();
        }
        this.floatingLabelPadding = typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_floatingLabelPadding, this.bottomSpacing);
        this.floatingLabelTextSize = typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_floatingLabelTextSize, this.getResources().getDimensionPixelSize(R.dimen.floating_label_text_size));
        this.floatingLabelTextColor = typedArray.getColor(R.styleable.MaterialEditText_met_floatingLabelTextColor, -1);
        this.floatingLabelAnimating = typedArray.getBoolean(R.styleable.MaterialEditText_met_floatingLabelAnimating, true);
        this.bottomTextSize = typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_bottomTextSize, this.getResources().getDimensionPixelSize(R.dimen.bottom_text_size));
        this.hideUnderline = typedArray.getBoolean(R.styleable.MaterialEditText_met_hideUnderline, false);
        this.underlineColor = typedArray.getColor(R.styleable.MaterialEditText_met_underlineColor, -1);
        this.autoValidate = typedArray.getBoolean(R.styleable.MaterialEditText_met_autoValidate, false);
        this.iconLeftBitmaps = this.generateIconBitmaps(typedArray.getResourceId(R.styleable.MaterialEditText_met_iconLeft, -1));
        this.iconRightBitmaps = this.generateIconBitmaps(typedArray.getResourceId(R.styleable.MaterialEditText_met_iconRight, -1));
        this.showClearButton = typedArray.getBoolean(R.styleable.MaterialEditText_met_clearButton, false);
        this.clearButtonBitmaps = this.generateIconBitmaps(R.drawable.met_ic_clear);
        this.iconPadding = typedArray.getDimensionPixelSize(R.styleable.MaterialEditText_met_iconPadding, this.getPixel(16));
        this.floatingLabelAlwaysShown = typedArray.getBoolean(R.styleable.MaterialEditText_met_floatingLabelAlwaysShown, false);
        this.helperTextAlwaysShown = typedArray.getBoolean(R.styleable.MaterialEditText_met_helperTextAlwaysShown, false);
        this.validateOnFocusLost = typedArray.getBoolean(R.styleable.MaterialEditText_met_validateOnFocusLost, false);
        this.checkCharactersCountAtBeginning = typedArray.getBoolean(R.styleable.MaterialEditText_met_checkCharactersCountAtBeginning, true);
        typedArray.recycle();
        TypedArray typedArray2 = context.obtainStyledAttributes(attributeSet, new int[]{16842965, 16842966, 16842967, 16842968, 16842969});
        int n3 = typedArray2.getDimensionPixelSize(0, 0);
        this.innerPaddingLeft = typedArray2.getDimensionPixelSize(1, n3);
        this.innerPaddingTop = typedArray2.getDimensionPixelSize(2, n3);
        this.innerPaddingRight = typedArray2.getDimensionPixelSize(3, n3);
        this.innerPaddingBottom = typedArray2.getDimensionPixelSize(4, n3);
        typedArray2.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.setBackground(null);
        } else {
            this.setBackgroundDrawable(null);
        }
        if (this.singleLineEllipsis) {
            TransformationMethod transformationMethod = this.getTransformationMethod();
            this.setSingleLine();
            this.setTransformationMethod(transformationMethod);
        }
        this.initMinBottomLines();
        this.initPadding();
        this.initText();
        this.initFloatingLabel();
        this.initTextWatcher();
        this.checkCharactersCount();
    }

    private void initFloatingLabel() {
        OnFocusChangeListener onFocusChangeListener;
        this.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                if (MaterialEditText.this.floatingLabelEnabled) {
                    if (editable.length() == 0) {
                        if (MaterialEditText.this.floatingLabelShown) {
                            MaterialEditText.this.floatingLabelShown = false;
                            MaterialEditText.this.getLabelAnimator().reverse();
                            return;
                        }
                    } else if (!MaterialEditText.this.floatingLabelShown) {
                        MaterialEditText.this.floatingLabelShown = true;
                        MaterialEditText.this.getLabelAnimator().start();
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.innerFocusChangeListener = onFocusChangeListener = new OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                if (MaterialEditText.this.floatingLabelEnabled && MaterialEditText.this.highlightFloatingLabel) {
                    if (bl) {
                        MaterialEditText.this.getLabelFocusAnimator().start();
                    } else {
                        MaterialEditText.this.getLabelFocusAnimator().reverse();
                    }
                }
                if (MaterialEditText.this.validateOnFocusLost && !bl) {
                    MaterialEditText.this.validate();
                }
                if (MaterialEditText.this.outerFocusChangeListener != null) {
                    MaterialEditText.this.outerFocusChangeListener.onFocusChange(view, bl);
                }
            }
        };
        super.setOnFocusChangeListener(onFocusChangeListener);
    }

    private void initMinBottomLines() {
        int n;
        boolean bl = this.minCharacters > 0 || this.maxCharacters > 0 || this.singleLineEllipsis || this.tempErrorText != null || this.helperText != null;
        int n2 = this.minBottomTextLines;
        if (n2 > 0) {
            n = n2;
        } else {
            n = 0;
            if (bl) {
                n = 1;
            }
        }
        this.minBottomLines = n;
        this.currentBottomLines = n;
    }

    private void initPadding() {
        int n = this.floatingLabelEnabled ? this.floatingLabelTextSize + this.floatingLabelPadding : this.floatingLabelPadding;
        this.extraPaddingTop = n;
        this.textPaint.setTextSize((float)this.bottomTextSize);
        Paint.FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        int n2 = (int)((fontMetrics.descent - fontMetrics.ascent) * this.currentBottomLines);
        int n3 = this.hideUnderline ? this.bottomSpacing : 2 * this.bottomSpacing;
        this.extraPaddingBottom = n2 + n3;
        int n4 = this.iconLeftBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding;
        this.extraPaddingLeft = n4;
        int n5 = this.iconRightBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding;
        this.extraPaddingRight = n5;
        this.correctPaddings();
    }

    private void initText() {
        if (!TextUtils.isEmpty((CharSequence)this.getText())) {
            Editable editable = this.getText();
            this.setText(null);
            this.resetHintTextColor();
            this.setText((CharSequence)editable);
            this.setSelection(editable.length());
            this.floatingLabelFraction = 1.0f;
            this.floatingLabelShown = true;
        } else {
            this.resetHintTextColor();
        }
        this.resetTextColor();
    }

    private void initTextWatcher() {
        this.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                MaterialEditText.this.checkCharactersCount();
                if (MaterialEditText.this.autoValidate) {
                    MaterialEditText.this.validate();
                } else {
                    MaterialEditText.this.setError(null);
                }
                MaterialEditText.this.postInvalidate();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
    }

    private boolean insideClearButton(MotionEvent motionEvent) {
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        int n = this.getScrollX();
        int n2 = this.iconLeftBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding;
        int n3 = n + n2;
        int n4 = this.getScrollX();
        int n5 = this.iconRightBitmaps == null ? this.getWidth() : this.getWidth() - this.iconOuterWidth - this.iconPadding;
        int n6 = n4 + n5;
        int n7 = this.isRTL() ? n3 : n6 - this.iconOuterWidth;
        int n8 = this.getScrollY() + this.getHeight() - this.getPaddingBottom() + this.bottomSpacing;
        int n9 = this.iconOuterHeight;
        int n10 = n8 - n9;
        float f3 = f FCMPL (float)n7;
        boolean bl = false;
        if (f3 >= 0) {
            float f4 = f FCMPG (float)(n7 + this.iconOuterWidth);
            bl = false;
            if (f4 < 0) {
                float f5 = f2 FCMPL (float)n10;
                bl = false;
                if (f5 >= 0) {
                    float f6 = f2 FCMPG (float)(n9 + n10);
                    bl = false;
                    if (f6 < 0) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    private boolean isInternalValid() {
        return this.tempErrorText == null && this.isCharactersCountValid();
    }

    private boolean isRTL() {
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        int n = this.getResources().getConfiguration().getLayoutDirection();
        boolean bl = false;
        if (n == 1) {
            bl = true;
        }
        return bl;
    }

    private void resetHintTextColor() {
        ColorStateList colorStateList = this.textColorHintStateList;
        if (colorStateList == null) {
            this.setHintTextColor(1140850688 | 16777215 & this.baseColor);
            return;
        }
        this.setHintTextColor(colorStateList);
    }

    private void resetTextColor() {
        ColorStateList colorStateList = this.textColorStateList;
        if (colorStateList == null) {
            ColorStateList colorStateList2;
            int[][] arrarrn = new int[][]{{16842910}, EMPTY_STATE_SET};
            int[] arrn = new int[2];
            int n = this.baseColor;
            arrn[0] = -553648128 | n & 16777215;
            arrn[1] = 1140850688 | n & 16777215;
            this.textColorStateList = colorStateList2 = new ColorStateList((int[][])arrarrn, arrn);
            this.setTextColor(colorStateList2);
            return;
        }
        this.setTextColor(colorStateList);
    }

    private Bitmap scaleIcon(Bitmap bitmap) {
        int n;
        int n2;
        int n3 = bitmap.getWidth();
        int n4 = Math.max((int)n3, (int)(n = bitmap.getHeight()));
        if (n4 == (n2 = this.iconSize)) {
            return bitmap;
        }
        if (n4 > n2) {
            int n5;
            int n6;
            if (n3 > n2) {
                n5 = this.iconSize;
                n6 = (int)((float)n2 * ((float)n / (float)n3));
            } else {
                int n7 = this.iconSize;
                n5 = (int)((float)n2 * ((float)n3 / (float)n));
                n6 = n7;
            }
            return Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n5, (int)n6, (boolean)false);
        }
        return bitmap;
    }

    private void setFloatingLabelInternal(int n) {
        if (n != 1) {
            if (n != 2) {
                this.floatingLabelEnabled = false;
                this.highlightFloatingLabel = false;
                return;
            }
            this.floatingLabelEnabled = true;
            this.highlightFloatingLabel = true;
            return;
        }
        this.floatingLabelEnabled = true;
        this.highlightFloatingLabel = false;
    }

    public MaterialEditText addValidator(METValidator mETValidator) {
        if (this.validators == null) {
            this.validators = new ArrayList();
        }
        this.validators.add((Object)mETValidator);
        return this;
    }

    public void clearValidators() {
        List<METValidator> list = this.validators;
        if (list != null) {
            list.clear();
        }
    }

    public Typeface getAccentTypeface() {
        return this.accentTypeface;
    }

    public int getBottomTextSize() {
        return this.bottomTextSize;
    }

    public float getCurrentBottomLines() {
        return this.currentBottomLines;
    }

    public CharSequence getError() {
        return this.tempErrorText;
    }

    public int getErrorColor() {
        return this.errorColor;
    }

    public float getFloatingLabelFraction() {
        return this.floatingLabelFraction;
    }

    public int getFloatingLabelPadding() {
        return this.floatingLabelPadding;
    }

    public CharSequence getFloatingLabelText() {
        return this.floatingLabelText;
    }

    public int getFloatingLabelTextColor() {
        return this.floatingLabelTextColor;
    }

    public int getFloatingLabelTextSize() {
        return this.floatingLabelTextSize;
    }

    public float getFocusFraction() {
        return this.focusFraction;
    }

    public String getHelperText() {
        return this.helperText;
    }

    public int getHelperTextColor() {
        return this.helperTextColor;
    }

    public int getInnerPaddingBottom() {
        return this.innerPaddingBottom;
    }

    public int getInnerPaddingLeft() {
        return this.innerPaddingLeft;
    }

    public int getInnerPaddingRight() {
        return this.innerPaddingRight;
    }

    public int getInnerPaddingTop() {
        return this.innerPaddingTop;
    }

    public int getMaxCharacters() {
        return this.maxCharacters;
    }

    public int getMinBottomTextLines() {
        return this.minBottomTextLines;
    }

    public int getMinCharacters() {
        return this.minCharacters;
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public List<METValidator> getValidators() {
        return this.validators;
    }

    public boolean hasValidators() {
        List<METValidator> list = this.validators;
        return list != null && !list.isEmpty();
    }

    public boolean isAutoValidate() {
        return this.autoValidate;
    }

    public boolean isCharactersCountValid() {
        return this.charactersCountValid;
    }

    public boolean isFloatingLabelAlwaysShown() {
        return this.floatingLabelAlwaysShown;
    }

    public boolean isFloatingLabelAnimating() {
        return this.floatingLabelAnimating;
    }

    public boolean isHelperTextAlwaysShown() {
        return this.helperTextAlwaysShown;
    }

    public boolean isHideUnderline() {
        return this.hideUnderline;
    }

    public boolean isShowClearButton() {
        return this.showClearButton;
    }

    @Deprecated
    public boolean isValid(String string2) {
        if (string2 == null) {
            return false;
        }
        return Pattern.compile((String)string2).matcher((CharSequence)this.getText()).matches();
    }

    public boolean isValidateOnFocusLost() {
        return this.validateOnFocusLost;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.firstShown) {
            this.firstShown = true;
        }
    }

    protected void onDraw(Canvas canvas) {
        Bitmap[] arrbitmap;
        int n = this.getScrollX();
        int n2 = this.iconLeftBitmaps == null ? 0 : this.iconOuterWidth + this.iconPadding;
        int n3 = n + n2;
        int n4 = this.getScrollX();
        int n5 = this.iconRightBitmaps == null ? this.getWidth() : this.getWidth() - this.iconOuterWidth - this.iconPadding;
        int n6 = n4 + n5;
        int n7 = this.getScrollY() + this.getHeight() - this.getPaddingBottom();
        this.paint.setAlpha(255);
        Bitmap[] arrbitmap2 = this.iconLeftBitmaps;
        if (arrbitmap2 != null) {
            int n8 = !this.isInternalValid() ? 3 : (!this.isEnabled() ? 2 : (this.hasFocus() ? 1 : 0));
            Bitmap bitmap = arrbitmap2[n8];
            int n9 = n3 - this.iconPadding;
            int n10 = this.iconOuterWidth;
            int n11 = n9 - n10 + (n10 - bitmap.getWidth()) / 2;
            int n12 = n7 + this.bottomSpacing;
            int n13 = this.iconOuterHeight;
            int n14 = n12 - n13 + (n13 - bitmap.getHeight()) / 2;
            canvas.drawBitmap(bitmap, (float)n11, (float)n14, this.paint);
        }
        if ((arrbitmap = this.iconRightBitmaps) != null) {
            int n15 = !this.isInternalValid() ? 3 : (!this.isEnabled() ? 2 : (this.hasFocus() ? 1 : 0));
            Bitmap bitmap = arrbitmap[n15];
            int n16 = n6 + this.iconPadding + (this.iconOuterWidth - bitmap.getWidth()) / 2;
            int n17 = n7 + this.bottomSpacing;
            int n18 = this.iconOuterHeight;
            int n19 = n17 - n18 + (n18 - bitmap.getHeight()) / 2;
            canvas.drawBitmap(bitmap, (float)n16, (float)n19, this.paint);
        }
        if (this.hasFocus() && this.showClearButton && !TextUtils.isEmpty((CharSequence)this.getText())) {
            this.paint.setAlpha(255);
            int n20 = this.isRTL() ? n3 : n6 - this.iconOuterWidth;
            Bitmap bitmap = this.clearButtonBitmaps[0];
            int n21 = n20 + (this.iconOuterWidth - bitmap.getWidth()) / 2;
            int n22 = n7 + this.bottomSpacing;
            int n23 = this.iconOuterHeight;
            int n24 = n22 - n23 + (n23 - bitmap.getHeight()) / 2;
            canvas.drawBitmap(bitmap, (float)n21, (float)n24, this.paint);
        }
        if (!this.hideUnderline) {
            int n25;
            int n26 = n7 + this.bottomSpacing;
            if (!this.isInternalValid()) {
                this.paint.setColor(this.errorColor);
                float f = n3;
                float f2 = n26;
                float f3 = n6;
                float f4 = n26 + this.getPixel(2);
                Paint paint = this.paint;
                n25 = n26;
                canvas.drawRect(f, f2, f3, f4, paint);
            } else {
                n25 = n26;
                if (!this.isEnabled()) {
                    float f;
                    Paint paint = this.paint;
                    int n27 = this.underlineColor;
                    if (n27 == -1) {
                        n27 = 1140850688 | 16777215 & this.baseColor;
                    }
                    paint.setColor(n27);
                    float f5 = this.getPixel(1);
                    for (float f6 = 0.0f; f6 < (float)this.getWidth(); f6 += f * 3.0f) {
                        float f7 = f6 + (float)n3;
                        float f8 = n25;
                        float f9 = f5 + (f6 + (float)n3);
                        float f10 = n25 + this.getPixel(1);
                        Paint paint2 = this.paint;
                        f = f5;
                        canvas.drawRect(f7, f8, f9, f10, paint2);
                        f5 = f;
                    }
                } else if (this.hasFocus()) {
                    this.paint.setColor(this.primaryColor);
                    canvas.drawRect((float)n3, (float)n25, (float)n6, (float)(n25 + this.getPixel(2)), this.paint);
                } else {
                    Paint paint = this.paint;
                    int n28 = this.underlineColor;
                    if (n28 == -1) {
                        n28 = 503316480 | 16777215 & this.baseColor;
                    }
                    paint.setColor(n28);
                    canvas.drawRect((float)n3, (float)n25, (float)n6, (float)(n25 + this.getPixel(1)), this.paint);
                }
            }
            n7 = n25;
        }
        this.textPaint.setTextSize((float)this.bottomTextSize);
        Paint.FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        float f = -fontMetrics.ascent - fontMetrics.descent;
        float f11 = (float)this.bottomTextSize + fontMetrics.ascent + fontMetrics.descent;
        if (this.hasFocus() && this.hasCharactersCounter() || !this.isCharactersCountValid()) {
            TextPaint textPaint = this.textPaint;
            int n29 = this.isCharactersCountValid() ? 1140850688 | 16777215 & this.baseColor : this.errorColor;
            textPaint.setColor(n29);
            String string2 = this.getCharactersCounterText();
            float f12 = this.isRTL() ? (float)n3 : (float)n6 - this.textPaint.measureText(string2);
            canvas.drawText(string2, f12, f + (float)(n7 + this.bottomSpacing), (Paint)this.textPaint);
        }
        if (this.textLayout != null && (this.tempErrorText != null || (this.helperTextAlwaysShown || this.hasFocus()) && !TextUtils.isEmpty((CharSequence)this.helperText))) {
            int n30;
            TextPaint textPaint = this.textPaint;
            if (this.tempErrorText != null) {
                n30 = this.errorColor;
            } else {
                n30 = this.helperTextColor;
                if (n30 == -1) {
                    n30 = 1140850688 | 16777215 & this.baseColor;
                }
            }
            textPaint.setColor(n30);
            canvas.save();
            if (this.isRTL()) {
                canvas.translate((float)(n6 - this.textLayout.getWidth()), (float)(n7 + this.bottomSpacing) - f11);
            } else {
                canvas.translate((float)(n3 + this.getBottomTextLeftOffset()), (float)(n7 + this.bottomSpacing) - f11);
            }
            this.textLayout.draw(canvas);
            canvas.restore();
        }
        if (this.floatingLabelEnabled && !TextUtils.isEmpty((CharSequence)this.floatingLabelText)) {
            this.textPaint.setTextSize((float)this.floatingLabelTextSize);
            TextPaint textPaint = this.textPaint;
            ArgbEvaluator argbEvaluator = this.focusEvaluator;
            float f13 = this.focusFraction;
            int n31 = this.floatingLabelTextColor;
            if (n31 == -1) {
                n31 = 1140850688 | 16777215 & this.baseColor;
            }
            textPaint.setColor(((Integer)argbEvaluator.evaluate(f13, (Object)n31, (Object)this.primaryColor)).intValue());
            float f14 = this.textPaint.measureText(this.floatingLabelText.toString());
            int n32 = (5 & this.getGravity()) != 5 && !this.isRTL() ? ((3 & this.getGravity()) == 3 ? n3 : n3 + (int)((float)this.getInnerPaddingLeft() + ((float)(this.getWidth() - this.getInnerPaddingLeft() - this.getInnerPaddingRight()) - f14) / 2.0f)) : (int)((float)n6 - f14);
            int n33 = this.floatingLabelPadding;
            float f15 = this.innerPaddingTop + this.floatingLabelTextSize + this.floatingLabelPadding;
            float f16 = n33;
            boolean bl = this.floatingLabelAlwaysShown;
            float f17 = 1.0f;
            float f18 = bl ? 1.0f : this.floatingLabelFraction;
            int n34 = (int)(f15 - f16 * f18 + (float)this.getScrollY());
            float f19 = this.floatingLabelAlwaysShown ? 1.0f : this.floatingLabelFraction;
            float f20 = f19 * 255.0f * (0.26f + 0.74f * this.focusFraction);
            int n35 = this.floatingLabelTextColor;
            if (n35 == -1) {
                f17 = (float)Color.alpha((int)n35) / 256.0f;
            }
            int n36 = (int)(f20 * f17);
            this.textPaint.setAlpha(n36);
            canvas.drawText(this.floatingLabelText.toString(), (float)n32, (float)n34, (Paint)this.textPaint);
        }
        if (this.hasFocus() && this.singleLineEllipsis && this.getScrollX() != 0) {
            Paint paint = this.paint;
            int n37 = this.isInternalValid() ? this.primaryColor : this.errorColor;
            paint.setColor(n37);
            float f21 = n7 + this.bottomSpacing;
            int n38 = this.isRTL() ? n6 : n3;
            int n39 = this.isRTL() ? -1 : 1;
            int n40 = n39;
            int n41 = this.bottomEllipsisSize;
            canvas.drawCircle((float)(n38 + n40 * n41 / 2), f21 + (float)(n41 / 2), (float)(n41 / 2), this.paint);
            int n42 = this.bottomEllipsisSize;
            canvas.drawCircle((float)(n38 + 5 * (n40 * n42) / 2), f21 + (float)(n42 / 2), (float)(n42 / 2), this.paint);
            int n43 = this.bottomEllipsisSize;
            canvas.drawCircle((float)(n38 + 9 * (n40 * n43) / 2), f21 + (float)(n43 / 2), (float)(n43 / 2), this.paint);
        }
        super.onDraw(canvas);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (bl) {
            this.adjustBottomLines();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        block8 : {
            block11 : {
                block9 : {
                    block10 : {
                        if (this.singleLineEllipsis && this.getScrollX() > 0 && motionEvent.getAction() == 0 && motionEvent.getX() < (float)this.getPixel(20) && motionEvent.getY() > (float)(this.getHeight() - this.extraPaddingBottom - this.innerPaddingBottom) && motionEvent.getY() < (float)(this.getHeight() - this.innerPaddingBottom)) {
                            this.setSelection(0);
                            return false;
                        }
                        if (!this.hasFocus() || !this.showClearButton) break block8;
                        int n = motionEvent.getAction();
                        if (n == 0) break block9;
                        if (n == 1) break block10;
                        if (n == 2) break block11;
                        if (n == 3) {
                            this.clearButtonTouched = false;
                            this.clearButtonClicking = false;
                        }
                        break block8;
                    }
                    if (this.clearButtonClicking) {
                        if (!TextUtils.isEmpty((CharSequence)this.getText())) {
                            this.setText(null);
                        }
                        this.clearButtonClicking = false;
                    }
                    if (this.clearButtonTouched) {
                        this.clearButtonTouched = false;
                        return true;
                    }
                    this.clearButtonTouched = false;
                    break block8;
                }
                if (this.insideClearButton(motionEvent)) {
                    this.clearButtonTouched = true;
                    this.clearButtonClicking = true;
                    return true;
                }
            }
            if (this.clearButtonClicking && !this.insideClearButton(motionEvent)) {
                this.clearButtonClicking = false;
            }
            if (this.clearButtonTouched) {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setAccentTypeface(Typeface typeface) {
        this.accentTypeface = typeface;
        this.textPaint.setTypeface(typeface);
        this.postInvalidate();
    }

    public void setAutoValidate(boolean bl) {
        this.autoValidate = bl;
        if (bl) {
            this.validate();
        }
    }

    public void setBaseColor(int n) {
        if (this.baseColor != n) {
            this.baseColor = n;
        }
        this.initText();
        this.postInvalidate();
    }

    public void setBottomTextSize(int n) {
        this.bottomTextSize = n;
        this.initPadding();
    }

    public void setCurrentBottomLines(float f) {
        this.currentBottomLines = f;
        this.initPadding();
    }

    public void setError(CharSequence charSequence) {
        String string2 = charSequence == null ? null : charSequence.toString();
        this.tempErrorText = string2;
        if (this.adjustBottomLines()) {
            this.postInvalidate();
        }
    }

    public void setErrorColor(int n) {
        this.errorColor = n;
        this.postInvalidate();
    }

    public void setFloatingLabel(int n) {
        this.setFloatingLabelInternal(n);
        this.initPadding();
    }

    public void setFloatingLabelAlwaysShown(boolean bl) {
        this.floatingLabelAlwaysShown = bl;
        this.invalidate();
    }

    public void setFloatingLabelAnimating(boolean bl) {
        this.floatingLabelAnimating = bl;
    }

    public void setFloatingLabelFraction(float f) {
        this.floatingLabelFraction = f;
        this.invalidate();
    }

    public void setFloatingLabelPadding(int n) {
        this.floatingLabelPadding = n;
        this.postInvalidate();
    }

    public void setFloatingLabelText(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence == null ? this.getHint() : charSequence;
        this.floatingLabelText = charSequence2;
        this.postInvalidate();
    }

    public void setFloatingLabelTextColor(int n) {
        this.floatingLabelTextColor = n;
        this.postInvalidate();
    }

    public void setFloatingLabelTextSize(int n) {
        this.floatingLabelTextSize = n;
        this.initPadding();
    }

    public void setFocusFraction(float f) {
        this.focusFraction = f;
        this.invalidate();
    }

    public void setHelperText(CharSequence charSequence) {
        String string2 = charSequence == null ? null : charSequence.toString();
        this.helperText = string2;
        if (this.adjustBottomLines()) {
            this.postInvalidate();
        }
    }

    public void setHelperTextAlwaysShown(boolean bl) {
        this.helperTextAlwaysShown = bl;
        this.invalidate();
    }

    public void setHelperTextColor(int n) {
        this.helperTextColor = n;
        this.postInvalidate();
    }

    public void setHideUnderline(boolean bl) {
        this.hideUnderline = bl;
        this.initPadding();
        this.postInvalidate();
    }

    public void setIconLeft(int n) {
        this.iconLeftBitmaps = this.generateIconBitmaps(n);
        this.initPadding();
    }

    public void setIconLeft(Bitmap bitmap) {
        this.iconLeftBitmaps = this.generateIconBitmaps(bitmap);
        this.initPadding();
    }

    public void setIconLeft(Drawable drawable2) {
        this.iconLeftBitmaps = this.generateIconBitmaps(drawable2);
        this.initPadding();
    }

    public void setIconRight(int n) {
        this.iconRightBitmaps = this.generateIconBitmaps(n);
        this.initPadding();
    }

    public void setIconRight(Bitmap bitmap) {
        this.iconRightBitmaps = this.generateIconBitmaps(bitmap);
        this.initPadding();
    }

    public void setIconRight(Drawable drawable2) {
        this.iconRightBitmaps = this.generateIconBitmaps(drawable2);
        this.initPadding();
    }

    public void setLengthChecker(METLengthChecker mETLengthChecker) {
        this.lengthChecker = mETLengthChecker;
    }

    public void setMaxCharacters(int n) {
        this.maxCharacters = n;
        this.initMinBottomLines();
        this.initPadding();
        this.postInvalidate();
    }

    public void setMetHintTextColor(int n) {
        this.textColorHintStateList = ColorStateList.valueOf((int)n);
        this.resetHintTextColor();
    }

    public void setMetHintTextColor(ColorStateList colorStateList) {
        this.textColorHintStateList = colorStateList;
        this.resetHintTextColor();
    }

    public void setMetTextColor(int n) {
        this.textColorStateList = ColorStateList.valueOf((int)n);
        this.resetTextColor();
    }

    public void setMetTextColor(ColorStateList colorStateList) {
        this.textColorStateList = colorStateList;
        this.resetTextColor();
    }

    public void setMinBottomTextLines(int n) {
        this.minBottomTextLines = n;
        this.initMinBottomLines();
        this.initPadding();
        this.postInvalidate();
    }

    public void setMinCharacters(int n) {
        this.minCharacters = n;
        this.initMinBottomLines();
        this.initPadding();
        this.postInvalidate();
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        if (this.innerFocusChangeListener == null) {
            super.setOnFocusChangeListener(onFocusChangeListener);
            return;
        }
        this.outerFocusChangeListener = onFocusChangeListener;
    }

    @Deprecated
    public final void setPadding(int n, int n2, int n3, int n4) {
        super.setPadding(n, n2, n3, n4);
    }

    public void setPaddings(int n, int n2, int n3, int n4) {
        this.innerPaddingTop = n2;
        this.innerPaddingBottom = n4;
        this.innerPaddingLeft = n;
        this.innerPaddingRight = n3;
        this.correctPaddings();
    }

    public void setPrimaryColor(int n) {
        this.primaryColor = n;
        this.postInvalidate();
    }

    public void setShowClearButton(boolean bl) {
        this.showClearButton = bl;
        this.correctPaddings();
    }

    public void setSingleLineEllipsis() {
        this.setSingleLineEllipsis(true);
    }

    public void setSingleLineEllipsis(boolean bl) {
        this.singleLineEllipsis = bl;
        this.initMinBottomLines();
        this.initPadding();
        this.postInvalidate();
    }

    public void setUnderlineColor(int n) {
        this.underlineColor = n;
        this.postInvalidate();
    }

    public void setValidateOnFocusLost(boolean bl) {
        this.validateOnFocusLost = bl;
    }

    public boolean validate() {
        List<METValidator> list = this.validators;
        if (list != null) {
            if (list.isEmpty()) {
                return true;
            }
            Editable editable = this.getText();
            boolean bl = editable.length() == 0;
            boolean bl2 = true;
            for (METValidator mETValidator : this.validators) {
                boolean bl3 = bl2 && mETValidator.isValid((CharSequence)editable, bl);
                if (bl2 = bl3) continue;
                this.setError(mETValidator.getErrorMessage());
                break;
            }
            if (bl2) {
                this.setError(null);
            }
            this.postInvalidate();
            return bl2;
        }
        return true;
    }

    @Deprecated
    public boolean validate(String string2, CharSequence charSequence) {
        boolean bl = this.isValid(string2);
        if (!bl) {
            this.setError(charSequence);
        }
        this.postInvalidate();
        return bl;
    }

    public boolean validateWith(METValidator mETValidator) {
        Editable editable;
        boolean bl = (editable = this.getText()).length() == 0;
        boolean bl2 = mETValidator.isValid((CharSequence)editable, bl);
        if (!bl2) {
            this.setError(mETValidator.getErrorMessage());
        }
        this.postInvalidate();
        return bl2;
    }

    public static @interface FloatingLabelType {
    }

}

