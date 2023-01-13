

#import "AFAppDotNetAPIClient.h"

static NSString * const AFAppDotNetAPIBaseURLString = @"https://captcha.anji-plus.com/captcha-api/";

//static NSString * const AFAppDotNetAPIBaseURLString = @"http://127.0.0.1:8080/";
@implementation AFAppDotNetAPIClient

+ (instancetype)sharedClient {
    static AFAppDotNetAPIClient *_sharedClient = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedClient = [[AFAppDotNetAPIClient alloc] initWithBaseURL:[NSURL URLWithString:AFAppDotNetAPIBaseURLString]];
        _sharedClient.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeNone];
    });
    
    return _sharedClient;
}

-(instancetype)initWithBaseURL:(NSURL *)url
{
    self = [super initWithBaseURL:url];
    if (self) {
        [self.reachabilityManager setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
            NSLog(@"Reachability:%@",AFStringFromNetworkReachabilityStatus(status));
        }];
        
        [self.reachabilityManager startMonitoring];
//        manager.requestSerializer = [AFHTTPRequestSerializer serializer];

        //数据请求是JSON格式  需要修改requestSerializer 为 AFJSONRequestSerializer
        self.requestSerializer =[AFJSONRequestSerializer serializer];
        self.requestSerializer.timeoutInterval = 15;
        self.requestSerializer.stringEncoding = NSUTF8StringEncoding;
        self.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"application/json", @"text/json", @"text/javascript", @"text/plain", @"text/html", nil];
//        [(AFJSONResponseSerializer *)self.responseSerializer setRemovesKeysWithNullValues:YES];
    }
    return self;
}

@end
