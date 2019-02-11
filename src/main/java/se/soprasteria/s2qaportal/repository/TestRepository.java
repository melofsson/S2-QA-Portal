package se.soprasteria.s2qaportal.repository;

import org.springframework.data.repository.CrudRepository;
import se.soprasteria.s2qaportal.model.TestCase;

import java.util.List;

public interface TestRepository extends CrudRepository<TestCase,Long> {

    List<TestCase> findAll();

}
