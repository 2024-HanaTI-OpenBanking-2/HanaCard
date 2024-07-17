package card1.card.dto;

import card1.card.entity.CardCustomerCards;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCardInfoDTO {
    private String customerCardId;
    private String cardProductId;
    private String customerId;
    private Date expirationDate;
    private Double lastMonthPerformance;
    private String customerPerformanceSegment;
    private String cardTypeCode;
    private String cardStatusCode;
    private byte[] cardImageUrl;
    private String cardName;
    private String base64EncodedImage;
    private Double cardBalance; // 추가됨

    public CustomerCardInfoDTO(String customerCardId, String cardProductId, String customerId, Date expirationDate, Double lastMonthPerformance, String customerPerformanceSegment, String cardTypeCode, String cardStatusCode, byte[] cardImageUrl, String cardName, Double cardBalance) {
        this.customerCardId = customerCardId;
        this.cardProductId = cardProductId;
        this.customerId = customerId;
        this.expirationDate = expirationDate;
        this.lastMonthPerformance = lastMonthPerformance;
        this.customerPerformanceSegment = customerPerformanceSegment;
        this.cardTypeCode = cardTypeCode;
        this.cardStatusCode = cardStatusCode;
        this.cardImageUrl = cardImageUrl;
        this.cardName = cardName;
        this.cardBalance = cardBalance;
    }

    // 이미지를 Base64로 인코딩하는 메소드
    public void encodeImage() {
        if (this.cardImageUrl != null) {
            this.base64EncodedImage = Base64.getEncoder().encodeToString(this.cardImageUrl);
        }
    }
}


