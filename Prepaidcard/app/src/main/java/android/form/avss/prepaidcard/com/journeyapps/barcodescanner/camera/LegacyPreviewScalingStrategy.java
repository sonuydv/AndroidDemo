/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.util.Log
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
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LegacyPreviewScalingStrategy
extends PreviewScalingStrategy {
    private static final String TAG = LegacyPreviewScalingStrategy.class.getSimpleName();

    public static Size scale(Size size, Size size2) {
        Size size3 = size;
        if (!size2.fitsIn(size3)) {
            do {
                Size size4 = size3.scale(3, 2);
                Size size5 = size3.scale(2, 1);
                if (size2.fitsIn(size4)) {
                    return size4;
                }
                if (size2.fitsIn(size5)) {
                    return size5;
                }
                size3 = size5;
            } while (true);
        }
        do {
            Size size6 = size3.scale(2, 3);
            Size size7 = size3.scale(1, 2);
            if (!size2.fitsIn(size7)) {
                if (size2.fitsIn(size6)) {
                    return size6;
                }
                return size3;
            }
            size3 = size7;
        } while (true);
    }

    @Override
    public Size getBestPreviewSize(List<Size> list, final Size size) {
        if (size == null) {
            return (Size)list.get(0);
        }
        Collections.sort(list, (Comparator)new Comparator<Size>(){

            public int compare(Size size3, Size size2) {
                int n = LegacyPreviewScalingStrategy.scale((Size)size3, (Size)size).width - size3.width;
                int n2 = LegacyPreviewScalingStrategy.scale((Size)size2, (Size)size).width - size2.width;
                if (n == 0 && n2 == 0) {
                    return size3.compareTo(size2);
                }
                if (n == 0) {
                    return -1;
                }
                if (n2 == 0) {
                    return 1;
                }
                if (n < 0 && n2 < 0) {
                    return size3.compareTo(size2);
                }
                if (n > 0 && n2 > 0) {
                    return -size3.compareTo(size2);
                }
                if (n < 0) {
                    return -1;
                }
                return 1;
            }
        });
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Viewfinder size: ");
        stringBuilder.append((Object)size);
        Log.i((String)string2, (String)stringBuilder.toString());
        String string3 = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Preview in order of preference: ");
        stringBuilder2.append(list);
        Log.i((String)string3, (String)stringBuilder2.toString());
        return (Size)list.get(0);
    }

    @Override
    public Rect scalePreview(Size size, Size size2) {
        Size size3 = LegacyPreviewScalingStrategy.scale(size, size2);
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

