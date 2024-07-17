package card1.card.service;

import card1.card.dto.CardApprovalRequestDTO;
import card1.card.dto.CardCustomerApprovalDTO;
import card1.card.dto.CustomerCardInfoDTO;
import card1.card.entity.CardCustomerApprovals;
import card1.card.entity.CardCustomerCards;
import card1.card.entity.CardCustomers;
import card1.card.repository.CardCustomerApprovalsRepository;
import card1.card.repository.CardCustomerCardsRepository;
import card1.card.repository.CardCustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardCustomersRepository cardCustomersRepository;

    @Autowired
    private CardCustomerCardsRepository cardCustomerCardsRepository;

    @Autowired
    private CardCustomerApprovalsRepository cardCustomerApprovalsRepository;

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
        return new CustomerCardInfoDTO(
                card.getCustomerCardId(),
                card.getCardProduct() != null ? card.getCardProduct().getCardProductId() : null,
                card.getCustomerId(),
                card.getExpirationDate(),
                card.getLastMonthPerformance(),
                card.getCustomerPerformanceSegment(),
                card.getCardTypeCode(),
                card.getCardStatusCode(),
                card.getCardProduct() != null ? card.getCardProduct().getCardImage() : null,
                card.getCardProduct() != null ? card.getCardProduct().getCardName() : null
        );
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
}
