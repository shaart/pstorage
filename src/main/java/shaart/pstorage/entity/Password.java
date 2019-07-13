package shaart.pstorage.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String alias;

  @Column(nullable = false)
  private String encryptedValue;
}