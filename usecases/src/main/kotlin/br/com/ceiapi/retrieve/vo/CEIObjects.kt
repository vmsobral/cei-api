package br.com.ceiapi.retrieve.vo

import br.com.ceiapi.retrieve.parser.HTMLParser

object CEILoginInput {

    private const val EVENT_TARGET = "__EVENTTARGET"
    private const val EVENT_ARGUMENT = "__EVENTARGUMENT"
    private const val VIEW_STATE = "__VIEWSTATE"
    private const val VIEW_STATE_GENERATOR = "__VIEWSTATEGENERATOR"
    private const val EVENT_VALIDATION = "__EVENTVALIDATION"

    fun parseInputVars(requestInput: RequestInput, loginHTML: String?): Map<String, String> {
        val hiddenInputs = HTMLParser.extractInputHiddenFromFirstForm(loginHTML)

        return mutableMapOf<String, String>().also {
            it["ctl00\$ContentPlaceHolder1\$txtLogin"] = requestInput.cpf
            it["ctl00\$ContentPlaceHolder1\$txtSenha"] = requestInput.password
            it["ctl00\$ContentPlaceHolder1\$smLoad"] =
                "ctl00\$ContentPlaceHolder1\$UpdatePanel1|ctl00\$ContentPlaceHolder1\$btnLogar"
            it["__ASYNCPOST"] = "true"
            it["ctl00\$ContentPlaceHolder1\$btnLogar"] = "Entrar"

            it[EVENT_TARGET] = hiddenInputs[EVENT_TARGET] ?: ""
            it[EVENT_ARGUMENT] = hiddenInputs[EVENT_ARGUMENT] ?: ""
            it[VIEW_STATE] = hiddenInputs[VIEW_STATE] ?: ""
            it[VIEW_STATE_GENERATOR] = hiddenInputs[VIEW_STATE_GENERATOR] ?: ""
            it[EVENT_VALIDATION] = hiddenInputs[EVENT_VALIDATION] ?: ""
        }
    }
}