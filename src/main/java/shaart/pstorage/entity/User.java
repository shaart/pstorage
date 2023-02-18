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

@Table(schema = "public", name = "usr_users")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "master_password", nullable = false)
  private String masterPassword;

  @Enumerated(EnumType.STRING)
  @Column(name = "encrypt_type", nullable = false, length = 20)
  private EncryptionType encryptionType;

  @ManyToOne(optional = false)
  @JoinColumn(name = "role_id")
  private Role role;

  @Column(name = "created_at", columnDefinition = "timestamp default now()")
  private Timestamp createdAt;
}