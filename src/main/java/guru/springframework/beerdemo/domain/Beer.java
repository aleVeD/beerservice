package guru.springframework.beerdemo.domain;

import guru.springframework.beerdemo.web.model.BeerStyleEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
  private UUID id;

  @Version
  private Long version;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp dateCreated;

  @UpdateTimestamp
  @Column(name = "update")
  private Timestamp lastModifiedDate;

  private String beerName;
  private BeerStyleEnum beerStyleEnum;

  @Column(unique = true)
  private Long upc;
  private BigDecimal price;
  private Integer quantityOnHand;


}
