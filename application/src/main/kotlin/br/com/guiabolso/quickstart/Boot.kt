package br.com.guiabolso.quickstart

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["br.com.guiabolso.quickstart"])
class Boot {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            AnnotationConfigApplicationContext(Boot::class.java)
        }
    }

}