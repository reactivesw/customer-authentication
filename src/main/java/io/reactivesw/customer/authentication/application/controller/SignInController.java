package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.customer.authentication.application.model.SignIn;
import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.service.SignInService;
import io.reactivesw.customer.authentication.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by umasuo on 17/2/13.
 */
@RestController
public class SignInController {

  /**
   * logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(SignInController.class);

  /**
   * sign in service.
   */
  private transient SignInService signInService;


  /**
   * sign in with email.
   *
   * @param signIn SignIn
   * @return SignInResult
   */
  @PostMapping(value = Router.AUTHENTICATION_SIGN_IN, params = "email")
  public SignInResult loginWithEmail(@RequestBody SignIn signIn) {
    LOG.info("enter: email:", signIn.getEmail());

    SignInResult result = signInService.signInWithEmail(signIn.getEmail(), signIn.getPassword());

    LOG.info("exit: loginResult:", result);
    return result;
  }

  /**
   * login with google.
   *
   * @param gToken String
   * @return LoginResult
   */
  @PostMapping(value = Router.AUTHENTICATION_SIGN_IN, params = "gToken")
  public SignInResult loginWithGoogle(@RequestBody String gToken) throws GeneralSecurityException,
      IOException {
    LOG.info("enter: gToken: {}", gToken);

    SignInResult result = signInService.signInWithGoogle(gToken);

    LOG.info("exit: customer:");
    return result;
  }
}
