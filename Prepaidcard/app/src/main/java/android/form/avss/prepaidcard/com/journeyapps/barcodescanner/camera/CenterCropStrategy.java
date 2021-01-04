/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.util.Log
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;

public class CenterCropStrategy
extends PreviewScalingStrategy {
    private static final String TAG = CenterCropStrategy.class.getSimpleName();

    @Override
    protected float getScore(Size size, Size size2) {
        if (size.width > 0 && size.height > 0) {
            Size size3 = size.scaleCrop(size2);
            float f = 1.0f * (float)size3.width / (float)size.width;
            float f2 = f > 1.0f ? (float)Math.pow((double)(1.0f / f), (double)1.1) : f;
            float f3 = 1.0f * (float)size3.width / (float)size2.width + 1.0f * (float)size3.height / (float)size2.height;
            return f2 * (1.0f / f3 / f3);
        }
        return 0.0f;
    }

    @Override
    public Rect scalePreview(Size size, Size size2) {
        Size size3 = size.scaleCrop(size2);
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Preview: ");
        stringBuilder.append((Object)size);
        stringBuilder.append("; Scaled: ");
        stringBuilder.append((Object)size3);
        stringBuilder.append("; Want: ");
        stringBuilder.append((Object)size2);
        Log.i((String)string2, (String)stringBuilder.toString());
        int n = (size3.width - size2.width) / 2;
        int n2 = (size3.height - size2.height) / 2;
        return new Rect(-n, -n2, size3.width - n, size3.height - n2);
    }
}

