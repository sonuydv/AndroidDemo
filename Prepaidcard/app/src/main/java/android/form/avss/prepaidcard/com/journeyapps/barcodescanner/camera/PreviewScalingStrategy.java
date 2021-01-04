/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.util.Log
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Collections
 *  java.util.Comparator
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import android.util.Log;
import com.journeyapps.barcodescanner.Size;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class PreviewScalingStrategy {
    private static final String TAG = PreviewScalingStrategy.class.getSimpleName();

    public List<Size> getBestPreviewOrder(List<Size> list, final Size size) {
        if (size == null) {
            return list;
        }
        Collections.sort(list, (Comparator)new Comparator<Size>(){

            public int compare(Size size3, Size size2) {
                float f = PreviewScalingStrategy.this.getScore(size3, size);
                return Float.compare((float)PreviewScalingStrategy.this.getScore(size2, size), (float)f);
            }
        });
        return list;
    }

    public Size getBestPreviewSize(List<Size> list, Size size) {
        List<Size> list2 = this.getBestPreviewOrder(list, size);
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Viewfinder size: ");
        stringBuilder.append((Object)size);
        Log.i((String)string2, (String)stringBuilder.toString());
        String string3 = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Preview in order of preference: ");
        stringBuilder2.append(list2);
        Log.i((String)string3, (String)stringBuilder2.toString());
        return (Size)list2.get(0);
    }

    protected float getScore(Size size, Size size2) {
        return 0.5f;
    }

    public abstract Rect scalePreview(Size var1, Size var2);

}

