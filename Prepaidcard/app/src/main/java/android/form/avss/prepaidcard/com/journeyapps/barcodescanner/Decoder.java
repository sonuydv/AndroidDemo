/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.zxing.Binarizer
 *  com.google.zxing.BinaryBitmap
 *  com.google.zxing.LuminanceSource
 *  com.google.zxing.MultiFormatReader
 *  com.google.zxing.Reader
 *  com.google.zxing.Result
 *  com.google.zxing.ResultPoint
 *  com.google.zxing.ResultPointCallback
 *  com.google.zxing.common.HybridBinarizer
 *  java.lang.Exception
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.HybridBinarizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Decoder
implements ResultPointCallback {
    private List<ResultPoint> possibleResultPoints = new ArrayList();
    private Reader reader;

    public Decoder(Reader reader) {
        this.reader = reader;
    }

    protected Result decode(BinaryBitmap binaryBitmap) {
        this.possibleResultPoints.clear();
        try {
            if (this.reader instanceof MultiFormatReader) {
                Result result = ((MultiFormatReader)this.reader).decodeWithState(binaryBitmap);
                return result;
            }
            Result result = this.reader.decode(binaryBitmap);
            return result;
        }
        catch (Exception exception) {
            return null;
        }
        finally {
            this.reader.reset();
        }
    }

    public Result decode(LuminanceSource luminanceSource) {
        return this.decode(this.toBitmap(luminanceSource));
    }

    public void foundPossibleResultPoint(ResultPoint resultPoint) {
        this.possibleResultPoints.add((Object)resultPoint);
    }

    public List<ResultPoint> getPossibleResultPoints() {
        return new ArrayList(this.possibleResultPoints);
    }

    protected Reader getReader() {
        return this.reader;
    }

    protected BinaryBitmap toBitmap(LuminanceSource luminanceSource) {
        return new BinaryBitmap((Binarizer)new HybridBinarizer(luminanceSource));
    }
}

