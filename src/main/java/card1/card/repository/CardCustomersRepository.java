package card1.card.repository;


import card1.card.entity.CardCustomerCards;
import card1.card.entity.CardCustomers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardCustomersRepository extends JpaRepository<CardCustomers, String> {
    CardCustomers findByCi(String ci);

}
