package card1.card.repository;

import card1.card.entity.CardCustomerApprovals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardCustomerApprovalsRepository extends JpaRepository<CardCustomerApprovals, String> {
}