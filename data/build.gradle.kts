import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.grusie.data"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(AppConfig.CONSUMER)

        buildConfigField("String", "POLICY_API_KEY", getValueFromLocalProperties("POLICY_API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.PROGUARD_OPTIMIZE),
                AppConfig.PROGUARD
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = AppConfig.JVM_TARGET
    }
}

fun getValueFromLocalProperties(key:String): String {
    return gradleLocalProperties(rootDir).getProperty(key)
}

dependencies {
    implementation(project(":domain"))

    implementation(KTX.CORE_KTX)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.GOOGLE_MATERIAL)
    testImplementation(Test.JUNIT)
    androidTestImplementation(Test.ANDROID_JUNIT)
    androidTestImplementation(Test.ESPRESSO_CORE)

    implementation(Google.HILT_ANDROID)
    kapt(Google.HILT_ANDROID_COMPILER)

    //Retrofit
    implementation(Libraries.RETROFIT)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Libraries.TIKXML_CONVERTER)

    //XML Parser
    implementation(Libraries.TIKXML_RETROFIT_CONVERTER)
    implementation(Libraries.TIKXML_ANNOTATION)

    //GOOGLE ERROR
    implementation(Google.GOOGLE_GUAVA_ERROR)

    //Room DB
    implementation(AndroidX.ROOM_RUNTIME)
    kapt(AndroidX.ROOM_COMPILER)
    implementation(AndroidX.ROOM_KTX)
    implementation(AndroidX.ROOM_PAGING)

    //Paging3
    implementation(AndroidX.PAGING)

    //Firebase
    implementation(platform(Google.FIREBASE_BOM))
    implementation(Google.FIREBASE_ANALYTICS)
    implementation(Google.FIREBASE_AUTH)

    implementation(Google.FIREBASE_FIRESTORE)

    kapt(Libraries.TIKXML_PROCESSOR)
}