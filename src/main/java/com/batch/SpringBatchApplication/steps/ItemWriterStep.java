package com.batch.SpringBatchApplication.steps;

import com.batch.SpringBatchApplication.entities.Person;
import com.batch.SpringBatchApplication.services.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;

import java.util.List;

@Slf4j
public class ItemWriterStep implements Tasklet {

    @Autowired
    private IPersonService personService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        List<Person> personList = (List<Person>)chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .get("personList");

        //TODO : Validar que personList no venga null

        personList.forEach(person -> {
            if(person != null){
                log.info(person.toString());
            }
        });

        personService.saveAll(personList);


        return RepeatStatus.FINISHED;
    }
}
