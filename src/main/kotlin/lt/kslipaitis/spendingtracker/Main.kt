package lt.kslipaitis.spendingtracker

fun main() {
    SpendingApp(
        CliPrompter(),
        ConsoleUtils(),
        FileWriter("./src/main/resources/results.csv")
    ).format()
}
