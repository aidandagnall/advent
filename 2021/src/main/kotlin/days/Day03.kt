package days

class Day03 : Day(3) {

    fun gamma() : Int {
        var count = 0
        var out = ""
        for (i in 0 until inputList[0].length) {
            for (k in inputList) {
                if (k[i] == '1')
                    count++
            }
            if (count * 2 > inputList.size) {
                out += "1"
            } else {
                out += "0"
            }
            count = 0
        }
        println("Gamma: $out")
        return Integer.parseInt(out, 2)
    }
    fun epsiolon() : Int {
        var count = 0
        var out = ""
        for (i in 0 until inputList[0].length) {
            for (k in inputList) {
                if (k[i] == '1')
                    count++
            }
            if (count * 2 > inputList.size) {
                out += "0"
            } else {
                out += "1"
            }
            count = 0
        }

        println("Epsilon: $out")
        return Integer.parseInt(out, 2)
    }
    override fun part1() : Any {
        return epsiolon() * gamma()
    }

    fun isMostCommon(index : Int, match : Char,  list : List<String> ) : Char {
        if (list.filter { it[index] == match }.count() * 2 >= list.size) {
            return match
        } else {
            return if (match == '0') '1' else '0'
        }
    }
    fun oxygen() : Int {
        val len = inputList[0].length
        var input = inputList
        var i = 0
        while (input.count() > 1) {
            input = input.filter { it[i] == isMostCommon( i, '1', input) }
            i++
            println(input)
        }

        return Integer.parseInt(input[0], 2)
    }
    fun isLeastCommon(index : Int, match : Char,  list : List<String> ) : Char {
        if (list.filter { it[index] == match }.count() * 2 <= list.size) {
            return match
        } else {
            return if (match == '0') '1' else '0'
        }
    }

    fun co2() : Int {

        val len = inputList[0].length
        var input = inputList
        var i = 0
        println(input)
        while (input.count() > 1) {
            input = input.filter {
                it[i] == isLeastCommon(i, '0', input)
            }
            i++
            println(input)
        }

        return Integer.parseInt(input[0], 2)
    }
    override fun part2() : Any {
        return oxygen() * co2()
    }
}