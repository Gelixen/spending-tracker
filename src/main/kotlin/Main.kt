import java.time.DateTimeException
import java.time.LocalDate

fun main() {
    SpendingApp().format()
}

class SpendingApp {
    fun format() {

        val date = promptValidDate()

        val amount = readln("Amount").toDouble()

        val comment = readln("Comment")

        val categoryString = readln("Category")
        val category = mapToCategoryEnum(categoryString)

        val vendor = readln("Vendor")

        println("$date,$amount,$comment,$category,$vendor")
    }

    private fun promptValidDate(): LocalDate {
        var date: LocalDate

        while (true) {
            val dayString = readln("Enter a payment day:")
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

    fun readln(prompt: String): String {
        println(prompt)
        return readln()
    }
}
