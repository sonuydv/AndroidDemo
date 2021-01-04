/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  java.lang.Math
 *  java.lang.Object
 */
package android.form.avss.prepaidcard.com.rengwuxian.materialedittext;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

class Density {
    Density() {
    }

    public static int dp2px(Context context, float f) {
        return Math.round((float)TypedValue.applyDimension((int)1, (float)f, (DisplayMetrics)context.getResources().getDisplayMetrics()));
    }
}

