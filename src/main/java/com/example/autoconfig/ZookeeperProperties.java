package com.example.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

// 编写自动配置  在配置文件中 自动显示  需要  spring-boot-configuration-processor 来更优雅得读取配置文件

@ConfigurationProperties(prefix = "elasticjob.zookeeper")
@Setter
@Getter
public class ZookeeperProperties {
    //zookeeper地址列表
    private String serverList;
    //zookeeper命名空间
    private String namespace;

}
