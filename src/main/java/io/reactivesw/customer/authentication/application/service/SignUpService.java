package io.reactivesw.customer.authentication.application.service;

import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.authentication.TokenType;
import io.reactivesw.customer.authentication.application.model.CustomerView;
import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.model.maper.CustomerMapper;
import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.domain.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/2/10.
 */
@Service
public class SignUpService {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignUpService.class);

  /**
   * customer service.
   */
  @Autowired
  private transient CustomerService customerService;

  /**
   * jwt tool for generate token.
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * signup with email.
   *
   * @param email    email
   * @param password password
   * @return Customer
   */
  public SignInResult signUpWithEmail(String email, String password) {
    LOGGER.debug("enter: email: {}", email);

    Customer customer = customerService.createWithEmail(email, password);

    String token = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId(), 0, new ArrayList<>());

    SignInResult signInResult = new SignInResult(CustomerMapper.modelToView(customer), token);

    LOGGER.debug("exit: signInResult: {}", signInResult);
    return signInResult;
  }

}
