#include <jni.h>
#include <string>
#include <android/log.h>

// 用于链接 C 库的头文件
extern "C" {
#include "two.h"
#include "one.h"
}

const char * LOG_TGA = "LOG_TGA";

extern "C" JNIEXPORT jstring JNICALL Java_com_example_hellondk_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ ";
    std::string result = hello + std::to_string(numReduce(19, 7));
    // 输出 debug 级别的日志信息
    // ANDROID_LOG_VERBOSE, ANDROID_LOG_DEBUG, ANDROID_LOG_INFO, ANDROID_LOG_WARN, ANDROID_LOG_ERROR
    __android_log_print(ANDROID_LOG_VERBOSE, LOG_TGA, "Result = %s", result.c_str());
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL Java_com_example_hellondk_MainActivity_stringFromJNI2(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ 2 ";
    std::string result = hello + std::to_string(numAdd(19, 7));
    return env->NewStringUTF(result.c_str());
}