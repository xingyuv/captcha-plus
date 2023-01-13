//
//  ViewController.m
//  captcha_oc
//
//  Created by kean_qi on 2020/5/23.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import "ViewController.h"
#import "CaptchaRequest.h"
#import "NSString+AES256.h"
#import "ESConfig.h"
#import "CaptchaView.h"
#import "CaptchaRequest.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}
- (IBAction)buttonClick:(UIButton *)sender {
    [CaptchaView showWithType: sender.tag == 0 ? puzzle: clickword CompleteBlock:^(NSString *  result) {
        NSLog(@"result: %@", result);
    }];
    
    
    
}

- (void)encode{

    NSDictionary *dic = @{@"x": @5, @"y": @5};
    NSLog(@"%@",dic);

    NSString *pointEncode = [ESConfig jsonEncode:dic];
//    NSString *pointEncode = [self jsonClickWordEncode:@"123456"];

    NSLog(@"序列化： %@",pointEncode);
    NSString *pointJson = [pointEncode aes256_encrypt:pointEncode  AESKey:@"XwKsGlMcdPMEhR1B"];
    NSLog(@"加密： %@",pointJson);
//
//    NSString *pointDecrypt = [pointEncode aes256_decrypt:pointJson AESKey:@"XwKsGlMcdPMEhR1B"];
//    NSLog(@"解密： %@",pointDecrypt);
    
    NSDictionary *d = [ESConfig jsonDecode:pointEncode];
    NSLog(@"反序列化 ：%@",d);

}


@end
