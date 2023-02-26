package lt.kslipaitis.spendingtracker

interface Prompter {
    fun prompt(message: String): String
}