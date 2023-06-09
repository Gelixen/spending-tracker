package lt.kslipaitis.spendingtracker

import java.time.DateTimeException
import java.time.LocalDate

class SpendingApp(
    private val prompter: Prompter,
    private val consoleUtils: ConsoleUtils,
    private val writer: Writer
) {

    private val entries = mutableListOf<Entry>()

    fun format() {
        do {
            val date = promptValidDate()
            val amount = promptValidAmount()
            val comment = promptValidComment()
            val category = promptValidCategory()
            val vendor = promptValidVendor()

            val entry = Entry(date, amount, comment, category, vendor)
            entries.add(entry)
        } while (promptEnd() != "end")

        entries.forEach { writer.writeLine(it.toString()) }
    }

    private fun promptEnd(): String {
        return prompter.prompt("Type 'end' to finish or press enter to proceed.")
    }

    fun promptValidVendor(): String {
        var vendor: String

        do {
            vendor = prompter.prompt("Input vendor:").trim()
        } while (vendor.isEmpty())

        return vendor
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
                consoleUtils.printError("Can't convert '$amountString' to double.")
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
                consoleUtils.printError("Category '$categoryString' doesn't exist.")
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
                    consoleUtils.printError("Invalid input. Only $daysInMonth days in current month")
                }
            } else {
                consoleUtils.printError("Invalid input.")
            }
        }

        return date
    }

}