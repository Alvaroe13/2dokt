plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(project(path = ":core"))
    implementation(project(path = ":note:note-domain"))
    implementation(project(path = ":note:note-datasource-test"))

    implementation(Libs.coroutines_core)
    implementation(Libs.coroutines_android)

    testImplementation(Libs.jUnit)
    testImplementation(Libs.core_testing)
    testImplementation(Libs.jUnit_androidx_ext)
    testImplementation(Libs.coroutines_test)
}