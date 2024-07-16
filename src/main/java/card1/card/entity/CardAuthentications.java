package card1.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "card_authentications")
public class CardAuthentications {

    @Id
    @Column(name = "access_token_id", length = 255, nullable = false)
    private String accessTokenId;

    @Column(name = "expires_in")
    private Integer expiresIn;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "auth_scope", length = 255)
    private String authScope;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "client_use_code", length = 255)
    private String clientUseCode;
}