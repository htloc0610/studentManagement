package vn.student_management.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.transaction.PlatformTransactionManager;
import vn.student_management.student.Student;
import vn.student_management.studentInfor.StudentInfo;
import vn.student_management.user.User;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchConfigTest {

    private BatchConfig batchConfig;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    void setUp() throws Exception {
        entityManager = mock(EntityManager.class);
        entityManagerFactory = mock(EntityManagerFactory.class);
        when(entityManager.getEntityManagerFactory()).thenReturn(entityManagerFactory);

        jobRepository = mock(JobRepository.class);
        transactionManager = mock(PlatformTransactionManager.class);

        batchConfig = new BatchConfig();

        // Inject mock entityManager bằng reflection
        Field emField = BatchConfig.class.getDeclaredField("entityManager");
        emField.setAccessible(true);
        emField.set(batchConfig, entityManager);
    }

    @Test
    void userReader_ShouldReturnNotNull() {
        JpaPagingItemReader<User> reader = batchConfig.userReader();
        assertNotNull(reader);
    }

    @Test
    void userWriter_ShouldReturnNotNull() {
        FlatFileItemWriter<User> writer = batchConfig.userWriter();
        assertNotNull(writer);
    }

    @Test
    void userProcessor_ShouldReturnPassThroughProcessor() throws Exception {
        ItemProcessor<User, User> processor = batchConfig.userProcessor();
        User user = new User();
        assertEquals(user, processor.process(user));
    }

    @Test
    void userStep_ShouldBuildSuccessfully() {
        Step step = batchConfig.userStep(jobRepository, transactionManager);
        assertNotNull(step);
    }

    @Test
    void exportUserJob_ShouldBuildSuccessfully() {
        Step step = batchConfig.userStep(jobRepository, transactionManager);
        Job job = batchConfig.exportUserJob(jobRepository, step);
        assertNotNull(job);
    }

    @Test
    void studentReader_ShouldReturnNotNull() {
        JpaPagingItemReader<Student> reader = batchConfig.studentReader();
        assertNotNull(reader);
    }

    @Test
    void studentWriter_ShouldReturnNotNull() {
        FlatFileItemWriter<Student> writer = batchConfig.studentWriter();
        assertNotNull(writer);
    }

    @Test
    void studentProcessor_ShouldReturnPassThroughProcessor() throws Exception {
        ItemProcessor<Student, Student> processor = batchConfig.studentProcessor();
        Student student = new Student();
        assertEquals(student, processor.process(student));
    }

    @Test
    void studentStep_ShouldBuildSuccessfully() {
        Step step = batchConfig.studentStep(jobRepository, transactionManager);
        assertNotNull(step);
    }

    @Test
    void exportStudentJob_ShouldBuildSuccessfully() {
        Step step = batchConfig.studentStep(jobRepository, transactionManager);
        Job job = batchConfig.exportStudentJob(jobRepository, step);
        assertNotNull(job);
    }

    @Test
    void studentInfoReader_ShouldReturnNotNull() {
        JpaPagingItemReader<StudentInfo> reader = batchConfig.studentInfoReader();
        assertNotNull(reader);
    }

    @Test
    void studentInfoWriter_ShouldReturnNotNull() {
        FlatFileItemWriter<StudentInfo> writer = batchConfig.studentInfoWriter();
        assertNotNull(writer);
    }

    @Test
    void studentInfoProcessor_ShouldReturnPassThroughProcessor() throws Exception {
        ItemProcessor<StudentInfo, StudentInfo> processor = batchConfig.studentInfoProcessor();
        StudentInfo info = new StudentInfo();
        assertEquals(info, processor.process(info));
    }

    @Test
    void studentInfoStep_ShouldBuildSuccessfully() {
        Step step = batchConfig.studentInfoStep(jobRepository, transactionManager);
        assertNotNull(step);
    }

    @Test
    void exportStudentInfoJob_ShouldBuildSuccessfully() {
        Step step = batchConfig.studentInfoStep(jobRepository, transactionManager);
        Job job = batchConfig.exportStudentInfoJob(jobRepository, step);
        assertNotNull(job);
    }
}
