package se.soprasteria.s2qaportal.repository;

import org.springframework.data.repository.CrudRepository;
import se.soprasteria.s2qaportal.model.TestCase;
import se.soprasteria.s2qaportal.model.TestRun;

import java.util.List;

public interface ITestRepository extends CrudRepository<TestRun,Long> {

    List<TestRun> findAll();

}
