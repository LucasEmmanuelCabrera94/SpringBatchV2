package com.batch.SpringBatchApplication.configurations;

import com.batch.SpringBatchApplication.entities.Person;
import com.batch.SpringBatchApplication.steps.ItemDescompressStep;
import com.batch.SpringBatchApplication.steps.ItemProcessorStep;
import com.batch.SpringBatchApplication.steps.ItemReaderStep;
import com.batch.SpringBatchApplication.steps.ItemWriterStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    @Bean
    @JobScope
    public ItemDescompressStep itemDescompressStep(){
        return new ItemDescompressStep();
    }

    @Bean
    @JobScope
    public ItemReaderStep itemReaderStep(){
        return new ItemReaderStep();
    }

    @Bean
    @JobScope
    public ItemProcessorStep itemProcessorStep(){
        return new ItemProcessorStep();
    }

    @Bean
    @JobScope
    public ItemWriterStep itemWriterStep(){
        return new ItemWriterStep();
    }

    @Bean
    public Step descompressFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("itemDescompressStep", jobRepository)
                .tasklet(itemDescompressStep(),transactionManager)
                .build();
    }
    @Bean
    public Step readFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("itemReaderStep", jobRepository)
                .tasklet(itemReaderStep(),transactionManager)
                .build();
    }
    @Bean
    public Step processDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("itemProcessorStep", jobRepository)
                .tasklet(itemProcessorStep(),transactionManager)
                .build();
    }
    @Bean
    public Step writeFileStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("itemWriterStep", jobRepository)
                .tasklet(itemWriterStep(),transactionManager)
                .build();
    }

    @Bean
    public Job readCSVJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("readCSVJob", jobRepository)
                .start(descompressFileStep(jobRepository, transactionManager))
                .next(readFileStep(jobRepository, transactionManager))
                .next(processDataStep(jobRepository, transactionManager))
                .next(writeFileStep(jobRepository, transactionManager))
                .build();
    }

}
