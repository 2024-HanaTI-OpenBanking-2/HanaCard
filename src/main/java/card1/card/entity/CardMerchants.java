package card1.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card_merchants")
public class CardMerchants {

    @Id
    @Column(name = "merchant_id", length = 20, nullable = false)
    private String merchantId;

    @Column(name = "merchant_name", length = 200, nullable = false)
    private String merchantName;

    @Column(name = "merchant_address", length = 200)
    private String merchantAddress;

    @Column(name = "merchant_phone", length = 20)
    private String merchantPhone;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "category_code", length = 20)
    private String categoryCode;
}
