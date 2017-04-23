package io.reactivesw.customer.authentication.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Event config.
 */
@Configuration
@Data
public class EventConfig {

  /**
   * Google cloud project id.
   */
  @Value("${io.reactivesw.message.google.project.id}")
  private String googleCloudProjectId;

  /**
   * Sign in topic name.
   */
  @Value("${io.reactivesw.message.topic.signin.name}")
  private String signInTopicName;

  /**
   * Sign in topic message version.
   */
  @Value("${io.reactivesw.message.topic.signin.version}")
  private Integer signInTopicVersion;
}
