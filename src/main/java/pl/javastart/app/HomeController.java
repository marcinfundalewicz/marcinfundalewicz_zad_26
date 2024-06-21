package pl.javastart.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private BookmakerService bookmakerService;

    public HomeController(BookmakerService bookmakerService) {
        this.bookmakerService = bookmakerService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("topMatches", bookmakerService.findTop3ByBets());
        model.addAttribute("matches", bookmakerService.showMatchesAvailableForBetting());
        return "home";
    }
}
