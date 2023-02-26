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