package com.batch.SpringBatchApplication.services;

import com.batch.SpringBatchApplication.entities.Person;

import java.util.List;

public interface IPersonService {
    void saveAll(List<Person> personList);

    List<Person> getAll();
}
