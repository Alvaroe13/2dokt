object Build {
    const val applicationId = "com.example.notes"
    const val minSdkVersion =  24
    const val targetSdkVersion = 29
    const val versionCode = 1
    const val versionName = "1.0"
    const val compileSdkVersion = 31
    const val buildToolsVersion = "29.0.3"
    const val java_version = "1.8.0"


    //gradle plugin
    const val gradle = "com.android.tools.build:gradle:${Version.gradle}"
    const val gradle_plugin_kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val hilt_gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
}