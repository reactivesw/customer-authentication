package io.reactivesw.customer.authentication.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * app config.
 */
@Configuration
@Data
public class AppConfig {

  /**
   * token expires time.
   */
  @Value("${customer.authentication.expires:7200000}")
  public long expiresIn;
}
