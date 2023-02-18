package shaart.pstorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import shaart.pstorage.enumeration.EncryptionType;

@Table(schema = "public", name = "usr_passwords")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password implements Serializable {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "alias", nullable = false, unique = true)
  private String alias;

  @Enumerated(EnumType.STRING)
  @Column(name = "encrypt_type", nullable = false, length = 20)
  private EncryptionType encryptionType;

  @Column(name = "encrypted_value", nullable = false, unique = true)
  private String encryptedValue;

  @Column(name = "created_at", columnDefinition = "timestamp default now()")
  private Timestamp createdAt;
}