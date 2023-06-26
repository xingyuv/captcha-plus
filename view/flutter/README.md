## flutter

### 引入插件

根据对应版本接入即可

```
dio: ^3.0.9
steel_crypt: ^1.5.3
encrypt: ^4.0.0
```

### 使用方式

```
//点选拼图
//import 'package:captcha/captcha/click_word_captcha.dart';
static void loadingClickWord(BuildContext context, {barrierDismissible = true}) {
  showDialog<Null>(
    context: context,
    barrierDismissible: barrierDismissible,
    builder: (BuildContext context) {
      return ClickWordCaptcha(
        onSuccess: (v){//成功回调（回调为加密后内容，依据项目而定）

        },
        onFail: (){

        },
      );
    },
  );
}

//滑动拼图
//import 'package:captcha/captcha/block_puzzle_captcha.dart';
static void loadingBlockPuzzle(BuildContext context, {barrierDismissible = true}) {
  showDialog<Null>(
    context: context,
    barrierDismissible: barrierDismissible,
    builder: (BuildContext context) {
      return BlockPuzzleCaptchaPage(
        onSuccess: (v){ //成功回调（回调为加密后内容，依据项目而定）

        },
        onFail: (){

        },
      );
    },
  );
}
```

### 事件

| 参数        | 说明                         |
|-----------|----------------------------|
| onSuccess | 可自定义回调内容，根据自己服务定制          |
| onFail    | 失败回调默认为空，可自定义回调内容，根据自己服务定制 |

### 请求处理

```
import 'package:captcha/request/HttpManager.dart';
```

请求接口地址 配置在 HttpManager类中 ,需要更改地址替换`baseUrl`即可

### 请求注意

请求验证码接口中会有`secretKey`参数，当`secretKey`有值， 进行`as`加密， 加密key为`secretKey`,不加密情况下`pointJson`
为json系列化为字符串

```
{
  "pointJson": cryptedStr,
  "captchaType": "clickWord",
  "token": _clickWordCaptchaModel.token
}
```

```
// secretKey 不为空 进行as加密
if(!ObjectUtils.isEmpty(_clickWordCaptchaModel.secretKey)){
  var aesEncrypter = AesCrypt(_clickWordCaptchaModel.secretKey, 'ecb', 'pkcs7');
  cryptedStr = aesEncrypter.encrypt(pointStr);
  var dcrypt = aesEncrypter.decrypt(cryptedStr);
}
```

### 二次校验传出的参数

```
//如果不加密  将  token  和 坐标序列化 通过  --- 链接成字符串
var captchaVerification = "$captchaToken---$pointStr";
if(!ObjectUtils.isEmpty(secretKey)){
  //如果加密  将  token  和 坐标序列化 通过  --- 链接成字符串 进行加密  加密密钥为 _clickWordCaptchaModel.secretKey
  captchaVerification = EncryptUtil.aesEncode(key: secretKey, content: captchaVerification);
}
checkSuccess(captchaVerification);
```
