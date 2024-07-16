package card1.card.repository;

import card1.card.entity.CardAuthentications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAuthenticationRepository extends JpaRepository<CardAuthentications, String> {
}
