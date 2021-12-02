package structuralPatterns

import org.junit.Test

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine : CoffeeMachine {
    override fun makeSmallCoffee() {
        println("normal coffee machine: making small coffee")

    }

    override fun makeLargeCoffee() {
        println("normal coffee machine: making large coffee")
    }
}

// Decorator.


class EnhancedCoffeeMachine(private val coffeeMachine: CoffeeMachine): CoffeeMachine by coffeeMachine {

//    overriding behaviour
    override fun makeLargeCoffee() {
        println("Enhanced coffee machine: Making large coffee")
    }

    // extending behaviour
    fun makeMilkCoffee(){
        println("Enhanced coffee machine: Making milk coffee")
        coffeeMachine.makeSmallCoffee()
        println("Enhanced coffee machine: adding Milk")
    }
}


class DecoratorTest {
    @Test
    fun testDecorator(){
        val normalMachine = NormalCoffeeMachine()
        val enhancedMachine = EnhancedCoffeeMachine(normalMachine)

        enhancedMachine.makeSmallCoffee()
        println("-----------------------")
        enhancedMachine.makeLargeCoffee()
        println("-----------------------")
        enhancedMachine.makeMilkCoffee()

    }

}