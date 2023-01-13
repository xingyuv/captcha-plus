import { Component } from '@angular/core';
import * as $ from 'jquery';
import "./verify/verify.js";
@Component({
  selector: 'verify-click',
  templateUrl: './verifyClick.component.html',
  styleUrls: ['./verifyClick.component.css']
})
export class verifyClickComponent {
  ngOnInit(): void {
    if (!window.Promise) {
      document.writeln('<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-promise/4.1.1/es6-promise.min.js"><' + '/' + 'script>');
    }
    this.getVerify()
  }
  getVerify(){
    // 初始化验证码  嵌入式
    (<any>$('#mpanel1')).slideVerify({
      baseUrl:'https://captcha.anji-plus.com/captcha-api',  //服务器请求地址, 默认地址为安吉服务器;
      mode:'fixed',
      imgSize : {       //图片的大小对象
        width: '400px',
        height: '200px',
      },
      barSize:{
        width: '400px',
        height: '40px',
      },
      ready : function() {
        //加载完毕的回调
      },
      success : function(params:any) {
        //成功的回调
        //返回的二次验证参数 合并到验证通过之后的逻辑 参数中回传服务器
      },
      error : function() {
        //失败的回调
      }
    });
  }
}
