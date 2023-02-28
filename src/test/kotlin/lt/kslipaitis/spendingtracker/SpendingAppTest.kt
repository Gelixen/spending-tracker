package lt.kslipaitis.spendingtracker

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import kotlin.test.assertEquals

class SpendingAppTest {

    private val consoleUtils = ConsoleUtils()
    private val testWriter = EmptyWriter()

    @ParameterizedTest
    @ValueSource(strings = ["gift", "GiFT", "GIFT"])
    fun promptValidCategory_randomCaseValidCategory(expectedCategory: String) {
        val prompter = EmptyPrompter(expectedCategory)
        val app = createApp(prompter)

        val actualCategory = app.promptValidCategory()

        assertEquals(Category.GIFT, actualCategory)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun promptValidCategory_categoryEnum(expectedCategory: Category) {
        val prompter = EmptyPrompter(expectedCategory.name)
        val app = createApp(prompter)

        val actualCategory = app.promptValidCategory()

        assertEquals(expectedCategory, actualCategory)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "GYFT", "random", "test"])
    fun promptValidCategory_invalidCategory_retryUntilValid(category: String) {
        val expectedCategory = Category.OTHER
        val prompter = EmptyPrompter(category, expectedCategory.name)
        val app = createApp(prompter)

        val actualCategory = app.promptValidCategory()

        assertEquals(expectedCategory, actualCategory)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "0.1", "1.23", "105.99"])
    fun promptValidAmount(amount: String) {
        val prompter = EmptyPrompter(amount)
        val app = createApp(prompter)

        val actualAmount = app.promptValidAmount()

        assert(actualAmount in 0.0..110.0)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "test", "1,5", "2 3"])
    fun promptValidAmount_invalidPrimaryAmount_retryUntilValid(amount: String) {
        val expectedAmount = "99.0"
        val prompter = EmptyPrompter(amount, expectedAmount)
        val app = createApp(prompter)

        val actualAmount = app.promptValidAmount()

        assertEquals(expectedAmount.toDouble(), actualAmount)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    @ParameterizedTest
    @ValueSource(strings = ["test comment", " another comment", "food ", " game "])
    fun promptValidComment(expectedComment: String) {
        val prompter = EmptyPrompter(expectedComment)
        val app = createApp(prompter)

        val actualComment = app.promptValidComment()

        assertEquals(expectedComment.trim(), actualComment)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun promptValidComment_invalidPrimaryAmount_retryUntilValid(initialComment: String) {
        val expectedComment = "retried comment"
        val prompter = EmptyPrompter(initialComment, expectedComment)
        val app = createApp(prompter)

        val actualComment = app.promptValidComment()

        assertEquals(expectedComment, actualComment)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    @ParameterizedTest
    @ValueSource(strings = ["topo centras", " kilobaitas", "senukai ", " maxima "])
    fun promptValidVendor(expectedVendor: String) {
        val prompter = EmptyPrompter(expectedVendor)
        val app = createApp(prompter)

        val actualVendor = app.promptValidVendor()

        assertEquals(expectedVendor.trim(), actualVendor)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun promptValidVendor_invalidPrimaryVendor_retryUntilValid(initialVendor: String) {
        val expectedVendor = "varle.lt"
        val prompter = EmptyPrompter(initialVendor, expectedVendor)
        val app = createApp(prompter)

        val actualVendor = app.promptValidVendor()

        assertEquals(expectedVendor, actualVendor)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    @ParameterizedTest
    @ValueSource(strings = [" 1", "13", "24 ", " 27 "])
    fun promptValidDate(expectedDate: String) {
        val prompter = EmptyPrompter(expectedDate)
        val app = createApp(prompter)

        val actualDate = app.promptValidComment()

        assertEquals(expectedDate.trim(), actualDate)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "0", "33", "100"])
    fun promptValidDate_invalidPrimaryDate_retryUntilValid(initialDate: String) {
        val expectedDate = LocalDate.now()
        val prompter = EmptyPrompter(initialDate, expectedDate.dayOfMonth.toString())
        val app = createApp(prompter)

        val actualDate = app.promptValidDate()

        assertEquals(expectedDate, actualDate)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    private fun createApp(prompter: EmptyPrompter) =
        SpendingApp(prompter, consoleUtils, testWriter)

    class EmptyPrompter(
        private val response1: String = "first",
        private val response2: String = "second"
    ) : Prompter {
        var counterToSecondaryResponse = 0
        override fun prompt(message: String): String {
            return if (counterToSecondaryResponse++ == 0) response1 else response2
        }

    }

    class EmptyWriter : Writer {
        override fun writeLine(text: String) {
            TODO("Not yet implemented")
        }
    }
}