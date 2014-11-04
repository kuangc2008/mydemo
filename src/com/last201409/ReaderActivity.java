package com.last201409;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.example.demo.R;
import com.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;


public class ReaderActivity extends Activity {
    private String filePath;
    private TextView mFileTV;
    private SpannableString spannableString;
    private List<MyWord> words = new ArrayList<MyWord>();

    private class MyWord {
        int pos;
        String text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_layout);
        mFileTV = (TextView) findViewById(R.id.fileTV);

        Uri dataUri = getIntent().getData();
        if(dataUri != null) {
            filePath = dataUri.getPath();
        }

        String text = new String(FileUtils.fileToByteArray(filePath));
        Log.w("kcc", "text->" + text);
        colorText(text);
    }



    private void colorText(String text) {

        spannableString = new SpannableString(text);
        initMyWord(text);

//        for(MyWord word : words) {
//            if(isKeyWord(word)) {
//                colorKeyWord(word.text, word.pos);
//            }
//        }
        mFileTV.setText(spannableString);
    }

    public void initMyWord(String text) {
        int len = text.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<len; i++ ) {
//            if(sb == null || !TextUtils.isEmpty(sb.toString())) {
//                sb =
//            }
            char posChar = text.charAt(i);
            if(isNewSymbol(posChar)) {
                if(!TextUtils.isEmpty(sb.toString())) {
                    MyWord word = new MyWord();
                    word.pos = i;
                    word.text  = sb.toString();
                    words.add(word);

                    if(isKeyWord(word)) {
                        colorKeyWord(word.text, word.pos);
                    }

                }
                sb.delete(0, sb.toString().length());
            } else {
                sb.append(posChar);
            }
        }
    }


    private void colorKeyWord(String word, int lasPos) {
        spannableString.setSpan(new ForegroundColorSpan(0xFFff8800), lasPos - word.length(), lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        int size = (int) (11 * getResources().getDisplayMetrics().scaledDensity);
        spannableString.setSpan(new AbsoluteSizeSpan(size),lasPos - word.length(),lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private  boolean isNewSymbol(char symbol) {
        for(char lineSymbol : NEW_LINE_SYMBOL) {
            if(lineSymbol == symbol) {
                return true;
            }
        }
        return false;
    }

    private  boolean isKeyWord(MyWord word) {
        for(String keyWord : KEY_WORD) {
            if(keyWord.equals(word.text)) {
                return true;
            }
        }
        return false;
    }

    public static final char[] NEW_LINE_SYMBOL = new char[] {
            '\n',
            '\r',
            '\t',
            ' ',
            '{',
            '}',
            '(',
            ')',
            '[',
            ']'
    };

    public static final String[] KEY_WORD = new String[] {
            "const",
            "interface",
            "goto",
            "public",
            "protected",
            "private",
            "class",
            "abstract",
            "implements",
            "extends",
            "new",
            "import",
            "package",
            "byte",
            "char",
            "boolean",
            "short",
            "int",
            "float",
            "long",
            "double",
            "void",
            "null",
            "true",
            "false",
            "if",
            "else",
            "while",
            "for",
            "switch",
            "case",
            "break",
            "default",
            "do",
            "continue",
            "return",
            "instanceof",
            "static",
            "final",
            "super",
            "this",
            "native",
            "synchronized",
            "transient",
            "catch",
            "try",
            "finally",
            "throw",
            "throws",
            "enum"
    };
}
