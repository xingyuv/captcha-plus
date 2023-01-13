//
//  CaptchaView.m
//  captcha_oc
//
//  Created by kean_qi on 2020/5/23.
//  Copyright © 2020 kean_qi. All rights reserved.
//

#import "CaptchaView.h"
#import "UIView+Extension.h"
#import "UIColor+PLColor.h"
#import "NSString+AES256.h"
#import "ESConfig.h"

#define TopHeight 40.0
#define BottomHeight 50.0
#define SliderBgColor [UIColor colorWithRed: 212/255 green: 212/255 blue: 212/255 alpha:1.0]


@interface CaptchaView()
@property (nonatomic, strong) CaptchaRepModel *captchaModle;
@property (nonatomic, strong) UIView *contentView;
@property (nonatomic, strong) UIView *topView;
@property (nonatomic, strong) UIImageView *baseImageView;
@property (nonatomic, strong) UIImageView *sliderImageView;
@property (nonatomic, strong) UIButton *refreshButton;

//底部view
@property (nonatomic, strong) UIView *bottomView;
//滑块view
@property (nonatomic, strong) UIView *sliderView;
//滑块左边线条view
@property (nonatomic, strong) UIView *borderView;
//底部标题
@property (nonatomic, strong) UILabel *bottomLabel;
@property (nonatomic, strong) UILabel *sliderLabel;


@property (nonatomic, assign) CGFloat puzzleThumbOffsetX;
@property (nonatomic, assign) CGFloat lastPointX;

@property (nonatomic, strong) UIView* resultView;
@property (nonatomic, strong) NSMutableArray* tapViewList;



@end
@implementation CaptchaView

+ (void)showWithType:(CaptchaType)type CompleteBlock:(void(^)(NSString *result))completeBlock{
    CaptchaView *captchaView = [[CaptchaView alloc]initWithFrame:[[UIScreen mainScreen] bounds] Type:type];
    captchaView.completeBlock = completeBlock;
    [[[[UIApplication sharedApplication] windows] objectAtIndex:0] addSubview:captchaView];
}



- (instancetype)initWithFrame:(CGRect)frame Type:(CaptchaType)type
{
    self = [super initWithFrame:frame];
    if (self) {
        self.currentType = type;
        self.lastPointX = 0;
        self.tapViewList = [NSMutableArray array];

        [self setBaseView];
        [self requestData];
    }
    return self;
}

-(void) setBaseView{
    UIView *shadowView = [[UIView alloc]initWithFrame:self.bounds];
    shadowView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.2];
    
    [self addSubview:shadowView];
    [shadowView addSubview:self.contentView];
    [self.contentView addSubview:self.topView];
    

    
}
//滑动验证码
- (void)setPuzzleView{
    [self.contentView addSubview:self.baseImageView];
    [self.baseImageView addSubview:self.sliderImageView];
    self.baseImageView.y = CGRectGetMaxY(self.topView.frame) + 20;
    self.baseImageView.centerX = self.topView.centerX;
    
    UIImage *baseImg = [self base64ConvertImageWithImgStr:self.captchaModle.originalImageBase64];
    self.baseImageView.layer.masksToBounds = YES;
    self.baseImageView.width = baseImg.size.width == 0 ?  310.0 : baseImg.size.width;
    self.baseImageView.height = baseImg.size.height == 0 ?  155.0 : baseImg.size.height;
    self.baseImageView.image = baseImg;
    UIImage *puzzleImg = [self base64ConvertImageWithImgStr:self.captchaModle.jigsawImageBase64];
    self.sliderImageView.frame = CGRectMake(0, 0, puzzleImg.size.width, puzzleImg.size.height);
    self.sliderImageView.image = puzzleImg;
    
    self.refreshButton.frame  = CGRectMake(CGRectGetMaxX(self.topView.frame) - TopHeight - 20,  CGRectGetMaxY(self.topView.frame) + 20, TopHeight, TopHeight);

    [self.contentView addSubview:self.refreshButton];
    
    [self.contentView addSubview:self.bottomView];
    
    
    self.bottomView.frame = CGRectMake(CGRectGetMinX(self.baseImageView.frame), CGRectGetMaxY(self.baseImageView.frame) + 20, self.baseImageView.frame.size.width, BottomHeight);
    self.bottomView.layer.borderColor = [SliderBgColor colorWithAlphaComponent:0.6].CGColor;
    self.bottomView.backgroundColor       = [UIColor whiteColor];
    self.bottomView.layer.borderWidth = 0.5;
    self.bottomView.layer.cornerRadius = 4;
    
    
    self.bottomLabel.text = @"按住滑块拖动到最右边";
    [self.bottomView addSubview:self.bottomLabel];
    self.bottomLabel.frame = self.bottomView.bounds;
    self.puzzleThumbOffsetX = 0.1;
    self.sliderView.frame = CGRectMake(self.puzzleThumbOffsetX, 0, BottomHeight, BottomHeight);
    self.borderView.frame = CGRectMake(0, 0, CGRectGetMidX(self.sliderView.frame), BottomHeight);
    self.sliderView.layer.borderWidth = 1.0;
    self.borderView.layer.borderWidth = 1.0;
    self.sliderLabel.frame = self.sliderView.bounds;
    
    
    [self.bottomView addSubview:self.borderView];
    [self.bottomView addSubview:self.sliderView];
    [self.sliderView addSubview:self.sliderLabel];

    [self setPuzzleCapchaResult:normalState];
    
    self.sliderView.userInteractionEnabled = YES;
    UIPanGestureRecognizer *brightnessPanRecognizer = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(slidThumbView:)];
    [self.sliderView addGestureRecognizer:brightnessPanRecognizer];
    [self.baseImageView insertSubview:self.resultView atIndex:0];
}

//点选验证码
- (void)setClickwordView{
    [self.contentView addSubview:self.baseImageView];
    self.baseImageView.y = CGRectGetMaxY(self.topView.frame) + 20;
    self.baseImageView.centerX = self.topView.centerX;
    
    UIImage *baseImg = [self base64ConvertImageWithImgStr:self.captchaModle.originalImageBase64];
    self.baseImageView.layer.masksToBounds = YES;
    self.baseImageView.width = baseImg.size.width == 0 ?  310.0 : baseImg.size.width;
    self.baseImageView.height = baseImg.size.height == 0 ?  155.0 : baseImg.size.height;
    self.baseImageView.image = baseImg;
    
    self.refreshButton.frame  = CGRectMake(CGRectGetMaxX(self.topView.frame) - TopHeight - 20,  CGRectGetMaxY(self.topView.frame) + 20, TopHeight, TopHeight);

    [self.contentView addSubview:self.refreshButton];
    
    [self.contentView addSubview:self.bottomView];
    
    
    self.bottomView.frame = CGRectMake(CGRectGetMinX(self.baseImageView.frame), CGRectGetMaxY(self.baseImageView.frame) + 20, self.baseImageView.frame.size.width, BottomHeight);
    self.bottomView.layer.borderColor = [SliderBgColor colorWithAlphaComponent:0.6].CGColor;
    self.bottomView.backgroundColor       = [UIColor whiteColor];
    self.bottomView.layer.borderWidth = 0.5;
    self.bottomView.layer.cornerRadius = 4;
    [self.bottomView addSubview:self.bottomLabel];
    self.bottomLabel.frame = self.bottomView.bounds;
    self.baseImageView.userInteractionEnabled = YES;
    UITapGestureRecognizer *brightnessPanRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapGesture:)];
    [self.baseImageView addGestureRecognizer:brightnessPanRecognizer];
    [self setClickwordCapchaResult:normalState];
}
-(void)setCaptchaType{
    switch (self.currentType) {
        case puzzle: //滑动验证码
            [self.baseImageView removeFromSuperview];
            [self.bottomView removeFromSuperview];
            [self setPuzzleView];
            break;
        case clickword: //点选验证码
            self.tapViewList = [NSMutableArray array];
            [self.baseImageView removeFromSuperview];
            self.baseImageView= nil;
            [self.bottomView removeFromSuperview];
            [self setClickwordView];
            break;
        default:
            break;
    }
}
#pragma mark - quest
-(void)requestData{
    if(self.currentType == clickword){
        [self setClickwordCapchaResult:progressState];
    }
    [CaptchaRequest captchaAccept:self.currentType FinishedBlock:^(BOOL result, CaptchaRepModel * captchaRepModel) {
        if(result){
            self.captchaModle = captchaRepModel;
            [self setCaptchaType];
        } else {
            self.captchaModle = [[CaptchaRepModel alloc]init];
            [self setCaptchaType];
        }
    }];
}

-(void)requestDataPointJson:(NSString*)pointJson Token:(NSString*)token PointStr:(NSString*)pointStr{
    if (token==nil || pointJson==nil || pointStr == nil) {
        [self showResultWithSuccess:NO SuccessStr:@""];
        return;
    }
    [CaptchaRequest captchaCheck:self.currentType PointJson:pointJson Token:token FinishedBlock:^(BOOL result, CaptchaRepModel * captchaRepModel) {
        if (result) {
            NSString *successStr = [NSString stringWithFormat:@"%@---%@",token, pointStr];
            if(self.captchaModle.secretKey.length > 0){
                successStr = [successStr aes256_encrypt:successStr AESKey:self.captchaModle.secretKey];
            }
            [self showResultWithSuccess:result SuccessStr:successStr];
        } else {
            [self showResultWithSuccess:result SuccessStr:@""];
        }
    }];
}

#pragma mark - set
//滑动验证码
- (void)setPuzzleCapchaResult:(CaptchaResult)resultType{
    self.capchaResult = resultType;
    switch (self.capchaResult) {
        case normalState:{
            self.borderView.layer.borderColor = [UIColor grayColor].CGColor;
            self.sliderView.layer.borderColor = [UIColor grayColor].CGColor;
            self.sliderView.backgroundColor = [UIColor whiteColor];
            self.bottomLabel.hidden = false;
            self.sliderLabel.text = @">";
            break;
        }
        case progressState:{
            self.borderView.layer.borderColor = [UIColor colorWithHexString:@"337AB7" alpha:1].CGColor;
            self.sliderView.layer.borderColor = [UIColor colorWithHexString:@"337AB7" alpha:1].CGColor;
            self.sliderView.backgroundColor = [UIColor colorWithHexString:@"337AB7" alpha:1];
            self.bottomLabel.hidden = true;
            self.sliderLabel.text = @">";
            break;
        }
        case successState:{
            self.borderView.layer.borderColor = [UIColor colorWithHexString:@"4cae4c" alpha:1].CGColor;
            self.sliderView.layer.borderColor = [UIColor colorWithHexString:@"4cae4c" alpha:1].CGColor;
            self.sliderView.backgroundColor = [UIColor colorWithHexString:@"4cae4c" alpha:1];
            self.bottomLabel.hidden = true;
            self.sliderLabel.text = @"✓";
            break;
        }
        case failureState:{
            self.borderView.layer.borderColor = [UIColor colorWithHexString:@"d9534f" alpha:1].CGColor;
            self.sliderView.layer.borderColor = [UIColor colorWithHexString:@"d9534f" alpha:1].CGColor;
            self.sliderView.backgroundColor = [UIColor colorWithHexString:@"d9534f" alpha:1];
            self.bottomLabel.hidden = true;
            self.sliderLabel.text = @"✕";
            break;
        }
        default:
            break;
    }
}

//文字点选
- (void)setClickwordCapchaResult:(CaptchaResult)resultType{
    self.capchaResult = resultType;
    switch (self.capchaResult) {
        case normalState:{
            if (self.captchaModle.wordList.count > 0) {
                NSString *clickWordStr = [self.captchaModle.wordList componentsJoinedByString:@","];
                self.bottomLabel.text = [NSString stringWithFormat:@"请依次点击【%@】", clickWordStr];
                self.bottomLabel.textColor = [UIColor blackColor];
            }
            self.bottomView.layer.borderColor = SliderBgColor.CGColor;
            break;
        }
        case progressState:{
            self.bottomView.layer.borderColor = SliderBgColor.CGColor;
            self.bottomLabel.text = @"加载中…";
            self.bottomLabel.textColor = [UIColor blackColor];
            break;
        }
        case successState:{
            self.bottomView.layer.borderColor = [UIColor colorWithHexString:@"4cae4c" alpha:1].CGColor;
            self.bottomLabel.text = @"验证成功";
            self.bottomLabel.textColor = [UIColor colorWithHexString:@"4cae4c" alpha:1];
            break;
        }
        case failureState:{
            self.bottomView.layer.borderColor = [UIColor colorWithHexString:@"d9534f" alpha:1].CGColor;
            self.bottomLabel.text = @"验证失败";
            self.bottomLabel.textColor = [UIColor colorWithHexString:@"d9534f" alpha:1];
            break;
        }
        default:
            break;
    }
}

#pragma mark - action
- (void)close {
    [self removeFromSuperview];
}

//滑动验证码
-(void)slidThumbView:(UIPanGestureRecognizer*)gestureRecognizer {
    [self setPuzzleCapchaResult:progressState];

    CGPoint point = [gestureRecognizer translationInView:self.sliderView];
    if (CGRectGetMaxX(self.sliderView.frame) < self.bottomView.bounds.size.width && CGRectGetMinX(self.sliderView.frame) > 0 && point.x < self.bottomView.bounds.size.width && point.x>0) {
        self.sliderView.transform = CGAffineTransformMakeTranslation(point.x, 0);
        self.borderView.frame = CGRectMake(0, 0, CGRectGetMidX(self.sliderView.frame), BottomHeight);
        self.sliderImageView.transform = CGAffineTransformMakeTranslation(point.x, 0);
    }

    if(gestureRecognizer.state == UIGestureRecognizerStateEnded){
        [self checkResult:point];
    }
}

//tapGesture
-(void)tapGesture:(UITapGestureRecognizer*)gestureRecognizer {
    CGPoint point = [gestureRecognizer locationInView:self.baseImageView];
    NSLog(@"%lf", point.x);
    
    if(self.captchaModle.wordList.count > 0 && self.tapViewList.count < self.captchaModle.wordList.count){
        NSDictionary *dic = @{
            @"x": @(point.x),
            @"y": @(point.y)
        };
        [self.tapViewList addObject:dic];
        UILabel *tapLabel = [[UILabel alloc]init];
        CGFloat tapSize = 20;
        tapLabel.frame = CGRectMake(point.x - tapSize*0.5, point.y - tapSize*0.5, tapSize, tapSize);
        tapLabel.backgroundColor = [UIColor colorWithHexString:@"4cae4c" alpha:1];
        tapLabel.textColor = [UIColor whiteColor];
        tapLabel.text = [NSString stringWithFormat:@"%ld",self.tapViewList.count];
        tapLabel.textAlignment = NSTextAlignmentCenter;
        tapLabel.layer.cornerRadius = tapSize*0.5;
        tapLabel.layer.masksToBounds = YES;
        [self.baseImageView addSubview:tapLabel];
    }
    if(self.captchaModle.wordList.count > 0 && self.tapViewList.count == self.captchaModle.wordList.count){
        [self checkResult:point];
    }
}

- (void)checkResult:(CGPoint)point {
    switch (self.currentType) {
        case puzzle: {
            NSDictionary *dic = @{@"x": @(point.x), @"y": @5};
            NSString *pointEncode = [ESConfig jsonEncode:dic];
            NSLog(@"序列化： %@",pointEncode);
            NSString *pointJson = pointEncode;
            if(self.captchaModle.secretKey.length > 0){
                pointJson = [pointEncode aes256_encrypt:pointEncode AESKey:self.captchaModle.secretKey];
            }
            
            NSLog(@"加密： %@",pointJson);

            [self requestDataPointJson:pointJson Token:self.captchaModle.token PointStr:pointEncode];
            

            break;
        }
        case clickword:{
            NSLog(@"%@",self.tapViewList);
            NSString *pointEncode = [ESConfig jsonEncode:self.tapViewList];
            NSString *pointJson = pointEncode;
            if(self.captchaModle.secretKey.length > 0){
                pointJson = [pointEncode aes256_encrypt:pointEncode AESKey:self.captchaModle.secretKey];
            }
            [self requestDataPointJson:pointJson Token:self.captchaModle.token PointStr:pointEncode];
            break;
        }
            
        default:
            break;
    }
}

- (void)showResultWithSuccess:(BOOL)success SuccessStr:(NSString*)successStr{
    switch (self.currentType) {
        case puzzle: {
            if (success) {
                [self setPuzzleCapchaResult:successState];
                dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                    if (self.completeBlock != nil) {
                        self.completeBlock(successStr);
                    }
                    [self close];
                });
                
            } else {
                [self setPuzzleCapchaResult:failureState];
                [UIView animateWithDuration:0.25 animations:^{
                    self.resultView.transform = CGAffineTransformMakeTranslation(0, -20);

                } completion:^(BOOL finished) {
                    [UIView animateWithDuration:0.5 delay:0.75 options:UIViewAnimationOptionCurveEaseIn animations:^{
                        self.resultView.transform =CGAffineTransformIdentity;
                    } completion:nil];
                }];
                [UIView animateWithDuration:0.75 animations:^{
                    self.sliderView.transform = CGAffineTransformIdentity;
                    self.sliderImageView.transform = CGAffineTransformIdentity;
                    self.borderView.layer.frame = CGRectMake(0, 0, CGRectGetMinX(self.sliderView.frame), BottomHeight);
                } completion:^(BOOL finished) {
                    [self setPuzzleCapchaResult:normalState];
                    [self requestData];
                }];
            }
           break;
        }
        case clickword:{
            if (success) {
                [self setClickwordCapchaResult:successState];
                dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                    if (self.completeBlock != nil) {
                        self.completeBlock(successStr);
                    }
                    [self close];
                });
            }else {
                [self setClickwordCapchaResult:failureState];
                dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                    [self requestData];
                });
            }

            break;
        }
        default:
            break;
    }
}

#pragma mark - getter
- (UIView*)contentView{
    if (!_contentView) {
        _contentView = [[UIView alloc]init];
        _contentView.frame =CGRectMake(0, 0, self.bounds.size.width * 0.9, 310);
        _contentView.center = self.center;
        _contentView.backgroundColor = [UIColor whiteColor];
        _contentView.layer.cornerRadius = 4;
        _contentView.layer.masksToBounds = YES;
    }
    return _contentView;
}
- (UIView*)topView{
    if (!_topView) {
        _topView = [[UIView alloc]init];
        _topView.frame = CGRectMake(0, 0, self.bounds.size.width * 0.9, TopHeight);
        UILabel *titleLable = [[UILabel alloc] initWithFrame:CGRectMake(20, 5, 200, TopHeight - 10)];
        titleLable.text = @"请完成安全验证";
        titleLable.textColor = [UIColor grayColor];
        
        UIButton *closeButton = [UIButton buttonWithType:UIButtonTypeSystem];
        closeButton.frame = CGRectMake(self.bounds.size.width * 0.9 - 40, 0, TopHeight, TopHeight);
        [closeButton setImage:[UIImage imageNamed:@"close"] forState:UIControlStateNormal];
        [closeButton addTarget:self action:@selector(close) forControlEvents:UIControlEventTouchUpInside];
        UIView *lineView = [[UIView alloc] initWithFrame:CGRectMake(10, TopHeight - 1, self.bounds.size.width * 0.9 - 20, 1)];
        lineView.backgroundColor = [UIColor lightGrayColor];
        [_topView addSubview:titleLable];
        [_topView addSubview:closeButton];
        [_topView addSubview:lineView];
    }
    return _topView;
}


- (UIImageView*)baseImageView{
    if (!_baseImageView) {
        _baseImageView = [[UIImageView alloc]init];
        _baseImageView.frame = CGRectMake(0, 0, 310, 155);
        _baseImageView.backgroundColor = [UIColor lightGrayColor];
    }
    return _baseImageView;
}
- (UIImageView*)sliderImageView{
    if (!_sliderImageView) {
        _sliderImageView = [[UIImageView alloc]init];
    }
    return _sliderImageView;
}


- (UIView*)bottomView{
    if (!_bottomView) {
        _bottomView = [[UIView alloc]init];
    }
    return _bottomView;
}

- (UIView*)sliderView{
    if (!_sliderView) {
        _sliderView = [[UIView alloc]init];
    }
    return _sliderView;
}

- (UIView*)borderView{
    if (!_borderView) {
        _borderView = [[UIView alloc]init];
    }
    return _borderView;
}

- (UILabel*)bottomLabel{
    if (!_bottomLabel) {
        _bottomLabel = [[UILabel alloc]init];
        _bottomLabel.textColor = [[UIColor blackColor] colorWithAlphaComponent:0.6];
        _bottomLabel.font = [UIFont systemFontOfSize:15];
        _bottomLabel.textAlignment = NSTextAlignmentCenter;
    }
    return _bottomLabel;
}
- (UILabel*)sliderLabel{
    if (!_sliderLabel) {
        _sliderLabel = [[UILabel alloc]init];
        _sliderLabel.font = [UIFont systemFontOfSize:15];
        _sliderLabel.textAlignment = NSTextAlignmentCenter;
    }
    return _sliderLabel;
}

- (UIButton*)refreshButton{
    if (!_refreshButton) {
        _refreshButton = [UIButton buttonWithType:UIButtonTypeSystem];
        
        [_refreshButton setImage:[UIImage imageNamed:@"refresh"] forState:UIControlStateNormal];
        [_refreshButton addTarget:self action:@selector(requestData) forControlEvents:UIControlEventTouchUpInside];
    }
    return _refreshButton;
}

- (UIView*)resultView{
    if (!_resultView) {
        _resultView = [[UIView alloc]init];
        _resultView.frame = CGRectMake(0, 155, 310, 20);
        _resultView.backgroundColor = [[UIColor redColor] colorWithAlphaComponent:0.4];
        UILabel *label  = [[UILabel alloc]initWithFrame:CGRectMake(5, 0, 290, 20)];
        label.text = @"验证失败: 再试一下吧~";
        label.textColor = [UIColor redColor];
        [_resultView addSubview:label];
    }
    return _resultView;
}




- (UIImage *)base64ConvertImageWithImgStr:(NSString *)base64Str {
    if (base64Str==nil) return nil;
    NSData *imageData = [[NSData alloc] initWithBase64EncodedString:base64Str options:NSDataBase64DecodingIgnoreUnknownCharacters];
    //base64字符串有图片前缀（前缀类似：data:image/jpeg;base64,xxxxxxx）转image：
    //NSURL *baseImageUrl = [NSURL URLWithString:base64Str];
//    NSData *imageData = [NSData dataWithContentsOfURL:baseImageUrl];
    UIImage *image = [UIImage imageWithData:imageData];
    return image;;
}
@end
