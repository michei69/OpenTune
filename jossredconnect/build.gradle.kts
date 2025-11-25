plugins {
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {

    // OkHttp (to JossRedClient)
    implementation(libs.okhttp) // or the latest version

    // If you also need interceptors for logging (optional)
    implementation(libs.logging.interceptor)

}
