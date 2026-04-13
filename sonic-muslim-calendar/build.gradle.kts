plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "com.muslim"
version = "1.0.1"

kotlin {
    jvmToolchain(11)
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
