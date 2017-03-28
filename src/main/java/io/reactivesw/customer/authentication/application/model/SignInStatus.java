package io.reactivesw.customer.authentication.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * sign in status.
 */
@Data
@AllArgsConstructor
public class SignInStatus implements Serializable {

  /**
   * auto generated serial version id.
   */
  private static final long serialVersionUID = -3600640015597918768L;

  /**
   * sign in time.
   */
  private long signInTime;

  /**
   * last visit time.
   */
  private long lastVisitTime;

  /**
   * the sign in status will be expired in `expiresIn`.
   */
  private long expiresIn;

}
