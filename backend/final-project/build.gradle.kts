plugins {
	java
	id("org.springframework.boot") version "3.5.11"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.asciidoctor.jvm.convert") version "4.0.5"
}

group = "com.software.engineering"
version = "0.0.1-SNAPSHOT"
description = "Final project for the Software Engineering course at UNC (Universidad Nacional de Córdoba)."

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	testRuntimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

springBoot {
	mainClass.set("com.iot.IotApplication")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Load monorepo root .env into bootRun so SPRING_PORT / POSTGRES_* match `docker compose` and `.env.example`
tasks.bootRun {
	val envFile = rootProject.projectDir.resolve("../../../.env").normalize()
	if (envFile.isFile) {
		envFile.readLines().forEach { raw ->
			val line = raw.trim()
			if (line.isEmpty() || line.startsWith("#")) {
				return@forEach
			}
			val eq = line.indexOf('=')
			if (eq <= 0) {
				return@forEach
			}
			val key = line.substring(0, eq).trim()
			if (!key.matches(Regex("[A-Za-z_][A-Za-z0-9_]*"))) {
				return@forEach
			}
			var value = line.substring(eq + 1).trim()
			if (value.length >= 2 && ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'")))) {
				value = value.substring(1, value.length - 1)
			}
			environment(key, value)
		}
	}
}

tasks.test {
	outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
	inputs.dir(project.extra["snippetsDir"]!!)
	dependsOn(tasks.test)
}
