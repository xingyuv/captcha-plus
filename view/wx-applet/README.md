## 微信小程序接入
### 1. 兼容性
    v2.4.4 (小程序基础库v2.4.4开始支持WXS响应事件)
### 2. 引入组件
    1)复制view/components/aj-captcha文件夹,到自己放置组件文件夹中

    2)在对应页面index.json引用组件
    
        "usingComponents": {
            "aj-captcha": "/components/aj-captcha/index"
          }

#### 基础示例
#####小程序代码片段：https://developers.weixin.qq.com/s/s9JdutmD73p0
```javascript
1)对应页面index.js

    data: {
        //滑动验证码-嵌入式
        captchaOpt1: {
          baseUrl: "https://captcha.anji-plus.com/captcha-api",       //服务器前缀，默认：https://captcha.anji-plus.com/captcha-api
          mode : 'fixed',                                             //弹出式pop，固定fixed, 默认：pop
          captchaType: "blockPuzzle",                                 //验证码类型：滑块blockPuzzle，点选clickWord，默认：blockPuzzle
          imgSize : {                                                 //底图大小, 默认值：{ width: '310px',height: '155px'}
            width: '295px',
            height: '147px',
          },
          barSize:{                                                   //滑块大小，默认值：{ width: '310px',height: '40px'}
            width: '295px',
            height: '32px',
          },
          vSpace: 5,                                                   //底图和verify-bar-area间距，默认值：5像素
          success:function(res){                                       //成功回调，默认空
            console.log("验证成功")
            wx.showToast({
              title: '验证成功',
              icon: 'success',
              duration: 1000
            })
          },
          fail:function(res){                                          //失败回调，默认空
            console.log("失败响应")
            console.log(res)
          },
        }
    }

2)对应页面index.wxml

    <aj-captcha class="demo1" opt="{{captchaOpt1}}"></aj-captcha>

2)组件内置方法

    1. show()//显示组件

        ###组件弹出式需要通过show()方法显示
        this.selectComponent('.demo1').show();

    2. hide()//隐藏组件

    3. reload()//重新装载
        
        //将滑动改为点选
        let opt = this.data.captchaOpt1;
        opt.captchaType = 'clickWord';
        opt.mode = "fixed";

        this.setData({
          captchaOpt1: opt
        },function(){
          //更改组件属性属性需要通过加载重置
          this.selectComponent('.demo1').reload();
        })
```
### 3.回调事件

|  参数 | 类型 |  说明 |
| ------------ | ------------ | ------------ |
| success(params)  |  funciton | 验证码匹配成功后的回调函数,params为返回需回传服务器的二次验证参数  |
| fail(data)  |  funciton | 验证码校验失败后的回调函数,data服务器响应数据  |

### 4. 验证码参数

|  参数 | 类型 |  说明 |
| ------------ | ------------ | ------------ |
| baseUrl | String | 服务器前缀，默认：https://captcha.anji-plus.com/captcha-api |
| captchaType | String | 验证码类型：滑动拼图blockPuzzle，文字点选clickWord，默认：blockPuzzle  |
| mode  | String | 验证码的显示方式，弹出式pop，固定fixed，默认：mode : ‘pop’  |
| imgSize | Object |  其中包含了width、height两个参数，分别代表图片的宽度和高度 如:{width:'400px',height:'200px'} 
| barHeight | String | 滑块，滑动区高度，默认:40px 
| vSpace  | String | 验证码图片和移动条容器的间隔，默认单位是px。如：间隔为5px，默认:vSpace:5  |

### 5.1默认接口api地址
|  请求URL | 请求方式 |  
| ------------ | ------------ |
| {{baseUrl}}/captcha/get  | Post | 
| {{baseUrl}}/captcha/check  | Post | 

### 5.2 接口返回数据结构
```
##### json格式：

1) get响应

{
    "repCode": "0000",
    "repData": {
        "originalImageBase64": "底图base64",
        "jigsawImageBase64": "滑动填充图base64",//captchaType为blockPuzzle返回
        "secretKey": "sVSXH5JYwVnHGmB6",//aes加密钥
        "token": "71dd26999e314f9abb0c635336976635", //一次校验唯一标识
        "result": false,
    },
    "success": true,
    "error": false
}

2) check响应

{
	"repCode":"0000",
	"repData":{
		"captchaType":"blockPuzzle",
		"clientUid":"slider-ee070257-2e5e-4bbd-8dfe-a9eec0aefe23",
		"pointJson":"gszofB2o8RGUYjHK6TFI69+agzBzXE0LsdnVw84wUBw=",
		"result":true,
		"token":"de685278d21448058963171c22307a8b",
		"ts":1618107911024
	},
	"success":true
}
```