package card1.card.repository;

import card1.card.entity.CardMerchants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardMerchantsRepository extends JpaRepository<CardMerchants, String> {
}
