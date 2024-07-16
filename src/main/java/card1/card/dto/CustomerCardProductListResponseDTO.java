package card1.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerCardProductListResponseDTO {
    private String cardId;
    private String cardName;
    private String cardImageUrl;
}
