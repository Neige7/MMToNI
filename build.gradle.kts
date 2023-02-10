plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.55"
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
}

taboolib {
    relocate("javassist","pers.neige.mmtoni.javassist")
    description {
        contributors {
            name("Neige")
        }
        dependencies {
            name("NeigeItems")
            name("MythicMobs")
        }
    }
    install(
        "common",
        "common-5",
        "module-configuration",
        "module-metrics",
        "platform-bukkit"
    )
    classifier = null
    version = "6.0.10-70"
}

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/public") }
    maven { url = uri("https://mvn.lumine.io/repository/maven-public") }
}

dependencies {
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11902:11902-minimize:mapped")
    compileOnly("ink.ptms.core:v11902:11902-minimize:universal")
    compileOnly(kotlin("stdlib"))
    taboo(fileTree("libs/javassist.jar"))
    compileOnly(fileTree("libs"))
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("io.lumine:Mythic-Dist:5.1.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            groupId = project.group.toString()
        }
    }
}