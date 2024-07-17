package card1.card.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "card_customer_cards")
public class CardCustomerCards {

    @Id
    @Column(name = "customer_card_id", length = 20, nullable = false)
    private String customerCardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_product_id", referencedColumnName = "card_product_id")
    private CardProducts cardProduct;  // 이제 CardProducts 엔티티에 직접 접근

    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "last_month_performance")
    private Double lastMonthPerformance;

    @Column(name = "customer_performance_segment", length = 18)
    private String customerPerformanceSegment;

    @Column(name = "card_type_code", length = 20)
    private String cardTypeCode;

    @Column(name = "card_status_code", length = 20)
    private String cardStatusCode;



    // CardProducts의 cardImageUrl에 접근하기 위한 편의 메소드
    public byte[] getCardImageUrl() {
        return cardProduct != null ? cardProduct.getCardImage() : null;
    }

    public String getCardName(){
        return cardProduct != null ? cardProduct.getCardName() : null;
    }
}
