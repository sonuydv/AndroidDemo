/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package android.form.avss.prepaidcard.com.journeyapps.barcodescanner;

public class Size
implements Comparable<Size> {
    public final int height;
    public final int width;

    public Size(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    public int compareTo(Size size) {
        int n = size.height * size.width;
        int n2 = this.height * this.width;
        if (n < n2) {
            return 1;
        }
        if (n > n2) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null) {
            if (this.getClass() != object.getClass()) {
                return false;
            }
            Size size = (Size)object;
            return this.width == size.width && this.height == size.height;
        }
        return false;
    }

    public boolean fitsIn(Size size) {
        return this.width <= size.width && this.height <= size.height;
    }

    public int hashCode() {
        return 31 * this.width + this.height;
    }

    public Size rotate() {
        return new Size(this.height, this.width);
    }

    public Size scale(int n, int n2) {
        return new Size(n * this.width / n2, n * this.height / n2);
    }

    public Size scaleCrop(Size size) {
        int n = this.width;
        int n2 = size.height;
        int n3 = n * n2;
        int n4 = size.width;
        int n5 = this.height;
        if (n3 <= n4 * n5) {
            return new Size(n4, n5 * n4 / n);
        }
        return new Size(n * n2 / n5, n2);
    }

    public Size scaleFit(Size size) {
        int n = this.width;
        int n2 = size.height;
        int n3 = n * n2;
        int n4 = size.width;
        int n5 = this.height;
        if (n3 >= n4 * n5) {
            return new Size(n4, n5 * n4 / n);
        }
        return new Size(n * n2 / n5, n2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.width);
        stringBuilder.append("x");
        stringBuilder.append(this.height);
        return stringBuilder.toString();
    }
}

