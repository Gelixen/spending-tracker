package lt.kslipaitis.spendingtracker

import java.time.DateTimeException
import java.time.LocalDate

class SpendingApp(private val prompter: Prompter) {
    fun format() {

        val date = promptValidDate()

        val amount = promptValidAmount()

        val comment = promptValidComment()

        val category = promptValidCategory()

        val vendor = prompter.prompt("Vendor")

        println("$date,$amount,$comment,$category,$vendor")
    }

    fun promptValidComment(): String {
        var comment: String

        do {
            comment = prompter.prompt("Input comment:").trim()
        } while (comment.isEmpty())

        return comment
    }

    fun promptValidAmount(): Double {
        var amount: Double

        while (true) {
            val amountString = prompter.prompt("Input spent amount:")

            try {
                amount = amountString.toDouble()
                break
            } catch (ex: NumberFormatException) {
                println("Can't convert '$amountString' to double.")
            }
        }

        return amount
    }

    fun promptValidCategory(): Category {
        var category: Category

        while (true) {
            val categoryString = prompter.prompt("Input category:")
            try {
                category = Category.valueOf(categoryString.uppercase())
                break
            } catch (ex: IllegalArgumentException) {
                println("Category '$categoryString' doesn't exist.")
            }
        }

        return category
    }

    fun promptValidDate(): LocalDate {
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

}