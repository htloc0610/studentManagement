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
    private final Job exportStudentJob;
    private final Job exportStudentInfoJob;

    public StartupJobRunner(JobLauncher jobLauncher, Job exportUserJob, Job exportStudentJob, Job exportStudentInfoJob) {
        this.jobLauncher = jobLauncher;
        this.exportUserJob = exportUserJob;
        this.exportStudentJob = exportStudentJob;
        this.exportStudentInfoJob = exportStudentInfoJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Tạo tham số cho job với giá trị thời gian hiện tại, nhằm đảm bảo mỗi lần chạy là duy nhất
        JobParameters params = new JobParametersBuilder()
                .addLong("JobID", System.currentTimeMillis())
                .toJobParameters();

        // Khởi chạy các job
        jobLauncher.run(exportUserJob, params);
        jobLauncher.run(exportStudentJob, params);
        jobLauncher.run(exportStudentInfoJob, params);
    }
}