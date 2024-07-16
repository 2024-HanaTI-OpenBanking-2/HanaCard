package card1.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "card_customer_approvals")
public class CardCustomerApprovals {

    @Id
    @Column(name = "approval_number", length = 50, nullable = false)
    private String approvalNumber;

    @Column(name = "approval_date")
    private Instant approvalDate;

    @Column(name = "approval_amount")
    private Double approvalAmount;

    @Column(name = "merchant_id", length = 20, nullable = false)
    private String merchantId;

    @Column(name = "benefit_amount")
    private Double benefitAmount;

    @Column(name = "approval_status_code", length = 20)
    private String approvalStatusCode;

    @Column(name = "payment_category", length = 20)
    private String paymentCategory;

    @Column(name = "customer_card_id", length = 20, nullable = false)
    private String customerCardId;
}