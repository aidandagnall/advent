import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.20"
    application
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:1.7.32")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.create("newDay") {
    doLast {
        val dayNumber = File(projectDir, "/src/main/kotlin/days/").walkTopDown().filter{ it.nameWithoutExtension.length == 5 } .map {
            it.nameWithoutExtension.removePrefix("Day").toInt()
        }.maxOf { it } + 1
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
            """.trimIndent()
        )

        File(projectDir, "/src/test/kotlin/days/Day${dayString}Test.kt").writeText(
            """
package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day${dayString}Test {
    private val day = Day${dayString}()

    @Test
    fun testPartOne() {
        assertEquals(day.part1(), 0)
    }


    @Test
    fun testPartTwo() {
        assertEquals(day.part2(), 0)
    }
}
            """.trimIndent()
        )

        File(projectDir, "/src/test/resources/day$dayString.txt").writeText("")
    }
}

application {
    mainClass.set("util.Runner")
}