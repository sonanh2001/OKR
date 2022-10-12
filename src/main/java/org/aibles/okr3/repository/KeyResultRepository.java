package org.aibles.okr3.repository;

import java.util.List;
import java.util.Optional;
import org.aibles.okr3.entity.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyResultRepository extends JpaRepository<KeyResult, Long> {
  void deleteAllByObjectiveId(long id);

  Boolean existsByTitle(String title);

  List<KeyResult> findAllByObjectiveId(long id);

  @Query("select avg(kr.progress) from KeyResult kr where kr.objectiveId = :id")
  Optional<Float> getAvgProgressByObjectiveId(@Param("id") long id);
}
