package se.soprasteria.s2qaportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestRun;

import java.util.List;

public interface TestRepository extends CrudRepository<TestRun,Long> {

    List<TestRun> findAll();

}
