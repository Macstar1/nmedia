plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("com.google.gms.google-services")
}

android {
    namespace = "ru.netology.nmedia"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.netology.nmedia"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {

        buildFeatures.viewBinding = true
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            manifestPlaceholders["usesCleartextTraffic"] = "false"
            buildConfigField("String", "BASE_URL", "\"https://netomedia.ru\"")
        }
        getByName("debug") {
            manifestPlaceholders["usesCleartextTraffic"] = "true"
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:9999\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.gson)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.room.runtime)
    kapt(libs.room.compiler)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging.ktx)


    // define a BOM and its version
    implementation(platform(libs.okhttp.bom))

    // define any required OkHttp artifacts without version
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.glide)
    kapt(libs.glide.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}