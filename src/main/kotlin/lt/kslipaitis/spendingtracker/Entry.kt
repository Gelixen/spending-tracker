package lt.kslipaitis.spendingtracker

import java.time.LocalDate

data class Entry(
    val date: LocalDate,
    val amount: Double,
    val comment: String,
    val category: Category,
    val vendor: String
) {
    override fun toString(): String {
        return "$date,$amount,$comment,$category,$vendor"
    }
}