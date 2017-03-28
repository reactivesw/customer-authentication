package io.reactivesw.customer.authentication.application.model;

/**
 * sign in result.
 */
public class SignInResult {

  /**
   * customer for view.
   */
  private CustomerView customerView;

  /**
   * String token.
   */
  private String token;

  /**
   * sign in result.
   *
   * @param customerView
   * @param token
   */
  public SignInResult(CustomerView customerView, String token) {
    this.customerView = customerView;
    this.token = token;
  }

  /**
   * get customer view.
   *
   * @return
   */
  public CustomerView getCustomerView() {
    return customerView;
  }

  /**
   * set custome view.
   *
   * @param customerView
   */
  public void setCustomerView(CustomerView customerView) {
    this.customerView = customerView;
  }

  /**
   * get token.
   *
   * @return
   */
  public String getToken() {
    return token;
  }

  /**
   * set token.
   *
   * @param token
   */
  public void setToken(String token) {
    this.token = token;
  }
}
