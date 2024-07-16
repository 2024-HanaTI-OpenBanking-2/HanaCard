package card1.card.repository;

import card1.card.entity.CardCustomerCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardCustomerCardsRepository extends JpaRepository<CardCustomerCards, String> {
    List<CardCustomerCards> findByCustomerId(String customerId);

}
