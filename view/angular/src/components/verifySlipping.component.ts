import {Component} from '@angular/core';
import * as $ from 'jquery';
import "./verify/verify.js";

@Component({
  selector: 'verify-slipping',
  templateUrl: './verifySlipping.component.html',
  styleUrls: ['./verifySlipping.component.css']
})
export class verifySlippingComponent {
  ngOnInit(): void {
    if (!window.Promise) {
      document.writeln('<script src="https://cdnjs.cloudflare.com/ajax/libs/es6-promise/4.1.1/es6-promise.min.js"><' + '/' + 'script>');
    }
    this.getVerify()
  }
  getVerify(){
    // 初始化验证码  嵌入式
    (<any>$('#mpanel2')).pointsVerify({
      baseUrl:'https://captcha.anji-plus.com/captcha-api',  //服务器请求地址, 默认地址为安吉服务器;
      mode:'fixed',
      imgSize : {
        width: '400px',
        height: '200px',
      },
      ready : function() {
      },
      success : function(params:any) {
        //返回的二次验证参数 合并到验证通过之后的逻辑 参数中回传服务器
      },
      error : function() {
      }
    });

  }
}
