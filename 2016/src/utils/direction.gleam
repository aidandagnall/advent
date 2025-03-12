pub type Direction {
  NORTH
  EAST
  SOUTH
  WEST
}

pub type Turn {
  LEFT
  RIGHT
}

pub type Position =
  #(Int, Int)

pub type PositionWithDirection =
  #(Int, Int, Direction)

pub fn turn(dir: Direction, turn: Turn) -> Direction {
  case turn {
    LEFT ->
      case dir {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
      }
    RIGHT ->
      case dir {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
      }
  }
}

pub fn move(pos: PositionWithDirection, magnitude: Int) -> PositionWithDirection {
  let #(x, y, dir) = pos
  case dir {
    NORTH -> #(x, y - magnitude, dir)
    EAST -> #(x + magnitude, y, dir)
    SOUTH -> #(x, y + magnitude, dir)
    WEST -> #(x - magnitude, y, dir)
  }
}
