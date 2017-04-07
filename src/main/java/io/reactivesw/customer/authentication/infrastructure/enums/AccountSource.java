package io.reactivesw.customer.authentication.infrastructure.enums;

/**
 * account source.
 */
public enum AccountSource {
  /**
   * sign up with email.
   */
  EMAIL("email"),

  /**
   * sign up with google.
   */
  GOOGLE("google"),

  /**
   * sign up with facebook.
   */
  FACEBOOK("facebook");

  /**
   * source.
   */
  private String source;

  AccountSource(String source) {
    this.source = source;
  }

  /**
   * get source.
   *
   * @return String
   */
  public String getValue() {
    return source;
  }
}
