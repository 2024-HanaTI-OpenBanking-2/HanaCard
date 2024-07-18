package card1.card.service;

import card1.card.dto.*;
import card1.card.entity.CardCustomerApprovals;
import card1.card.entity.CardCustomerCards;
import card1.card.entity.CardCustomers;
import card1.card.repository.CardCustomerApprovalsRepository;
import card1.card.repository.CardCustomerCardsRepository;
import card1.card.repository.CardCustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Base64;


@Service
public class CardService {

    @Autowired
    private CardCustomersRepository cardCustomersRepository;

    @Autowired
    private CardCustomerCardsRepository cardCustomerCardsRepository;

    @Autowired
    private CardCustomerApprovalsRepository cardCustomerApprovalsRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openbanking.server.url}")
    private String openbankingServerUrl;


    // 해당 메서드가 DTO 리스트를 반환하도록 변경
    public List<CustomerCardInfoDTO> findCardsByCustomerId(String customerId) {
        // DB에서 엔티티 리스트를 가져온 후, DTO로 변환하여 반환
        return cardCustomerCardsRepository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ci를 기반으로 카드 목록을 가져오는 메서드
    public List<CustomerCardInfoDTO> findCardsByCustomerCi(String ci) {
        CardCustomers customer = cardCustomersRepository.findByCi(ci);
        if (customer != null) {
            List<CardCustomerCards> cards = cardCustomerCardsRepository.findByCustomerId(customer.getCustomerId());
            return cards.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
        return List.of(); // 고객 정보가 없는 경우 빈 리스트 반환
    }

    // CardCustomerCards 엔티티를 CardCustomerCardsDto로 변환하는 헬퍼 메서드
    private CustomerCardInfoDTO convertToDto(CardCustomerCards card) {
        // CardProduct이 null이 아닌 경우에만 접근
        String cardProductId = card.getCardProduct() != null ? card.getCardProduct().getCardProductId() : null;
        byte[] cardImage = card.getCardProduct() != null ? card.getCardProduct().getCardImage() : null;
        String cardName = card.getCardProduct() != null ? card.getCardProduct().getCardName() : null;

        // DTO 객체 생성
        CustomerCardInfoDTO dto = new CustomerCardInfoDTO(
                card.getCustomerCardId(),
                cardProductId,
                card.getCustomerId(),
                card.getExpirationDate(),
                card.getLastMonthPerformance(),
                card.getCustomerPerformanceSegment(),
                card.getCardTypeCode(),
                card.getCardStatusCode(),
                cardImage,
                cardName,
                card.getCardBalance()  // double이 아니라 Double 사용 시 null 처리 필요
        );

        // Base64 인코딩 수행, null 체크 후 진행
        dto.encodeImage();
        return dto;
    }


    public List<CardCustomerApprovalDTO> getCardsApprovalList(CardApprovalRequestDTO cardApprovalRequestDTO) {
        List<CardCustomerApprovals> customerApprovals = cardCustomerApprovalsRepository.findByCustomerCardId(cardApprovalRequestDTO.getCustomerCardId());

        return customerApprovals.stream()
                .map(this::convertToCardCustomerApprovalDTO)
                .collect(Collectors.toList());
    }


    private CardCustomerApprovalDTO convertToCardCustomerApprovalDTO(CardCustomerApprovals approval) {
        return new CardCustomerApprovalDTO(
                approval.getApprovalNumber(),
                approval.getApprovalDate(),
                approval.getApprovalAmount(),
                approval.getMerchantId(),
                approval.getBenefitAmount(),
                approval.getApprovalStatusCode(),
                approval.getPaymentCategory(),
                approval.getCustomerCardId()
        );
    }

    public List<AccountInfoResponseDTO> getAccountList(String customerCardId) {
        CardCustomerCards card = cardCustomerCardsRepository.findByCustomerCardId(customerCardId);
        System.out.println("뽑아낸 card 객체" + card);
        if (card != null) {
            String customerId = card.getCustomerId();
            System.out.println("사용자 id" + customerId);
            CardCustomers customer = cardCustomersRepository.findById(customerId).orElse(null);
             CiDTO ciDTO = new CiDTO(customer.getCi());
            System.out.println("ci" + ciDTO.getCi());
            String url = openbankingServerUrl + "/card/account-list";
             AccountInfoResponseDTO[] result = restTemplate.postForObject(url, ciDTO, AccountInfoResponseDTO[].class);
             return Arrays.asList(result);
        }else{
            return null;
        }
    }

    public PayMoneyChargeResponseDTO getPayMoneyChargeResponse(PayMoneyChargeRequestDTO request) {
        String url = openbankingServerUrl + "/card/paymoney-charge";
        // REST 템플릿을 사용해 외부 API 호출
        PayMoneyChargeResponseDTO response = restTemplate.postForObject(url, request, PayMoneyChargeResponseDTO.class);
        String cardId = "1111-1111-1111-1111"; // 이 부분은 보통 메서드의 파라미터로 받거나 다른 방법으로 결정됩니다.

        // 데이터베이스에서 카드 정보 조회
        CardCustomerCards card = cardCustomerCardsRepository.findByCustomerCardId(cardId);
        if (card != null && response != null) {
            // 충전 금액을 현재 잔액에 추가
            card.addBalance(response.getAmount());
            // 엔티티 상태 업데이트 및 데이터베이스에 반영
            cardCustomerCardsRepository.save(card);
            // 충전 작업의 결과를 반환
            return response;
        } else {
            // 카드 정보나 응답이 유효하지 않은 경우 예외 발생
            throw new RuntimeException("카드 정보를 찾을 수 없거나 충전 응답이 유효하지 않습니다.");
        }
    }



}
