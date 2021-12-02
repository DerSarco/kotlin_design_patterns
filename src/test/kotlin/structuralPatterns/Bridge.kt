package structuralPatterns

import org.assertj.core.api.Assertions
import org.junit.Test

interface Device {
    var volume: Int
    var size: Int
    fun getName(): String
}

class Radio: Device{
    override var volume = 0
    override var size = 0
    override fun getName() = "Radio: $this"
}


class TV: Device {
    override var volume = 0
    override var size = 0
    override fun getName() = "TV: $this"
}


interface Remote {
    fun volumeUp()
    fun volumeDown()
}


interface Size {
    fun size(number: Int)
}

class BasicRemote(val device: Device): Remote{
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down ${device.volume}")
    }
}

class GeneralSize(val device: Device): Size{
    override fun size(number: Int) {
        device.size = number
        println("The size of this ${device.getName()} is ${device.size}'")
    }
}
class BridgeTest {
    @Test
    fun testBridge(){
        val tv = TV()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        val tvSize = GeneralSize(tv)

        tvSize.size(24)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

        Assertions.assertThat(tv.volume).isEqualTo(1)
        Assertions.assertThat(radio.volume).isEqualTo(2)

        Assertions.assertThat(tv.size).isEqualTo(24)

    }
}