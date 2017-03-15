package io.reactivesw.customer.authentication.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/3/15.
 */
@Data
@AllArgsConstructor
public class SignInStatus implements Serializable {

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
