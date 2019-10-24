package help_system_api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HelpSystemAPIApplication

fun main(args: Array<String>) {
    SpringApplication.run(HelpSystemAPIApplication::class.java, *args)
}
