/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.View
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$id
 *  com.google.zxing.client.android.R$layout
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CaptureActivity
extends Activity {
    private DecoratedBarcodeView barcodeScannerView;
    private CaptureManager capture;

    protected DecoratedBarcodeView initializeContent() {
        this.setContentView(R.layout.zxing_capture);
        return (DecoratedBarcodeView)this.findViewById(R.id.zxing_barcode_scanner);
    }

    protected void onCreate(Bundle bundle) {
        CaptureManager captureManager;
        super.onCreate(bundle);
        this.barcodeScannerView = this.initializeContent();
        this.capture = captureManager = new CaptureManager(this, this.barcodeScannerView);
        captureManager.initializeFromIntent(this.getIntent(), bundle);
        this.capture.decode();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.capture.onDestroy();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        return this.barcodeScannerView.onKeyDown(n, keyEvent) || super.onKeyDown(n, keyEvent);
        {
        }
    }

    protected void onPause() {
        super.onPause();
        this.capture.onPause();
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        this.capture.onRequestPermissionsResult(n, arrstring, arrn);
    }

    protected void onResume() {
        super.onResume();
        this.capture.onResume();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.capture.onSaveInstanceState(bundle);
    }
}

