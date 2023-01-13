package com.example.verificationcodejavademo.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date:2020/5/19
 * author:wuyan
 */
public class DiyStyleTextView extends AppCompatTextView {

    private String colorRegex = "";//需要改变颜色的内容
    private int color = 0;//需要改变的颜色
    private ArrayList<Integer> indexArr = new ArrayList<>();
    private ArrayList<String> strArr = new ArrayList<>();

    public DiyStyleTextView(Context context) {
        super(context);
    }

    public DiyStyleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置需要改变颜色的文本，和改变的颜色
     */
    private DiyStyleTextView setColorRegex(String colorRegex, int color) {
        this.colorRegex = colorRegex;
        this.color = color;
        return this;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(setText(text, false), type);
    }

    private CharSequence setText(CharSequence text, Boolean flag) {
        if (TextUtils.isEmpty(text)) {
            if (flag) super.setText(text);
            return text;
        }
        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        if (!TextUtils.isEmpty(colorRegex)) {
            indexArr.clear();
            strArr.clear();
            Pattern p = Pattern.compile(colorRegex);
            Matcher m = p.matcher(text);
            while (m.find()) {
                strArr.add(m.group());
                indexArr.add(m.start());
            }
            for (int i = 0; i < indexArr.size(); i++) {
                int index = indexArr.get(i);
                String clickText = strArr.get(i);

                styledText.setSpan(
                        new TextViewClickSpan(clickText),
                        index,
                        index + clickText.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }
        if (flag) super.setText(styledText);
        return styledText;
    }

    private class TextViewClickSpan extends ClickableSpan {

        public TextViewClickSpan(String clickText) {
        }

        @Override
        public void onClick(@NonNull View widget) {

        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(color);
        }
    }
}
