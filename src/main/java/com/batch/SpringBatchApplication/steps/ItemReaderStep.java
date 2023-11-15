package com.batch.SpringBatchApplication.steps;

import com.batch.SpringBatchApplication.entities.Person;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ItemReaderStep implements Tasklet {
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Reader reader = new FileReader(resourceLoader.getResource("classpath:files/destination/persons.csv").getFile());

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .build();

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();

        List<Person> personList = new ArrayList<>();
        String[] actualLine;

        //Tendria que repensar como hacerlo sin saber cantidad de columnas.
        while((actualLine = csvReader.readNext()) != null){
            Person person = new Person();
            person.setName(actualLine[0]);
            person.setLastName(actualLine[1]);
            person.setAge(Integer.parseInt(actualLine[2]));

            personList.add(person);
        }

        csvReader.close();
        reader.close();

        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("personList", personList);

        return RepeatStatus.FINISHED;
    }
}
