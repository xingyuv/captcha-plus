//
//  CaptchaRequest.h
//  captcha_oc
//
//  Created by kean_qi on 2020/5/23.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CaptchaRepModel.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger,CaptchaType){
    puzzle = 0,//滑动拼图
    clickword = 1//字符校验
};
typedef NS_ENUM(NSUInteger,CaptchaResult){
    normalState,
    progressState,
    successState,
    failureState
};
@interface CaptchaRequest : NSObject
//获取验证码接口
+ (void)captchaAccept:(CaptchaType )type FinishedBlock:(void(^)(BOOL result,CaptchaRepModel* captchaRepModel))finishedBlock;

 //校验验证码
+ (void)captchaCheck:(CaptchaType )type PointJson:(NSString*)pointJson Token:(NSString*)token FinishedBlock:(void(^)(BOOL result,CaptchaRepModel* captchaRepModel))finishedBlock;
@end

NS_ASSUME_NONNULL_END
