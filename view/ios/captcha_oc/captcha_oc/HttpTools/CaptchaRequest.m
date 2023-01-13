//
//  CaptchaRequest.m
//  captcha_oc
//
//  Created by kean_qi on 2020/5/23.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import "CaptchaRequest.h"
#import "HttpToolManager.h"

@implementation CaptchaRequest
//获取验证码接口
+ (void)captchaAccept:(CaptchaType )type FinishedBlock:(void(^)(BOOL result,CaptchaRepModel* captchaRepModel))finishedBlock {
    NSString *URLString = @"captcha/get";
    NSString *captchaType =  @"blockPuzzle";
    switch (type) {
        case puzzle:
            captchaType = @"blockPuzzle";
            break;
        case clickword:
        captchaType = @"clickWord";
        break;
            
        default:
            break;
    }
    NSDictionary *patameters = @{
        @"captchaType": captchaType,
        @"distinguishSignatureVerificationMethod": @"ios"
    };
    [[HttpToolManager sharedManager] requestWithMethod:kPOST URLString:URLString parameters:patameters finished:^(id result, NSError *error){
        Boolean r = false;
        CaptchaRepModel *model =  [[CaptchaRepModel alloc]init];
        if(result && [result isKindOfClass:[NSDictionary class]] && [result[@"repCode"]  isEqual: @"0000"]){
            r = YES;
            model = [[CaptchaRepModel alloc]  initWithDictionary:result[@"repData"] error:nil];
            
        }
        if(finishedBlock){
            finishedBlock(r , model);

        }
    }];

}

 //校验验证码
+ (void)captchaCheck:(CaptchaType )type PointJson:(NSString*)pointJson Token:(NSString*)token FinishedBlock:(void(^)(BOOL result,CaptchaRepModel* captchaRepModel))finishedBlock {
    NSString *URLString = @"captcha/check";
    NSString *captchaType =  @"blockPuzzle";
    switch (type) {
        case puzzle:
            captchaType = @"blockPuzzle";
            break;
        case clickword:
        captchaType = @"clickWord";
        break;
            
        default:
            break;
    }
    NSDictionary *patameters = @{
        @"pointJson": pointJson,
        @"captchaType": captchaType,
        @"token": token,
        @"distinguishSignatureVerificationMethod": @"ios"
    };
    
    [[HttpToolManager sharedManager] requestWithMethod:kPOST URLString:URLString parameters:patameters finished:^(id result, NSError *error){
        Boolean r = false;
        CaptchaRepModel *model =  [[CaptchaRepModel alloc]init];
        if(result && [result isKindOfClass:[NSDictionary class]] && [result[@"repCode"]  isEqual: @"0000"]){
            r = YES;
            model = [[CaptchaRepModel alloc]  initWithDictionary:result[@"repData"] error:nil];
            
        }
        if(finishedBlock){
            finishedBlock(r , model);
        }
    }];
}
@end
