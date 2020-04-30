package top.ericson;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@EnableSwagger2
@EnableFeignClients
@MapperScan("top.ericson.mapper")
public class SpringBootRun {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}
}
