package com.batch.SpringBatchApplication.services;

import com.batch.SpringBatchApplication.entities.Person;
import com.batch.SpringBatchApplication.repositories.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {
    @Autowired
    private IPersonRepository personRepository;

    @Override
    @Transactional
    public void saveAll(List<Person> personList) {
        personRepository.saveAll(personList);
    }
}
