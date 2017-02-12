package io.reactivesw.customer.authentication.application.model;

import lombok.Data;

/**
 * Created by umasuo on 17/2/10.
 */
@Data
public class SignInResult {

  /**
   * customer for view.
   */
  private CustomerView customerView;

  /**
   * String token.
   */
  private String token;

  public SignInResult(CustomerView customerView, String token) {
    this.customerView = customerView;
    this.token = token;
  }
}
