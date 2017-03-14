package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.model.SignUp;
import io.reactivesw.customer.authentication.application.service.SignUpService;
import io.reactivesw.customer.authentication.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/2/10.
 */
@RestController
public class SignUpController {

  /**
   * logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(SignUpController.class);

  /**
   * sign up service.
   */
  @Autowired
  private transient SignUpService signUpService;

  /**
   * generate the token.
   *
   * @param signUp SignUp model
   * @return
   */
  @PostMapping(Router.AUTHENTICATION_SIGN_UP)
  public SignInResult signUpWithEmail(@RequestBody SignUp signUp) {
    LOG.info("enter: email:", signUp.getEmail());

    SignInResult result = signUpService.signUpWithEmail(signUp.getEmail(), signUp.getPassword());

    LOG.info("exit: signInResult:", result);
    return result;
  }

}
