package help_system_api.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SimpleController {

    @GetMapping("/")
    fun homePage(): String {
        return "home"
    }
}