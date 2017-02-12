package io.reactivesw.customer.authentication.infrastructure;

/**
 * Created by umasuo on 17/2/10.
 */
public class Router {
  /**
   * authentication root.
   */
  public static final String AUTHENTICATION_ROOT = "/auth";

  /**
   * login.
   */
  public static final String AUTHENTICATION_LOGIN = AUTHENTICATION_ROOT + "/signin";

  /**
   * logout.
   */
  public static final String AUTHENTICATION_LOGOUT = AUTHENTICATION_ROOT + "/signout";

  /**
   * sign up.
   */
  public static final String AUTHENTICATION_SIGN_UP = AUTHENTICATION_ROOT + "/signup";

  /**
   * sign up.
   */
  public static final String AUTHENTICATION_ANONYMOUS = AUTHENTICATION_ROOT + "/anonymous";

}
