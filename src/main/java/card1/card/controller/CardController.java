package card1.card.controller;

import card1.card.entity.CardCustomerCards;
import card1.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CardController {
    @Autowired
    private final CardService cardService;

//    @GetMapping("/api/cards")
//    public String index(Model model) {
//        // 예제를 위해 하드코딩된 사용자 ID, 실제로는 인증 정보에서 사용자 ID를 가져와야 함
//        String loggedInUserId = "id";
//        List<CardCustomerCards> cards = cardService.findCardsByCustomerId(loggedInUserId);
//        model.addAttribute("cards", cards);
//        return "cardmain";  // 템플릿 파일의 이름을 반환
//    }

    @GetMapping("/api/cards")
    public ResponseEntity<List<CardCustomerCards>> getCardsApi(Model model) {
        String loggedInUserId = "id";  // 하드코딩된 사용자 ID
        List<CardCustomerCards> cards = cardService.findCardsByCustomerId(loggedInUserId);
        return ResponseEntity.ok(cards);  // JSON 형태로 카드 정보를 반환
    }


}
