/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package android.form.avss.prepaidcard.com.rengwuxian.materialedittext.validation;

public abstract class METValidator {
    protected String errorMessage;

    public METValidator(String string2) {
        this.errorMessage = string2;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public abstract boolean isValid(CharSequence var1, boolean var2);

    public void setErrorMessage(String string2) {
        this.errorMessage = string2;
    }
}

