# 功能概述

#### &emsp; 2.1 组件介绍

&emsp;&emsp;
行为验证码采用嵌入式集成方式，接入方便，安全，高效。抛弃了传统字符型验证码展示-填写字符-比对答案的流程，采用验证码展示-采集用户行为-分析用户行为流程，用户只需要产生指定的行为轨迹，不需要键盘手动输入，极大优化了传统验证码用户体验不佳的问题；同时，快速、准确的返回人机判定结果。目前对外提供两种类型的验证码，其中包含滑动拼图、文字点选。如图1-1、1-2所示。若希望不影响原UI布局，可采用弹出式交互。<br>
&emsp;&emsp; 后端基于Java实现，提供纯Java.jar和SpringBoot
Starter。前端提供了Android、iOS、Futter、Uni-App、ReactNative、Vue、Angular、Html、Php等多端示例。<br>

#### &emsp; 2.2 概念术语描述

| 术语    | 描述                                                                  |
|-------|---------------------------------------------------------------------|
| 验证码类型 | 1）滑动拼图 blockPuzzle  2）文字点选 clickWord                                |
| 验证    | 用户拖动/点击一次验证码拼图即视为一次“验证”，不论拼图/点击是否正确                                 |
| 二次校验  | 验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。目的是核实验证数据的有效性。 |

# 交互流程

① 用户访问应用页面，请求显示行为验证码<br>
② 用户按照提示要求完成验证码拼图/点击<br>
③ 用户提交表单，前端将第二步的输出一同提交到后台<br>
④ 验证数据随表单提交到后台后，后台需要调用captchaService.verification做二次校验。<br>
⑤ 第4步返回校验通过/失败到产品应用后端，再返回到前端。如下图所示。

# 目录结构

├─core<br>
│&emsp;├─captcha &emsp;&emsp; java核心源码<br>
│&emsp;└─captcha-spring-boot-starter &emsp;&emsp; springboot快速启动<br>
├─images &emsp;&emsp;&emsp;&emsp;&emsp; 效果图<br>
├─service<br>
│&emsp;├─springboot &emsp;&emsp; 后端为springboot项目示例<br>
│&emsp;└─springmvc &emsp;&emsp; 后端为springmvc非springboot项目示例<br>
└─view &emsp;&emsp;&emsp;&emsp;&emsp; 多语言客户端示例<br>
&emsp;├─android &emsp;&emsp; 原生android实现示例<br>
&emsp;├─angular &emsp;&emsp; angular实现示例<br>
&emsp;├─flutter &emsp;&emsp; flutter实现示例<br>
&emsp;├─html &emsp;&emsp; 原生html实现示例<br>
&emsp;├─ios &emsp;&emsp; 原生ios实现示例<br>
&emsp;├─php &emsp;&emsp; php实现示例<br>
&emsp;├─react-native &emsp;&emsp; react-native实现示例<br>
&emsp;├─uni-app &emsp;&emsp; uni-app实现示例<br>
&emsp;└─vue &emsp;&emsp; vue实现示例<br>
&emsp;

# 接入文档

#### &emsp; 5.1 本地启动

&emsp;
第一步，启动后端，导入Eclipse或者Intellij,启动service/springboot的StartApplication。[社区底图库](https://gitee.com/anji-plus/AJ-Captcha-Images)<br>
&emsp; 第二步，启动前端，使用visual code打开文件夹view/vue，npm install后npm run dev，浏览器登录<br>

``` node
npm install
npm run dev

DONE  Compiled successfully in 29587ms                       12:06:38
I  Your application is running here: http://localhost:8081
```

&emsp;详细的前后端接入文档，后端示例代码service目录下，前端示例代码view目录下。

#### 开源不易，劳烦各位star ☺


