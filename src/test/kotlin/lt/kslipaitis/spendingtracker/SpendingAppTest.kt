package lt.kslipaitis.spendingtracker

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class SpendingAppTest {

    @ParameterizedTest
    @ValueSource(strings = ["gift", "GiFT", "GIFT"])
    fun promptValidCategory_randomCaseValidCategory(expectedCategory: String) {
        val prompter = EmptyPrompter(expectedCategory)
        val app = SpendingApp(prompter)

        val actualCategory = app.promptValidCategory()

        assertEquals(Category.GIFT, actualCategory)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @EnumSource(Category::class)
    fun promptValidCategory_categoryEnum(expectedCategory: Category) {
        val prompter = EmptyPrompter(expectedCategory.name)
        val app = SpendingApp(prompter)

        val actualCategory = app.promptValidCategory()

        assertEquals(expectedCategory, actualCategory)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "GYFT", "random", "test"])
    fun promptValidCategory_invalidCategory_retryUntilValid(category: String) {
        val expectedCategory = Category.OTHER
        val prompter = EmptyPrompter(category, expectedCategory.name)
        val app = SpendingApp(prompter)

        val actualCategory = app.promptValidCategory()

        assertEquals(expectedCategory, actualCategory)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "0.1", "1.23", "105.99"])
    fun promptValidAmount(amount: String) {
        val prompter = EmptyPrompter(amount)
        val app = SpendingApp(prompter)

        val actualAmount = app.promptValidAmount()

        assert(actualAmount in 0.0..110.0)
        assert(prompter.counterToSecondaryResponse == 1)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "test", "1,5", "2 3"])
    fun promptValidAmount_invalidPrimaryAmount_retryUntilValid(amount: String) {
        val expectedAmount = "99.0"
        val prompter = EmptyPrompter(amount, expectedAmount)
        val app = SpendingApp(prompter)

        val actualAmount = app.promptValidAmount()

        assertEquals(expectedAmount.toDouble(), actualAmount)
        assert(prompter.counterToSecondaryResponse == 2)
    }

    class EmptyPrompter(
        private val response1: String = "first",
        private val response2: String = "second"
    ) : Prompter {
        var counterToSecondaryResponse = 0

        override fun prompt(message: String): String {
            return if (counterToSecondaryResponse++ == 0) response1 else response2
        }
    }
}