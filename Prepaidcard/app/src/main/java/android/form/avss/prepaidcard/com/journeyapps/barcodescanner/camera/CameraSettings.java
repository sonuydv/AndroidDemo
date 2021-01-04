/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner.camera;

public class CameraSettings {
    private boolean autoFocusEnabled = true;
    private boolean autoTorchEnabled = false;
    private boolean barcodeSceneModeEnabled = false;
    private boolean continuousFocusEnabled = false;
    private boolean exposureEnabled = false;
    private FocusMode focusMode = FocusMode.AUTO;
    private boolean meteringEnabled = false;
    private int requestedCameraId = -1;
    private boolean scanInverted = false;

    public FocusMode getFocusMode() {
        return this.focusMode;
    }

    public int getRequestedCameraId() {
        return this.requestedCameraId;
    }

    public boolean isAutoFocusEnabled() {
        return this.autoFocusEnabled;
    }

    public boolean isAutoTorchEnabled() {
        return this.autoTorchEnabled;
    }

    public boolean isBarcodeSceneModeEnabled() {
        return this.barcodeSceneModeEnabled;
    }

    public boolean isContinuousFocusEnabled() {
        return this.continuousFocusEnabled;
    }

    public boolean isExposureEnabled() {
        return this.exposureEnabled;
    }

    public boolean isMeteringEnabled() {
        return this.meteringEnabled;
    }

    public boolean isScanInverted() {
        return this.scanInverted;
    }

    public void setAutoFocusEnabled(boolean bl) {
        this.autoFocusEnabled = bl;
        if (bl && this.continuousFocusEnabled) {
            this.focusMode = FocusMode.CONTINUOUS;
            return;
        }
        if (bl) {
            this.focusMode = FocusMode.AUTO;
            return;
        }
        this.focusMode = null;
    }

    public void setAutoTorchEnabled(boolean bl) {
        this.autoTorchEnabled = bl;
    }

    public void setBarcodeSceneModeEnabled(boolean bl) {
        this.barcodeSceneModeEnabled = bl;
    }

    public void setContinuousFocusEnabled(boolean bl) {
        this.continuousFocusEnabled = bl;
        if (bl) {
            this.focusMode = FocusMode.CONTINUOUS;
            return;
        }
        if (this.autoFocusEnabled) {
            this.focusMode = FocusMode.AUTO;
            return;
        }
        this.focusMode = null;
    }

    public void setExposureEnabled(boolean bl) {
        this.exposureEnabled = bl;
    }

    public void setFocusMode(FocusMode focusMode) {
        this.focusMode = focusMode;
    }

    public void setMeteringEnabled(boolean bl) {
        this.meteringEnabled = bl;
    }

    public void setRequestedCameraId(int n) {
        this.requestedCameraId = n;
    }

    public void setScanInverted(boolean bl) {
        this.scanInverted = bl;
    }

    public static final class FocusMode
    extends Enum<FocusMode> {
        private static final /* synthetic */ FocusMode[] $VALUES;
        public static final /* enum */ FocusMode AUTO;
        public static final /* enum */ FocusMode CONTINUOUS;
        public static final /* enum */ FocusMode INFINITY;
        public static final /* enum */ FocusMode MACRO;

        static {
            FocusMode focusMode;
            AUTO = new FocusMode();
            CONTINUOUS = new FocusMode();
            INFINITY = new FocusMode();
            MACRO = focusMode = new FocusMode();
            FocusMode[] arrfocusMode = new FocusMode[]{AUTO, CONTINUOUS, INFINITY, focusMode};
            $VALUES = arrfocusMode;
        }

        public static FocusMode valueOf(String string2) {
            return (FocusMode)Enum.valueOf(FocusMode.class, (String)string2);
        }

        public static FocusMode[] values() {
            return (FocusMode[])$VALUES.clone();
        }
    }

}

