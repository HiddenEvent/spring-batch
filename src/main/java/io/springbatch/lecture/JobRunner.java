package io.springbatch.lecture;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements ApplicationRunner {/* 수동으로 Job을 실행 시키기 위해 ApplicationRunner 상속 받음*/
    @Autowired
    private JobLauncher jobLauncher; /* Spring Boot가 기동 되면서 생성되는 인터페이스 이므로 의존성 주입이 가능하다*/

    @Autowired
    private Job job;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user2")
                .toJobParameters(); /* Job 파라미터 객체 생성*/
        jobLauncher.run(job, jobParameters);
    }
}
