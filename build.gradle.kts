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
//	implementation("javax.xml.bind:jaxb-api:2.3.1")
//	// https://mvnrepository.com/artifact/org.jvnet.jaxb/jaxb-maven-plugin-core
//	implementation("org.jvnet.jaxb:jaxb-maven-plugin-core:4.0.7")
	implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
	implementation("org.glassfish.jaxb:jaxb-runtime:3.0.1")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2") // JUnit 5
	testImplementation("org.mockito:mockito-core:5.3.1")
	// https://mvnrepository.com/artifact/com.h2database/h2
	testImplementation("com.h2database:h2:2.2.220")

	implementation("org.springframework.boot:spring-boot-starter-web:3.5.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
