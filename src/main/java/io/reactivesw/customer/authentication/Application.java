package io.reactivesw.customer.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * application stater.
 */
@SpringBootApplication(scanBasePackages = "io.reactivesw")
public class Application {

  /**
   * main function.
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
