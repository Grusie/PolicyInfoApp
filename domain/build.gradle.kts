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
}