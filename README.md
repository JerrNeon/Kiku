# AndroidProject
一些项目中常用的Activity&Fragment、带刷新的Activity&Fragment、列表Activity&Fragment封装(包含权限RxPermission、
ButterKnife、EventBus、状态栏一体化的集成)
基础的启动页、引导页、主界面(带有版本更新服务)、富文本界面的封装
Retrofit请求封装，包含基础的表单、json格式的请求，还有上传图片和下载文件的请求
常用第三方SDK(QQ分享&登录、微信分享&登录&支付、支付宝支付、新浪微博登录、友盟统计、百度统计、Ping++、BugTags、诸葛IO)的封装

1.如需使用Retrofit,请在Application中先调用RetrofitManager中的initRetrofit()方法(用于传入BaseUrl)
2.如需使用ActivityManager,请在Application中先调用ActivityManager中的register()方法(用于传入Application)
2.如需使用LogUtils,请在Application中先调用LogUtils中的init()方法(用于传入Tag的名称)
2.如需使用ImageUtils,请在Application中先调用ImageUtils中的init()方法(用于传入Application,获取对应的目录资源)
2.如需使用UriUtils,请在Application中先调用UriUtils中的init()方法()(用于传入包名,适配Android7.0获取本地文件Uri资源,如安装Apk)


