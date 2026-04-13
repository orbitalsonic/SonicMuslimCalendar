plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "com.muslim"
version = "1.0.0"

kotlin {
    jvmToolchain(11)
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
