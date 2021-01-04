/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.Log
 *  android.view.Display
 *  android.view.Window
 *  android.view.WindowManager
 *  androidx.core.app.ActivityCompat
 *  androidx.core.content.ContextCompat
 *  com.google.zxing.BarcodeFormat
 *  com.google.zxing.ResultMetadataType
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.client.android.BeepManager
 *  com.google.zxing.client.android.InactivityTimer
 *  com.google.zxing.client.android.R
 *  com.google.zxing.client.android.R$string
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Iterable
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.List
 *  java.util.Map
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class CaptureManager {
    private static final String SAVED_ORIENTATION_LOCK = "SAVED_ORIENTATION_LOCK";
    private static final String TAG = CaptureManager.class.getSimpleName();
    private static int cameraPermissionReqCode = 250;
    private Activity activity;
    private boolean askedPermission = false;
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private BarcodeCallback callback = new BarcodeCallback(){

        @Override
        public void barcodeResult(final BarcodeResult barcodeResult) {
            CaptureManager.this.barcodeView.pause();
            CaptureManager.this.beepManager.playBeepSoundAndVibrate();
            CaptureManager.this.handler.post(new Runnable(){

                public void run() {
                    CaptureManager.this.returnResult(barcodeResult);
                }
            });
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> list) {
        }

    };
    private boolean destroyed = false;
    private boolean finishWhenClosed = false;
    private Handler handler;
    private InactivityTimer inactivityTimer;
    private int orientationLock = -1;
    private boolean returnBarcodeImagePath = false;
    private final CameraPreview.StateListener stateListener = new CameraPreview.StateListener(){

        @Override
        public void cameraClosed() {
            if (CaptureManager.this.finishWhenClosed) {
                Log.d((String)TAG, (String)"Camera closed; finishing activity");
                CaptureManager.this.finish();
            }
        }

        @Override
        public void cameraError(Exception exception) {
            CaptureManager.this.displayFrameworkBugMessageAndExit();
        }

        @Override
        public void previewSized() {
        }

        @Override
        public void previewStarted() {
        }

        @Override
        public void previewStopped() {
        }
    };

    public CaptureManager(Activity activity, DecoratedBarcodeView decoratedBarcodeView) {
        this.activity = activity;
        this.barcodeView = decoratedBarcodeView;
        decoratedBarcodeView.getBarcodeView().addStateListener(this.stateListener);
        this.handler = new Handler();
        this.inactivityTimer = new InactivityTimer((Context)activity, new Runnable(){

            public void run() {
                Log.d((String)TAG, (String)"Finishing due to inactivity");
                CaptureManager.this.finish();
            }
        });
        this.beepManager = new BeepManager(activity);
    }

    private void finish() {
        this.activity.finish();
    }

    private String getBarcodeImagePath(BarcodeResult barcodeResult) {
        if (this.returnBarcodeImagePath) {
            Bitmap bitmap = barcodeResult.getBitmap();
            try {
                File file = File.createTempFile((String)"barcodeimage", (String)".jpg", (File)this.activity.getCacheDir());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream)fileOutputStream);
                fileOutputStream.close();
                String string2 = file.getAbsolutePath();
                return string2;
            }
            catch (IOException iOException) {
                String string3 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to create temporary file and store bitmap! ");
                stringBuilder.append((Object)iOException);
                Log.w((String)string3, (String)stringBuilder.toString());
            }
        }
        return null;
    }

    public static int getCameraPermissionReqCode() {
        return cameraPermissionReqCode;
    }

    private void openCameraWithPermission() {
        if (ContextCompat.checkSelfPermission((Context)this.activity, (String)"android.permission.CAMERA") == 0) {
            this.barcodeView.resume();
            return;
        }
        if (!this.askedPermission) {
            ActivityCompat.requestPermissions((Activity)this.activity, (String[])new String[]{"android.permission.CAMERA"}, (int)cameraPermissionReqCode);
            this.askedPermission = true;
        }
    }

    public static Intent resultIntent(BarcodeResult barcodeResult, String string2) {
        Map<ResultMetadataType, Object> map;
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.addFlags(524288);
        intent.putExtra("SCAN_RESULT", barcodeResult.toString());
        intent.putExtra("SCAN_RESULT_FORMAT", barcodeResult.getBarcodeFormat().toString());
        byte[] arrby = barcodeResult.getRawBytes();
        if (arrby != null && arrby.length > 0) {
            intent.putExtra("SCAN_RESULT_BYTES", arrby);
        }
        if ((map = barcodeResult.getResultMetadata()) != null) {
            Number number;
            Iterable iterable;
            String string3;
            if (map.containsKey((Object)ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra("SCAN_RESULT_UPC_EAN_EXTENSION", map.get((Object)ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }
            if ((number = (Number)map.get((Object)ResultMetadataType.ORIENTATION)) != null) {
                intent.putExtra("SCAN_RESULT_ORIENTATION", number.intValue());
            }
            if ((string3 = (String)map.get((Object)ResultMetadataType.ERROR_CORRECTION_LEVEL)) != null) {
                intent.putExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL", string3);
            }
            if ((iterable = (Iterable)map.get((Object)ResultMetadataType.BYTE_SEGMENTS)) != null) {
                int n = 0;
                for (byte[] arrby2 : iterable) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SCAN_RESULT_BYTE_SEGMENTS_");
                    stringBuilder.append(n);
                    intent.putExtra(stringBuilder.toString(), arrby2);
                    ++n;
                }
            }
        }
        if (string2 != null) {
            intent.putExtra("SCAN_RESULT_IMAGE_PATH", string2);
        }
        return intent;
    }

    public static void setCameraPermissionReqCode(int n) {
        cameraPermissionReqCode = n;
    }

    protected void closeAndFinish() {
        if (this.barcodeView.getBarcodeView().isCameraClosed()) {
            this.finish();
        } else {
            this.finishWhenClosed = true;
        }
        this.barcodeView.pause();
        this.inactivityTimer.cancel();
    }

    public void decode() {
        this.barcodeView.decodeSingle(this.callback);
    }

    protected void displayFrameworkBugMessageAndExit() {
        if (!this.activity.isFinishing() && !this.destroyed) {
            if (this.finishWhenClosed) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)this.activity);
            builder.setTitle((CharSequence)this.activity.getString(R.string.zxing_app_name));
            builder.setMessage((CharSequence)this.activity.getString(R.string.zxing_msg_camera_framework_bug));
            builder.setPositiveButton(R.string.zxing_button_ok, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    CaptureManager.this.finish();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    CaptureManager.this.finish();
                }
            });
            builder.show();
            return;
        }
    }

    public void initializeFromIntent(Intent intent, Bundle bundle) {
        this.activity.getWindow().addFlags(128);
        if (bundle != null) {
            this.orientationLock = bundle.getInt(SAVED_ORIENTATION_LOCK, -1);
        }
        if (intent != null) {
            if (intent.getBooleanExtra("SCAN_ORIENTATION_LOCKED", true)) {
                this.lockOrientation();
            }
            if ("com.google.zxing.client.android.SCAN".equals((Object)intent.getAction())) {
                this.barcodeView.initializeFromIntent(intent);
            }
            if (!intent.getBooleanExtra("BEEP_ENABLED", true)) {
                this.beepManager.setBeepEnabled(false);
            }
            if (intent.hasExtra("TIMEOUT")) {
                Runnable runnable = new Runnable(){

                    public void run() {
                        CaptureManager.this.returnResultTimeout();
                    }
                };
                this.handler.postDelayed(runnable, intent.getLongExtra("TIMEOUT", 0L));
            }
            if (intent.getBooleanExtra("BARCODE_IMAGE_ENABLED", false)) {
                this.returnBarcodeImagePath = true;
            }
        }
    }

    protected void lockOrientation() {
        if (this.orientationLock == -1) {
            int n;
            int n2 = this.activity.getWindowManager().getDefaultDisplay().getRotation();
            int n3 = this.activity.getResources().getConfiguration().orientation;
            if (n3 == 2) {
                n = n2 != 0 && n2 != 1 ? 8 : 0;
            } else {
                n = 0;
                if (n3 == 1) {
                    n = n2 != 0 && n2 != 3 ? 9 : 1;
                }
            }
            this.orientationLock = n;
        }
        this.activity.setRequestedOrientation(this.orientationLock);
    }

    public void onDestroy() {
        this.destroyed = true;
        this.inactivityTimer.cancel();
        this.handler.removeCallbacksAndMessages(null);
    }

    public void onPause() {
        this.inactivityTimer.cancel();
        this.barcodeView.pauseAndWait();
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        if (n == cameraPermissionReqCode) {
            if (arrn.length > 0 && arrn[0] == 0) {
                this.barcodeView.resume();
                return;
            }
            this.displayFrameworkBugMessageAndExit();
        }
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT >= 23) {
            this.openCameraWithPermission();
        } else {
            this.barcodeView.resume();
        }
        this.inactivityTimer.start();
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(SAVED_ORIENTATION_LOCK, this.orientationLock);
    }

    protected void returnResult(BarcodeResult barcodeResult) {
        Intent intent = CaptureManager.resultIntent(barcodeResult, this.getBarcodeImagePath(barcodeResult));
        this.activity.setResult(-1, intent);
        this.closeAndFinish();
    }

    protected void returnResultTimeout() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("TIMEOUT", true);
        this.activity.setResult(0, intent);
        this.closeAndFinish();
    }

}

