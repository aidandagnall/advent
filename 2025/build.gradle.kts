import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.21"
    application
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
    jvmToolchain(8)
}

dependencies {
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass = "util.Runner"
}

tasks.register<JavaExec>("runLatest") {
    group = "advent"
    description = "Run the latest day"
    mainClass = "util.RunLatest"
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("runMonth") {
    group = "advent"
    description = "Run the entire month"
    mainClass = "util.RunMonth"
    classpath = sourceSets.main.get().runtimeClasspath
}

(1..25).forEach {
    tasks.register<JavaExec>("%02d".format(it)) {
        group = "advent.days"
        mainClass = "util.RunDay"
        description = "Run day $it"
        classpath = sourceSets.main.get().runtimeClasspath
        args = listOf("$it")
    }
}

tasks.register("newDay") {
    group = "advent"
    description = "Create implementation, test, and test input files for a new day"
    doLast {
        val dayNumber =
            File(projectDir, "/src/main/kotlin/days/")
                .walkTopDown()
                .filter { it.nameWithoutExtension.length == 5 }
                .map {
                    it.nameWithoutExtension.removePrefix("Day").toInt()
                }.maxOfOrNull { it + 1 } ?: 1
        val dayString = "%02d".format(dayNumber)
        File(projectDir, "/src/main/kotlin/days/Day$dayString.kt").writeText(
            """
package days

class Day$dayString : Day($dayNumber) {
    override fun part1() : Any {
        return 0
    }

    override fun part2() : Any {
        return 0
    }
}
            """.trimIndent(),
        )

        val testFile = File(projectDir, "/src/test/kotlin/days/Day${dayString}Test.kt")
        testFile.ensureParentDirsCreated()
        testFile.writeText(
            """
package days

import kotlin.test.Test
import kotlin.test.assertEquals

class Day${dayString}Test {
    private val day = Day$dayString()

    @Test
    fun testPartOne() {
        assertEquals(0, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
    }
}
            """.trimIndent(),
        )

        val testInputFile = File(projectDir, "/src/test/resources/day$dayString.txt")
        testInputFile.ensureParentDirsCreated()
        testInputFile.writeText("")
    }
}

val compileKotlin: KotlinCompile by tasks