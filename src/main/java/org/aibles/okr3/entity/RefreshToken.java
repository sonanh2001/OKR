package org.aibles.okr3.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class RefreshToken {
  @Id private String tokenId;

  @Column(nullable = false)
  private String content;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    RefreshToken that = (RefreshToken) o;
    return tokenId != null && Objects.equals(tokenId, that.tokenId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
