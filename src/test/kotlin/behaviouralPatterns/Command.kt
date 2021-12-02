package behaviouralPatterns

import org.junit.Test

interface Command {
    fun execute()
}

class OrderAddCommand(val id: Long): Command{
    override fun execute() {
        println("Adding order with id: $id")
    }
}

class OrderPayCommand(val id: Long): Command {
    override fun execute() {
        println("Paying for order with id: $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addToQueue(command: Command): CommandProcessor = apply { queue.add(command) }

    fun processCommands(): CommandProcessor = apply {
        queue.forEach{ it.execute() }
        queue.clear()
    }
}

class CommandTest{
    @Test
    fun testCommand(){
        CommandProcessor()
            .addToQueue(OrderAddCommand(1L))
            .addToQueue(OrderAddCommand(2L))
            .addToQueue(OrderPayCommand(2L))
            .addToQueue(OrderPayCommand(1L))
            .processCommands()



    }
}