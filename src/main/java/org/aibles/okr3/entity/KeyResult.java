package org.aibles.okr3.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "key_result")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class KeyResult {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long keyResultId;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String step;

  private String mentor;

  @Column(nullable = false)
  private Float progress;

  @Column(nullable = false)
  private Long dueDate;

  @Column(nullable = false)
  private Long startDate;

  @Column(nullable = false)
  private Long objectiveId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    KeyResult keyResult = (KeyResult) o;
    return keyResultId != null && Objects.equals(keyResultId, keyResult.keyResultId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
