/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.IBinder
 *  android.text.method.KeyListener
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  androidx.core.content.ContextCompat
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Calendar
 */
package android.form.avss.prepaidcard.com.weiwangcn.betterspinner.library.material;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import androidx.core.content.ContextCompat;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.weiwangcn.betterspinner.library.material.R;
import java.util.Calendar;

public class MaterialBetterSpinner
extends MaterialAutoCompleteTextView
implements AdapterView.OnItemClickListener {
    private static final int MAX_CLICK_DURATION = 200;
    private boolean isPopup;
    private int mPosition = -1;
    private long startClickTime;

    public MaterialBetterSpinner(Context context) {
        super(context);
        this.setOnItemClickListener((AdapterView.OnItemClickListener)this);
    }

    public MaterialBetterSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOnItemClickListener((AdapterView.OnItemClickListener)this);
    }

    public MaterialBetterSpinner(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.setOnItemClickListener((AdapterView.OnItemClickListener)this);
    }

    public boolean enoughToFilter() {
        return true;
    }

    public int getPosition() {
        return this.mPosition;
    }

    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        if (bl) {
            this.performFiltering((CharSequence)"", 0);
            ((InputMethodManager)this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.getWindowToken(), 0);
            this.setKeyListener(null);
            this.dismissDropDown();
            return;
        }
        this.isPopup = false;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        this.mPosition = n;
        this.isPopup = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isEnabled()) {
            return false;
        }
        int n = motionEvent.getAction();
        if (n != 0) {
            if (n == 1 && Calendar.getInstance().getTimeInMillis() - this.startClickTime < 200L) {
                if (this.isPopup) {
                    this.dismissDropDown();
                    this.isPopup = false;
                } else {
                    this.requestFocus();
                    this.showDropDown();
                    this.isPopup = true;
                }
            }
        } else {
            this.startClickTime = Calendar.getInstance().getTimeInMillis();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        Drawable drawable6 = ContextCompat.getDrawable((Context)this.getContext(), (int)R.drawable.ic_expand_more_black_18dp);
        if (drawable6 != null) {
            drawable4 = drawable6;
            drawable4.mutate().setAlpha(66);
        }
        super.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
    }
}

