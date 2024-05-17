plugins {
    id("com.android.application")
}

android {
    namespace = "top.deeke.javet"
    compileSdk = 34

    defaultConfig {
        applicationId = "top.deeke.javet"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
//    implementation("androidx.navigation:navigation-fragment:2.7.7")
//    implementation("androidx.navigation:navigation-ui:2.7.7")
//    implementation("androidx.compose.material3:material3:1.2.0")
//    implementation ("androidx.compose.material3:material3-window-size-class:1.2.0")
//    implementation ("androidx.compose.material3:material3-adaptive:1.0.0-alpha06")
//    implementation ("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha03")
//    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
//    implementation("androidx.recyclerview:recyclerview:1.3.2")
//
//    implementation("com.google.code.gson:gson:2.10.1")

    //implementation("net.bytebuddy:byte-buddy:1.14.12")
    implementation("com.caoccao.javet:javenode:0.6.0") {
        exclude(group = "com.caoccao.javet", module = "javet")
    }
    implementation("io.vertx:vertx-core:4.5.0")
    //noinspection GradleDependency  注释不提示更新
    //implementation "com.caoccao.javet:javet-android:3.0.4"
    //implementation("com.caoccao.javet:javet-android-v8:3.1.0")
    //implementation "com.caoccao.javet:javet-android-node:3.1.0"
    // Android Node (arm, arm64, x86 and x86_64)
    // Android (arm, arm64, x86 and x86_64)

    //noinspection GradleDependency  注释不提示更新
    //implementation("com.caoccao.javet:javet:3.1.0") // Linux and Windows (x86_64)
    implementation("com.caoccao.javet:javet-macos:3.1.2") // Mac OS (x86_64 and arm64)
    // 添加SLF4J API的依赖
    implementation ("org.slf4j:slf4j-api:2.0.12") // 使用最新版本

    // 添加Logback作为SLF4J的实现
    implementation ("ch.qos.logback:logback-classic:1.5.0") // 使用最新版本
   // implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation ("org.jetbrains:annotations:23.0.0")
  //  implementation ("androidx.preference:preference:1.2.0")// 请检查最新版本

   // implementation ("androidx.datastore:datastore-preferences:1.0.0")
    // optional - RxJava3 support
   // implementation ("androidx.datastore:datastore-preferences-rxjava3:1.0.0")

    //implementation ("de.hdodenhof:circleimageview:3.1.0")
    //implementation "org.opencv:opencv:4.9.0"
    //implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // 如果你需要Android特定的Logback实现，可以添加这个依赖
    // 注意：这个依赖可能不是必需的，因为Logback本身支持Android
//    implementation "com.github.tony19:logback-android:3.0.0" // 使用最新版本

    testImplementation ("junit:junit:4.13.2")

    // Optional -- Robolectric environment
    testImplementation ("androidx.test:core:1.5.0")
    testImplementation ("androidx.test.ext:junit:1.1.5")
    testImplementation ("androidx.test:runner:1.5.2")

    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}