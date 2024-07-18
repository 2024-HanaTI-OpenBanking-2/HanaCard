package card1.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "card_customers")
public class CardCustomers {

    @Id
    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(name = "customer_password", length = 256, nullable = false)
    private String customerPassword;

    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(name = "customer_phone_number", length = 15)
    private String customerPhoneNumber;

    @Column(name = "customer_annual_income")
    private Double customerAnnualIncome;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "customer_ssn", length = 15, nullable = false)
    private String customerSsn;

    @Column(name = "customer_status_code", length = 20)
    private String customerStatusCode;

    @Column(name = "customer_gender_code", length = 20)
    private String customerGenderCode;

    @Column(name = "ci", length = 200)
    private String ci;
}
