// index.js
// 获取应用实例
const app = getApp()

Page({
    data: {
        //点选验证码-嵌入式
        captchaOpt1: {
            baseUrl: "https://captcha.anji-plus.com/captcha-api",       //服务器前缀，默认：https://captcha.anji-plus.com/captcha-api
            mode: 'fixed',	                                              //弹出式pop，固定fixed, 默认：pop
            captchaType: "clickWord",                                   //验证码类型：滑块blockPuzzle，点选clickWord，默认：blockPuzzle
            imgSize: {                                                 //底图大小, 默认值：{ width: '310px',height: '155px'}
                width: '295px',
                height: '147px',
            },
            barHeight: '32px',                                             //滑块大小，默认值：{ width: '310px',height: '40px'}
            vSpace: 5,                                                   //底图和verify-bar-area间距，默认值：5像素
            success: function (res) {
                console.log("验证成功")
                wx.showToast({
                    title: '验证成功',
                    icon: 'success',
                    duration: 1000
                })
            },
            fail: function (res) {
                console.log("失败响应")
                console.log(res)
            },
        },
        //点选验证码-弹出式
        captchaOpt2: {
            baseUrl: "https://captcha.anji-plus.com/captcha-api",       //服务器前缀，默认：https://captcha.anji-plus.com/captcha-api
            mode: 'pop',	                                              //弹出式pop，固定fixed, 默认：pop
            captchaType: "clickWord",                                   //验证码类型：滑块blockPuzzle，点选clickWord，默认：blockPuzzle
            imgSize: {                                                 //底图大小, 默认值：{ width: '310px',height: '155px'}
                width: '295px',
                height: '147px',
            },
            barHeight: '32px',                                           //滑块大小，默认值：{ width: '310px',height: '40px'}
            vSpace: 5,                                                   //底图和verify-bar-area间距，默认值：5像素
            success: function (res) {
                console.log("验证成功")
                wx.showToast({
                    title: '验证成功',
                    icon: 'success',
                    duration: 1000
                })
            },
            fail: function (res) {
                console.log("失败响应")
                console.log(res)
            },
        },
    },
    //打开验证组件
    openDemo() {
        var demo2 = this.selectComponent('.demo2').show();
    },
    onLoad() {
        console.log('代码片段是一种迷你、可分享的小程序或小游戏项目，可用于分享小程序和小游戏的开发经验、展示组件和 API 的使用、复现开发问题和 Bug 等。可点击以下链接查看代码片段的详细文档：')
        console.log('https://mp.weixin.qq.com/debug/wxadoc/dev/devtools/devtools.html')
    },
})
