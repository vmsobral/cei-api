package br.com.ceiapi.retrieve.parser

import org.apache.commons.lang3.StringUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.HashMap

object HTMLParser {
    fun extractActionFromFirstForm(html: String?): String {
        if (html == null) {
            throw IllegalArgumentException("html can't be null on HtmlParser.extractInputHiddenFromFirstForm()")
        }

        val firstForm = getFirstFormFrom(html) ?: return ""

        return firstForm.attr("action")
    }

    fun extractInputHiddenFromFirstForm(html: String?): Map<String, String> {
        if (html == null) {
            throw IllegalArgumentException("html can't be null on HtmlParser.extractInputHiddenFromFirstForm()")
        }

        val select = getFirstFormFrom(html)!!.select("input[type=\"hidden\"]")
        val inputs = HashMap<String, String>()
        for (element in select) {
            inputs[element.attr("name")] = element.attr("value")
        }

        return inputs
    }

    private fun getFirstFormFrom(html: String): Element? {
        val parse = Jsoup.parse(html)
        val select = parse.select("form")
        return select[0]
    }

    fun extractActionFromForm(html: String?, formId: String?): String {
        if (html == null) {
            throw IllegalArgumentException("html can't be null on HtmlParser.extractActionFromForm()")
        }

        if (formId == null) {
            throw IllegalArgumentException("formId can't be null on HtmlParser.extractActionFromForm()")
        }

        val parse = Jsoup.parse(html)
        val form =
            parse.getElementById(formId) ?: throw IllegalStateException("form with id $formId couldn\'t be found")

        return form.attr("action")
    }

    fun extractInputFromNamedForm(html: String?, formName: String?): Map<String, String> {
        if (html == null) {
            throw IllegalArgumentException("html can't be null on HtmlParser.extractInputFromForm()")
        }

        if (formName == null) {
            throw IllegalArgumentException("formName can't be null on HtmlParser.extractInputFromForm()")
        }

        val parse = Jsoup.parse(html)
        val select = parse.select("form[name=\"$formName\"] input")
        val inputs = HashMap<String, String>()
        for (element in select) {
            if (element.attr("name") == null || StringUtils.isBlank(element.attr("name"))) {
                continue
            }

            if ("radio" == element.attr("type") && "checked" != element.attr("checked")) {
                continue
            }

            inputs[element.attr("name")] = element.attr("value")
        }

        return inputs
    }

    fun extractInputFromFormWithId(html: String?, formId: String?): Map<String, String> {
        if (html == null) {
            throw IllegalArgumentException("html can't be null on HtmlParser.extractInputFromFormWithId()")
        }

        if (formId == null) {
            throw IllegalArgumentException("formId can't be null on HtmlParser.extractInputFromFormWithId()")
        }

        val parse = Jsoup.parse(html)
        val select = parse.select("#$formId input")
        val inputs = HashMap<String, String>()
        for (element in select) {
            if ("radio" == element.attr("type") && "checked" != element.attr("checked")) {
                continue
            }

            if ("submit" == element.attr("type")) {
                continue
            }

            inputs[element.attr("name")] = element.attr("value")
        }

        return inputs
    }

}