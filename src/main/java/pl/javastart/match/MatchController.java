package pl.javastart.match;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.javastart.app.BookmakerService;

import java.util.Optional;


@Controller
public class MatchController {
    private BookmakerService bookmakerService;

    public MatchController(BookmakerService bookmakerService) {
        this.bookmakerService = bookmakerService;
    }

    @GetMapping("/match/add")
    public String showFormToAddNewMatch(Model model) {
        model.addAttribute("match", new Match());
        return "addNewMatch";
    }

    @PostMapping("/match/add")
    public String addNewMatch(Match match) {
        bookmakerService.addNewMatchForBetting(match);
        return "redirect:/";
    }

    @GetMapping("/match/{id}/delete")
    public String deleteMatch(@PathVariable Long id, Model model) {
        Optional<Match> matchById = bookmakerService.findMatchById(id);

        if (matchById.isPresent()) {
            model.addAttribute("match", matchById.get());
            return "confirmDeleteBet";
        }
        return "error";
    }

    @GetMapping("/match/{id}/delete/confirm")
    public String confirmDeletingMatch(@PathVariable Long id) {
        Optional<Match> matchById = bookmakerService.findMatchById(id);

        if (matchById.isPresent()) {
            Match match = matchById.get();
            bookmakerService.deleteMatch(match);
            return "redirect:/";
        }
        return "error";
    }

    @GetMapping("/match/{id}/score")
    public String showFormToAddScoreMatch(@PathVariable Long id, Model model) {
        Optional<Match> matchById = bookmakerService.findMatchById(id);

        if (matchById.isPresent()) {
            model.addAttribute("match", matchById.get());
            return "scoreMatch";
        }
        return "redirect:/";
    }

    @PostMapping("/match/score")
    public String addScoreMatch(@RequestParam String result, @RequestParam Long matchId) {
        bookmakerService.addMatchScore(result, matchId);

        return "redirect:/";
    }
}
