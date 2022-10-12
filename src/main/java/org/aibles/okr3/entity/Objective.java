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
@Table(name = "objective")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Objective {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long objectiveId;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String reason;

  private String mentor;

  @Column(length = 100)
  private String type;

  @Column(nullable = false)
  private Long startDate;

  @Column(nullable = false)
  private Long dueDate;

  @Column(nullable = false)
  private Long userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Objective objective = (Objective) o;
    return objectiveId != null && Objects.equals(objectiveId, objective.objectiveId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
