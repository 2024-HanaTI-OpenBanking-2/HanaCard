package card1.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card_products")
public class CardProducts {

    @Id
    @Column(name = "card_product_id", length = 100, nullable = false)
    private String cardProductId;

    @Column(name = "card_name", length = 100, nullable = false)
    private String cardName;

    @Column(name = "annual_fee")
    private Double annualFee;

    @Lob
    @Column(name = "card_image")
    private byte[] cardImage;

    @Column(name = "card_website_link", length = 1000)
    private String cardWebsiteLink;

    @Column(name = "card_description", length = 1000)
    private String cardDescription;
}
