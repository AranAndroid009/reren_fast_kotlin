import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.21"
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

repositories {
    mavenLocal()
    maven {
        url = uri("http://maven.aliyun.com/nexus/content/groups/public/")
    }

    maven {
        url = uri("http://repo.maven.apache.org/maven2")
    }
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web:2.2.4.RELEASE")
    implementation ("org.springframework.boot:spring-boot-starter-aop:2.2.4.RELEASE")
    implementation ("org.springframework:spring-context-support:5.2.3.RELEASE")
    implementation ("org.springframework.boot:spring-boot-starter-data-redis:2.2.4.RELEASE")
    implementation ("org.springframework.boot:spring-boot-configuration-processor:2.2.4.RELEASE")
    implementation ("com.baomidou:mybatis-plus-boot-starter:3.3.1")
    implementation ("mysql:mysql-connector-java:8.0.17")
    implementation ("com.oracle:ojdbc6:11.2.0.3")
    implementation ("com.microsoft.sqlserver:sqljdbc4:4.0")
    implementation ("org.postgresql:postgresql:42.2.9")
    implementation ("com.alibaba:druid-spring-boot-starter:1.1.13")
    implementation ("org.quartz-scheduler:quartz:2.3.0")
    implementation ("commons-lang:commons-lang:2.6")
    implementation ("commons-fileupload:commons-fileupload:1.2.2")
    implementation ("commons-io:commons-io:2.5")
    implementation ("commons-codec:commons-codec:1.10")
    implementation ("commons-configuration:commons-configuration:1.10")
    implementation ("org.apache.shiro:shiro-core:1.4.0")
    implementation ("org.apache.shiro:shiro-spring:1.4.0")
    implementation ("io.jsonwebtoken:jjwt:0.7.0")
    implementation ("com.github.axet:kaptcha:0.0.9")
    implementation ("io.springfox:springfox-swagger2:2.7.0")
    implementation ("io.springfox:springfox-swagger-ui:2.7.0")
    implementation ("com.qiniu:qiniu-java-sdk:7.2.23")
    implementation ("com.aliyun.oss:aliyun-sdk-oss:2.8.3")
    implementation ("com.qcloud:cos_api:4.4" ){
        exclude("org.slf4j", "slf4j-log4j12")
    }
    implementation ("joda-time:joda-time:2.9.9")
    implementation ("com.google.code.gson:gson:2.8.5")
    implementation ("com.alibaba:fastjson:1.2.72")
    implementation ("cn.hutool:hutool-all:4.1.1")
    compileOnly ("org.projectlombok:lombok:1.18.4")
    annotationProcessor ("org.projectlombok:lombok:1.18.4")
    testCompileOnly ("org.projectlombok:lombok:1.18.4")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.4")

    testImplementation ("org.springframework.boot:spring-boot-starter-test:2.2.4.RELEASE")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

group = "io.renren"
version = "3.0.0"


tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
