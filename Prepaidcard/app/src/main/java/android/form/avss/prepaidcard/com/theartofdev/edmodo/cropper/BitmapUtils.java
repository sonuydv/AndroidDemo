/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.net.Uri
 *  android.util.Log
 *  android.util.Pair
 *  androidx.exifinterface.media.ExifInterface
 *  java.io.Closeable
 *  java.io.File
 *  java.io.FileNotFoundException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.OutOfMemoryError
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.ref.WeakReference
 *  javax.microedition.khronos.egl.EGL10
 *  javax.microedition.khronos.egl.EGLConfig
 *  javax.microedition.khronos.egl.EGLContext
 *  javax.microedition.khronos.egl.EGLDisplay
 */
package android.form.avss.prepaidcard.com.theartofdev.edmodo.cropper;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import androidx.exifinterface.media.ExifInterface;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

final class BitmapUtils {
    static final Rect EMPTY_RECT = new Rect();
    static final RectF EMPTY_RECT_F = new RectF();
    static final float[] POINTS;
    static final float[] POINTS2;
    static final RectF RECT;
    private static int mMaxTextureSize;
    static Pair<String, WeakReference<Bitmap>> mStateBitmap;

    static {
        RECT = new RectF();
        POINTS = new float[6];
        POINTS2 = new float[6];
    }

    BitmapUtils() {
    }

    private static int calculateInSampleSizeByMaxTextureSize(int n, int n2) {
        int n3 = 1;
        if (mMaxTextureSize == 0) {
            mMaxTextureSize = BitmapUtils.getMaxTextureSize();
        }
        if (mMaxTextureSize > 0) {
            int n4;
            int n5;
            while ((n4 = n2 / n3) > (n5 = mMaxTextureSize) || n / n3 > n5) {
                n3 *= 2;
            }
        }
        return n3;
    }

    private static int calculateInSampleSizeByReqestedSize(int n, int n2, int n3, int n4) {
        int n5 = 1;
        if (n2 > n4 || n > n3) {
            while (n2 / 2 / n5 > n4 && n / 2 / n5 > n3) {
                n5 *= 2;
            }
        }
        return n5;
    }

    private static void closeSafe(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                return;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    static BitmapSampled cropBitmap(Context context, Uri uri, float[] arrf, int n, int n2, int n3, boolean bl, int n4, int n5, int n6, int n7, boolean bl2, boolean bl3) {
        int n8 = 1;
        do {
            int n9 = n8;
            try {
                BitmapSampled bitmapSampled = BitmapUtils.cropBitmap(context, uri, arrf, n, n2, n3, bl, n4, n5, n6, n7, bl2, bl3, n9);
                return bitmapSampled;
            }
            catch (OutOfMemoryError outOfMemoryError) {
                if ((n8 *= 2) <= 16) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to handle OOM by sampling (");
                stringBuilder.append(n8);
                stringBuilder.append("): ");
                stringBuilder.append((Object)uri);
                stringBuilder.append("\r\n");
                stringBuilder.append(outOfMemoryError.getMessage());
                throw new RuntimeException(stringBuilder.toString(), (Throwable)outOfMemoryError);
            }
            break;
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static BitmapSampled cropBitmap(Context context, Uri uri, float[] arrf, int n, int n2, int n3, boolean bl, int n4, int n5, int n6, int n7, boolean bl2, boolean bl3, int n8) {
        Bitmap bitmap;
        void var24_27;
        block8 : {
            int n9;
            Bitmap bitmap3;
            Rect rect = BitmapUtils.getRectFromPoints(arrf, n2, n3, bl, n4, n5);
            int n10 = n6 > 0 ? n6 : rect.width();
            int n11 = n7 > 0 ? n7 : rect.height();
            Bitmap bitmap2 = null;
            try {
                BitmapSampled bitmapSampled = BitmapUtils.decodeSampledBitmapRegion(context, uri, rect, n10, n11, n8);
                bitmap2 = bitmapSampled.bitmap;
                int n12 = bitmapSampled.sampleSize;
                bitmap = bitmap2;
                n9 = n12;
            }
            catch (Exception exception) {
                bitmap = bitmap2;
                n9 = 1;
            }
            if (bitmap == null) {
                int n13 = n10;
                int n14 = n11;
                return BitmapUtils.cropBitmap(context, uri, arrf, n, bl, n4, n5, n8, rect, n13, n14, bl2, bl3);
            }
            Bitmap bitmap4 = BitmapUtils.rotateAndFlipBitmapInt(bitmap, n, bl2, bl3);
            if (n % 90 == 0) {
                bitmap3 = bitmap4;
                return new BitmapSampled(bitmap3, n9);
            }
            try {
                bitmap3 = BitmapUtils.cropForRotatedImage(bitmap4, arrf, rect, n, bl, n4, n5);
                return new BitmapSampled(bitmap3, n9);
            }
            catch (OutOfMemoryError outOfMemoryError) {
                bitmap = bitmap4;
            }
            break block8;
            catch (OutOfMemoryError outOfMemoryError) {
                // empty catch block
            }
        }
        if (bitmap == null) throw var24_27;
        bitmap.recycle();
        throw var24_27;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static BitmapSampled cropBitmap(Context context, Uri uri, float[] arrf, int n, boolean bl, int n2, int n3, int n4, Rect rect, int n5, int n6, boolean bl2, boolean bl3) {
        void var26_23;
        Bitmap bitmap2;
        Bitmap bitmap;
        block16 : {
            Bitmap bitmap3;
            void var15_33;
            block15 : {
                void var16_30;
                block14 : {
                    Bitmap bitmap4;
                    float[] arrf2;
                    int n8;
                    int n7;
                    bitmap = null;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    int n9 = rect.width();
                    int n10 = rect.height();
                    options.inSampleSize = n8 = n4 * BitmapUtils.calculateInSampleSizeByReqestedSize(n9, n10, n5, n6);
                    bitmap3 = BitmapUtils.decodeImage(context.getContentResolver(), uri, options);
                    if (bitmap3 == null) return new BitmapSampled(bitmap, n8);
                    try {
                        arrf2 = new float[arrf.length];
                        System.arraycopy((Object)arrf, (int)0, (Object)arrf2, (int)0, (int)arrf.length);
                    }
                    catch (Throwable throwable) {
                        bitmap2 = bitmap3;
                        break block16;
                    }
                    catch (Exception exception) {
                        // empty catch block
                        break block14;
                    }
                    catch (OutOfMemoryError outOfMemoryError) {
                        // empty catch block
                        break block15;
                    }
                    for (int i = 0; i < (n7 = arrf2.length); ++i) {
                        try {
                            arrf2[i] = arrf2[i] / (float)options.inSampleSize;
                            continue;
                        }
                        catch (Throwable throwable) {
                            bitmap2 = bitmap3;
                            break block16;
                        }
                    }
                    bitmap = bitmap4 = BitmapUtils.cropBitmapObjectWithScale(bitmap3, arrf2, n, bl, n2, n3, 1.0f, bl2, bl3);
                    if (bitmap == bitmap3) return new BitmapSampled(bitmap, n8);
                    try {
                        bitmap3.recycle();
                        return new BitmapSampled(bitmap, n8);
                    }
                    catch (Exception exception) {
                    }
                    catch (OutOfMemoryError outOfMemoryError) {
                        break block15;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to load sampled bitmap: ");
                stringBuilder.append((Object)uri);
                stringBuilder.append("\r\n");
                stringBuilder.append(var16_30.getMessage());
                throw new RuntimeException(stringBuilder.toString(), (Throwable)var16_30);
            }
            if (bitmap == null) throw var15_33;
            bitmap.recycle();
            throw var15_33;
            catch (Throwable throwable) {
                bitmap2 = bitmap3;
            }
        }
        bitmap = null;
        if (bitmap2 == null) throw var26_23;
        bitmap2.recycle();
        throw var26_23;
    }

    static BitmapSampled cropBitmapObjectHandleOOM(Bitmap bitmap, float[] arrf, int n, boolean bl, int n2, int n3, boolean bl2, boolean bl3) {
        int n4 = 1;
        do {
            float f = 1.0f / (float)n4;
            try {
                BitmapSampled bitmapSampled = new BitmapSampled(BitmapUtils.cropBitmapObjectWithScale(bitmap, arrf, n, bl, n2, n3, f, bl2, bl3), n4);
                return bitmapSampled;
            }
            catch (OutOfMemoryError outOfMemoryError) {
                if ((n4 *= 2) <= 8) continue;
                throw outOfMemoryError;
            }
            break;
        } while (true);
    }

    private static Bitmap cropBitmapObjectWithScale(Bitmap bitmap, float[] arrf, int n, boolean bl, int n2, int n3, float f, boolean bl2, boolean bl3) {
        Rect rect = BitmapUtils.getRectFromPoints(arrf, bitmap.getWidth(), bitmap.getHeight(), bl, n2, n3);
        Matrix matrix = new Matrix();
        matrix.setRotate((float)n, (float)(bitmap.getWidth() / 2), (float)(bitmap.getHeight() / 2));
        float f2 = bl2 ? -f : f;
        float f3 = bl3 ? -f : f;
        matrix.postScale(f2, f3);
        Bitmap bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)rect.left, (int)rect.top, (int)rect.width(), (int)rect.height(), (Matrix)matrix, (boolean)true);
        Bitmap bitmap3 = bitmap2 == bitmap ? bitmap.copy(bitmap.getConfig(), false) : bitmap2;
        if (n % 90 != 0) {
            bitmap3 = BitmapUtils.cropForRotatedImage(bitmap3, arrf, rect, n, bl, n2, n3);
        }
        return bitmap3;
    }

    private static Bitmap cropForRotatedImage(Bitmap bitmap, float[] arrf, Rect rect, int n, boolean bl, int n2, int n3) {
        Bitmap bitmap2;
        if (n % 90 != 0) {
            int n4;
            int n5;
            int n6;
            int n7;
            double d = Math.toRadians((double)n);
            int n8 = n >= 90 && (n <= 180 || n >= 270) ? rect.right : rect.left;
            int n9 = 0;
            do {
                int n10 = arrf.length;
                n4 = 0;
                n7 = 0;
                n6 = 0;
                n5 = 0;
                if (n9 >= n10) break;
                if (arrf[n9] >= (float)(n8 - 1) && arrf[n9] <= (float)(n8 + 1)) {
                    n4 = (int)Math.abs((double)(Math.sin((double)d) * (double)((float)rect.bottom - arrf[n9 + 1])));
                    n7 = (int)Math.abs((double)(Math.cos((double)d) * (double)(arrf[n9 + 1] - (float)rect.top)));
                    n6 = (int)Math.abs((double)((double)(arrf[n9 + 1] - (float)rect.top) / Math.sin((double)d)));
                    n5 = (int)Math.abs((double)((double)((float)rect.bottom - arrf[n9 + 1]) / Math.cos((double)d)));
                    break;
                }
                n9 += 2;
            } while (true);
            rect.set(n4, n7, n4 + n6, n7 + n5);
            if (bl) {
                BitmapUtils.fixRectForAspectRatio(rect, n2, n3);
            }
            if (bitmap != (bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)rect.left, (int)rect.top, (int)rect.width(), (int)rect.height()))) {
                bitmap.recycle();
                return bitmap2;
            }
        } else {
            bitmap2 = bitmap;
        }
        return bitmap2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Bitmap decodeImage(ContentResolver contentResolver, Uri uri, BitmapFactory.Options options) throws FileNotFoundException {
        InputStream inputStream;
        Throwable throwable2222;
        do {
            inputStream = null;
            inputStream = contentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)inputStream, (Rect)EMPTY_RECT, (BitmapFactory.Options)options);
            BitmapUtils.closeSafe((Closeable)inputStream);
            return bitmap;
            {
                catch (Throwable throwable2222) {
                    break;
                }
                catch (OutOfMemoryError outOfMemoryError) {}
                {
                    options.inSampleSize = 2 * options.inSampleSize;
                }
                BitmapUtils.closeSafe((Closeable)inputStream);
                if (options.inSampleSize <= 512) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to decode image: ");
                stringBuilder.append((Object)uri);
                throw new RuntimeException(stringBuilder.toString());
            }
            break;
        } while (true);
        BitmapUtils.closeSafe((Closeable)inputStream);
        throw throwable2222;
    }

    private static BitmapFactory.Options decodeImageForOption(ContentResolver contentResolver, Uri uri) throws FileNotFoundException {
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream((InputStream)inputStream, (Rect)EMPTY_RECT, (BitmapFactory.Options)options);
            options.inJustDecodeBounds = false;
            return options;
        }
        finally {
            BitmapUtils.closeSafe((Closeable)inputStream);
        }
    }

    static BitmapSampled decodeSampledBitmap(Context context, Uri uri, int n, int n2) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            BitmapFactory.Options options = BitmapUtils.decodeImageForOption(contentResolver, uri);
            options.inSampleSize = Math.max((int)BitmapUtils.calculateInSampleSizeByReqestedSize(options.outWidth, options.outHeight, n, n2), (int)BitmapUtils.calculateInSampleSizeByMaxTextureSize(options.outWidth, options.outHeight));
            BitmapSampled bitmapSampled = new BitmapSampled(BitmapUtils.decodeImage(contentResolver, uri, options), options.inSampleSize);
            return bitmapSampled;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to load sampled bitmap: ");
            stringBuilder.append((Object)uri);
            stringBuilder.append("\r\n");
            stringBuilder.append(exception.getMessage());
            throw new RuntimeException(stringBuilder.toString(), (Throwable)exception);
        }
    }

    /*
     * Exception decompiling
     */
    private static BitmapSampled decodeSampledBitmapRegion(Context var0, Uri var1_1, Rect var2_2, int var3_3, int var4_4, int var5_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
        // java.lang.Thread.run(Thread.java:764)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void fixRectForAspectRatio(Rect rect, int n, int n2) {
        if (n == n2 && rect.width() != rect.height()) {
            if (rect.height() > rect.width()) {
                rect.bottom -= rect.height() - rect.width();
                return;
            }
            rect.right -= rect.width() - rect.height();
        }
    }

    private static int getMaxTextureSize() {
        EGL10 eGL10 = (EGL10)EGLContext.getEGL();
        EGLDisplay eGLDisplay = eGL10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        eGL10.eglInitialize(eGLDisplay, new int[2]);
        int[] arrn = new int[1];
        eGL10.eglGetConfigs(eGLDisplay, null, 0, arrn);
        EGLConfig[] arreGLConfig = new EGLConfig[arrn[0]];
        eGL10.eglGetConfigs(eGLDisplay, arreGLConfig, arrn[0], arrn);
        int[] arrn2 = new int[1];
        int n = 0;
        int n2 = 0;
        do {
            block6 : {
                try {
                    if (n2 < arrn[0]) {
                        eGL10.eglGetConfigAttrib(eGLDisplay, arreGLConfig[n2], 12332, arrn2);
                        if (n < arrn2[0]) {
                            n = arrn2[0];
                        }
                        break block6;
                    }
                    eGL10.eglTerminate(eGLDisplay);
                    int n3 = Math.max((int)n, (int)2048);
                    return n3;
                }
                catch (Exception exception) {
                    return 2048;
                }
            }
            ++n2;
        } while (true);
    }

    static float getRectBottom(float[] arrf) {
        return Math.max((float)Math.max((float)Math.max((float)arrf[1], (float)arrf[3]), (float)arrf[5]), (float)arrf[7]);
    }

    static float getRectCenterX(float[] arrf) {
        return (BitmapUtils.getRectRight(arrf) + BitmapUtils.getRectLeft(arrf)) / 2.0f;
    }

    static float getRectCenterY(float[] arrf) {
        return (BitmapUtils.getRectBottom(arrf) + BitmapUtils.getRectTop(arrf)) / 2.0f;
    }

    static Rect getRectFromPoints(float[] arrf, int n, int n2, boolean bl, int n3, int n4) {
        Rect rect = new Rect(Math.round((float)Math.max((float)0.0f, (float)BitmapUtils.getRectLeft(arrf))), Math.round((float)Math.max((float)0.0f, (float)BitmapUtils.getRectTop(arrf))), Math.round((float)Math.min((float)n, (float)BitmapUtils.getRectRight(arrf))), Math.round((float)Math.min((float)n2, (float)BitmapUtils.getRectBottom(arrf))));
        if (bl) {
            BitmapUtils.fixRectForAspectRatio(rect, n3, n4);
        }
        return rect;
    }

    static float getRectHeight(float[] arrf) {
        return BitmapUtils.getRectBottom(arrf) - BitmapUtils.getRectTop(arrf);
    }

    static float getRectLeft(float[] arrf) {
        return Math.min((float)Math.min((float)Math.min((float)arrf[0], (float)arrf[2]), (float)arrf[4]), (float)arrf[6]);
    }

    static float getRectRight(float[] arrf) {
        return Math.max((float)Math.max((float)Math.max((float)arrf[0], (float)arrf[2]), (float)arrf[4]), (float)arrf[6]);
    }

    static float getRectTop(float[] arrf) {
        return Math.min((float)Math.min((float)Math.min((float)arrf[1], (float)arrf[3]), (float)arrf[5]), (float)arrf[7]);
    }

    static float getRectWidth(float[] arrf) {
        return BitmapUtils.getRectRight(arrf) - BitmapUtils.getRectLeft(arrf);
    }

    static Bitmap resizeBitmap(Bitmap bitmap, int n, int n2, CropImageView.RequestSizeOptions requestSizeOptions) {
        block9 : {
            if (n > 0 && n2 > 0) {
                Bitmap bitmap2;
                block10 : {
                    int n3;
                    int n4;
                    float f;
                    block11 : {
                        try {
                            if (requestSizeOptions != CropImageView.RequestSizeOptions.RESIZE_FIT && requestSizeOptions != CropImageView.RequestSizeOptions.RESIZE_INSIDE && requestSizeOptions != CropImageView.RequestSizeOptions.RESIZE_EXACT) break block9;
                            if (requestSizeOptions == CropImageView.RequestSizeOptions.RESIZE_EXACT) {
                                bitmap2 = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)n, (int)n2, (boolean)false);
                                break block10;
                            }
                            n4 = bitmap.getWidth();
                            f = Math.max((float)((float)n4 / (float)n), (float)((float)(n3 = bitmap.getHeight()) / (float)n2));
                            if (f > 1.0f) break block11;
                        }
                        catch (Exception exception) {
                            Log.w((String)"AIC", (String)"Failed to resize cropped image, return bitmap before resize", (Throwable)exception);
                            return bitmap;
                        }
                        CropImageView.RequestSizeOptions requestSizeOptions2 = CropImageView.RequestSizeOptions.RESIZE_FIT;
                        bitmap2 = null;
                        if (requestSizeOptions != requestSizeOptions2) break block10;
                    }
                    bitmap2 = Bitmap.createScaledBitmap((Bitmap)bitmap, (int)((int)((float)n4 / f)), (int)((int)((float)n3 / f)), (boolean)false);
                }
                if (bitmap2 != null) {
                    if (bitmap2 != bitmap) {
                        bitmap.recycle();
                    }
                    return bitmap2;
                }
            }
        }
        return bitmap;
    }

    private static Bitmap rotateAndFlipBitmapInt(Bitmap bitmap, int n, boolean bl, boolean bl2) {
        if (n <= 0 && !bl && !bl2) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate((float)n);
        float f = -1.0f;
        float f2 = bl ? -1.0f : 1.0f;
        if (!bl2) {
            f = 1.0f;
        }
        matrix.postScale(f2, f);
        Bitmap bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)bitmap.getWidth(), (int)bitmap.getHeight(), (Matrix)matrix, (boolean)false);
        if (bitmap2 != bitmap) {
            bitmap.recycle();
        }
        return bitmap2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static RotateBitmapResult rotateBitmapByExif(Bitmap bitmap, Context context, Uri uri) {
        ExifInterface exifInterface = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            exifInterface = null;
            if (inputStream != null) {
                exifInterface = new ExifInterface(inputStream);
                inputStream.close();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (exifInterface != null) {
            return BitmapUtils.rotateBitmapByExif(bitmap, exifInterface);
        }
        return new RotateBitmapResult(bitmap, 0);
    }

    static RotateBitmapResult rotateBitmapByExif(Bitmap bitmap, ExifInterface exifInterface) {
        int n = exifInterface.getAttributeInt("Orientation", 1);
        int n2 = n != 3 ? (n != 6 ? (n != 8 ? 0 : 270) : 90) : 180;
        return new RotateBitmapResult(bitmap, n2);
    }

    static void writeBitmapToUri(Context context, Bitmap bitmap, Uri uri, Bitmap.CompressFormat compressFormat, int n) throws FileNotFoundException {
        OutputStream outputStream = null;
        try {
            outputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(compressFormat, n, outputStream);
        }
        catch (Throwable throwable) {
            BitmapUtils.closeSafe(outputStream);
            throw throwable;
        }
        BitmapUtils.closeSafe((Closeable)outputStream);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Uri writeTempStateStoreBitmap(Context var0, Bitmap var1_1, Uri var2_2) {
        var3_3 = true;
        if (var2_2 != null) ** GOTO lbl6
        try {
            block3 : {
                var2_2 = Uri.fromFile((File)File.createTempFile((String)"aic_state_store_temp", (String)".jpg", (File)var0.getCacheDir()));
                break block3;
lbl6: // 1 sources:
                if (new File(var2_2.getPath()).exists()) {
                    var3_3 = false;
                }
            }
            if (var3_3 == false) return var2_2;
            BitmapUtils.writeBitmapToUri(var0, var1_1, var2_2, Bitmap.CompressFormat.JPEG, 95);
            return var2_2;
        }
        catch (Exception var4_4) {
            Log.w((String)"AIC", (String)"Failed to write bitmap to temp file for image-cropper save instance state", (Throwable)var4_4);
            return null;
        }
    }

    static final class BitmapSampled {
        public final Bitmap bitmap;
        final int sampleSize;

        BitmapSampled(Bitmap bitmap, int n) {
            this.bitmap = bitmap;
            this.sampleSize = n;
        }
    }

    static final class RotateBitmapResult {
        public final Bitmap bitmap;
        final int degrees;

        RotateBitmapResult(Bitmap bitmap, int n) {
            this.bitmap = bitmap;
            this.degrees = n;
        }
    }

}

