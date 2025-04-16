package vn.student_management.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.*;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import vn.student_management.student.Student;
import vn.student_management.studentInfor.StudentInfo;
import vn.student_management.user.User;

@Configuration
public class BatchConfig {

    @PersistenceContext
    private EntityManager entityManager;

    // Định nghĩa writer cho user
    @Bean
    public FlatFileItemWriter<User> userWriter() {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output/users.csv"));

        DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<User> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"userId", "userName", "password", "createdAt", "updatedAt"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    // Định nghĩa reader cho user
    @Bean
    public JpaPagingItemReader<User> userReader() {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT u FROM User u");
        reader.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        reader.setPageSize(100);
        return reader;
    }

    // Xử lý dữ liệu cho user
    @Bean
    public ItemProcessor<User, User> userProcessor() {
        return user -> user;
    }

    // Định nghĩa step cho job export user
    @Bean
    public Step userStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("userStep", jobRepository)
                .<User, User>chunk(100, transactionManager)
                .reader(userReader())
                .processor(userProcessor())
                .writer(userWriter())
                .build();
    }

    // Định nghĩa job export user
    @Bean
    public Job exportUserJob(JobRepository jobRepository, Step userStep) {
        return new JobBuilder("exportUserJob", jobRepository)
                .start(userStep)
                .build();
    }

    // Định nghĩa writer cho studentInfo
    @Bean
    public FlatFileItemWriter<StudentInfo> studentInfoWriter() {
        FlatFileItemWriter<StudentInfo> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output/student_info.csv"));

        DelimitedLineAggregator<StudentInfo> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<StudentInfo> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"infoId", "address", "averageScore", "dateOfBirth", "createdAt", "updatedAt"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    // Định nghĩa reader cho studentInfo
    @Bean
    public JpaPagingItemReader<StudentInfo> studentInfoReader() {
        JpaPagingItemReader<StudentInfo> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT si FROM StudentInfo si");
        reader.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        reader.setPageSize(100);
        return reader;
    }

    // Xử lý dữ liệu cho studentInfo
    @Bean
    public ItemProcessor<StudentInfo, StudentInfo> studentInfoProcessor() {
        return studentInfo -> studentInfo;
    }

    // Định nghĩa step cho job export studentInfo
    @Bean
    public Step studentInfoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("studentInfoStep", jobRepository)
                .<StudentInfo, StudentInfo>chunk(100, transactionManager)
                .reader(studentInfoReader())
                .processor(studentInfoProcessor())
                .writer(studentInfoWriter())
                .build();
    }

    // Định nghĩa job export studentInfo
    @Bean
    public Job exportStudentInfoJob(JobRepository jobRepository, Step studentInfoStep) {
        return new JobBuilder("exportStudentInfoJob", jobRepository)
                .start(studentInfoStep)
                .build();
    }

    // Định nghĩa writer cho student
    @Bean
    public FlatFileItemWriter<Student> studentWriter() {
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output/students.csv"));

        DelimitedLineAggregator<Student> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<Student> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"studentId", "studentName", "studentCode", "createdAt", "updatedAt"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    // Định nghĩa reader cho student
    @Bean
    public JpaPagingItemReader<Student> studentReader() {
        JpaPagingItemReader<Student> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT s FROM Student s");
        reader.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        reader.setPageSize(100);
        return reader;
    }

    // Xử lý dữ liệu cho student
    @Bean
    public ItemProcessor<Student, Student> studentProcessor() {
        return student -> student;
    }

    // Định nghĩa step cho job export student
    @Bean
    public Step studentStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(100, transactionManager)
                .reader(studentReader())
                .processor(studentProcessor())
                .writer(studentWriter())
                .build();
    }

    // Định nghĩa job export student
    @Bean
    public Job exportStudentJob(JobRepository jobRepository, Step studentStep) {
        return new JobBuilder("exportStudentJob", jobRepository)
                .start(studentStep)
                .build();
    }

    // Platform transaction manager
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }
}

