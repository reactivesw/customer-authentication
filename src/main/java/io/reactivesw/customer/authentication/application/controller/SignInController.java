package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.customer.authentication.application.model.FbSignInRequest;
import io.reactivesw.customer.authentication.application.model.SignIn;
import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.service.SignInService;
import io.reactivesw.customer.authentication.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * sign in controller.
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
  @Autowired
  private transient SignInService signInService;


  /**
   * sign in with email.
   *
   * @param signIn SignIn
   * @return SignInResult
   */
  @PostMapping(value = Router.AUTHENTICATION_SIGN_IN)
  public SignInResult signInWithEmail(@RequestBody @Valid SignIn signIn) {
    LOG.info("enter. signIn: {}", signIn);

    SignInResult result = signInService.signInWithEmail(signIn.getEmail(), signIn.getPassword());

    LOG.info("exit: SignInResult: {}", result);
    return result;
  }

  /**
   * login with google.
   *
   * @param gToken String
   * @return LoginResult
   */
  @PostMapping(value = Router.AUTHENTICATION_SIGN_IN_GOOGLE)
  public SignInResult signInWithGoogle(@RequestBody @Valid @NotNull String gToken)
      throws GeneralSecurityException, IOException {
    LOG.info("enter. gToken: {}", gToken);

    SignInResult result = signInService.signInWithGoogle(gToken);

    LOG.info("exit. SignInResult: {}", result);
    return result;
  }

  /**
   * login with facebook.
   *
   * @param request FbSignInRequest
   * @return LoginResult
   */
  @PostMapping(value = Router.AUTHENTICATION_SIGN_IN_FB)
  public SignInResult signInWithFB(@RequestBody @Valid @NotNull FbSignInRequest request)
      throws GeneralSecurityException, IOException {
    LOG.info("enter. fbToken: {}", request);

    SignInResult result = signInService.signInWithFacebook(request);

    LOG.info("exit. SignInResult: {}", result);
    return result;
  }

}
