import java.time.LocalDate

fun main(args: Array<String>) {
    val day = readln("Day").toInt()
    val date = LocalDate.now().withDayOfMonth(day)

    val amount = readln("Amount").toDouble()

    val comment = readln("Comment")

    val category = readln("Category")

    val vendor = readln("Vendor")

    println("$date,$amount,$comment,$category,$vendor")
}

fun readln(prompt: String): String {
    println(prompt)
    return readln()
}
