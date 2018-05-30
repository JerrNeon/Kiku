# KiKu
[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![JCenter](https://img.shields.io/badge/%20Kiku.JCenter%20-1.0.1-5bc0de.svg)](https://bintray.com/jerrneon/maven/Kiku/_latestVersion)
[![JCenter](https://img.shields.io/badge/%20Kiku.ttp.JCenter%20-1.0.1-5bc0de.svg)](https://bintray.com/jerrneon/maven/Kiku-ttp/_latestVersion)

在 build.gradle 中添加依赖(基础库)

    implementation 'com.jn.kiku:KiKu:1.0.1'
    
在 build.gradle 中添加依赖(第三方平台库)

    implementation 'com.jn.kiku:KiKu-ttp:1.0.1'

### 主要功能：
* 一些项目中常用的Activity&Fragment、带刷新的Activity&Fragment、列表Activity&Fragment封装(包含权限RxPermission、
  ButterKnife、EventBus、状态栏一体化的集成)
* 基础的启动页、引导页、主界面(带有版本更新服务)、富文本界面的封装
* Retrofit请求封装，包含基础的表单、json格式的请求，还有上传图片和下载文件的请求
* 常用第三方SDK(QQ分享&登录、微信分享&登录&支付、支付宝支付、新浪微博登录、友盟统计、百度统计、Ping++、BugTags、诸葛IO)的封装

#### 注意：
1. 如需使用Retrofit,请在Application中先调用RetrofitManager中的initRetrofit()方法(用于传入BaseUrl)
2. 如需使用ActivityManager,请在Application中先调用ActivityManager中的register()方法(用于传入Application)
3. 如需使用LogUtils,请在Application中先调用LogUtils中的init()方法(用于传入Tag的名称)
4. 如需使用ImageUtils,请在Application中先调用ImageUtils中的init()方法(用于传入Application,获取对应的目录资源)
5. 如需使用UriUtils,请在Application中先调用UriUtils中的init()方法()(用于传入包名,适配Android7.0获取本地文件Uri资源,如安装Apk)


