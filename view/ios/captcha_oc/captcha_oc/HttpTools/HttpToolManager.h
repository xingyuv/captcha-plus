//
//  HttpToolManager.h
//  SunnyPrj
//
//  Created by Pac on 16/3/6.
//  Copyright © 2016年 Pactera. All rights reserved.
//

#import <AFNetworking/AFNetworking.h>
#import <AFHTTPSessionManager.h>
//请求方法define
typedef enum {
    kGET,
    kPOST
} HTTPMethod;

typedef void(^RequestFinishedBlock)(id result, NSError *error);


@interface HttpToolManager : NSObject
+ (HttpToolManager *)sharedManager;
- (void)requestWithMethod:(HTTPMethod)method URLString:(NSString *)URLString parameters:(id)patameters finished:(RequestFinishedBlock)finishedBlock;
@end
