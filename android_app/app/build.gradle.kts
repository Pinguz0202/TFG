// build.gradle.kts (Module: app)

plugins {
    // Aquí es donde se aplican los plugins realmente para este módulo.
    // Las versiones ya están definidas a nivel de proyecto (excepto si hay alguna específica del módulo).
    id("com.android.application")
    id("org.jetbrains.kotlin.android") version "1.9.22" // Versión explícita del plugin de Kotlin
    id("kotlin-kapt")                                 // Plugin de KAPT para procesamiento de anotaciones
    id("com.google.dagger.hilt.android")              // Plugin de Hilt para este módulo
}

android {
    namespace = "com.example.bibliotecadigitalappd"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bibliotecadigitalappd"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // Versión del compilador de Compose
    }

    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/versions/9/previous-compilation-data.bin"
            )
        }
    }
}

dependencies {
    // Desugaring para características de Java 8+
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Dependencias Core de Android y Compose
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Bill of Materials (BOM) para Compose, asegura versiones compatibles
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.foundation:foundation")

    // Navegación con Compose y Hilt
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Dagger Hilt: Core y compiladores
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    kapt("androidx.hilt:hilt-compiler:1.2.0") // Compilador específico de AndroidX para Hilt

    // Red: Retrofit y OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12") // Interceptor para logs HTTP

    // Imágenes: Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Persistencia: DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation ("com.auth0.android:jwtdecode:2.0.1")

    // Dependencias de Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

// Configuración de KAPT (Kotlin Annotation Processing Tool)
kapt {
    correctErrorTypes = true
    javacOptions {
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
        option("-Xmaxerrs", "1000") // Aumenta el número máximo de errores mostrados
    }
}

// Configuración de Kotlin para el compilador JVM
kotlin {
    jvmToolchain(17) // Usa la herramienta JVM 17
}