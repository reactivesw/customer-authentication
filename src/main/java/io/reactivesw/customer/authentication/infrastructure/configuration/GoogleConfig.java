package io.reactivesw.customer.authentication.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * google config.
 */
@Configuration
@Data
public class GoogleConfig {

  /**
   * google client id.
   */
  @Value("${google.client.id:131564184321-8o7d2rtmansr22v7hlubvjkqmqgkd08h.apps.googleusercontent" +
      ".com}")
  private String googleId;
}
