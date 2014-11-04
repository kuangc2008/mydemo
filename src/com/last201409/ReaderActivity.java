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
import java.util.LinkedList;
import java.util.List;


public class ReaderActivity extends Activity {
    private String filePath;
    private TextView mFileTV;
    private SpannableString spannableString;
    private LinkedList<MyWord> words = new LinkedList<MyWord>();
    private List<MyWord> filed = new ArrayList<MyWord>();
    private boolean isClass = false;

    private class MyWord {
        int endPos;
        String text;
        WodeType type;
    }

    enum WodeType {
        KEY, FUNCTIONS, FIELDS
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
        spannableString = new SpannableString(text);
        initFiledMethod(text);
        initKeyFiled(text);

//        for(MyWord word : words) {
//            if(isKeyWord(word)) {
//                colorKeyWord(word.text, word.endPos);
//            }
//        }
        mFileTV.setText(spannableString);
    }


    public void initKeyFiled(String text) {
        int len = text.length();
        StringBuilder sb = new StringBuilder();
        words.clear();
        for(int i=0; i<len; i++ ) {
            char posChar = text.charAt(i);
            if(isNewSymbol(posChar)) {
                MyWord lstWord = words.peekLast();
                if(!TextUtils.isEmpty(sb.toString()) && (lstWord == null || lstWord!=null && !lstWord.text.equals("{"))) {
                    MyWord word = new MyWord();
                    word.endPos = i;
                    word.text  = sb.toString();
                    words.add(word);

                    if(isKeyWord(word)) {
                        word.type = WodeType.KEY;
                        colorKeyWord(word.text, word.endPos);
                    } else if(isFiledWord(word)) {
                        colorFieldWord(word.text, word.endPos);
                    }

                }
                sb.delete(0, sb.toString().length());

                if(posChar == ';') {  //;则认为是属性
                    words.clear();
                }
            } else {
                sb.append(posChar);
            }
        }
    }

    private boolean isFiledWord(MyWord word) {
        for(MyWord myWord : filed) {
            if(myWord.text.equals(word.text)) {
                return true;
            }
        }
        return false;
    }


    public void initFiledMethod(String text) {
        int len = text.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<len; i++ ) {
            char posChar = text.charAt(i);
            if(isNewSymbol(posChar)) {
                MyWord lstWord = words.peekLast();
                if(!TextUtils.isEmpty(sb.toString()) && (lstWord == null || lstWord!=null &&
                        !lstWord.text.equals("{")
                        && !lstWord.text.equals("=")
                )) {
                    MyWord word = new MyWord();
                    word.endPos = i;
                    word.text  = sb.toString();
                    words.add(word);

                    if(isKeyWord(word)) {
//                        word.type = WodeType.KEY;
//                        colorKeyWord(word.text, word.endPos);
                        if(word.text.equals("class") || word.text.equals("package")) {
                            isClass = true;
                        }
                    }

                }
                sb.delete(0, sb.toString().length());

                if(posChar == '{') {
                    if(isClass) {
                        isClass = false;
                        continue;
                    }

                    MyWord word = new MyWord();
                    word.endPos = i;
                    word.text  = "{";
                    words.add(word);
                } else if(posChar == '}') {
                    MyWord lstWord2 = words.peekLast();
                    if(lstWord2 != null &&  lstWord2.text.equals("{")) {
                        words.removeLast();
                    }
                } else if(posChar == '=') {
                    MyWord word = new MyWord();
                    word.endPos = i;
                    word.text  = "=";
                    words.add(word);
                }

                MyWord word = words.peekLast();
                if(word == null || isZuoKuoHao()) {
                    continue;
                }

                if(posChar == '(') { //如果是括号，直接认为上一个是方法
                    colorMethodWord(word.text, word.endPos);

                } else if(posChar == ';') {  //;则认为是属性
                    MyWord word1 = words.pollLast();
                    MyWord word2 = words.pollLast();
                    if(word2 == null || word2.text.equals("package") || word2.text.equals("import")) {
                    } else {
                        if(word.text.equals("=")) {
                            colorFieldWord(word2.text, word2.endPos);
                        } else {
                            colorFieldWord(word.text, word.endPos);
                            filed.add(word);
                        }
                    }

                    words.clear();
                }


            } else {
                sb.append(posChar);
            }
        }
    }

    public boolean isZuoKuoHao() {
        MyWord lstWord2 = words.peekLast();
        if(lstWord2 != null &&  lstWord2.text.equals("{")) {
            return true;
        }
        return false;
    }



    private void colorFieldWord(String word, int lasPos) {
        spannableString.setSpan(new ForegroundColorSpan(0xFFaa66cc), lasPos - word.length(), lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        int size = (int) (11 * getResources().getDisplayMetrics().scaledDensity);
        spannableString.setSpan(new AbsoluteSizeSpan(size),lasPos - word.length(),lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }


    private void colorKeyWord(String word, int lasPos) {
        spannableString.setSpan(new ForegroundColorSpan(0xFFff8800), lasPos - word.length(), lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        int size = (int) (11 * getResources().getDisplayMetrics().scaledDensity);
        spannableString.setSpan(new AbsoluteSizeSpan(size),lasPos - word.length(),lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void colorMethodWord(String word, int lasPos) {
        spannableString.setSpan(new ForegroundColorSpan(0xFFfeaa0c), lasPos - word.length(), lasPos, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
            ']',
            ';'
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
