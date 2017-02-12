package io.reactivesw.customer.authentication.application.model;

import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Created by umasuo on 17/2/10.
 */
@Data
public class CustomerView {

  /**
   * customer id.
   */
  protected String id;

  /**
   * the time sign up.
   */
  protected ZonedDateTime createdAt;

  /**
   * last modified time.
   */
  protected ZonedDateTime lastModifiedAt;

  /**
   * current version.
   */
  private Integer version;

  /**
   * email if exist.
   */
  private String email;

  /**
   * external id, like facebook id.
   */
  private String externalId;

  /**
   * customer source, like sign up by email, facebook, google.
   */
  private String source;

}
