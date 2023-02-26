package lt.kslipaitis.spendingtracker

import java.time.DateTimeException
import java.time.LocalDate

class SpendingApp(private val prompter: Prompter) {
    fun format() {

        val date = promptValidDate()

        val amount = prompter.prompt("Amount").toDouble()

        val comment = prompter.prompt("Comment")

        val categoryString = prompter.prompt("Category")
        val category = mapToCategoryEnum(categoryString)

        val vendor = prompter.prompt("Vendor")

        println("$date,$amount,$comment,$category,$vendor")
    }

    private fun promptValidDate(): LocalDate {
        var date: LocalDate

        while (true) {
            val dayString = prompter.prompt("Enter a payment day:")
            val day = dayString.toIntOrNull()
            if (day != null) {
                try {
                    date = LocalDate.now().withDayOfMonth(day)
                    break
                } catch (ex: DateTimeException) {
                    val daysInMonth = LocalDate.now().lengthOfMonth()
                    println("Invalid input. Only $daysInMonth days in current month")
                }
            } else {
                println("Invalid input.")
            }
        }

        return date
    }

    fun mapToCategoryEnum(category: String): String {
        return Category.valueOf(category.uppercase()).name
    }

}