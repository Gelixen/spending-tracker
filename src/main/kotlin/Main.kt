import java.time.LocalDate

fun main() {
    SpendingApp().format()
}

class SpendingApp {
    fun format() {
        val day = readln("Day").toInt()
        val date = LocalDate.now().withDayOfMonth(day)

        val amount = readln("Amount").toDouble()

        val comment = readln("Comment")

        val categoryString = readln("Category")
        val category = mapToCategoryEnum(categoryString)

        val vendor = readln("Vendor")

        println("$date,$amount,$comment,$category,$vendor")
    }

    fun mapToCategoryEnum(category: String): String {
        return Category.valueOf(category.uppercase()).name
    }

    fun readln(prompt: String): String {
        println(prompt)
        return readln()
    }
}
