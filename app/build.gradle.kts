import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = AppConfig.NAME_SPACE
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = "com.grusie.policyinfo"
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = AppConfig.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KOTLINE_COMPILER_EXTENSION
    }
    packaging {
        resources {
            excludes += AppConfig.PACKAGE_EXCLUDE
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(KTX.CORE_KTX)
    implementation(KTX.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.ACTIVITY_COMPOSE)
    implementation(platform(AndroidX.COMPOSE_BOM))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation(Test.JUNIT)
    androidTestImplementation(Test.ANDROID_JUNIT)
    androidTestImplementation(Test.ESPRESSO_CORE)
    androidTestImplementation(platform(AndroidX.COMPOSE_BOM))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(Google.HILT_ANDROID)
    kapt          (Google.HILT_ANDROID_COMPILER)

    implementation(Libraries.RETROFIT)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Libraries.TIKXML_CONVERTER)
    implementation(Libraries.TIKXML_RETROFIT_CONVERTER)
    implementation(Libraries.TIKXML_ANNOTATION)
    implementation(Google.GOOGLE_GUAVA_ERROR)

    kapt(Libraries.TIKXML_PROCESSOR)
}