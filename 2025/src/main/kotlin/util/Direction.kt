package util

enum class Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    fun toVector() = when(this) {
        NORTH -> 0 vto -1
        NORTHEAST -> 1 vto -1
        EAST -> 1 vto 0
        SOUTHEAST -> 1 vto 1
        SOUTH -> 0 vto 1
        SOUTHWEST -> -1 vto 1
        WEST -> -1 vto 0
        NORTHWEST -> -1 vto -1
    }
    fun turnRight() = when(this) {
        NORTH -> EAST
        NORTHEAST -> SOUTHEAST
        EAST -> SOUTH
        SOUTHEAST -> SOUTHWEST
        SOUTH -> WEST
        SOUTHWEST -> NORTHWEST
        WEST -> NORTH
        NORTHWEST -> NORTHEAST
    }
    fun turnLeft() = when(this) {
        NORTH -> WEST
        NORTHEAST -> NORTHWEST
        EAST -> NORTH
        SOUTHEAST -> NORTHEAST
        SOUTH -> EAST
        SOUTHWEST -> SOUTHEAST
        WEST -> SOUTH
        NORTHWEST -> SOUTHWEST
    }
}
