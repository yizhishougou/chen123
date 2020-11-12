package com.example.autoconfig;


import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 编写自动配置
//  https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/html/boot-features-developing-auto-configuration.html

@Configuration // 我是配置类
@ConditionalOnProperty("elasticjob.zookeeper.server-list") // 自动配置的前提条件
@EnableConfigurationProperties(ZookeeperProperties.class) // 配置的过程用到的 配置属性
public class ZookeeperAutoConfig {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    /**
     * zookeeper注册中心
     * @return
     */
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter zkCenter(){
        String serverList = zookeeperProperties.getServerList();
        String namespace = zookeeperProperties.getNamespace();
        ZookeeperConfiguration zc = new ZookeeperConfiguration(serverList,namespace);
        ZookeeperRegistryCenter crc = new ZookeeperRegistryCenter(zc);
        return crc;
    }





}
