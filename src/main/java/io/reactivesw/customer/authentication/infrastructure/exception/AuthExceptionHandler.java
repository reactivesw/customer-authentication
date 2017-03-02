package io.reactivesw.customer.authentication.infrastructure.exception;

import io.reactivesw.customer.authentication.infrastructure.util.JsonUtils;
import io.reactivesw.exception.AlreadyExistException;
import io.reactivesw.exception.NotExistException;
import io.reactivesw.exception.PasswordErrorException;
import io.reactivesw.exception.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by umasuo on 17/3/2.
 */
@Component
public class AuthExceptionHandler implements ExceptionHandler, HandlerExceptionResolver {

  private static Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                       Object handler, Exception ex) {
    setResponse(request, response, handler, ex);
    addExceptionBody(response, ex);
    return new ModelAndView();
  }

  /**
   * add customized message body to the response.
   *
   * @param response
   * @param ex
   */
  private void addExceptionBody(HttpServletResponse response, Exception ex) {
    try {
      ExceptionBody body = getBody(ex);
      response.getWriter().print(JsonUtils.serialize(body));
    } catch (IOException e) {
      logger.error("failed to write response JSON", e);
      throw new IllegalStateException(e);
    }
  }

  /**
   * get customized message body by exception type.
   *
   * @param ex exception.
   * @return exception body.
   */
  private ExceptionBody getBody(Exception ex) {
    ExceptionBody body = null;
    if (ex instanceof NotExistException) {
      body = ExceptionBody.of(ExceptionBody.CUSTOMER_NOT_EXIST_CODE, ExceptionBody
          .CUSTOMER_NOT_EXIST_MESSAGE);
    }
    if (ex instanceof AlreadyExistException) {
      body = ExceptionBody.of(ExceptionBody.CUSTOMER_ALREADY_EXIST_CODE, ExceptionBody
          .CUSTOMER_ALREADY_EXIST_MESSAGE);
    }
    if (ex instanceof PasswordErrorException) {
      body = ExceptionBody.of(ExceptionBody.EMAIL_OR_PASSWORD_ERROR_CODE, ExceptionBody
          .EMAIL_OR_PASSWORD_ERROR_MESSAGE);
    }
    return body;
  }
}
