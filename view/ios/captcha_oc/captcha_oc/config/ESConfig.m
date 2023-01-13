//
//  ESConfig.m
//  dddd
//
//  Created by kean_qi on 2020/5/20.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import "ESConfig.h"

@implementation ESConfig
+ (NSString*)jsonEncode:(id) obj {
    NSData *newData = [NSJSONSerialization dataWithJSONObject:obj options:NSJSONWritingFragmentsAllowed error:nil];
    NSString *paramsStr = [[NSString alloc] initWithData:newData encoding:NSUTF8StringEncoding];
    return paramsStr;
}

+ (id)jsonDecode:(NSString *) obj {
    NSData *newData = [obj dataUsingEncoding:NSUTF8StringEncoding];
    id r = [NSJSONSerialization JSONObjectWithData:newData options:NSJSONReadingMutableContainers error:nil];
    return r;
}
@end
