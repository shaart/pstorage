package shaart.pstorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shaart.pstorage.enumeration.EncryptionType;

@Table(schema = "public", name = "password")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_id_generator")
  @SequenceGenerator(name = "password_id_generator", sequenceName = "seq_password",
      allocationSize = 1)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "alias", nullable = false, unique = true)
  private String alias;

  @Enumerated(EnumType.STRING)
  @Column(name = "encrypt_type", nullable = false, length = 20)
  private EncryptionType encryptionType;

  @Column(name = "value", nullable = false, unique = true)
  private String value;

  @Column(name = "created_at", columnDefinition = "timestamp default now()")
  private Timestamp createdAt;
}