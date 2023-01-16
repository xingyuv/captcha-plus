import React, {Component} from 'react'
import VerifyPoint from "../components/verifyPoint";
import VerifyPointFixed from "../components/verifyPointFixed";
import VerifySlide from "../components/verifySlide";
import VerifySlideFixed from "../components/verifySlideFixed";
import '../assets/index.css';

class Pages extends Component {
  constructor(props) {
    super(props);
    this.state = {
      imgSize: {width: "330px", height: "200px"},
      setSize: {imgHeight: 200, imgWidth: 330, barHeight: 40, barWidth: 310},
      barSize: {width: "310px", height: "40px"},
      vSpace: 5,
      isPointShow: false,
      isSlideShow: false
    }
  }
  handlePointClick = () => {
    this.setState({
      isPointShow: true
    })
  }
  handleSlideClick = () => {
    this.setState({
      isSlideShow: true
    })
  }
  verifyPointFixedChild(data) {
    this.setState({
      isPointShow: data
    })
  }
  verifySlideFixedChild(data) {
    this.setState({
      isSlideShow: data
    })
  }
  render() {
      return (
        <div>
          <div>
            <h1>verify---anji</h1>
            <p>前后端联合交互的验证码插件</p>
          </div>
          <div className="siderEmbed">
            <h3>滑动嵌入式（slider-embed）</h3>
            <VerifySlide />
          </div>
          <div className="sliderPopup">
            <h3>滑动弹出式（slider-popup）</h3>
            <button className="btn" onClick={(e)=>this.handleSlideClick(e)}>点击我</button>
            {
            this.state.isSlideShow ?
            (<VerifySlideFixed isSlideShow={this.state.isSlideShow} verifyPointFixedChild={this.verifySlideFixedChild.bind(this)}/>)
            : ''
            }
          </div>
          <div className="pointEmbed">
            <h3>点选嵌入式（point-embed）</h3>
            <VerifyPoint />
          </div>
          <div className="pointPopup">
            <h3>点选弹出式（point-popup）</h3>
            <button className="btn" onClick={(e)=>this.handlePointClick(e)}>点击我</button>
            {
            this.state.isPointShow ?
            (<VerifyPointFixed isPointShow={this.state.isPointShow} verifyPointFixedChild={this.verifyPointFixedChild.bind(this)}/>)
            : ''
            }
          </div>
        </div>
      )
  }
}

export default Pages