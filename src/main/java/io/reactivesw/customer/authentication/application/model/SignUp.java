package io.reactivesw.customer.authentication.application.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by umasuo on 17/2/10.
 * sign up model.
 */
public class SignUp {

  /**
   * email.
   */
  @Email
  @NotNull
  private String email;

  /**
   * password.
   */
  /**
   * ^                 # start-of-string
   * (?=.*[0-9])       # a digit must occur at least once
   * (?=.*[a-z])       # a lower case letter must occur at least once
   * (?=\S+$)          # no whitespace allowed in the entire string
   * .{8,}             # anything, at least eight places though
   * $                 # end-of-string
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  @NotNull
  private String password;

  /**
   * Get email.
   *
   * @return String
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set email.
   *
   * @param email email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Get password.
   *
   * @return String
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set password.
   *
   * @param password String
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * To string.
   *
   * @return
   */
  @Override
  public String toString() {
    return "SignUp{"
        + "email='" + email + '\''
        + '}';
  }
}
