object Versions {
    const val ACTIVITY_COMPOSE = "1.8.2"
    const val COMPOSE_BOM = "2023.03.00"
    const val APPCOMPAT = "1.6.1"

    const val CORE_KTX = "1.12.0"
    const val LIFECYCLE_RUNTIME = "2.7.0"

    const val GOOGLE_MATERIAL = "1.11.0"
    const val HILT = "2.44"
    const val HILT_COMPOSE = "1.1.0"

    const val JUNIT = "4.13.2"
    const val ANDROID_JUNIT = "1.1.5"
    const val ESPRESSO_CORE = "3.5.1"

    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.12.0"
    const val TIKXML = "0.8.13"

    const val ROOM = "2.6.1"

    const val COMPOSE_NAVIGATION = "2.5.3"
    const val PAGING = "3.1.1"
    const val PAGING_COMPOSE = "1.0.0-alpha18"
}

object AndroidX {
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_PAGING = "androidx.room:room-paging:${Versions.ROOM}"

    const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"
    const val PAGING = "androidx.paging:paging-common:${Versions.PAGING}"
    const val PAGING_COMPOSE = "androidx.paging:paging-compose:${Versions.PAGING_COMPOSE}"
}

object KTX {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME}"
}

object Google {
    const val GOOGLE_MATERIAL = "com.google.android.material:material:${Versions.GOOGLE_MATERIAL}"

    /* Hilt */
    const val HILT_ANDROID          = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_COMPOSE  = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMPOSE}"

    const val GOOGLE_GUAVA_ERROR = "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava"
}

object Libraries {
    /* Retrofit */
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON    = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    /* TikXml (xml Parser) */
    const val TIKXML_CONVERTER  = "com.tickaroo.tikxml:processor:${Versions.TIKXML}"
    const val TIKXML_ANNOTATION = "com.tickaroo.tikxml:annotation:${Versions.TIKXML}"
    const val TIKXML_CORE = "com.tickaroo.tikxml:core:${Versions.TIKXML}"
    const val TIKXML_RETROFIT_CONVERTER = "com.tickaroo.tikxml:retrofit-converter:${Versions.TIKXML}"
    const val TIKXML_PROCESSOR = "com.tickaroo.tikxml:processor:0.8.13"

    /* OkHttp */
    const val OKHTTP                     = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
}

object Test {
    const val JUNIT         = "junit:junit:${Versions.JUNIT}"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}

