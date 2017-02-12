package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.model.SignUp;
import io.reactivesw.customer.authentication.infrastructure.Router;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
   * generate the token.
   *
   * @param signUp SignUp model
   * @return
   */
  @ApiOperation("sign up with email & password")
  @PostMapping(Router.AUTHENTICATION_SIGN_UP)
  public SignInResult signUpWithEmail(@RequestBody SignUp signUp) {
    LOG.info("enter: email:", signUp.getEmail());

//    LoginResult result = signupApplication.signupWithEmail(emailModel.getEmail(), emailModel
//        .getPassword());

//    LOG.info("exit: loginResult:", result);
    return null;
  }

}
