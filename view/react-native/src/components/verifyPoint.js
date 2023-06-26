import React, {Component} from 'react'
import {getPicture, reqCheck} from '../api/base.js'
import '../assets/index.css';
import defaultImg from './../assets/images/default.jpg'
import {aesEncrypt} from "../api/ase.js";

class VerifyPoints extends Component {
    constructor(props) {
        super(props);
        this.state = {
            secretKey: '', //后端返回的ase加密秘钥
            checkNum: 3, //默认需要点击的字数
            fontPos: [], //选中的坐标信息
            checkPosArr: [], //用户点击的坐标
            num: 1, //点击的记数
            pointBackImgBase: '', //后端获取到的背景图片
            poinTextList: [], //后端返回的点击字体顺序
            backToken: '', //后端返回的token值
            captchaType: 'clickWord',
            setSize: {
                imgHeight: 0,
                imgWidth: 0,
                barHeight: 0,
                barWidth: 0,
            },
            tempPoints: [],
            text: '',
            barAreaColor: 'rgb(0,0,0)',
            barAreaBorderColor: 'rgb(221, 221, 221)',
            showRefresh: true,
            bindingClick: true,
        };
    }

    componentDidMount() {
        this.uuid()
        this.getData()
    }

    // 刷新
    refresh = () => {
        this.getData()
        this.setState({
            num: 1,
            tempPoints: [],
            bindingClick: true,
            barAreaColor: 'rgb(0,0,0)',
            barAreaBorderColor: 'rgb(221, 221, 221)',
        })
    }

    // 初始话 uuid
    uuid() {
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
        if (!localStorage.getItem('slider')) {
            localStorage.setItem('slider', slider)
        }
        if (!localStorage.getItem('point')) {
            localStorage.setItem("point", point);
        }
    }

    // 初始化数据
    getData() {
        getPicture({
            captchaType: this.state.captchaType,
            clientUid: localStorage.getItem('point'),
            ts: Date.now()
        }).then(res => {
            if (res.repCode === '0000') {
                this.setState({
                    pointBackImgBase: res.repData.originalImageBase64,
                    backToken: res.repData.token,
                    secretKey: res.repData.secretKey,
                    text: '请依次点击【' + res.repData.wordList + '】'
                })
            } else {
                this.setState({
                    text: res.repMsg,
                    barAreaColor: '#d9534f',
                    barAreaBorderColor: '#d9534f'
                })
            }

            // 请求次数超限
            if (res.repCode == '6201') {
                this.setState({
                    pointBackImgBase: null,
                    text: res.repMsg,
                    barAreaColor: '#d9534f',
                    barAreaBorderColor: '#d9534f'
                })
            }
        })
    }

    canvasClick = (e) => {
        if (this.state.bindingClick) {
            this.state.tempPoints.push(this.getMousePos(e))
            this.setState({
                tempPoints: this.state.tempPoints
            })
            if (this.state.num === this.state.checkNum) {
                this.setState({
                    bindingClick: false
                })
                let data = {
                    captchaType: this.state.captchaType,
                    "pointJson": this.state.secretKey ? aesEncrypt(JSON.stringify(this.state.tempPoints), this.state.secretKey) : JSON.stringify(this.state.tempPoints),
                    "token": this.state.backToken,
                    clientUid: localStorage.getItem('point'),
                    ts: Date.now()
                }
                reqCheck(data).then(res => {
                    if (res.repCode === '0000') {
                        this.setState({
                            text: '验证成功',
                            barAreaColor: '#4cae4c',
                            barAreaBorderColor: '#5cb85c'
                        })
                        setTimeout(() => {
                            this.refresh()
                        }, 1500)
                    } else {
                        this.setState({
                            text: res.repMsg,
                            barAreaColor: '#d9534f',
                            barAreaBorderColor: '#d9534f'
                        })
                        setTimeout(() => {
                            this.refresh();
                        }, 1000);
                    }
                })
            }
            if (this.state.num < this.state.checkNum) {
                this.createPoint(this.getMousePos(e))
                this.setState({
                    num: this.state.num++
                })
            }
        }
    }
    //获取坐标
    getMousePos = (e) => {
        var x = e.nativeEvent.offsetX
        var y = e.nativeEvent.offsetY
        return {x, y}
    }
    // 创建坐标点
    createPoint = () => {
        let num = this.state.num++
        this.setState({
            num
        })
    }

    //坐标转换函数
    pointTransfrom = (pointArr, imgSize) => {
        var newPointArr = pointArr.map(p => {
            let x = Math.round(310 * p.x / parseInt(imgSize.imgWidth))
            let y = Math.round(155 * p.y / parseInt(imgSize.imgHeight))
            return {x, y}
        })
        // console.log(newPointArr,"newPointArr");
        return newPointArr
    }

    render() {
        let tempPoints = this.state.tempPoints
        const {vSpace, barSize, setSize} = this.props;
        return (
            <div style={{position: 'relative'}}>
                <div className='verify-img-out'>
                    <div
                        className='verify-img-panel'
                        style={{
                            width: setSize.imgWidth + 'px',
                            height: setSize.imgHeight + 'px',
                            backgroundSize: setSize.imgWidth + 'px' + ' ' + setSize.imgHeight + 'px',
                            marginBottom: vSpace + 'px',
                        }}
                    >
                        <div className='verify-refresh' style={{zIndex: 3}} onClick={this.refresh}>
                            <i className='iconfont icon-refresh'></i>
                        </div>
                        {this.state.pointBackImgBase ?
                            <img src={'data:image/png;base64,' + this.state.pointBackImgBase} alt=""
                                 style={{width: '100%', height: '100%', display: 'block'}}
                                 onClick={($event) => this.canvasClick($event)}/> :
                            <img src={defaultImg} alt="" style={{width: '100%', height: '100%', display: 'block'}}/>}

                        {tempPoints.map((tempPoint, index) => {
                            return (
                                <div
                                    key={index}
                                    className="point-area"
                                    style={{
                                        backgroundColor: '#1abd6c',
                                        color: '#fff',
                                        zIndex: 9999,
                                        width: '20px',
                                        height: '20px',
                                        textAlign: 'center',
                                        lineHeight: '20px',
                                        borderRadius: '50%',
                                        position: 'absolute',
                                        top: parseInt(tempPoint.y - 10) + 'px',
                                        left: parseInt(tempPoint.x - 10) + 'px',
                                        overflow: 'hidden'
                                    }}
                                >{index + 1}</div>
                            );
                        })}
                    </div>
                </div>

                <div
                    className='verify-bar-area'
                    style={{
                        width: setSize.imgWidth,
                        color: this.state.barAreaColor,
                        borderColor: this.state.barAreaBorderColor,
                        lineHeight: barSize.height,
                    }}
                >
                    <span className='verify-msg'>{this.state.text}</span>
                </div>
            </div>
        );
    }
}

VerifyPoints.defaultProps = {
    mode: 'fixed',
    vSpace: 5,
    imgSize: {
        width: '310px',
        height: '200px',
    },
    barSize: {
        width: '310px',
        height: '40px',
    },
    setSize: {
        imgHeight: 200,
        imgWidth: 310,
        barHeight: 0,
        barWidth: 0,
    },
};

export default VerifyPoints
