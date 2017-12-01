package commons.test.dsl

import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.spek.api.dsl.TestBody
import org.jetbrains.spek.api.dsl.TestContainer
import org.jetbrains.spek.api.dsl.it

fun TestContainer.case(description: String, body: suspend TestBody.() -> Unit) {
    it(description) {
        runBlocking {
            body
        }
    }
}