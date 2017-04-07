package io.reactivesw.customer.authentication.infrastructure;

/**
 * customer auth router.
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
   * login.
   */
  public static final String AUTHENTICATION_SIGN_IN_FB = AUTHENTICATION_SIGN_IN +
      "signin/facebook";

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
   * customer id.
   */
  public static final String CUSTOMER_ID = "customerId";

  /**
   * updater.
   */
  public static final String CUSTOMER_WITH_ID = AUTHENTICATION_ROOT + "{" + CUSTOMER_ID + "}";

  /**
   * The constant AUTHENTICATION_HEALTH_CHECK.
   */
  public static final String AUTHENTICATION_HEALTH_CHECK = AUTHENTICATION_ROOT + "health";

}
