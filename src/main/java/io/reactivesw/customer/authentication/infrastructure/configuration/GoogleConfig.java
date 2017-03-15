package io.reactivesw.customer.authentication.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * Created by umasuo on 17/3/15.
 */
@Configuration
@Data
public class GoogleConfig implements Serializable{

  /**
   * google client id.
   */
  @Value("${google.client.id:131564184321-8o7d2rtmansr22v7hlubvjkqmqgkd08h.apps.googleusercontent.com}")
  private String googleId;
}
