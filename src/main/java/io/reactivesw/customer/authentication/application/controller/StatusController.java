package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.customer.authentication.application.service.StatusService;
import io.reactivesw.customer.authentication.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/3/7.
 */
@RestController
public class StatusController {
  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(StatusController.class);

  /**
   * status service.
   */
  @Autowired
  private transient StatusService statusService;

  /**
   * get sign in status.
   * used by other services.
   *
   * @param token
   * @return String customer id
   */
  @GetMapping(value = Router.AUTHENTICATION_SIGN_IN_STATUS)
  public String getSignInStatus(@RequestParam @Valid @NotNull String token) {
    logger.info("Enter: token: {}", token);

    String customerId = statusService.checkSignInStatus(token);

    logger.info("Exit: token: {}, customerId: {}", token, customerId);
    return customerId;
  }
}
