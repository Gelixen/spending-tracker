package lt.kslipaitis.spendingtracker

import java.io.File

class FileWriter(pathname: String) : Writer {
    private val file = File(pathname)

    override fun writeLine(text: String) {
        file.appendText(text + "\n")
    }
}
