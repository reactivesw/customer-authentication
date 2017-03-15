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
public class AppConfig implements Serializable{

  @Value("${customer.authentication.expires:7200000}")
  public long expiresIn;
}
