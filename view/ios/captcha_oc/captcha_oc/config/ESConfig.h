//
//  ESConfig.h
//  dddd
//
//  Created by kean_qi on 2020/5/20.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface ESConfig : NSObject
//序列化
+ (NSString*)jsonEncode:(id) obj;
//反序列化
+ (id)jsonDecode:(NSString *) obj;
@end

NS_ASSUME_NONNULL_END
