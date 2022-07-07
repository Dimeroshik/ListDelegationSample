plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")


    
}

android {

    signingConfigs {
        getByName("debug") {

        }
    }

    compileSdkVersion(30)

    defaultConfig {
        applicationId =  "com.example.listdelegationsample"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 7
        versionName = "1.1.2"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.10")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.startup:startup-runtime:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.google.firebase:firebase-messaging:21.1.0")

    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:4.3.0")
    implementation("com.hannesdorfmann:adapterdelegates4-pagination:4.3.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    var moxyVersion = "2.2.2"
    implementation("com.github.moxy-community:moxy:$moxyVersion")
    implementation("com.github.moxy-community:moxy-androidx:$moxyVersion")
    implementation("com.github.moxy-community:moxy-material:$moxyVersion")
    implementation("com.github.moxy-community:moxy-ktx:$moxyVersion")
    kapt("com.github.moxy-community:moxy-compiler:$moxyVersion")

    //DI
    val hilt = "2.37"
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-compiler:$hilt")
    //Coroutines
    val coroutinesVersion = "1.5.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$coroutinesVersion")
    //Network
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    //Moshi
    val moshiVersion = "1.12.0"
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
    //Room
    val roomVersion = "2.3.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    //Coil
    implementation("io.coil-kt:coil:1.2.0")
    //dynamic animation
    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")
    // AdapterDelegates
    val adapterDelegates = "4.3.0"
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:$adapterDelegates")
    implementation("com.hannesdorfmann:adapterdelegates4-pagination:$adapterDelegates")
    //Timber
    implementation("com.jakewharton.timber:timber:4.7.1")
}