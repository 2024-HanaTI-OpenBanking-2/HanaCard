package card1.card.repository;


import card1.card.entity.CardProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardProductsRepository extends JpaRepository<CardProducts, String> {
}
