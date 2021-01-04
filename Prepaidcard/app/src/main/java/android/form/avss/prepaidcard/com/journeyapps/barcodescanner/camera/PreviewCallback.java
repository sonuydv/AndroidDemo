/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

import com.journeyapps.barcodescanner.SourceData;

public interface PreviewCallback {
    public void onPreview(SourceData var1);

    public void onPreviewError(Exception var1);
}

