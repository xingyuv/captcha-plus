//
//   NSData+AES256.m
//  dddd
//
//  Created by kean_qi on 2020/5/19.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import "NSData+AES256.h"

@implementation NSData (AES256)
//  加密
- (NSData *)aes256_encrypt:(NSString *)text AESKey:(NSString*)aesKey{
    NSData *enData = [text dataUsingEncoding:NSUTF8StringEncoding];
//    NSLog(@"text ：%@",text);
//    NSLog(@"enData ：%@",enData);
//    NSString *MD5key = @"XwKsGlMcdPMEhR1B";
    NSData *key = [aesKey dataUsingEncoding:NSUTF8StringEncoding];
    NSData *iv = [aesKey dataUsingEncoding:NSUTF8StringEncoding];

    if (key.length != 16 && key.length != 24 && key.length != 32) {
        return nil;
    }
    if (iv.length != 16 && iv.length != 0) {
        return nil;
    }
    
    NSData *result = nil;
    size_t bufferSize = enData.length + kCCBlockSizeAES128;
    void *buffer = malloc(bufferSize);
    if (!buffer) return nil;
    size_t encryptedSize = 0;
    CCCryptorStatus cryptStatus = CCCrypt(kCCEncrypt,
                                          kCCAlgorithmAES128,
                                          kCCOptionPKCS7Padding | kCCOptionECBMode,//填充方式
                                          key.bytes,
                                          key.length,
                                          iv.bytes,
                                          enData.bytes,
                                          enData.length,
                                          buffer,
                                          bufferSize,
                                          &encryptedSize);
    if (cryptStatus == kCCSuccess) {
        result = [[NSData alloc]initWithBytes:buffer length:encryptedSize];
        free(buffer);
//        NSLog(@"result ：%@",result);

        return result;
    } else {
        free(buffer);
        return nil;
    }
}

- (NSData *)aes256_decrypt:(NSString *)text AESKey:(NSString*)aesKey{
    NSData *deData = [text dataUsingEncoding:NSUTF8StringEncoding];
//    NSString *MD5key = @"XwKsGlMcdPMEhR1B";
    NSData *key = [aesKey dataUsingEncoding:NSUTF8StringEncoding];
    NSData *iv = [aesKey dataUsingEncoding:NSUTF8StringEncoding];

    if (key.length != 16 && key.length != 24 && key.length != 32) {
        return nil;
    }
    if (iv.length != 16 && iv.length != 0) {
        return nil;
    }
    
    NSData *result = nil;
    size_t bufferSize = deData.length + kCCBlockSizeAES128;
    void *buffer = malloc(bufferSize);
    if (!buffer) return nil;
    size_t encryptedSize = 0;
    CCCryptorStatus cryptStatus = CCCrypt(kCCDecrypt,
                                          kCCAlgorithmAES128,
                                          kCCOptionPKCS7Padding | kCCOptionECBMode,//填充方式
                                          key.bytes,
                                          key.length,
                                          iv.bytes,
                                          deData.bytes,
                                          deData.length,
                                          buffer,
                                          bufferSize,
                                          &encryptedSize);
    if (cryptStatus == kCCSuccess) {
        result = [[NSData alloc]initWithBytes:buffer length:encryptedSize];
        NSLog(@"result ：%@",result);
        free(buffer);
        return result;
    } else {
        free(buffer);
        return nil;
    }
    
}

@end
