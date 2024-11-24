package edu.icet.demo.model;

import edu.icet.demo.constants.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private Date expirationDate;
    private Boolean revoked = false;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_token"))
    private User user;
}
