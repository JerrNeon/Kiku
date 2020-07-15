package com.jn.kiku.widget.edittext;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：Stevie.Chen Time：2019/8/14
 * Class Comment：
 */
public class EnglishCharacterInputFilter implements InputFilter {

    private Pattern mEnglishPattern = Pattern.compile("^[0-9a-zA-Z_.]$",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = mEnglishPattern.matcher(source);
        if (!emojiMatcher.find()) {
            return "";
        }
        return null;
    }
}
