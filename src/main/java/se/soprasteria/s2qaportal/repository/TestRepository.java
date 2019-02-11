package se.soprasteria.s2qaportal.repository;

import org.springframework.data.repository.CrudRepository;
import se.soprasteria.s2qaportal.model.Test;

import java.util.List;

public interface TestRepository extends CrudRepository<Test,Long> {

    List<Test> findAll();

}
