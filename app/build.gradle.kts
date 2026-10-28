plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.rechel.geotracker"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.rechel.geotracker"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["MAPS_API_KEY"] =
            providers.gradleProperty("MAPS_API_KEY").orNull
                ?: "NO_CONFIGURADA"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Navegación entre pantallas
    implementation("androidx.navigation:navigation-compose:2.9.6")

    // ViewModel y StateFlow con Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // Retrofit y conversión JSON
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp para interceptar y enviar el token
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Corrutinas para operaciones asíncronas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Google Maps con Jetpack Compose
    implementation("com.google.maps.android:maps-compose:6.12.0")

    // Ubicación GPS
    implementation("com.google.android.gms:play-services-location:21.3.0")

}
// Lee la clave local para Google Maps
val mapsKey = providers.fileContents(
    rootProject.layout.projectDirectory.file("local.properties")
).asText.map { content ->
    content.lineSequence()
        .firstOrNull { it.startsWith("MAPS_API_KEY=") }
        ?.substringAfter("=")
        ?.trim()
        ?: "NO_CONFIGURADA"
}

android.defaultConfig.manifestPlaceholders["MAPS_API_KEY"] =
    mapsKey.get()

