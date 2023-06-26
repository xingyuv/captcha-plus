# Android  Kotlin

## 引入依賴包

根据对应版本接入即可

```
kapt "com.squareup.moshi:moshi-kotlin-codegen:1.8.0"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1"
implementation 'com.google.android.material:material:1.0.0'
implementation 'com.google.code.gson:gson:2.8.5'
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation "com.squareup.retrofit2:converter-moshi:2.5.0"
implementation 'com.squareup.okhttp3:logging-interceptor:3.12.1'
implementation "com.squareup.moshi:moshi-kotlin:1.8.0"
implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
```

## 使用方式

```
blockPuzzleDialog.show() //"滑动拼图"
wordCaptchaDialog.show()//"字符校验"
```

## 事件

| 参数                      | 说明                     |
|-------------------------|------------------------|
| OnSeekBarChangeListener | 回调函数，可自定义回调内容，根据自己服务定制 |
| OnResultsListener       | 回调函数，二次校验回调            |

## 请求处理

```
CommonInterceptor.kt
请求参数和请求结果处理的过滤器
Configuration.kt
网络请求基类
ServerApi.kt
网络请求接口
```

请求接口地址 配置在 ServerApi类中 ,需要更改地址替换`urlDefault`即可

## 校验注意事项

```
请求验证码接口中会有secretKey参数，当secretKey有值， 进行aes加密， 加密key为
secretKey,不加密情况下pointJson为json系列化为字符串

  //secretKey有值 代表需要进行加密，否则不加密
  key= b.repData!!.secretKey

   val o = CaptchaCheckOt(
                    captchaType = "clickWord",
                    pointJson = AESUtil.encode(pointListStr,key),
                    token = Configuration.token
                )
                val b = Configuration.server.checkAsync(o).await().body()

 AESUtil：aes加密工具类
```

## 二次校验传参

```

BlockPuzzleDialog.kt/WordCaptchaDialog.kt

      val result = token + "---" + pointListStr
      mOnResultsListener!!.onResultsClick(AESUtil.encode(result, key))

LoginActivity.kt

      blockPuzzleDialog.setOnResultsListener(object : BlockPuzzleDialog.OnResultsListener{
            override fun onResultsClick(result: String) {
                //todo 二次校验回调结果
                val s = result
            }
        })

        wordCaptchaDialog.setOnResultsListener(object : WordCaptchaDialog.OnResultsListener{
            override fun onResultsClick(result: String) {
                //todo 二次校验回调结果
                val s = result
            }
        })

```

## Android  Java

### 引入依賴包

根据对应版本接入即可

```
implementation 'com.squareup.retrofit2:retrofit:2.8.1'
implementation 'com.squareup.retrofit2:converter-gson:2.8.1'
implementation 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'
implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
implementation 'io.reactivex.rxjava2:rxjava:2.1.6'
implementation 'com.trello.rxlifecycle2:rxlifecycle-components:2.1.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.7.1'
```

### 使用方式

```
blockPuzzleDialog.show() //"滑动拼图"
wordCaptchaDialog.show()//"字符校验"
```

### 事件

| 参数                      | 说明                     |
|-------------------------|------------------------|
| OnSeekBarChangeListener | 回调函数，可自定义回调内容，根据自己服务定制 |
| OnResultsListener       | 回调函数，二次校验回调            |

### 请求处理

```
RetrofitUtils.java
网络请求基类
ServerApi.java
网络请求接口
BaseObserver.java
请求结果处理
RxExceptionUtil
异常结果处理
```

请求接口地址 配置在 ServerApi类中 ,需要更改地址替换`BaseUrl`即可

### 校验注意事项

```
请求验证码接口中会有secretKey参数，当secretKey有值， 进行aes加密， 加密key为
secretKey,不加密情况下pointJson为json系列化为字符串

  //secretKey有值 代表需要进行加密，否则不加密
   key = data.getSecretKey();

    Map<String, Object> params = new HashMap<>();
        params.put("captchaType", "blockPuzzle");
        params.put("token", token);
        params.put("pointJson", AESUtil.encode(pointStr, key));
        JSONObject jsonObject = new JSONObject(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

 AESUtil：aes加密工具类
```

### 二次校验传参

```

BlockPuzzleDialog.java/WordCaptchaDialog.java

      if (mOnResultsListener!=null){
            String result=token+"---"+pointStr;
            mOnResultsListener.onResultsClick(AESUtil.encode(result,key));
        }

LoginActivity.java

       blockPuzzleDialog.setOnResultsListener(new BlockPuzzleDialog.OnResultsListener() {
            @Override
            public void onResultsClick(String result) {
                //todo 二次校验回调结果
                String s=result;
            }
        });
        wordCaptchaDialog.setOnResultsListener(new WordCaptchaDialog.OnResultsListener() {
            @Override
            public void onResultsClick(String result) {
                //todo 二次校验回调结果
                String s=result;
            }
        });

```

