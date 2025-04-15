package vn.student_management.runner;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupJobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job exportUserJob;

    public StartupJobRunner(JobLauncher jobLauncher, Job exportUserJob) {
        this.jobLauncher = jobLauncher;
        this.exportUserJob = exportUserJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Tạo tham số cho job với giá trị thời gian hiện tại, nhằm đảm bảo mỗi lần chạy là duy nhất
        JobParameters params = new JobParametersBuilder()
                .addLong("JobID", System.currentTimeMillis())
                .toJobParameters();

        // Khởi chạy job exportUserJob với các tham số đã tạo
        jobLauncher.run(exportUserJob, params);
    }
}