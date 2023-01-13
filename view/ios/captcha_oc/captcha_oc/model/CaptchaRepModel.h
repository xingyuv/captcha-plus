//
//  CaptchaRepModel.h
//  captcha_oc
//
//  Created by kean_qi on 2020/5/23.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseJsonModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface CaptchaRepModel : BaseJsonModel
@property (nonatomic, copy) NSString *originalImageBase64;
@property (nonatomic, copy) NSString *jigsawImageBase64;
@property (nonatomic, copy) NSString *token;
@property (nonatomic, copy) NSString *secretKey;
@property (nonatomic, copy) NSString *result;
@property (nonatomic, strong) NSArray *wordList;

@end

NS_ASSUME_NONNULL_END
