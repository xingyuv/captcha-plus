# captcha-plus

## 简介

* Captcha-plus行为验证码，包含滑动拼图、文字点选两种方式，UI支持弹出和嵌入两种方式。后端提供Java实现，前端提供了php、angular、html、vue、uni-app、flutter、android、ios等代码示例。

## 在线体验

- [在线体验 antdv](https://gitee.com/yudaocode/yudao-ui-admin-vben/)
- [在线体验 element-plus](https://gitee.com/yudaocode/yudao-ui-admin-vue3/)

## 安装

### 1.Maven

在项目的pom.xml的dependencies中加入以下内容:

spring mvc

``` xml
<dependency>
  <groupId>com.xingyuv</groupId>
  <artifactId>captcha-plus</artifactId>
  <version>1.0.7</version>
</dependency>
```

spring boot 2

``` xml
<dependency>
  <groupId>com.xingyuv</groupId>
  <artifactId>spring-boot-starter-captcha-plus</artifactId>
  <version>1.0.7</version>
</dependency>
```

spring boot 3

``` xml
<dependency>
  <groupId>com.xingyuv</groupId>
  <artifactId>spring-boot-starter-captcha-plus</artifactId>
  <version>1.0.7</version>
</dependency>
```

### 2.验证码接口

- [参考 springboot2 demo](./demo/springboot.java)
- [参考 springboot3 demo](./demo/springboot3.java)

### 功能概述

#### 组件介绍

*
行为验证码采用嵌入式集成方式，接入方便，安全，高效。抛弃了传统字符型验证码展示-填写字符-比对答案的流程，采用验证码展示-采集用户行为-分析用户行为流程，用户只需要产生指定的行为轨迹，不需要键盘手动输入，极大优化了传统验证码用户体验不佳的问题；同时，快速、准确的返回人机判定结果。目前对外提供两种类型的验证码，其中包含滑动拼图、文字点选。如图1-1、1-2所示。若希望不影响原UI布局，可采用弹出式交互。<br>
* 后端基于Java实现，提供纯Java.jar和SpringBoot
  Starter。前端提供了Android、iOS、Futter、Uni-App、ReactNative、Vue、Angular、Html、Php等多端示例。<br>

| 滑动拼图                                                                  | 文字点选                                                            |
|-----------------------------------------------------------------------|-----------------------------------------------------------------|
| ![滑动拼图](images/%E6%BB%91%E5%8A%A8%E6%8B%BC%E5%9B%BE.gif "滑动拼图")&emsp; | ![点选文字](images/%E7%82%B9%E9%80%89%E6%96%87%E5%AD%97.gif "点选文字") |
| 图1-1                                                                  | 图1-2                                                            |

#### 概念术语描述

| 术语    | 描述                                                                  |
|-------|---------------------------------------------------------------------|
| 验证码类型 | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord                                |
| 验证    | 用户拖动/点击一次验证码拼图即视为一次“验证”，不论拼图/点击是否正确                                 |
| 二次校验  | 验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。目的是核实验证数据的有效性。 |

### 交互流程

① 用户访问应用页面，请求显示行为验证码<br>
② 用户按照提示要求完成验证码拼图/点击<br>
③ 用户提交表单，前端将第二步的输出一同提交到后台<br>
④ 验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。<br>
⑤ 第4步返回校验通过/失败到产品应用后端，再返回到前端。如下图所示。
![时序图](view/vue/static/shixu.png "时序图")

### 目录结构

```
├─core
│ ├─captcha-plus  java核心源码
│ └─spring-boot-starter-captcha-plus  springboot快速启动
├─demo
│ ├─springboot     springboot2接口实现示例
│ ├─springboot3    springboot3接实现示例
├─images      效果图
└─view        多语言客户端示例
   ├─android     原生android实现示例
   ├─angular     angular实现示例
   ├─flutter     flutter实现示例
   ├─html        原生html实现示例
   ├─ios         原生ios实现示例
   ├─php         php实现示例
   ├─react       react实现示例
   ├─uni-app     uni-app实现示例
   ├─wx-applet   微信小程序实现示例
   └─vue         vue实现示例
```

### 如有问题，请提交[Issue](https://github.com/xingyuv/captcha-plus/issues)

#### 开源不易，劳烦各位star ☺

### 谁在用

- [ruoyi-vue-pro](https://gitee.com/zhijiantianya/ruoyi-vue-pro)


