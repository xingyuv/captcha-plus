module.exports = {
    title: 'AJ-Captcha',
    description: 'anji-plus',
    base: '/captcha-doc/',
    themeConfig: {
        nav: [
            {
                text: '官网',
                link: 'http://www.anji-plus.com'
            },
            {
                text: 'gitee',
                link: 'https://gitee.com/anji-plus/captcha'
            },
            {
                text: 'github',
                link: 'https://github.com/anji-plus/captcha'
            }
        ],
        sidebar: [{
            title: '介绍',
            collapsable: false,
            sidebarDepth: 3,
            children: [
                '/'
            ]
        }, {
            title: '文档',
            collapsable: false,
            children: [
                '/captchaDoc/java',
                '/captchaDoc/html',
                '/captchaDoc/vue',
                '/captchaDoc/flutterDoc',
                '/captchaDoc/uni-app',
                '/captchaDoc/wx-applet',
                '/captchaDoc/reactNative',
                '/captchaDoc/android',
                '/captchaDoc/ios',
                '/captchaDoc/php',
                '/captchaDoc/angular'
            ]
        }, {
            title: '其他',
            collapsable: false,
            children: [
                '/captchaDoc/help'
            ]
        }]
    }
}
