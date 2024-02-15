object Versions {
    const val ACTIVITY_COMPOSE = "1.8.2"
    const val COMPOSE_BOM = "2023.03.00"
    const val APPCOMPAT = "1.6.1"

    const val CORE_KTX = "1.12.0"
    const val LIFECYCLE_RUNTIME = "2.7.0"

    const val GOOGLE_MATERIAL = "1.11.0"
    const val HILT = "2.44"

    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.5"
    const val ESPRESSO_CORE = "3.5.1"

    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.12.0"
}

object AndroidX {
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
}

object KTX {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME}"
}

object Google {
    const val GOOGLE_MATERIAL = "com.google.android.material:material:${Versions.GOOGLE_MATERIAL}"
    const val HILT_ANDROID          = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}

object Libraries {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON    = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP                     = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
}

object Test {
    const val JUNIT         = "junit:junit:${Versions.JUNIT}"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

