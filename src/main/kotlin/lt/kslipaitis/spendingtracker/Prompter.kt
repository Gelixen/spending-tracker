package lt.kslipaitis.spendingtracker

class Prompter {
    fun prompt(message: String): String {
        println(message)
        return readln()
    }
}