package com.Urban_India.config;

import com.Urban_India.batch.CustomerProcessor;
import com.Urban_India.batch.model.Customer;
import com.Urban_India.batch.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    @Autowired
    private CustomerProcessor customerProcessor;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Partitioner partitioner;

    @Autowired
    private ItemWriter itemWriter;

    @Autowired
    private SkipPolicy skipPolicy;
    @Autowired
    private SkipListener skipListener;
    @Bean
    public Job runJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("importCustomers",jobRepository)
                .flow(masterStep(jobRepository,transactionManager)).end().build();
    }

    @Bean
    public Step slaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("slaveStep",jobRepository).
                <Customer, Customer>chunk(250,transactionManager)
                .reader(reader())
                .processor(customerProcessor)
                .writer(itemWriter)
                .faultTolerant()
                //.skipLimit(1000)
               // .skip(NumberFormatException.class)
                .listener(skipListener)
                .skipPolicy(skipPolicy)
                .noSkip(IllegalArgumentException.class)
                .build();
    }

    @Bean
    public Step masterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("masterSTep",jobRepository).
                partitioner(slaveStep(jobRepository,transactionManager).getName(), partitioner)
                .partitionHandler(partitionHandler(jobRepository,transactionManager))
                .build();
    }

    @Bean
    public PartitionHandler partitionHandler(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(10);
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
        taskExecutorPartitionHandler.setStep(slaveStep(jobRepository,transactionManager));
        return taskExecutorPartitionHandler;
    }

    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        return taskExecutor;
    }
}
