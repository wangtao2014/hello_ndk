# 注意点
命令列出并查看 so 库的函数：
nm -D liblistdevs.so > listdevs.txt

## 引入多个三方库
采用预编译的方式，将需要加载的目录编译进去, 设置预编译的目录，与 gradle 配合使用，把第三方库直接引用
```cmake
set(my_lib_path ${CMAKE_SOURCE_DIR}/../../../libs/${ANDROID_ABI} )
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${my_lib_path}")
```
原文链接：https://blog.csdn.net/ma598214297/article/details/78387847

## SHARED VS STATIC 
* SHARED : 编译成动态 so 库, 需要通过 System.loadLibrary() 加载
* STATIC : 编译成静态库, 需要链接到 APK 中

## Android 主线程执行
* view.post(Runnable runnable)
* runOnUiThread(Runnable runnable)
* handler.post(Runnable runnable)

## Android 聊天界面
* https://www.cnblogs.com/hyacinthLJP/p/14352840.html

## CMakeList.txt 文件配置
* https://blog.csdn.net/qq_25065595/article/details/108639736