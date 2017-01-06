# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\Android-SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-ignorewarnings
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose

#bugly proguard setting
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#libs proguard setting
#-libraryjars libs/BmobSDK_3.5.2_20161027.jar
#-libraryjars libs/org.apache.http.legacy.jar
#-libraryjars libs/rxandroid-1.2.0.jar
#-libraryjars libs/rxjava-1.1.6.jar
-dontwarn cn.bmob.v3.**
-dontwarn android.net.**
-dontwarn org.apache.**
-dontwarn rx.**

-keep public class cn.bmob.v3.**{*;}
-keep public class android.net.**{*;}
-keep public class org.apache.**{*;}
-keep public class rx.**{*;}


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.github.lecho.**{*;}
-keep public class com.nispok.**{*;}
-keep public class com.squareup.okhttp3.**{*;}
-keep public class com.squareup.okio.**{*;}
-keep public class com.squareup.picasso.**{*;}

-keepclasseswithmembernames class * {
                            native <methods>;
                            }