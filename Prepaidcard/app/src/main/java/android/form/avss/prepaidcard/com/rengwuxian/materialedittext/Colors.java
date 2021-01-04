/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  java.lang.Math
 *  java.lang.Object
 */
package android.form.avss.prepaidcard.com.rengwuxian.materialedittext;

import android.graphics.Color;

public class Colors {
    public static boolean isLight(int n) {
        return Math.sqrt((double)(0.241 * (double)(Color.red((int)n) * Color.red((int)n)) + 0.691 * (double)(Color.green((int)n) * Color.green((int)n)) + 0.068 * (double)(Color.blue((int)n) * Color.blue((int)n)))) > 130.0;
    }
}

