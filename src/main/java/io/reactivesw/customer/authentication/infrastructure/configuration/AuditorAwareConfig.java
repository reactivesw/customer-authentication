package io.reactivesw.customer.authentication.infrastructure.configuration;

import io.reactivesw.customer.authentication.infrastructure.util.ZonedDateTimeAuditorAware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by Davis on 16/11/28.
 */
@Configuration
@EnableJpaAuditing
public class AuditorAwareConfig {
  /**
   * Auditor provider zoned date time auditor aware.
   *
   * @return the zoned date time auditor aware
   */
  @Bean
  public ZonedDateTimeAuditorAware auditorProvider() {
    return new ZonedDateTimeAuditorAware();
  }
}