package shaart.pstorage.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(schema = "public", name = "password")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "alias", nullable = false, unique = true)
  private String alias;

  @Column(name = "value", nullable = false, unique = true)
  private String value;

  @Column(name = "created_at", columnDefinition = "timestamp default now()")
  private Timestamp createdAt;
}