package br.com.ceiapi.retrieve

import br.com.ceiapi.retrieve.connector.ConnectorFactory
import br.com.ceiapi.retrieve.vo.CEILoginInput
import br.com.ceiapi.retrieve.vo.RequestInput
import br.com.ceiapi.retrieve.vo.RequestOutput
import org.springframework.stereotype.Service
import retrofit2.Response

@Service
class RetrieveService(
    val connectorFactory: ConnectorFactory
) {

    fun retrieve(requestInput: RequestInput): RequestOutput {
        val connectorFactory = connectorFactory.create()

        val homepage = connectorFactory.ceiHomepage().execute()

        val ceiLoginInput = CEILoginInput.parseInputVars(requestInput, homepage.body()?.html())
        val login = connectorFactory.login(ceiLoginInput, refreshCookies(homepage))
        println(login)
        val loginResponse = login.execute()
        println(loginResponse)

        val home = connectorFactory.home(refreshCookies(homepage))
        println(home)
        val homeResponse = home.execute()
        println(homeResponse)

        return RequestOutput("", "")
    }

    private fun <T : Any?> refreshCookies(response: Response<T>): String {
        return response.headers()
            .toMultimap()
            .filter { header -> header.key.equals("set-cookie", true) }
            .map { cookies ->
                cookies.value
                    .map { set_cookie ->
                        set_cookie
                            .split(";")
                            .filterNot { cookie ->
                                cookie.trim().startsWith("path", true) ||
                                        cookie.trim().contains("HTTPOnly", true) ||
                                        cookie.trim().contains("domain", true)
                            }
                    }
            }
            .joinToString(";")
    }
}