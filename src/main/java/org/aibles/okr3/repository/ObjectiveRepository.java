package org.aibles.okr3.repository;

import java.util.List;
import java.util.Optional;
import org.aibles.okr3.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
  Boolean existsByTitle(String title);

  @Query("select o.dueDate from Objective o where o.objectiveId = :id")
  Optional<Long> findDueDateById(long id);

  @Query("select o.startDate from Objective o where o.objectiveId = :id")
  Optional<Long> findStartDateById(long id);

  List<Objective> findAllByUserId(long id);
}
