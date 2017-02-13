package io.reactivesw.customer.authentication.application.model;

import lombok.Data;

/**
 * Created by umasuo on 17/2/10.
 * sign up model.
 */
@Data
public class SignIn {

  /**
   * email.
   */
  private String email;

  /**
   * password.
   */
  private String password;
}
