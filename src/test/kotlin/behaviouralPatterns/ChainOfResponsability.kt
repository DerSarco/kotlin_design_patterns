package behaviouralPatterns

import org.assertj.core.api.Assertions
import org.junit.Test

interface HandlerChain {

    fun addHeader(inputHeader: String): String

}

class AuthenticationHeader(val token: String?, var next: HandlerChain? = null): HandlerChain {

    override fun addHeader(inputHeader: String): String = "$inputHeader\nAuthorization: $token"
        .let {
            next?.addHeader(it) ?: it
        }
}

class ContentTypeHeader(val contentType: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String): String  =
        "$inputHeader\nContent-Type: $contentType"
            .let {
                next?.addHeader(it) ?: it
            }
}

class BodyPayloadHeader(val body: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String): String =
        "$inputHeader\n$body"
            .let {
                next?.addHeader(it) ?: it

            }
}


class ChainOfResponsabilityTest {
    @Test
    fun testChain(){
        val authenticationHeader = AuthenticationHeader("123455667")
        val contentTypeHeader = ContentTypeHeader("json")
        val messageBody = BodyPayloadHeader("BODY: {\" USERNAME\" : \"jOHN\"}")


        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = messageBody

        val messageWithAuth = authenticationHeader.addHeader("Headers with authentication")
        println(messageWithAuth)
        println("---------------------------------------")
        val messageWithoutAuth = contentTypeHeader.addHeader("headers without auth")
        println(messageWithoutAuth)

    Assertions.assertThat(messageWithAuth).isEqualTo(
        """
            Headers with authentication
            Authorization: 123455667
            Content-Type: json
            BODY: {" USERNAME" : "jOHN"}
        """.trimIndent()
    )

        Assertions.assertThat(messageWithoutAuth).isEqualTo(
            """
            headers without auth
            Content-Type: json
            BODY: {" USERNAME" : "jOHN"}
        """.trimIndent()
        )

    }
}