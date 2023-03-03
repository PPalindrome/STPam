package com.wfback;

import com.wfback.config.LoadBalancerConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@LoadBalancerClient(name = "wps-service", configuration = LoadBalancerConfig.class)
@SpringBootApplication
@MapperScan("com.wfback.mapper")
@EnableDiscoveryClient
@EnableDubbo
public class WfBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(WfBackApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
