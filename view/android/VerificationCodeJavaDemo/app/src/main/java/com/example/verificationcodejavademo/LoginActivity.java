package com.example.verificationcodejavademo;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.verificationcodejavademo.widget.BlockPuzzleDialog;
import com.example.verificationcodejavademo.widget.WordCaptchaDialog;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView bGo;
    private TextView bGo_word;
    private BlockPuzzleDialog blockPuzzleDialog;
    private WordCaptchaDialog wordCaptchaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bGo = findViewById(R.id.bGo);
        bGo.setOnClickListener(this);
        bGo_word = findViewById(R.id.bGo_word);
        bGo_word.setOnClickListener(this);
        blockPuzzleDialog = new BlockPuzzleDialog(this);
        wordCaptchaDialog = new WordCaptchaDialog(this);

        blockPuzzleDialog.setOnResultsListener(new BlockPuzzleDialog.OnResultsListener() {
            @Override
            public void onResultsClick(String result) {
                //todo 二次校验回调结果
                String s = result;
            }
        });
        wordCaptchaDialog.setOnResultsListener(new WordCaptchaDialog.OnResultsListener() {
            @Override
            public void onResultsClick(String result) {
                //todo 二次校验回调结果
                String s = result;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGo:
                blockPuzzleDialog.show();
                break;
            case R.id.bGo_word:
                wordCaptchaDialog.show();
                break;
        }
    }
}
