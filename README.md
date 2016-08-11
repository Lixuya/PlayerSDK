# TwirlingPlayerSDK
==========
这是时代拓灵全景视频播放器AndroidSDK的文档及使用方法<br/>
# 最新版本 version 1.5.6
	运行环境：Android 4.4（API level 19）及其以上版本。<br/>
##1. 目录介绍<br/>
  1)	SDK：SDK所需的文件，如果开发者重新建立工程或将播放器集成到自己工程，请将该目录下所有文件拷贝到工程对应目录下。<br/>
  2)	TwirlingPlayerSDK：demo程序，演示了如何使用全景播放器SDK。此demo是一个moudle，导入project后使用。开发环境Android Studio2.1，如果Android Studio版本比较低需要更新一下插件。<br/>
  3)	Demo程序中有3个java类：<br/>
    ListShowActivity.java：列表显示类，类中介绍了在线视频和本地视频播放方式。<br/>
    PlayLoadActivity.java：下载和播放页面，主要是针对在线视频设计的。<br/>
    SimpleVrVideoActivity.java:播放器各种功能的实现。<br/>
##2. 接口介绍：<br/>
  import com.google.vr.sdk.widgets.video.VrVideoView;<br/>
##3. 开发工具<br/>
	Android Studio工程配置方法<br/>
	推荐用Android Studio 2.0版本及以上<br/>
###第一步：在工程app/libs目录下放入aar包。<br/>
###第二步：导入aar包。<br/>
    菜单栏选择File->Project Structor->Modules->Dependencies,点击+号，选择File dependency，选择jar包导入。如果导入失败可以找到moudle下的build.gradle文件进行修改：<br/>
<code>repositories {</code><br/>
<code>	flatDir {<br/></code><br/>
<code>		dirs 'libs'</code><br/>
<code>	}</code><br/>
<code>}</code><br/>
<p></p>
<code>dependencies {<br/></code><br/>
<code>	compile fileTree(dir: 'libs', include: ['*.jar'])</code><br/>
<code>	compile(name: 'videowidget', ext: 'aar')</code><br/>
<code>	compile(name: 'common', ext: 'aar')</code><br/>
<code>	compile(name: 'commonwidget', ext: 'aar')</code><br/>
<code>	compile(name: 'base', ext: 'aar')</code><br/>
<code>}</code><br/>
<p></p>
###第三步：修改AndroidManifest.xml文件，在里面加入：<br/>
<!-- These permissions are used by Google VR SDK to get the best Google VR headset profiles. !-->
<code>\<uses-permission android:name="android.permission.INTERNET" /></code><br/>
<code>\<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /></code><br/>
<code>\<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /></code><br/>
<code>\<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /></code><br/>
<code>\<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /></code><br/>
<code>\<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/></code><br/>
在你的<intent-filter>里添加：<br/>
<code>\<category android:name="com.google.intent.category.CARDBOARD" /></code><br/>
##4. issue<br/>
如果有bug反馈或者建议，欢迎在issues里讨论。<br/>
