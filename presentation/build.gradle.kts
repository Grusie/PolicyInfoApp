plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.grusie.presentation"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(AppConfig.CONSUMER)
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

    implementation(KTX.CORE_KTX)
    implementation(AndroidX.APPCOMPAT)
    implementation(Google.GOOGLE_MATERIAL)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation(Test.JUNIT)
    androidTestImplementation(Test.ANDROID_JUNIT)
    androidTestImplementation(Test.ESPRESSO_CORE)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //RETROFIT
    implementation(Libraries.RETROFIT)
    implementation(Libraries.OKHTTP)
    implementation(Libraries.OKHTTP_LOGGING_INTERCEPTOR)

    //Hilt
    implementation(Google.HILT_ANDROID)
    kapt          (Google.HILT_ANDROID_COMPILER)
    implementation(Google.HILT_COMPOSE)

    //COMPOSE Navigation
    implementation(AndroidX.COMPOSE_NAVIGATION)
    implementation(AndroidX.COMPOSE_MATERIAL)

    //COMPOSE AnimatedNavHost
    implementation(Google.NAVIGATION_ANIMATION)

    //COMPOSE Swipe
    implementation(Google.COMPOSE_SWIPE)

    //Paging3
    implementation(AndroidX.PAGING)
    implementation(AndroidX.PAGING_COMPOSE)

    //Firebase
    implementation(platform(Google.FIREBASE_BOM))
    implementation(Google.FIREBASE_ANALYTICS)
    implementation(Google.FIREBASE_AUTH)
    implementation(Google.FIREBASE_DYNAMIC_LINKS)

    //Lottie
    implementation(Libraries.Lottie)
}