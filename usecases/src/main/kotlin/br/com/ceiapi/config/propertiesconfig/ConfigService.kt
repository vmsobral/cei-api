package br.com.ceiapi.config.propertiesconfig

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Service

@Service
@PropertySource("classpath:/application.properties")
class ConfigService(private val baseConfigService: BaseConfigService) {

    private val logger = LoggerFactory.getLogger(ConfigService::class.java)!!

    fun getString(property: String, default: String) = getProperty(property) ?: default

    fun getRequiredString(property: String) = getRequiredProperty(property)

    fun getInt(property: String, default: Int) = getProperty(property)?.toInt() ?: default

    fun getRequiredInt(property: String) = getRequiredProperty(property).toInt()

    fun getBoolean(property: String, default: Boolean) = getProperty(property)?.toBoolean() ?: default

    fun getRequiredBoolean(property: String) = getRequiredProperty(property).toBoolean()

    private fun getRequiredProperty(property: String): String {
        return getProperty(property) ?: throw IllegalArgumentException("Required configuration property $property not found.")
    }

    private fun getProperty(property: String): String? {
        return baseConfigService.getString(property)
    }

}