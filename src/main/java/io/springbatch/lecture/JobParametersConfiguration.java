package io.springbatch.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobParametersConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        /* contribution 을 이용한 Job 파라미터 정보를 가져오는 방식 - LinkedHashMap 으로 리턴*/
                        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
                        jobParameters.getString("name");
                        jobParameters.getLong("seq");
                        jobParameters.getDate("date");
                        jobParameters.getDouble("age");
                        /* chunkContext 을 이용한 Job 파라미터 정보를 가져오는 방식 - Map 으로 리턴 */
                        Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();

                        System.out.println("setp1 실행");
                        System.out.println("setp1 실행");
                        System.out.println("setp1 실행");
                        return RepeatStatus.FINISHED /* 1번 실행하고 종료*/;
                    }
                })
                .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new CustomTasklet())
                .build();
    }

}
