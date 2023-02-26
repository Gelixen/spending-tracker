package lt.kslipaitis.spendingtracker

class CliPrompter : Prompter {
    override fun prompt(message: String): String {
        println(message)
        return readln()
    }
}