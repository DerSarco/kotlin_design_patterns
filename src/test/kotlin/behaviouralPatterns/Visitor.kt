package behaviouralPatterns

import org.assertj.core.api.Assertions
import org.junit.Test


interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

class FixedPriceContract(val costPerYear: Long): ReportElement{
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialsContract(val costPerHour: Long, val hours: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long) : ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}
interface ReportVisitor<out R>{
    fun visit(contract:FixedPriceContract): R
    fun visit(contract:TimeAndMaterialsContract): R
    fun visit(contract:SupportContract): R
}

class MonthlyCostReportVisitor: ReportVisitor<Long>{
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear/12
    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours
    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyCostReportVisitor: ReportVisitor<Long>{
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear
    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours
    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {

    @Test
    fun testVisitor(){
        val projectAlpha = FixedPriceContract(10_000)
        val projectBeta = SupportContract(500)
        val projectGamm = TimeAndMaterialsContract(150, 10)
        val projectKappa = TimeAndMaterialsContract(50, 50)

        val project = arrayListOf(projectAlpha,projectBeta, projectGamm, projectKappa)
        val monthlyCostReportVisitor = MonthlyCostReportVisitor()

        val monthlyCost = project.sumOf { it.accept(monthlyCostReportVisitor) }
        println("Monthly Cost: $monthlyCost")
        Assertions.assertThat(monthlyCost).isEqualTo(5333)

        val yearlyCostVisitor = YearlyCostReportVisitor()
        val yearlyCost = project.sumOf { it.accept(yearlyCostVisitor) }
        println("Yearly Cost: $yearlyCost")
        Assertions.assertThat(yearlyCost).isEqualTo(20_000)

    }
}

