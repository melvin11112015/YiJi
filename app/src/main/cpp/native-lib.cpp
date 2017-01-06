#include <jni.h>
#include <string>

extern "C"
jstring
Java_io_github_yylyingy_yiji_tools_NativeString_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
