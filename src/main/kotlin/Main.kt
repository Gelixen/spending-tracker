import java.time.LocalDate

fun main(args: Array<String>) {
    println("Day")
    val day = readln().toInt()
    val date = LocalDate.now().withDayOfMonth(day)

    println("Amount")
    val amount = readln().toDouble()

    println("Comment")
    val comment = readln()

    println("Category")
    val category = readln()

    println("Vendor")
    val vendor = readln()

    println("$date,$amount,$comment,$category,$vendor")
}