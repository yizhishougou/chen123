package com.example.autoconfig;


import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class) // 在它注册之后
public class SimpleJobAutoConfig {

    // 注入 Spring 上下文
    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private  CoordinatorRegistryCenter zkCenter;

    @PostConstruct
    public void initSimpleJob(){
        // 获取带有  ElasticSimpleJob 注解的 Bean
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticSimpleJob.class);

        for(Map.Entry<String,Object> entry : beans.entrySet()){
            Object instance = entry.getValue();
            // 在判断这个类是否实现 SimpleJob 接口 双保险
            Class<?>[] interfaces = instance.getClass().getInterfaces();

            for (Class<?> supperInterface: interfaces){
                // 获得成功
                if(supperInterface == SimpleJob.class){
                    // 拿到这个注解 获取这个注解里面的值
                    ElasticSimpleJob annotation = instance.getClass().getAnnotation(ElasticSimpleJob.class);
                    String jobName = annotation.jobName();
                    String corn = annotation.corn();
                    int shardingTotalCount = annotation.shardingTotalCount();
                    boolean isOverWrite = annotation.overWrite();

                    // *********** 注册这个定时任务

                    //  配置 作业

                    //  1  job 的核心参数
                    JobCoreConfiguration jcc = JobCoreConfiguration.
                            newBuilder(jobName, corn, shardingTotalCount)
                            .build();

                    // Job类型
                    SimpleJobConfiguration jtc = new SimpleJobConfiguration(jcc, instance.getClass().getCanonicalName());

                    LiteJobConfiguration ljc = LiteJobConfiguration
                            .newBuilder(jtc)
                            .overwrite(isOverWrite).build();

                    new JobScheduler(zkCenter,ljc).init();

                }
            }

        }
    }
}
