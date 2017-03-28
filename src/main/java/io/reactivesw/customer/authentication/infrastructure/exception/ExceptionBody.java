package io.reactivesw.customer.authentication.infrastructure.exception;

/**
 * exception body.
 * return customized code and message to the client.
 */
public class ExceptionBody {

  /**
   * customer not exist code.
   */
  public static final int CUSTOMER_NOT_EXIST_CODE = 10001;
  /**
   * customer not exist message.
   */
  public static final String CUSTOMER_NOT_EXIST_MESSAGE = "customer not exist.";

  /**
   * customer already exist code.
   */
  public static final int CUSTOMER_ALREADY_EXIST_CODE = 10002;
  /**
   * customer already exist code.
   */
  public static final String CUSTOMER_ALREADY_EXIST_MESSAGE = "customer already exist.";


  /**
   * password error code.
   */
  public static final int EMAIL_OR_PASSWORD_ERROR_CODE = 10003;
  /**
   * password error message.
   */
  public static final String EMAIL_OR_PASSWORD_ERROR_MESSAGE = "email or password not correct.";

  /**
   * CODE.
   */
  private int code;

  /**
   * Message
   */
  private String message;

  /**
   * build.
   *
   * @param code
   * @param message
   * @return
   */
  public static ExceptionBody build(int code, String message) {
    ExceptionBody body = new ExceptionBody();
    body.code = code;
    body.message = message;
    return body;
  }

  /**
   * get code.
   *
   * @return
   */
  public int getCode() {
    return code;
  }

  /**
   * set code.
   *
   * @param code
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * get message.
   *
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * set message.
   *
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
