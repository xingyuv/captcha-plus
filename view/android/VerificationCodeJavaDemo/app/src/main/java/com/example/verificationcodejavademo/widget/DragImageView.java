package com.example.verificationcodejavademo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.verificationcodejavademo.R;
import com.example.verificationcodejavademo.utils.DisplayUtil;

/**
 * Date:2020/5/19
 * author:wuyan
 */
public class DragImageView extends FrameLayout implements SeekBar.OnSeekBarChangeListener {

    private SeekBar drag_sb;
    private FrameLayout drag_fl_content;
    private ImageView drag_iv_cover;
    private ImageView drag_iv_block;
    private View drag_v_flash;
    private DiyStyleTextView drag_tv_tips;
    private TextView drag_tv_tips2;
    private Bitmap cover;
    private Bitmap block;

    private Long animeTime = 333L;
    private float timeUse = 0.0f;
    private Long timeTemp = 0l;

    private Handler handler = new Handler();

    public DragImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public DragImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.drag_view, this);
        drag_sb = findViewById(R.id.drag_sb);
        drag_sb.setOnSeekBarChangeListener(this);
        drag_fl_content = findViewById(R.id.drag_fl_content);
        drag_iv_cover = findViewById(R.id.drag_iv_cover);
        drag_iv_block = findViewById(R.id.drag_iv_block);
        drag_v_flash = findViewById(R.id.drag_v_flash);
        drag_tv_tips = findViewById(R.id.drag_tv_tips);
        drag_tv_tips2 = findViewById(R.id.drag_tv_tips2);
        drag_sb.setMax(getContext().getResources().getDisplayMetrics().widthPixels);
        reset();
    }

    //重置
    public void reset() {
        int position = drag_sb.getProgress();
        if (position != 0) {
            ValueAnimator animator = ValueAnimator.ofFloat(1f, 0.0f);
            animator.setDuration(animeTime).start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float f = (float) animation.getAnimatedValue();
                    drag_sb.setProgress((int) (position * f));
                }
            });
        }
        tipsShowAnime(false);
        tips2ShowAnime(true);
        setSbThumb(R.drawable.drag_btn_n);
        drag_sb.setEnabled(true);
        drag_sb.setProgressDrawable(getResources().getDrawable(R.drawable.drag_seek_progress));
        drag_iv_block.setVisibility(VISIBLE);
    }

    /**
     * 设置资源
     *
     * @param cover 拼图
     * @param block 滑块
     */
    public void setUp(Bitmap cover, Bitmap block) {
        this.cover = cover;
        this.block = block;

        int w = cover.getWidth();
        int h = cover.getHeight();

        LayoutParams l = (LayoutParams) drag_iv_cover.getLayoutParams();
        l.width = DisplayUtil.dip2px(getContext(), (float) w);
        l.height = DisplayUtil.dip2px(getContext(), (float) h);

        drag_iv_cover.setLayoutParams(l);
        drag_iv_cover.setImageBitmap(cover);

        LayoutParams layoutParams2 = (LayoutParams) drag_iv_block.getLayoutParams();
        layoutParams2.width = DisplayUtil.dip2px(getContext(), (float) block.getWidth());
        layoutParams2.height = DisplayUtil.dip2px(getContext(), (float) block.getHeight());
        drag_iv_block.setLayoutParams(layoutParams2);
        drag_iv_block.setImageBitmap(block);

        setLocation(1f * cover.getWidth() / cover.getHeight(), cover.getWidth());

    }

    //设置容器宽高
    private void setLocation(float cover_wph, int cover_w) {
        int w = cover_w;
        int h = (int) (w / cover_wph);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) drag_fl_content.getLayoutParams();
        layoutParams.width = DisplayUtil.dip2px(getContext(), (float) w);
        layoutParams.height = DisplayUtil.dip2px(getContext(), (float) h);
        drag_fl_content.setLayoutParams(layoutParams);
    }

    //验证成功:滑块隐藏，成功提示语，滑块不能滑动，设置setSbThumb，SeekBar的背景
    public void ok() {
        blockHideAnime();

        int penset;
        if (timeUse > 1) {
            penset = (int) (99 - (timeUse - 1) / 0.1f);
        } else {
            penset = (int) (99 - 0 / 0.1f);
        }
        if (penset < 1) penset = 1;
        drag_tv_tips.setText(String.format("拼图成功: 耗时%.1f秒,打败了%d%%的用户!", timeUse, penset));
        tipsShowAnime(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipsShowAnime(false);
                tips2ShowAnime(true);
                drag_sb.setEnabled(true);

                int position = drag_sb.getProgress();
                ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
                animator.setDuration(animeTime).start();
                animator.addUpdateListener(animation -> {
                    float f = (float) animation.getAnimatedValue();
                    drag_sb.setProgress((int) (position * f));
                    setSbThumb(R.drawable.drag_btn_n);
                    drag_sb.setProgressDrawable(getResources().getDrawable(R.drawable.drag_seek_progress));
                });

            }
        }, 1500);

        drag_sb.setEnabled(false);
        setSbThumb(R.drawable.drag_btn_success);
        drag_sb.setProgressDrawable(getResources().getDrawable(R.drawable.drag_seek_progress_success));

    }

    //成功完成拼图滑块消失
    private void blockHideAnime() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(animeTime);
        drag_iv_block.setAnimation(alphaAnimation);
        drag_iv_block.setVisibility(GONE);
    }

    //验证失败
    public void fail() {
        drag_tv_tips.setText("拼图失败: 请重新拖曳滑块到正确的位置!");
        tipsShowAnime(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipsShowAnime(false);
                tips2ShowAnime(true);
                drag_sb.setEnabled(true);

                int position = drag_sb.getProgress();
                ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
                animator.setDuration(animeTime).start();
                animator.addUpdateListener(animation -> {
                    float f = (float) animation.getAnimatedValue();
                    drag_sb.setProgress((int) (position * f));
                    setSbThumb(R.drawable.drag_btn_n);
                    drag_sb.setProgressDrawable(getResources().getDrawable(R.drawable.drag_seek_progress));
                });

            }
        }, 1500);
        drag_sb.setEnabled(false);
        setSbThumb(R.drawable.drag_btn_error);
        drag_sb.setProgressDrawable(getResources().getDrawable(R.drawable.drag_seek_progress_fail));
    }

    //弹出提示语是否显示
    private void tipsShowAnime(boolean isShow) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, isShow ? 1f : 0f, Animation.RELATIVE_TO_SELF, isShow ? 0f : 1f);
        translateAnimation.setDuration(animeTime);
        drag_tv_tips.setAnimation(translateAnimation);
        drag_tv_tips.setVisibility(isShow ? VISIBLE : GONE);
    }

    //seekBar上面的提示语是否显示
    private void tips2ShowAnime(boolean isShow) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(isShow ? 0f : 1f, isShow ? 1f : 0f);
        alphaAnimation.setDuration(animeTime);
        drag_tv_tips2.setAnimation(alphaAnimation);
        drag_tv_tips2.setVisibility(isShow ? VISIBLE : GONE);
    }


    //===================seekbar监听===================

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int cw = drag_iv_cover.getMeasuredWidth();
        int bw = drag_iv_block.getMeasuredWidth();
        ViewGroup.MarginLayoutParams l = (MarginLayoutParams) drag_iv_block.getLayoutParams();
        l.leftMargin = (cw - bw) * progress / seekBar.getMax();
        drag_iv_block.setLayoutParams(l);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        setSbThumb(R.drawable.drag_btn_n);
        drag_sb.setProgressDrawable(getResources().getDrawable(R.drawable.drag_seek_progress));
        drag_iv_block.setVisibility(VISIBLE);
        drag_iv_cover.setImageBitmap(cover);
        tips2ShowAnime(false);
        timeTemp = System.currentTimeMillis();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        timeUse = (System.currentTimeMillis() - timeTemp) / 1000f;//滑动滑块所花的时间，单位s
        if (dragListenner != null) {
            dragListenner.onDrag(DisplayUtil.px2dip(
                    getContext(), (drag_iv_cover.getMeasuredWidth() - drag_iv_block.getMeasuredWidth()) * 1f * seekBar.getProgress() / seekBar.getMax()
            ));
        }


    }

    //设置seekBar的Thumb
    private void setSbThumb(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(drag_sb.getThumb().getBounds());
        drag_sb.setThumb(drawable);
        drag_sb.setThumbOffset(0);
    }

    //设置seekBar是否可以滑动
    public void setSBUnMove(boolean isMove) {
        drag_sb.setEnabled(isMove);
    }

    //设置滑动监听
    private DragListenner dragListenner;

    interface DragListenner {
        void onDrag(double position);
    }

    public void setDragListenner(DragListenner dragListenner) {
        this.dragListenner = dragListenner;
    }
}
