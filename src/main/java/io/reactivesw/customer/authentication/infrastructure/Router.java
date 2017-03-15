package io.reactivesw.customer.authentication.infrastructure;

/**
 * Created by umasuo on 17/2/10.
 */
public class Router {
  /**
   * authentication root.
   */
  public static final String AUTHENTICATION_ROOT = "/";

  /**
   * login.
   */
  public static final String AUTHENTICATION_SIGN_IN = AUTHENTICATION_ROOT + "signin";
  /**
   * login.
   */
  public static final String AUTHENTICATION_SIGN_IN_GOOGLE = AUTHENTICATION_SIGN_IN +
      "signin/google";

  /**
   * logout.
   */
  public static final String AUTHENTICATION_SIGN_OUT = AUTHENTICATION_ROOT + "signout";

  /**
   * sign up.
   */
  public static final String AUTHENTICATION_SIGN_UP = AUTHENTICATION_ROOT + "signup";

  /**
   * sign up.
   */
  public static final String AUTHENTICATION_ANONYMOUS = AUTHENTICATION_ROOT + "anonymous";

  /**
   * sign in status.
   */
  public static final String AUTHENTICATION_SIGN_IN_STATUS = AUTHENTICATION_ROOT + "status";

  /**
   * The constant AUTHENTICATION_HEALTH_CHECK.
   */
  public static final String AUTHENTICATION_HEALTH_CHECK = AUTHENTICATION_ROOT + "health";

}
