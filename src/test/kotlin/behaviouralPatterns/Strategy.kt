package behaviouralPatterns

import org.junit.Test

class Printer(private val stringFormatterStrategy: (String) -> String) {
    fun printString(message: String){
        println(stringFormatterStrategy(message))
    }
}

val lowerCaseFormatter = {it: String -> it.lowercase()}
val upperCaseFormatter = {it:String -> it.uppercase()}

class StrategyTest{

    @Test
    fun testStrategy(){
        val inputString = "LOrem IPSUM dolor SIT Amet"

        val lowerCasePrinter = Printer(lowerCaseFormatter)
        lowerCasePrinter.printString(inputString)

        val upperCasePrinter = Printer(upperCaseFormatter)
        upperCasePrinter.printString(inputString)

    }

}