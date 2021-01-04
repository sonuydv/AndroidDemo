/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 *  android.widget.TextView
 *  com.google.zxing.BarcodeFormat
 *  com.google.zxing.DecodeHintType
 *  com.google.zxing.MultiFormatReader
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.client.android.DecodeFormatManager
 *  com.google.zxing.client.android.DecodeHintManager
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$id
 *  com.google.zxing.client.android.R$layout
 *  com.google.zxing.client.android.R$styleable
 *  java.lang.CharSequence
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collection
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.DecodeFormatManager;
import com.google.zxing.client.android.DecodeHintManager;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.DecoderFactory;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecoratedBarcodeView
extends FrameLayout {
    private BarcodeView barcodeView;
    private TextView statusView;
    private TorchListener torchListener;
    private ViewfinderView viewFinder;

    public DecoratedBarcodeView(Context context) {
        super(context);
        this.initialize();
    }

    public DecoratedBarcodeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initialize(attributeSet);
    }

    public DecoratedBarcodeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initialize(attributeSet);
    }

    private void initialize() {
        this.initialize(null);
    }

    private void initialize(AttributeSet attributeSet) {
        BarcodeView barcodeView;
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.zxing_view);
        int n = typedArray.getResourceId(R.styleable.zxing_view_zxing_scanner_layout, R.layout.zxing_barcode_scanner);
        typedArray.recycle();
        DecoratedBarcodeView.inflate((Context)this.getContext(), (int)n, (ViewGroup)this);
        this.barcodeView = barcodeView = (BarcodeView)this.findViewById(R.id.zxing_barcode_surface);
        if (barcodeView != null) {
            ViewfinderView viewfinderView;
            barcodeView.initializeAttributes(attributeSet);
            this.viewFinder = viewfinderView = (ViewfinderView)this.findViewById(R.id.zxing_viewfinder_view);
            if (viewfinderView != null) {
                viewfinderView.setCameraPreview(this.barcodeView);
                this.statusView = (TextView)this.findViewById(R.id.zxing_status_view);
                return;
            }
            throw new IllegalArgumentException("There is no a com.journeyapps.barcodescanner.ViewfinderView on provided layout with the id \"zxing_viewfinder_view\".");
        }
        throw new IllegalArgumentException("There is no a com.journeyapps.barcodescanner.BarcodeView on provided layout with the id \"zxing_barcode_surface\".");
    }

    public void changeCameraParameters(CameraParametersCallback cameraParametersCallback) {
        this.barcodeView.changeCameraParameters(cameraParametersCallback);
    }

    public void decodeContinuous(BarcodeCallback barcodeCallback) {
        this.barcodeView.decodeContinuous(new WrappedCallback(barcodeCallback));
    }

    public void decodeSingle(BarcodeCallback barcodeCallback) {
        this.barcodeView.decodeSingle(new WrappedCallback(barcodeCallback));
    }

    public BarcodeView getBarcodeView() {
        return (BarcodeView)this.findViewById(R.id.zxing_barcode_surface);
    }

    public TextView getStatusView() {
        return this.statusView;
    }

    public ViewfinderView getViewFinder() {
        return this.viewFinder;
    }

    public void initializeFromIntent(Intent intent) {
        int n;
        String string2;
        Set set = DecodeFormatManager.parseDecodeFormats((Intent)intent);
        Map map = DecodeHintManager.parseDecodeHints((Intent)intent);
        CameraSettings cameraSettings = new CameraSettings();
        if (intent.hasExtra("SCAN_CAMERA_ID") && (n = intent.getIntExtra("SCAN_CAMERA_ID", -1)) >= 0) {
            cameraSettings.setRequestedCameraId(n);
        }
        if ((string2 = intent.getStringExtra("PROMPT_MESSAGE")) != null) {
            this.setStatusText(string2);
        }
        int n2 = intent.getIntExtra("SCAN_TYPE", 0);
        String string3 = intent.getStringExtra("CHARACTER_SET");
        new MultiFormatReader().setHints(map);
        this.barcodeView.setCameraSettings(cameraSettings);
        this.barcodeView.setDecoderFactory(new DefaultDecoderFactory((Collection<BarcodeFormat>)set, map, string3, n2));
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n != 24) {
            if (n != 25) {
                if (n != 27 && n != 80) {
                    return super.onKeyDown(n, keyEvent);
                }
                return true;
            }
            this.setTorchOff();
            return true;
        }
        this.setTorchOn();
        return true;
    }

    public void pause() {
        this.barcodeView.pause();
    }

    public void pauseAndWait() {
        this.barcodeView.pauseAndWait();
    }

    public void resume() {
        this.barcodeView.resume();
    }

    public void setStatusText(String string2) {
        TextView textView = this.statusView;
        if (textView != null) {
            textView.setText((CharSequence)string2);
        }
    }

    public void setTorchListener(TorchListener torchListener) {
        this.torchListener = torchListener;
    }

    public void setTorchOff() {
        this.barcodeView.setTorch(false);
        TorchListener torchListener = this.torchListener;
        if (torchListener != null) {
            torchListener.onTorchOff();
        }
    }

    public void setTorchOn() {
        this.barcodeView.setTorch(true);
        TorchListener torchListener = this.torchListener;
        if (torchListener != null) {
            torchListener.onTorchOn();
        }
    }

    public static interface TorchListener {
        public void onTorchOff();

        public void onTorchOn();
    }

    private class WrappedCallback
    implements BarcodeCallback {
        private BarcodeCallback delegate;

        public WrappedCallback(BarcodeCallback barcodeCallback) {
            this.delegate = barcodeCallback;
        }

        @Override
        public void barcodeResult(BarcodeResult barcodeResult) {
            this.delegate.barcodeResult(barcodeResult);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> list) {
            for (ResultPoint resultPoint : list) {
                DecoratedBarcodeView.this.viewFinder.addPossibleResultPoint(resultPoint);
            }
            this.delegate.possibleResultPoints(list);
        }
    }

}

