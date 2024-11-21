plugins {
    kotlin("jvm") version "2.0.21"
    application
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:1.7.32")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.create("newDay") {
    group = "Application"
    doLast {
        val dayNumber = File(projectDir, "/src/main/kotlin/days/").walkTopDown().filter{ it.nameWithoutExtension.length == 5 } .map {
            it.nameWithoutExtension.removePrefix("Day").toInt()
        }.maxOfOrNull{ it + 1 } ?: 1
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
        assertEquals(0, day.part1())
    }


    @Test
    fun testPartTwo() {
        assertEquals(0, day.part2())
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