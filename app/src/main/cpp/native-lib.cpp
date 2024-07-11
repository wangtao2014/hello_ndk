#include <jni.h>
#include <string>

// 用于链接 C 库的头文件
extern "C" {
#include "two.h"
#include "one.h"
}

extern "C" JNIEXPORT jstring JNICALL Java_com_example_hellondk_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ ";
    std::string result = hello + std::to_string(numReduce(19, 7));
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL Java_com_example_hellondk_MainActivity_stringFromJNI2(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ 2 ";
    std::string result = hello + std::to_string(numAdd(19, 7));
    return env->NewStringUTF(result.c_str());
}