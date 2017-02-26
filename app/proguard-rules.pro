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
-dontwarn org.greenrobot.eventbus.**

-keep public class cn.bmob.v3.**{*;}
-keep public class android.net.**{*;}
-keep public class org.apache.**{*;}
-keep public class rx.**{*;}
-keepclasseswithmembers public class io.github.yylyingy.yiji.javabeans.**{*;}

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
-keep public class org.greenrobot.eventbus.**{*;}

-keep public class com.github.florent37.**{*;}
-keep public class com.dd.**{*;}
-keep public class com.squareup.**{*;}
-keep public class com.android.support.**{*;}
-keep public class com.google.**{*;}
-keep public class butterknife.**{*;}
-keep public class net.steamcrafted.**{*;}
-keep public class com.daimajia.**{*;}
-keep public class com.balysv.**{*;}
-keep public class com.orhanobut.**{*;}
-keep public class com.github.lecho.**{*;}
-keep public class com.nispok.**{*;}
-keep public class com.rengwuxian.**{*;}
-keep public class com.github.johnpersano.**{*;}
-keep public class com.tencent.**{*;}
-keep public class com.nineoldandroids.**{*;}
-keep public class com.afollestad.**{*;}
-keep public class de.hdodenhof.**{*;}
-keep public class com.facebook.**{*;}
-keep public class jp.wasabeef.**{*;}
-keep public class uk.co.senab.photoview.**{*;}
-keep public class com.wang.avi.**{*;}

#####################################EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}
#########################################

#native methods
-keepclasseswithmembernames class * {
                            native <methods>;
                            }