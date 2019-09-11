package com.jn.kiku.widget.edittext;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (EditText过滤Emoji表情)
 * @create by: chenwei
 * @date 2017/7/20 18:23
 */
public class EmoJiInputFilter implements InputFilter {

    private Context mContext = null;

    private Pattern mEmojiPattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    public EmoJiInputFilter(Context context) {
        mContext = context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = mEmojiPattern.matcher(source);
        if (emojiMatcher.find()) {
            Toast.makeText(mContext.getApplicationContext(), "不支持输入表情", Toast.LENGTH_SHORT).show();
            return "";
        }
        return null;
    }
}
