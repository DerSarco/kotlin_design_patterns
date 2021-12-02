package creationalPatterns


import org.assertj.core.api.Assertions
import org.junit.Test

class AlertBox {
    var message: String? = null

    fun show(){
        println("Alert Box: $this $message")
    }
}

class Window2 {
    lateinit var box: AlertBox

    fun showMessage(message: String){
        box = AlertBox()
        box.message = message
        box.show()
    }
}

class Window {
    val box by lazy {AlertBox()}

    fun showMessage(message: String){
        box.message = message
        box.show()
    }
}

class WindowTest {
    @Test
    fun windowTest() {
        val window = Window()
        window.showMessage("Hello Window")
        Assertions.assertThat(window.box).isNotNull

        val window2 = Window2()
        window2.showMessage("Second Window")
        Assertions.assertThat(window2.box).isNotNull


    }
}

