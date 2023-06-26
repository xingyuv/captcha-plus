import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {VerifyService} from './service/app.service';
import {verifySlippingComponent} from '../components/verifySlipping.component';
import {verifyClickComponent} from '../components/verifyClick.component';

@NgModule({
  declarations: [
    AppComponent,
    verifySlippingComponent,
    verifyClickComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule
  ],
  providers: [VerifyService],
  bootstrap: [AppComponent],
  exports: [
    verifySlippingComponent,
    verifyClickComponent]
})
export class AppModule {
}
