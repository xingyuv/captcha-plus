import {Component} from '@angular/core';
import {verify} from './model/verify';
import {VerifyService} from './service/app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  verifys: verify[] = [];
  constructor(private verifyService: VerifyService) {
  }
  ngOnInit(): void {
    // this.onGetImg();
  }
  closeBox(){

  }
  // onGetImg(){
  //   let params = {captchaType: "blockPuzzle"}
  //   this.verifyService.getVerify(params).then(verifys => {
  //     this.verifys=verifys
  //   });
  // };
  // onClickMe() {
  //   let params = {captchaType: "blockPuzzle",pointJson: "2kGy0CHZWODobqPiySI4OSSdxCLoKFA0qY8P4z+v+/g="}
  //   this.verifyService.getVerifyCheck(params).then(verifys => {
  //     this.verifys=verifys
  //   });
  // }
}
