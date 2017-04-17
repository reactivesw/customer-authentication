package io.reactivesw.customer.authentication.application.model;

import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * sign up model.
 */
public class SignIn implements Serializable {

  /**
   * auto generated serial id.
   */
  private static final long serialVersionUID = 690172155668126335L;

  /**
   * email.
   */
  @Email
  @NotNull
  private String email;

  /**
   * ^                 # start-build-string
   * (?=.*[0-9])       # a digit must occur at least once
   * (?=.*[a-z])       # a lower case letter must occur at least once
   * (?=\S+$)          # no whitespace allowed in the entire string
   * .{8,}             # anything, at least eight places though
   * $                 # end-build-string
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  @NotNull
  private String password;

  /**
   * anonymous id if exist.
   */
  private String anonymousId;

  /**
   * override to string method, do not serialize password.
   *
   * @return
   */
  @Override
  public String toString() {
    return "SignIn{"
        + "email='" + email + '\''
        + '}';
  }

  /**
   * get email.
   *
   * @return
   */
  public String getEmail() {
    return email;
  }

  /**
   * set email.
   *
   * @param email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * get password.
   *
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   * set password.
   *
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * get anonymousId
   *
   * @return String
   */
  public String getAnonymousId() {
    return anonymousId;
  }

  /**
   * set anonymous id.
   *
   * @param anonymousId String
   */
  public void setAnonymousId(String anonymousId) {
    this.anonymousId = anonymousId;
  }
}
