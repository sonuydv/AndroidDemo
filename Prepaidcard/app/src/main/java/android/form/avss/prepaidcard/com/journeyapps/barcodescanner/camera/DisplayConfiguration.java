/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import android.graphics.Rect;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.camera.FitCenterStrategy;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;
import java.util.List;

public class DisplayConfiguration {
    private static final String TAG = DisplayConfiguration.class.getSimpleName();
    private boolean center = false;
    private PreviewScalingStrategy previewScalingStrategy = new FitCenterStrategy();
    private int rotation;
    private Size viewfinderSize;

    public DisplayConfiguration(int n) {
        this.rotation = n;
    }

    public DisplayConfiguration(int n, Size size) {
        this.rotation = n;
        this.viewfinderSize = size;
    }

    public Size getBestPreviewSize(List<Size> list, boolean bl) {
        Size size = this.getDesiredPreviewSize(bl);
        return this.previewScalingStrategy.getBestPreviewSize(list, size);
    }

    public Size getDesiredPreviewSize(boolean bl) {
        Size size = this.viewfinderSize;
        if (size == null) {
            return null;
        }
        if (bl) {
            return size.rotate();
        }
        return size;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        return this.previewScalingStrategy;
    }

    public int getRotation() {
        return this.rotation;
    }

    public Size getViewfinderSize() {
        return this.viewfinderSize;
    }

    public Rect scalePreview(Size size) {
        return this.previewScalingStrategy.scalePreview(size, this.viewfinderSize);
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.previewScalingStrategy = previewScalingStrategy;
    }
}

