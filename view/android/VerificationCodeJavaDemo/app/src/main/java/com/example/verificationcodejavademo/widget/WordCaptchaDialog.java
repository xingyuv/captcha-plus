package com.example.verificationcodejavademo.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.verificationcodejavademo.R;
import com.example.verificationcodejavademo.model.CaptchaCheckIt;
import com.example.verificationcodejavademo.model.WordCaptchaGetIt;
import com.example.verificationcodejavademo.network.BaseObserver;
import com.example.verificationcodejavademo.network.RetrofitUtils;
import com.example.verificationcodejavademo.network.RxHelper;
import com.example.verificationcodejavademo.utils.AESUtil;
import com.example.verificationcodejavademo.utils.ImageUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Date:2020/5/19
 * author:wuyan
 */
public class WordCaptchaDialog extends Dialog {
    private String baseImageBase64;//背景图片
    private String token;
    private Context mContext;
    private TextView tvDelete;
    private ImageView tvRefresh;
    private WordImageView wordView;
    private TextView bottomTitle;
    private Handler handler = new Handler();
    private String key;


    public WordCaptchaDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        setContentView(R.layout.dialog_word_captcha);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        ViewGroup.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 9 / 10;
        getWindow().setAttributes((WindowManager.LayoutParams) lp);
        setCanceledOnTouchOutside(false);//点击外部Dialog不消失
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        loadCaptcha();

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCaptcha();
            }
        });
    }

    private void initView() {
        tvDelete = findViewById(R.id.tv_delete);
        tvRefresh = findViewById(R.id.tv_refresh);
        wordView = findViewById(R.id.wordView);
        bottomTitle = findViewById(R.id.bottomTitle);

        Bitmap bitmap = ImageUtil.getBitmap(getContext(), R.drawable.bg_default);
        wordView.setUp(bitmap);
    }

    private void loadCaptcha() {
        bottomTitle.setText("数据加载中......");
        bottomTitle.setTextColor(Color.BLACK);
        Map<String, Object> params = new HashMap<>();
        params.put("captchaType", "clickWord");
        JSONObject jsonObject = new JSONObject(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Log.i("wuyan", "body:" + jsonObject);
        RetrofitUtils.getServerApi().getWordCaptchaAsync(body).compose(RxHelper.observableIO2Main(mContext)).subscribe(new BaseObserver<WordCaptchaGetIt>(mContext, true) {
            @Override
            public void onSuccess(WordCaptchaGetIt data) {
                baseImageBase64 = data.getOriginalImageBase64();
                token = data.getToken();
                key = data.getSecretKey();
                String wordStr = "";
                int j = 0;
                for (int i = 0; i < data.getWordList().size(); i++) {
                    j++;
                    wordStr += data.getWordList().get(i);
                    if (j < data.getWordList().size()) {
                        wordStr += ",";
                    }
                }
                wordView.setSize(data.getWordList().size());
                bottomTitle.setText("请依此点击【" + wordStr + "】");
                bottomTitle.setTextColor(Color.BLACK);
                wordView.setUp(
                        ImageUtil.base64ToBitmap(baseImageBase64)
                );
                initEvent();
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
                bottomTitle.setText("加载失败,请刷新");
                bottomTitle.setTextColor(Color.RED);
                wordView.setSize(-1);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkCaptcha(String cryptedStr) {
        Map<String, Object> params = new HashMap<>();
        params.put("captchaType", "clickWord");
        params.put("token", token);
        params.put("pointJson", AESUtil.encode(cryptedStr, key));
        JSONObject jsonObject = new JSONObject(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Log.i("wuyan", "body:" + jsonObject);
        RetrofitUtils.getServerApi().checkAsync(body).compose(RxHelper.observableIO2Main(mContext)).subscribe(new BaseObserver<CaptchaCheckIt>(mContext, false) {
            @Override
            public void onSuccess(CaptchaCheckIt data) {
                bottomTitle.setText("验证成功");
                bottomTitle.setTextColor(Color.GREEN);
                wordView.ok();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadCaptcha();
                        dismiss();
                    }
                }, 1000);
                if (mOnResultsListener!=null){
                    String result=token+"---"+cryptedStr;
                    mOnResultsListener.onResultsClick(AESUtil.encode(result,key));
                }
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
                bottomTitle.setText("验证失败");
                bottomTitle.setTextColor(Color.RED);
                wordView.fail();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新验证码
                        loadCaptcha();
                    }
                }, 1000);
            }
        });

    }

    private void initEvent() {
        wordView.setWordListenner(new WordImageView.WordListenner() {
            @Override
            public void onWord(String cryptedStr) {
                checkCaptcha(cryptedStr);
            }
        });
    }

    private OnResultsListener mOnResultsListener;

    public interface OnResultsListener {
        void onResultsClick(String result);
    }

    public void setOnResultsListener(OnResultsListener mOnResultsListener) {
        this.mOnResultsListener = mOnResultsListener;
    }
}
