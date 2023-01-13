//
//  NSString+AES256.h
//  dddd
//
//  Created by kean_qi on 2020/5/19.
//  Copyright © 2020 kean_qi. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonCryptor.h>
#import "NSData+AES256.h"
@interface NSString (AES256)
-(NSString *) aes256_encrypt:(NSString *)key AESKey:(NSString*)aesKey;// 加密
-(NSString *) aes256_decrypt:(NSString *)key AESKey:(NSString*)aesKey;// 解密
@end
