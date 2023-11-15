package com.batch.SpringBatchApplication.repositories;

import com.batch.SpringBatchApplication.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonRepository extends CrudRepository<Person, Long>{
}
