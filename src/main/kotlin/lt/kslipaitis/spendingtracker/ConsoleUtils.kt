package lt.kslipaitis.spendingtracker

class ConsoleUtils {
    fun print(message: String) {
        println(message)
    }

    fun printError(message: String) {
        val redColorMessage = String.format("\u001B[31m%s\u001B[0m", message)
        println(redColorMessage)
    }

    fun read(): String {
        return readln()
    }

}
