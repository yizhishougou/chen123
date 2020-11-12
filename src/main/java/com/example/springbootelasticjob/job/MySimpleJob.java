package com.example.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.example.autoconfig.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ElasticSimpleJob(
       jobName = "myjob" ,
        corn = "0/5 * * * * ?",
        shardingTotalCount = 2,
        overWrite = true
)
@Component
public class MySimpleJob implements SimpleJob {


    @Override
    public void execute(ShardingContext shardingContext) {
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        int shardingItem = shardingContext.getShardingItem();
        log.info("我的 " +shardingItem);
        log.info("分片总数: " +shardingTotalCount );


    }
}
