package card1.card.service;

import card1.card.entity.CardCustomerCards;
import card1.card.repository.CardCustomerCardsRepository;
import card1.card.repository.CardCustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardCustomersRepository cardCustomersRepository;

    @Autowired
    private CardCustomerCardsRepository cardCustomerCardsRepository;

    public List<CardCustomerCards> findCardsByCustomerId(String customerId) {
        return cardCustomerCardsRepository.findByCustomerId(customerId);
    }

}
