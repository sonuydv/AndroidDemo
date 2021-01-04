/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.String
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package android.form.avss.prepaidcard.com.rengwuxian.materialedittext.validation;

import com.rengwuxian.materialedittext.validation.METValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpValidator
extends METValidator {
    private Pattern pattern;

    public RegexpValidator(String string2, String string3) {
        super(string2);
        this.pattern = Pattern.compile((String)string3);
    }

    @Override
    public boolean isValid(CharSequence charSequence, boolean bl) {
        return this.pattern.matcher(charSequence).matches();
    }
}

