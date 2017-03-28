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
 * status api.
 */
@RestController
public class StatusController {
  /**
   * LOG.
   */
  private final static Logger LOG = LoggerFactory.getLogger(StatusController.class);

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
    LOG.info("Enter: token: {}", token);

    String customerId = statusService.checkSignInStatus(token);

    LOG.info("Exit: token: {}, customerId: {}", token, customerId);
    return customerId;
  }
}
