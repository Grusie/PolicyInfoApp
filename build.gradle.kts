// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {

        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}