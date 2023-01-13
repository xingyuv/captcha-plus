//
//  NSData+AES256.h
//  dddd
//
//  Created by kean_qi on 2020/5/19.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonCryptor.h>
@interface NSData (AES256)
//NSString *aesKey = @"XwKsGlMcdPMEhR1B";
-(NSData *) aes256_encrypt:(NSString*)key  AESKey:(NSString*)aesKey;//  加密
-(NSData *) aes256_decrypt:(NSString *)key  AESKey:(NSString*)aesKey;//  解密


@end
