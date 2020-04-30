package top.ericson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class EsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsEurekaApplication.class, args);
	}

}
