import {Injectable} from '@angular/core';

import {verify} from '../model/verify';
import {Headers, Http} from '@angular/http';

@Injectable()
export class VerifyService {
  private headers = new Headers({'Content-Type': 'application/json'});
  private VerifyesUrl = 'https://captcha.anji-plus.com/captcha-api';  // URL to web api

  constructor(private http: Http){}
  //请求图片get事件
  getVerify(params:any): Promise<verify[]> {
    return this.http.post(this.VerifyesUrl +'/captcha/get',params).toPromise()
      .then(response => response.json() as verify[])
      .catch(this.handleError);
  }
  //验证图片check事件
  getVerifyCheck(params:any): Promise<verify[]> {
    return this.http.post(this.VerifyesUrl +'/captcha/check',params).toPromise()
      .then(response => response.json() as verify[])
      .catch(this.handleError);
  }
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
