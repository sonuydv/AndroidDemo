/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;

public class FitXYStrategy
extends PreviewScalingStrategy {
    private static final String TAG = FitXYStrategy.class.getSimpleName();

    private static float absRatio(float f) {
        if (f < 1.0f) {
            return 1.0f / f;
        }
        return f;
    }

    @Override
    protected float getScore(Size size, Size size2) {
        if (size.width > 0 && size.height > 0) {
            float f = FitXYStrategy.absRatio(1.0f * (float)size.width / (float)size2.width);
            float f2 = FitXYStrategy.absRatio(1.0f * (float)size.height / (float)size2.height);
            float f3 = 1.0f / f / f2;
            float f4 = FitXYStrategy.absRatio(1.0f * (float)size.width / (float)size.height / (1.0f * (float)size2.width / (float)size2.height));
            return f3 * (1.0f / f4 / f4 / f4);
        }
        return 0.0f;
    }

    @Override
    public Rect scalePreview(Size size, Size size2) {
        return new Rect(0, 0, size2.width, size2.height);
    }
}

