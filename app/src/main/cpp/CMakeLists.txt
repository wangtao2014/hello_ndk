# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("hellondk")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#
# In this top level CMakeLists.txt, ${CMAKE_PROJECT_NAME} is used to define
# the target library name; in the sub-module's CMakeLists.txt, ${PROJECT_NAME}
# is preferred for the same purpose.
#
# In order to load a library into your app from Java/Kotlin, you must call
# System.loadLibrary() and pass the name of the library defined here;
# for GameActivity/NativeActivity derived applications, the same library name must be
# used in the AndroidManifest.xml file.

set(CMAKE_VERBOSE_MAKEFILE on)
# set() 来设置一个路径全局变量 distribution_DIR
set(distribution_DIR ${CMAKE_SOURCE_DIR}/../../../../libBuild)

message(WARNING ${CMAKE_SOURCE_DIR})
message(WARNING ${distribution_DIR})

# 创建总的库native-lib并链接一个.cpp文件，可以直接引用第三方库的头文件
# SHARED : 编译成动态 so 库, 需要通过 System.loadLibrary() 加载; STATIC : 编译成静态库, 需要链接到 APK 中
add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        native-lib.cpp)

# 链接第三方库的头文件
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE
                            ${distribution_DIR}/one/include
                            ${distribution_DIR}/two/include)

# 创建一个静态库 lib_one 直接引用lib_a.a
add_library(lib_a STATIC IMPORTED)
set_target_properties(lib_a PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/one/lib/${ANDROID_ABI}/liblib_a.a)

# 创建一个动态库 lib_two 直接引用lib_so.so
add_library(lib_so SHARED IMPORTED)
set_target_properties(lib_so PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/two/lib/${ANDROID_ABI}/liblib_so.so)

# Specifies libraries CMake should link to your target library. You
# can link libraries from various origins, such as libraries defined in this
# build script, prebuilt third-party libraries, or Android system libraries.
target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        lib_a
        lib_so
        android
        log)