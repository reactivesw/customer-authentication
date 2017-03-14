package io.reactivesw.customer.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by umasuo on 17/2/9.
 */
@SpringBootApplication(scanBasePackages = "io.reactivesw")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
