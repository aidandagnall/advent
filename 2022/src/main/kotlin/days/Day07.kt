package days

class Day07 : Day(7) {

    private val root: Directory = Directory("/", 0, mutableListOf()).also {
        readInput(it, inputList.drop(1).toMutableList())
    }

    private fun readInput(directory: Directory, input: MutableList<String>) {
        if (input.isEmpty()) return
        val command = input.removeFirst()

        when {
            "$ ls" in command -> {
                while (!input.first().startsWith('$')) {
                    val (type, name) = input.removeFirst().split(" ")
                    when (type) {
                        "dir" -> directory.files.add(Directory(name, 0, mutableListOf()))
                        else -> directory.files.add(File(name, type.toInt()))
                    }
                    if (input.isEmpty()) return
                }
            }
            "$ cd .." in command -> return
            else -> {
                val dir = command.split(" ")[2]
                val subDirectory = directory.files.find { it.name == dir }!! as Directory
                readInput(subDirectory, input)
            }
        }
        readInput(directory, input)
    }

    override fun part1() : Any {
        return root.getSubdirectories().map{ it.calculateSize() }.filter { it < 100_000 }.sum()
    }

    override fun part2() : Any {
        return root.getSubdirectories().map { it.calculateSize() }.filter {
            root.calculateSize() - it < (70_000_000 - 30_000_000)
        }.min()
    }

    open class File(open val name: String, open val size: Int) {
        open fun calculateSize(): Int = size
    }

    class Directory (
        override val name: String,
        override val size: Int,
        val files: MutableList<File>
    ): File(name, size) {
        override fun calculateSize(): Int = files.sumOf { it.calculateSize() }

        fun getSubdirectories(): List<Directory> = files.filterIsInstance<Directory>()
            .map { it.getSubdirectories() }.flatten() + this
    }

}