package days

import sun.security.jgss.GSSNameImpl
import kotlin.math.ceil
import kotlin.math.floor

class Day18 : Day(18) {

    private val input : List<SnailNumber> = inputList.map { SnailNumber(it) }

    override fun part1() : Any {
        return input.reduce { acc, next -> (acc + next).apply { simplify() }}
            .magnitude()
    }

    override fun part2() : Any {
        return input.flatMapIndexed { first, _ ->
            input.mapIndexed inner@{ second, _ ->
                if (first == second) return@inner 0
                else (SnailNumber(inputList[first]) + SnailNumber(inputList[second])).apply { simplify() }.magnitude()
            }
        }.maxOf { it }
    }

    class SnailNumber(
        private var left: SnailNumber? = null,
        private var right: SnailNumber? = null,
        private var parent: SnailNumber? = null,
        private var value: Int? = null,
    ) {
        enum class TYPE { PAIR, REGULAR}
        private var type = if (left == null && right == null) TYPE.REGULAR else TYPE.PAIR

        constructor(input: String, parent: SnailNumber? = null) : this(null, null, parent, null) {
            val (leftString, rightString) = readNumber(input.substring(1 until input.lastIndex))
            left = if (leftString.contains("[")) SnailNumber(leftString, this) else SnailNumber(parent = this, value = Integer.parseInt(leftString))
            right = if (rightString.contains("[")) SnailNumber(rightString, this) else SnailNumber(parent = this, value = Integer.parseInt(rightString))
            type = if (left == null && right == null) TYPE.REGULAR else TYPE.PAIR
        }

        private fun getLeftNode(): SnailNumber? {
            return if (parent == null || parent?.left == null) null
            else if (parent!!.left != this) parent!!.left!!
            else parent!!.getLeftNode()
        }

        private fun getRightNode(): SnailNumber? {
            return if (parent == null || parent?.right == null) null
            else if (parent!!.right != this) parent!!.right!!
            else parent!!.getRightNode()
        }

        private fun getLeftChild(): SnailNumber {
            return if (type == TYPE.REGULAR) this
            else left!!.getLeftChild()
        }

        private fun getRightChild(): SnailNumber {
            return if (type == TYPE.REGULAR) this
            else right!!.getRightChild()
        }

        fun simplify() {
            while(reduce());
        }

        fun magnitude(): Int {
            if (type == TYPE.REGULAR) return value!!
            else return 3 * left!!.magnitude() + 2 * right!!.magnitude()
        }

        private fun explode(depth: Int = 0) {
            left?.explode(depth + 1)
            if (depth >= 4 && type == TYPE.PAIR) {
                val leftNode = getLeftNode()?.getRightChild()
                val rightNode = getRightNode()?.getLeftChild()

                leftNode?.value = leftNode?.value?.plus(left!!.value!!)
                rightNode?.value = rightNode?.value?.plus(right!!.value!!)
                left = null
                right = null
                value = 0
                type = TYPE.REGULAR
            }
            right?.explode(depth + 1)
        }

        private fun split(): Boolean {
            if (left?.split() == true) return true
            if (type == TYPE.REGULAR && value!! >= 10) {
                left = SnailNumber(parent = this, value = floor(value!!.toDouble() / 2.0).toInt())
                right = SnailNumber(parent = this, value = ceil(value!!.toDouble() / 2.0).toInt())
                type = TYPE.PAIR
                value = null
                return true
            }

            if (right?.split() == true) return true
            return false
        }

        private fun reduce(): Boolean {
            explode()
            return split()
        }

        private fun readNumber(input: String): Pair<String, String> {
            var squareBrackets = 0
            var mid = -1
            input.forEachIndexed { index, char ->
                if (char == '[') squareBrackets += 1
                if (char == ']') squareBrackets -= 1
                if (char == ',' && squareBrackets == 0) mid = index
            }
            return input.substring(0 until mid) to input.substring(mid + 1)
        }
        operator fun plus(n: SnailNumber): SnailNumber {
            return SnailNumber(this, n, null).apply {
                left!!.parent = this
                right!!.parent = this
            }
        }

        override fun toString(): String {
            return if (type == TYPE.REGULAR) "$value"
            else "[${left.toString()},${right.toString()}]"
        }
    }
}