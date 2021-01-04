/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.os.Looper;

public class Util {
    public static void validateMainThread() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            return;
        }
        throw new IllegalStateException("Must be called from the main thread.");
    }
}

