plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.valeria.pethealth"
    compileSdk = 34

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    defaultConfig {
        applicationId = "com.valeria.pethealth"
        minSdk = 24
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
}

dependencies {
    implementation("androidx.cardview:cardview:1.0.0")
    implementation ("com.jjoe64:graphview:4.2.2")
    implementation ("androidx.work:work-runtime:2.7.1")
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Calendario

    implementation ("com.google.android.gms:play-services-auth:20.5.0")
    implementation ("com.google.api-client:google-api-client-android:1.33.2")
    implementation ("com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0")
    implementation ("com.google.api-client:google-api-client-gson:1.33.2")
    implementation ("com.google.http-client:google-http-client-android:1.39.2")
    implementation ("com.google.http-client:google-http-client-gson:1.39.2")
    implementation ("com.google.http-client:google-http-client-jackson2:1.39.2")










}