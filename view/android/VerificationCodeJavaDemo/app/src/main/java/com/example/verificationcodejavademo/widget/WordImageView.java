package com.example.verificationcodejavademo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.verificationcodejavademo.R;
import com.example.verificationcodejavademo.model.Point;
import com.example.verificationcodejavademo.utils.DisplayUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2020/5/21
 * author:wuyan
 */
public class WordImageView extends FrameLayout {

    private FrameLayout word_fl_content;
    private ImageView word_iv_cover;
    private View word_v_flash;

    private Bitmap cover;
    private int size = 0;//需要点击文字的数量
    private List<Point> mList = new ArrayList<>();
    private Handler mHandler = new Handler();

    public WordImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public WordImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WordImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.word_view, this);
        word_fl_content = findViewById(R.id.word_fl_content);
        word_iv_cover = findViewById(R.id.word_iv_cover);
        word_v_flash = findViewById(R.id.word_v_flash);
        reset();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUp(Bitmap cover) {
        this.cover = cover;

        int w = cover.getWidth();
        int h = cover.getHeight();

        FrameLayout.LayoutParams l = (FrameLayout.LayoutParams) word_iv_cover.getLayoutParams();
        l.width = DisplayUtil.dip2px(getContext(), (float) w);
        l.height = DisplayUtil.dip2px(getContext(), (float) h);

        word_iv_cover.setLayoutParams(l);
        word_iv_cover.setImageBitmap(cover);

        setLocation(cover.getWidth(), cover.getHeight());
    }

    private void setLocation(int w, int h) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) word_fl_content.getLayoutParams();
        layoutParams.width = DisplayUtil.dip2px(getContext(), (float) w);
        layoutParams.height = DisplayUtil.dip2px(getContext(), (float) h);
        word_fl_content.setLayoutParams(layoutParams);
    }

    public void ok() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 1000);
    }

    public void fail() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 1000);
    }

    private void reset() {
        mList.clear();
        word_fl_content.removeAllViews();
        word_fl_content.addView(word_iv_cover);
        word_fl_content.addView(word_v_flash);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            size--;
            Point point = new Point();
            point.setX(DisplayUtil.px2dip(getContext(), event.getX()));
            point.setY(DisplayUtil.px2dip(getContext(), event.getY()));
            mList.add(point);

            if (size > 0) {
                //添加小圆点
                addTextView(event);
            } else if (size == 0) {
                addTextView(event);
                if (wordListenner != null) {
                    wordListenner.onWord(new Gson().toJson(mList));
                }
            }
        }
        return true;
    }

    //点击后添加小圆点
    private void addTextView(MotionEvent event) {
        TextView textView = new TextView(getContext());
        LayoutParams l = new LayoutParams(DisplayUtil.dip2px(getContext(), 20 * 1.0f), DisplayUtil.dip2px(getContext(), 20 * 1.0f));
        textView.setLayoutParams(l);
        textView.setGravity(Gravity.CENTER);
        textView.setText(mList.size() + "");
        textView.setTextColor(Color.WHITE);
        textView.setBackground(getResources().getDrawable(R.drawable.shape_dot_bg));
        MarginLayoutParams postion = (MarginLayoutParams) textView.getLayoutParams();
        postion.leftMargin = (int) (event.getX() - 10);
        postion.topMargin = (int) (event.getY() - 10);
        word_fl_content.addView(textView);
    }

    //设置滑动监听
    private WordListenner wordListenner;

    interface WordListenner {
        void onWord(String cryptedStr);
    }

    public void setWordListenner(WordListenner wordListenner) {
        this.wordListenner = wordListenner;
    }
}
