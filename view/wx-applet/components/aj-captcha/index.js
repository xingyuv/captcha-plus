//引入aes加密文件
const CryptoJS = require('./utils/aes_util.js')
// var key = "7IWlRFRgSO3G3h7F";
// //aes 加密
// console.log('123456--aes 加密',CryptoJS.AesEncrypt('123456',key))
// // 1GL46CatmleXJmA9YmTEIA==
// //aes 解密
// console.log('123456--aes 解密',CryptoJS.AesDecrypt('1GL46CatmleXJmA9YmTEIA==',key))
// console.log('123456--aes 解密',CryptoJS.AesDecrypt('Z5/mlIkvjH9UarQHAY4AXw==',"0ypBWlmJKfYuGnx8"))


/**
 * @desc 验证码插件
 * @author WuJiangWu
 * @datatime 2021/4/7 8:59
 */
Component({
    options: {
        multipleSlots: true,
    },
    properties: {
        opt: {
            type: Object,
            value: null,
        },
    },
    data: {
        show: false,                                                //是否显示组件
        token: "",                                                  //验证码token
        secretKey: "",                                              //aes密钥
        originalImageBase64: "",                                    //原图base64
        jigsawImageBase64: "",                                      //拼图滑块base64
        verifyImgOutHeight: "",                                     //底图父容器高度
        verifySubBlockWidth: "",                                    //填充块宽度值
        verifySubBlockTop: "",                                      //填充块top值
        leftBarClass: "status-1",                                    //滑块默认样式
        verifyTipsClass: "",                                        //文字提示框样式
        verifyTipsText: "",                                         //文字提示框内容
        verifyMsgText: "",                                          //滑块框文字内容
        verifyBarAreaClass: "",                                     //滑块区域样式类
        clickWordTapName: "",                                       //点击事件名称
        clickWordTapNum: 0,                                         //点选点击次数
        clickWordXYList: [],                                        //点选坐标对象集合
        clickWordPointList: [                                       //坐标标点集合，初始化五个
            {left: 0, top: 0, display: 'none'},
            {left: 0, top: 0, display: 'none'},
            {left: 0, top: 0, display: 'none'},
            {left: 0, top: 0, display: 'none'},
            {left: 0, top: 0, display: 'none'},
        ],
        backImgLeft: 0,                                             //背景图右边界坐标
        backImgTop: 0,                                              //背景图上边界坐标
    },
    lifetimes: {
        attached: function () {
            var _self = this;
            // 在组件实例进入页面节点树时执行
            //初始化属性默认值
            var defaults = {
                baseUrl: "https://captcha.anji-plus.com/captcha-api",       //服务器前缀，默认值：https://captcha.anji-plus.com/captcha-api
                mode: 'pop',	                                            //弹出式pop，固定fixed, 默认值：pop
                captchaType: "blockPuzzle",                                 //验证码类型：滑块blockPuzzle，点选clickWord，默认值：blockPuzzle
                imgSize: {                                                 //底图大小，默认值：{ width: '310px',height: '155px'}
                    width: '310px',
                    height: '155px',
                },
                barHeight: '40px',                                          //滑块大小，默认值：'40px'
                vSpace: 5,                                                   //底图和verify-bar-area间距，默认值：5像素
                success: function (res) {
                },                                   //成功回调
                fail: function (res) {
                },                                      //失败回调
            }
            var res = _self._extend(defaults, _self.data.opt);
            console.log(res)
            //点选获取底图位置-嵌入式
            if (res.captchaType == "clickWord" && res.mode == "fixed") {
                wx.createSelectorQuery().in(_self).select('.backImg').boundingClientRect(function (rect) {
                    console.log(rect)
                    _self.setData({
                        backImgLeft: rect.left,
                        backImgTop: rect.top,
                    })
                }).exec();
            }
            _self.setData({
                verifyImgOutHeight: (parseInt(res.imgSize.height) + parseInt(res.vSpace)) + "px",
                verifySubBlockWidth: Math.floor(parseInt(res.imgSize.width) * 47 / 310) + 'px',
                verifySubBlockTop: "-" + (parseInt(res.imgSize.height) + parseInt(res.vSpace)) + "px",
                show: res.mode == "pop" ? false : true,
            })
            _self._uuid();
            _self._refresh();
        },
    },
    methods: {
        //显示组件
        show() {
            let _self = this;
            this.setData({show: true}, function () {
                //点选获取底图位置-弹出式
                if (_self.data.opt.captchaType == "clickWord" && _self.data.opt.mode == "pop") {
                    wx.createSelectorQuery().in(_self).select('.backImg').boundingClientRect(function (rect) {
                        console.log(rect)
                        _self.setData({
                            backImgLeft: rect.left,
                            backImgTop: rect.top,
                        })
                    }).exec();
                }
            })
        },
        //隐藏组件
        hide() {
            this.setData({show: false})
        },
        //重新装载
        reload() {
            var _self = this;
            var res = _self.data.opt
            console.log(res)
            //点选获取底图位置-嵌入式
            if (res.captchaType == "clickWord" && res.mode == "fixed") {
                wx.createSelectorQuery().in(_self).select('.backImg').boundingClientRect(function (rect) {
                    console.log(rect)
                    _self.setData({
                        backImgLeft: rect.left,
                        backImgTop: rect.top,
                    })
                }).exec();
            }
            _self.setData({
                verifyImgOutHeight: (parseInt(res.imgSize.height) + parseInt(res.vSpace)) + "px",
                verifySubBlockWidth: Math.floor(parseInt(res.imgSize.width) * 47 / 310) + 'px',
                verifySubBlockTop: "-" + (parseInt(res.imgSize.height) + parseInt(res.vSpace)) + "px",
                show: res.mode == "pop" ? false : true,
            })
            _self._refresh();
        },
        //滑块触摸结束验证
        _blockPuzzleCheck(obj) {
            console.log("开始校验：" + JSON.stringify(obj));
            let _self = this;
            let url = _self.data.opt.baseUrl + "/captcha/check";
            let clientUid = _self.data.opt.captchaType == 'blockPuzzle' ? clientUid = wx.getStorageSync('slider') : clientUid = wx.getStorageSync('point');
            //服务端默认310宽度底图
            var serverX = obj.offsetX / parseInt(_self.data.opt.imgSize.width) * 310;
            console.log("移动距离:" + obj.offsetX + "\t换算比例距离X:" + serverX)
            var pointJson = JSON.stringify({x: serverX, y: 5.0});
            var data = {
                captchaType: _self.data.opt.captchaType,
                "pointJson": _self.data.secretKey ? CryptoJS.AesEncrypt(pointJson, _self.data.secretKey) : pointJson,
                "token": _self.data.token,
                clientUid: clientUid,
                ts: Date.now()
            }
            console.log(data)
            _self._postData(url, data, function (res) {
                if (res.data.repCode == "0000") {
                    //响应正确
                    console.log(obj.expendTime + "s验证成功")
                    _self.setData({
                        leftBarClass: "status-4",
                        verifyTipsClass: "suc-bg",
                        verifyTipsText: obj.expendTime + "s验证成功",
                    })
                    setTimeout(function () {
                        _self._refresh();
                        _self.data.opt.mode == "pop" ? _self.hide() : "";
                        //回调成功函数
                        var captchaVerification = _self.data.token + '---' + pointJson;
                        captchaVerification = _self.data.secretKey ? CryptoJS.AesEncrypt(captchaVerification, _self.data.secretKey) : captchaVerification;
                        if (typeof _self.data.opt.success == "function") {
                            _self.data.opt.success({'captchaVerification': captchaVerification});
                        }
                    }, 700);
                } else {
                    //响应错误
                    _self.setData({
                        leftBarClass: "status-3",
                        verifyTipsClass: "err-bg",
                        verifyTipsText: res.data.repMsg,
                    })
                    if (typeof _self.data.opt.fail == "function") {
                        _self.data.opt.fail(res.data);
                    }
                    setTimeout(function () {
                        _self._refresh();
                    }, 700);
                }
            });
        },
        //点选点击事件
        _clickWordTap(res) {
            var _self = this;
            console.log(res)
            var detail_x = res.detail.x;
            var detail_y = res.detail.y;
            //显示对应标记点
            _self._setPoint(detail_x, detail_y);

            console.log(_self.data.clickWordTapNum)
            console.log(_self.data.clickWordXYList)
            if (_self.data.clickWordTapNum > 2) {
                //更新服务器校验，取消点击事件
                _self.setData({clickWordTapName: ''})

                let url = _self.data.opt.baseUrl + "/captcha/check";
                let clientUid = _self.data.opt.captchaType == 'blockPuzzle' ? clientUid = wx.getStorageSync('slider') : clientUid = wx.getStorageSync('point');
                var pointJson = JSON.stringify(_self.data.clickWordXYList);
                var data = {
                    captchaType: _self.data.opt.captchaType,
                    "pointJson": _self.data.secretKey ? CryptoJS.AesEncrypt(pointJson, _self.data.secretKey) : pointJson,
                    "token": _self.data.token,
                    clientUid: clientUid,
                    ts: Date.now()
                }
                console.log(data)
                _self._postData(url, data, function (res) {
                    if (res.data.repCode == "0000") {
                        //响应正确
                        _self.setData({
                            verifyMsgText: "验证成功",
                            verifyBarAreaClass: "suc-area",
                        })
                        setTimeout(function () {
                            _self._refresh();
                            _self.data.opt.mode == "pop" ? _self.hide() : "";
                            //回调成功函数
                            var captchaVerification = _self.data.token + '---' + pointJson;
                            captchaVerification = _self.data.secretKey ? CryptoJS.AesEncrypt(captchaVerification, _self.data.secretKey) : captchaVerification;
                            if (typeof _self.data.opt.success == "function") {
                                _self.data.opt.success({'captchaVerification': captchaVerification});
                            }
                        }, 700);
                    } else {
                        //响应错误
                        _self.setData({
                            verifyMsgText: "验证失败",
                            verifyBarAreaClass: "err-area",
                        })
                        if (typeof _self.data.opt.fail == "function") {
                            _self.data.opt.fail(res.data);
                        }
                        setTimeout(function () {
                            _self._refresh();
                        }, 700);
                    }
                });
            }
        },
        //显示坐标
        _setPoint(detail_x, detail_y) {
            var _self = this;
            var xylist = _self.data.clickWordXYList;
            var pointList = _self.data.clickWordPointList;
            var offsetX = detail_x - _self.data.backImgLeft;
            var offsetY = detail_y - _self.data.backImgTop;
            console.log("点击位置：" + detail_x + "\t" + detail_y + "\t底图位置：" + _self.data.backImgLeft + "\t" + _self.data.backImgTop)
            //服务端默认310宽度底图，按比例换成对应坐标
            var serverX = offsetX / parseInt(_self.data.opt.imgSize.width) * 310;
            var serverY = offsetY / parseInt(_self.data.opt.imgSize.width) * 310;
            xylist.push({x: serverX, y: serverY});
            pointList[_self.data.clickWordTapNum] = {
                left: (offsetX - 10) + "px",
                top: (offsetY - 10) + "px",
                display: "block"
            };
            _self.setData({
                clickWordTapNum: ++_self.data.clickWordTapNum,
                clickWordXYList: xylist,
                clickWordPointList: pointList,
            })
        },
        //刷新
        _refresh() {
            let _self = this;
            let url = _self.data.opt.baseUrl + "/captcha/get";
            let clientUid = "";
            if (_self.data.opt.captchaType == "blockPuzzle") {
                clientUid = wx.getStorageSync('slider');
            } else {
                clientUid = wx.getStorageSync('point')
            }
            let data = {
                captchaType: _self.data.opt.captchaType,
                clientUid: clientUid,
                ts: Date.now()
            }
            console.log(data)
            _self._postData(url, data, function (res) {
                if (res.data.repCode == "0000") {
                    var text = data.captchaType == 'blockPuzzle' ? '向右滑动完成验证' : '请依次点击【' + res.data.repData.wordList.join(",") + '】';
                    _self.setData({
                        originalImageBase64: "data:image/png;base64," + res.data.repData.originalImageBase64,
                        jigsawImageBase64: "data:image/png;base64," + res.data.repData.jigsawImageBase64,
                        secretKey: res.data.repData.secretKey,
                        token: res.data.repData.token,
                        leftBarClass: "status-1",
                        verifyTipsClass: "",
                        verifyTipsText: "",
                        verifyMsgText: text,
                        verifyBarAreaClass: '',
                        clickWordTapName: data.captchaType == 'blockPuzzle' ? '' : '_clickWordTap',
                        clickWordTapNum: 0,
                        clickWordXYList: [],
                        clickWordPointList: [
                            {left: 0, top: 0, display: 'none'},
                            {left: 0, top: 0, display: 'none'},
                            {left: 0, top: 0, display: 'none'},
                            {left: 0, top: 0, display: 'none'},
                            {left: 0, top: 0, display: 'none'},
                        ],
                    })
                } else {
                    //响应错误
                    _self.setData({
                        leftBarClass: "status-3",
                        verifyTipsClass: "err-bg",
                        verifyTipsText: res.data.repMsg,
                    })
                    setTimeout(function () {
                        _self._refresh();
                    }, 700);
                }
            });
        },
        //生成uuid
        _uuid() {
            var s = [];
            var hexDigits = "0123456789abcdef";
            for (var i = 0; i < 36; i++) {
                s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
            }
            s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
            s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
            s[8] = s[13] = s[18] = s[23] = "-";
            var slider = 'slider' + '-' + s.join("");
            var point = 'point' + '-' + s.join("");
            // 判断下是否存在 slider和point
            if (!wx.getStorageSync('slider')) {
                wx.setStorageSync("slider", slider)
            }
            if (!wx.getStorageSync('point')) {
                wx.setStorageSync("point", point)
            }
        },
        //post请求封装
        _postData(url, data, success) {
            wx.request({
                url: url,
                data: JSON.stringify(data),
                method: 'POST',
                enableCache: false,
                header: {
                    'Content-Type': 'application/json;charset=UTF-8',
                },
                //成功执行
                success: function (res) {
                    //http状态码判断
                    var status_code = res.statusCode;
                    if (status_code != 200) {
                        wx.showToast({
                            title: '响应错误:\t' + status_code,
                            icon: 'error',
                            duration: 2000
                        })
                    } else {
                        if (typeof success == "function") {
                            success(res);
                        }
                    }
                },
                //失败执行
                fail: function (res) {
                    wx.showToast({
                        title: '网络错误',
                        icon: 'error',
                        duration: 2000
                    })
                }
            })
        },
        //json对象合并
        _extend: function (defaults, opt) {
            var res = {};
            for (var key in defaults) {
                res[key] = defaults[key];
            }
            for (var key in opt) {
                res[key] = opt[key];
            }
            this.setData({opt: res})
            return res;
        },
    }
})