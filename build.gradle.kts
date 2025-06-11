plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.m"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	// https://mvnrepository.com/artifact/org.postgresql/postgresql
	implementation("org.postgresql:postgresql:42.7.6")
	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-core
	implementation("org.springframework.boot:spring-boot-starter-security")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc
	implementation("org.springframework.boot:spring-boot-starter-jdbc:3.2.9")
	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-crypto
	implementation("org.springframework.security:spring-security-crypto:6.4.4")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web
	// https://mvnrepository.com/artifact/com.auth0/java-jwt
	//--implementation("com.auth0:java-jwt:4.4.0")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	implementation("org.springframework.boot:spring-boot-starter-web:3.5.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
