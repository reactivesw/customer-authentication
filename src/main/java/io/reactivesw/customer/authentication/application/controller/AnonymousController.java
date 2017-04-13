package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.customer.authentication.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * anonymous controller.
 */
@RestController
public class AnonymousController {

  /**
   * logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(AnonymousController.class);

  /**
   * token update.
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * get anonymous token.
   */
  @GetMapping(value = Router.AUTHENTICATION_ANONYMOUS)
  public String getAnonymousToken() {
    LOG.info("enter.");
    String token = jwtUtil.generateAnonymousToken();

    LOG.info("exit. token: {}.", token);
    return token;
  }


}