package pl.javastart.bet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.app.BookmakerService;
import pl.javastart.match.Match;

import java.util.List;
import java.util.Optional;


@Controller
public class BetController {
    private BookmakerService bookmakerService;

    public BetController(BookmakerService bookmakerService) {
        this.bookmakerService = bookmakerService;
    }

    @GetMapping("/bet/archive")
    public String showAllBets(Model model) {
        List<Bet> bets = bookmakerService.findAll();
        model.addAttribute("bets", bets);

        return "betsArchive";
    }

    @PostMapping("/match/bet")
    public String betMatch(Bet bet, @RequestParam Long matchId) {
        Long betId = bookmakerService.makeBet(bet, matchId);

        if (betId != null) {
            return "redirect:/bet/%s/details".formatted(betId);
        } else {
            return "error";
        }
    }

    @GetMapping("/match/{id}/bet")
    public String showFormToBetMatch(@PathVariable Long id, Model model) {
        Optional<Match> matchById = bookmakerService.findMatchById(id);

        if (matchById.isPresent()) {
            Match match = matchById.get();
            model.addAttribute("match", match);
            model.addAttribute("bet", new Bet());
            return "betMatch";
        }
        return "redirect:/";
    }

    @GetMapping("bet/{id}/details")
    String showBetDetails(@PathVariable Long id, Model model) {
        Optional<Bet> betById = bookmakerService.findBetById(id);

        if (betById.isPresent()) {
            Bet bet = betById.get();
            Long matchId = bet.getMatch().getId();
            Optional<Match> matchById = bookmakerService.findMatchById(matchId);
            model.addAttribute("bet", betById.get());
            model.addAttribute("match", matchById.get());
            return "betDetails";
        }
        return "error";
    }
}
