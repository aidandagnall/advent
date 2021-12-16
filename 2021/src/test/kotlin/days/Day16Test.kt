package days

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day = Day16()

    @Test
    fun testPartOne() {
        assertEquals(16, Day16.Packet("8A004A801A8002F478").sumVersion)
        assertEquals(12, Day16.Packet("620080001611562C8802118E34").sumVersion)
        assertEquals(23, Day16.Packet("C0015000016115A2E0802F182340").sumVersion)
        assertEquals(31, Day16.Packet("A0016C880162017C3686B18A3D4780").sumVersion)
    }


    @Test
    fun testPartTwo() {
        assertEquals(3, Day16.Packet("C200B40A82").result)
        assertEquals(54, Day16.Packet("04005AC33890").result)
        assertEquals(7, Day16.Packet("880086C3E88112").result)
        assertEquals(9, Day16.Packet("CE00C43D881120").result)
        assertEquals(1, Day16.Packet("D8005AC2A8F0").result)
        assertEquals(0, Day16.Packet("F600BC2D8F").result)
        assertEquals(0, Day16.Packet("9C005AC2F8F0").result)
        assertEquals(1, Day16.Packet("9C0141080250320F1802104A08").result)
    }
}