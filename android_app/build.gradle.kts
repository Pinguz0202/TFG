plugins {
    kotlin("android") version "1.9.22" apply false
    kotlin("kapt") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false // Plugin de Hilt
    id("com.android.application") version "8.1.0" apply false // Plugin para módulos de aplicación
    id("com.android.library") version "8.1.0" apply false     // Plugin para módulos de librería
}