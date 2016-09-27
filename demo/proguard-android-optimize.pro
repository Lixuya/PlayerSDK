# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html



# Optimizations: If you don't want to optimize, use the
# proguard-android.txt configuration file instead of this one, which
# turns off the optimization flags.  Adding optimization introduces
# certain risks, since for example not all optimizations performed by
# ProGuard works on all versions of Dalvik.  The following flags turn
# off various optimizations known to have issues, but the list may not
# be complete or up to date. (The "arithmetic" optimization can be
# used if you are only targeting Android 2.0 or later.)  Make sure you
# test thoroughly if you go this route.
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# The remainder of this file is identical to the non-optimized version
# of the Proguard configuration file (except that the other file has
# flags to turn off optimization).

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose



-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService



# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}


#for javascript
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}



# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.v4.app.ActivityCompatHoneycomb
-dontwarn android.support.v4.os.ParcelableCompatCreatorHoneycombMR2
-dontwarn android.support.v4.view.MotionEventCompatEclair
-dontwarn android.support.v4.view.VelocityTrackerCompatHoneycomb
-dontwarn android.support.v4.view.ViewConfigurationCompatFroyo
-dontwarn android.support.v4.view.MenuCompatHoneycomb

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
#CameraActivity 阻止混淆
#-keep public class com.toughwo.activity2.CameraActivity{*;}

# ButterKnife 6
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##
-dontwarn com.squareup.okhttp.**

#rxjava
-dontwarn rx.**
-keep class rx.** { *; }
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


#com.toughwo.entities,because using Gson
-keep class com.twirling.player.client.PreferenceKeys{*;}
-keep class com.twirling.player.client.Client01{*;}
-keep class com.twirling.player.client.Client02{*;}
-keep class com.twirling.player.client.Client03{*;}

#wechat proguard
-keep class com.tencent.mm.sdk.** {
   *;
}

#weibo proguard
-dontwarn com.weibo.sdk.android.WeiboDialog
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient
-keep public class android.net.http.SslError{
     *;
}
-keep public class android.webkit.WebViewClient{
    *;
}
-keep public class android.webkit.WebChromeClient{
    *;
}
-keep public interface android.webkit.WebChromeClient$CustomViewCallback {
    *;
}
-keep public interface android.webkit.ValueCallback {
    *;
}
-keep class * implements android.webkit.WebChromeClient {
    *;
}

