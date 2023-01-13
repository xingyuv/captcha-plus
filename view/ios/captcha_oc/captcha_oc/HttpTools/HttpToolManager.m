//
//  HttpToolManager.m
//  SunnyPrj
//
//  Created by Pac on 16/3/6.
//  Copyright © 2016年 Pactera. All rights reserved.
//

#import "HttpToolManager.h"
#import "AFAppDotNetAPIClient.h"


@implementation HttpToolManager
static HttpToolManager *httpManager = nil;
+ (HttpToolManager *)sharedManager
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        httpManager = [[self alloc] init];
    });
    return httpManager;
}

#pragma mark - 封装AFN method

/**
 *  发起网络请求
 *
 *  @param method        GET / POST
 *  @param URLString     URLString
 *  @param patameters    请求参数（一般为字典）
 *  @param finishedBlock 完成的回调
 */
- (void)requestWithMethod:(HTTPMethod)method URLString:(NSString *)URLString parameters:(id)patameters finished:(RequestFinishedBlock)finishedBlock {
    NSString *methodName = (method == kGET) ? @"GET" : @"POST";
    NSLog(@"URLString: %@%@ \n patameters: %@",[[AFAppDotNetAPIClient sharedClient]baseURL], URLString, patameters);
    [[[AFAppDotNetAPIClient sharedClient] dataTaskWithHTTPMethod:methodName URLString:URLString parameters:patameters headers:@{@"Content-Type":@"application/json"} uploadProgress:NULL downloadProgress:NULL success:^(NSURLSessionDataTask *  task, id   responseObject) {
        NSLog(@"%@",responseObject);

        if (finishedBlock) {
            finishedBlock(responseObject, nil);
        }
    } failure:^(NSURLSessionDataTask *  task, NSError *  error) {
        NSLog(@"%@",error);

        if (finishedBlock) {
            finishedBlock(nil, error);
        }
    }] resume];
//    [[[AFAppDotNetAPIClient sharedClient]POST:URLString parameters:patameters headers:@{@"Content-Type":@"application/json"} progress:NULL success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
//        NSLog(@"%@",responseObject);
//
//        if (finishedBlock) {
//            finishedBlock(responseObject, nil);
//        }
//    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
//        NSLog(@"%@",error);
//        if (finishedBlock) {
//            finishedBlock(nil, error);
//        }
//    }] resume];
}

@end
