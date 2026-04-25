plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appbandacomponente"
    compileSdk {
        version = release(36) {
        }
    }

    defaultConfig {
        applicationId = "com.example.appbandacomponente"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // Biblioteca principal para realizar las peticiones HTTP a la API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Conversor para traducir la respuesta del servidor (JSON) a las clases Modelo de Java
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}