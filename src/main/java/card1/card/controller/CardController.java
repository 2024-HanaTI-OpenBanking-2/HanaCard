package card1.card.controller;

import card1.card.dto.*;
import card1.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {
    @Autowired
    private final CardService cardService;

    @GetMapping("/card-list")
    public ResponseEntity<List<CustomerCardInfoDTO>> getCardsApi(Model model) {
        String loggedInUserId = "id1";  // 하드코딩된 사용자 ID
        List<CustomerCardInfoDTO> cards = cardService.findCardsByCustomerId(loggedInUserId);
        System.out.println(cards);
        return ResponseEntity.ok(cards);  // JSON 형태로 카드 정보를 반환
    }

    @PostMapping("/card-list-ci") // postman으로 테스트 완료
    private ResponseEntity<List<CustomerCardInfoDTO>> getCardsList(@RequestBody CiDTO ciDTO) {
        List<CustomerCardInfoDTO> cards = cardService.findCardsByCustomerCi(ciDTO.getCi());
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/card-approval-list")
    private ResponseEntity<List<CardCustomerApprovalDTO>> getCardsApprovalList(@RequestBody CardApprovalRequestDTO cardApprovalRequestDTO) {
        List<CardCustomerApprovalDTO> response = cardService.getCardsApprovalList(cardApprovalRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account-list")
    private ResponseEntity<List<AccountInfoResponseDTO>> getAccountList(@RequestParam("customer-card-id") String customerCardId) {
        System.out.println("컨트롤러에서 뽑은 카드 id : " + customerCardId);
        List<AccountInfoResponseDTO> response = cardService.getAccountList(customerCardId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/paymoney-charge")
    public ResponseEntity<PayMoneyChargeResponseDTO> chargePayMoney(
            @RequestBody PayMoneyChargeRequestDTO requestDTO) {
        PayMoneyChargeResponseDTO responseDTO = cardService.getPayMoneyChargeResponse(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

}
