package io.reactivesw.customer.authentication;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by umasuo on 17/2/9.
 */
@SpringBootApplication
public class Application {

  private static final Logger LOGGER = Logger.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
