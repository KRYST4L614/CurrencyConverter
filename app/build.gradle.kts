plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleDevtoolsKsp)
}

android {
    namespace = "com.example.currencyconverter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.currencyconverter"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.com.github.terrakok.cicerone)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.com.squareup.retrofit2.gson)

    implementation(project(":feature:choosecurrency"))
    implementation(project(":feature:result"))
    implementation(project(":shared:fragmentdependencies"))
    implementation(project(":shared:currency"))
    implementation(project(":shared:resourceprovider"))
}