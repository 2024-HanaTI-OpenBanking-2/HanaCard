package card1.card.repository;

import card1.card.entity.CardAuthenticationInformations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAuthenticationInformationRepository extends JpaRepository<CardAuthenticationInformations, String> {
}