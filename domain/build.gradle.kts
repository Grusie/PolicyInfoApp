import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.grusie.domain"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(AppConfig.CONSUMER)

        buildConfigField("String", "PASSWORD_AES_KEY", getValueFromLocalProperties("PASSWORD_AES_KEY"))
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

    implementation(KTX.CORE_KTX)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.GOOGLE_MATERIAL)
    testImplementation(Test.JUNIT)
    androidTestImplementation(Test.ANDROID_JUNIT)
    androidTestImplementation(Test.ESPRESSO_CORE)
    //Paging3
    implementation(AndroidX.PAGING)

    //Firebase
    implementation(platform(Google.FIREBASE_BOM))
    implementation(Google.FIREBASE_ANALYTICS)
    implementation(Google.FIREBASE_AUTH)
    implementation(Google.FIREBASE_DYNAMIC_LINKS)
}