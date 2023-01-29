#  java

## springboot
### SpringBoot项目，参考示例：service/springboot
a.引入jar，已上传至maven中央仓库。
```xml
<dependency>
   <groupId>com.xingyuv</groupId>
   <artifactId>spring-boot-starter-captcha-plus</artifactId>
   <version>1.0.2</version>
</dependency>
```
b.修改application.properties，自定义底图和水印，启动后前端就可以请求接口了。[社区底图库](https://gitee.com/anji-plus/AJ-Captcha-Images)<br>
```properties
# 滑动验证，底图路径，不配置将使用默认图片
# 支持全路径
# 支持项目路径,以classpath:开头,取resource目录下路径,例：classpath:images/jigsaw
aj.captcha.jigsaw=classpath:images/jigsaw
    #滑动验证，底图路径，不配置将使用默认图片
    ##支持全路径
# 支持项目路径,以classpath:开头,取resource目录下路径,例：classpath:images/pic-click
aj.captcha.pic-click=classpath:images/pic-click

# 对于分布式部署的应用，我们建议应用自己实现CaptchaCacheService，比如用Redis或者memcache，
# 参考CaptchaCacheServiceRedisImpl.java
# 如果应用是单点的，也没有使用redis，那默认使用内存。
# 内存缓存只适合单节点部署的应用，否则验证码生产与验证在节点之间信息不同步，导致失败。
# ！！！ 注意啦，如果应用有使用spring-boot-starter-data-redis，
# 请打开CaptchaCacheServiceRedisImpl.java注释。
# redis ----->  SPI： 在resources目录新建META-INF.services文件夹(两层)，参考当前服务resources。
# 缓存local/redis...
aj.captcha.cache-type=local
# local缓存的阈值,达到这个值，清除缓存
#aj.captcha.cache-number=1000
# local定时清除过期缓存(单位秒),设置为0代表不执行
#aj.captcha.timing-clear=180
#spring.redis.host=10.108.11.46
#spring.redis.port=6379
#spring.redis.password=
#spring.redis.database=2
#spring.redis.timeout=6000

# 验证码类型default两种都实例化。
aj.captcha.type=default
# 汉字统一使用Unicode,保证程序通过@value读取到是中文，可通过这个在线转换;yml格式不需要转换
# https://tool.chinaz.com/tools/unicode.aspx 中文转Unicode
# 右下角水印文字(我的水印)
aj.captcha.water-mark=\u6211\u7684\u6c34\u5370
# 右下角水印字体(不配置时，默认使用文泉驿正黑)
# 由于宋体等涉及到版权，我们jar中内置了开源字体【文泉驿正黑】
# 方式一：直接配置OS层的现有的字体名称，比如：宋体
# 方式二：自定义特定字体，请将字体放到工程resources下fonts文件夹，支持ttf\ttc\otf字体
# aj.captcha.water-font=WenQuanZhengHei.ttf
# 点选文字验证码的文字字体(文泉驿正黑)
# aj.captcha.font-type=WenQuanZhengHei.ttf
# 校验滑动拼图允许误差偏移量(默认5像素)
aj.captcha.slip-offset=5
# aes加密坐标开启或者禁用(true|false)
aj.captcha.aes-status=true
# 滑动干扰项(0/1/2)
aj.captcha.interference-options=2

#点选字体样式 默认Font.BOLD
aj.captcha.font-style=1
#点选字体字体大小
aj.captcha.font-size=25
#点选文字个数,存在问题，暂不支持修改
#aj.captcha.click-word-count=4

aj.captcha.history-data-clear-enable=false

# 接口请求次数一分钟限制是否开启 true|false
aj.captcha.req-frequency-limit-enable=false
# 验证失败5次，get接口锁定
aj.captcha.req-get-lock-limit=5
# 验证失败后，锁定时间间隔,s
aj.captcha.req-get-lock-seconds=360
# get接口一分钟内请求数限制
aj.captcha.req-get-minute-limit=30
# check接口一分钟内请求数限制
aj.captcha.req-check-minute-limit=60
# verify接口一分钟内请求数限制
aj.captcha.req-verify-minute-limit=60
```
c.`非常重要`。对于分布式多实例部署的应用，应用必须自己实现CaptchaCacheService，比如用Redis或者memcache，参考service/springboot/src/.../CaptchaCacheServiceRedisImpl.java<br>
--1.2.5版本移除@AutoService<br>
**在resources目录新建META-INF.services文件夹，参考resource/META-INF/services中的写法。**

###  后端二次校验接口
**二次校验参数请查看前端接入文档,例：vue,html接入文档等**

以登录为例，用户在提交表单到后台，会携带一个验证码相关的参数。后端登录接口login，首先调用CaptchaService.verification做二次校验，
```java
@Autowired
private CaptchaService captchaService;

@PostMapping("/login")
public ResponseModel get(@RequestBody CaptchaVO captchaVO) {
    //必传参数：captchaVO.captchaVerification
    ResponseModel response = captchaService.verification(captchaVO);
    if(response.isSuccess() == false){
        //验证码校验失败，返回信息告诉前端
        //repCode  0000  无异常，代表成功
        //repCode  9999  服务器内部异常
        //repCode  0011  参数不能为空
        //repCode  6110  验证码已失效，请重新获取
        //repCode  6111  验证失败
        //repCode  6112  获取验证码失败,请联系管理员
        //repCode  6113  底图未初始化成功，请检查路径
        //repCode  6201  get接口请求次数超限，请稍后再试!
        //repCode  6206  无效请求，请重新获取验证码
        //repCode  6202  接口验证失败数过多，请稍后再试
        //repCode  6204  check接口请求次数超限，请稍后再试!
    }
    return response;
}
```
### 2.2.3 后端接口
#### 获取验证码接口：http://*:*/captcha/get
##### 请求参数：
```json
{
	"captchaType": "blockPuzzle",  //验证码类型 clickWord
	"clientUid": "唯一标识"  //客户端UI组件id,组件初始化时设置一次，UUID（非必传参数）
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "originalImageBase64": "底图base64",
        "point": {    //默认不返回的，校验的就是该坐标信息，允许误差范围
            "x": 205,
            "y": 5
        },
        "jigsawImageBase64": "滑块图base64",
        "token": "71dd26999e314f9abb0c635336976635", //一次校验唯一标识
        "secretKey": "16位随机字符串", //aes秘钥，开关控制，前端根据此值决定是否加密
        "result": false,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```
#### 核对验证码接口接口：http://*:*/captcha/check
##### 请求参数：
```json
{
	 "captchaType": "blockPuzzle",
	 "pointJson": "QxIVdlJoWUi04iM+65hTow==",  //aes加密坐标信息
	 "token": "71dd26999e314f9abb0c635336976635"  //get请求返回的token
}
```
##### 响应参数：
```json
{
    "repCode": "0000",
    "repData": {
        "captchaType": "blockPuzzle",
        "token": "71dd26999e314f9abb0c635336976635",
        "result": true,
        "opAdmin": false
    },
    "success": true,
    "error": false
}
```

### 防刷功能(补充中)
    a.同一用户，登录错误3次才要求验证码，考虑是登录模块的功能。
    b.同一用户，限制请求验证码，5分钟不能超过100次等。
    以上功能，我们会在service/springboot示例代码中提供额外的参考代码，不集成在jar中。




## springmvc



###  SpringMVC项目,参考示例：service/springMVC
```
示例：仓库service\springmvc。考虑部分老项目，还是非springboot的，我们提供spring mvc的项目示例代码。
     主要是配置redisTemplate和包扫描。
```
a.引入jar，已上传至maven中央仓库。
```java
<dependency>
   <groupId>com.xingyuv</groupId>
   <artifactId>captcha-plus</artifactId>
   <version>0.0.1</version>
</dependency>
```
b.引入CaptchaConfig.java配置文件，需自行配置参数，
详情参照service\springmvc目录下

c.引入外部缓存，例：redis
```
参考：
1.CaptchaCacheServiceRedisImpl
2.resources.META-INF.services配置文件

```

