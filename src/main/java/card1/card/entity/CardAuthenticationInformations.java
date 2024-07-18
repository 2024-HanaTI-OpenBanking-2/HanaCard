package card1.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "card_auth_informations")
public class CardAuthenticationInformations {

    @Id
    @Column(name = "client_id", length = 100, nullable = false)
    private String clientId;

    @Column(name = "client_secret", length = 100)
    private String clientSecret;

    @Column(name = "client_name", length = 100)
    private String clientName;
}