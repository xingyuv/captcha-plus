//
//   NSString+AES256.m
//  dddd
//
//  Created by kean_qi on 2020/5/19.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import "NSString+AES256.h"

@implementation NSString (AES256)
-(NSString *) aes256_encrypt:(NSString*)content  AESKey:(NSString*)aesKey
{
    const char *cstr = [self cStringUsingEncoding:NSUTF8StringEncoding];
    NSData *data = [NSData dataWithBytes:cstr length:self.length];
    //对数据进行加密
    NSData *result = [data aes256_encrypt:content AESKey:aesKey];
    //转换为2进制字符串
    if (result && result.length > 0)
    {
        NSString *base64Encode = [result base64EncodedStringWithOptions:0];
        return  base64Encode;
    }
    return nil;
}
-(NSString *) aes256_decrypt:(NSString *)content  AESKey:(NSString*)aesKey
{
    //转换为2进制Data
    NSMutableData *data = [NSMutableData dataWithCapacity:self.length / 2];
    unsigned char whole_byte;
    char byte_chars[3] = {'\0','\0','\0'};
    int i;
    for (i=0; i < [self length] / 2; i++)
    {
        byte_chars[0] = [self characterAtIndex:i*2];
        byte_chars[1] = [self characterAtIndex:i*2+1];
        whole_byte = strtol(byte_chars, NULL, 16);
        [data appendBytes:&whole_byte length:1];
    }
    //对数据进行解密
    NSData* result = [data aes256_decrypt:content AESKey:aesKey];
    if (result && result.length > 0)
    {
        
            NSString *base64Encode = [result base64EncodedStringWithOptions:0];
        NSLog(@"result %@", base64Encode);

        NSData *nsdataFromBase64String = [[NSData alloc]
        initWithBase64EncodedString:base64Encode options:0];

        NSString *base64Decoded = [[NSString alloc]
          initWithData:nsdataFromBase64String encoding:NSUTF8StringEncoding];
        NSLog(@"Decoded: %@", base64Decoded);

        return base64Decoded ;
    }
    return nil;
}

@end
