package io.reactivesw.customer.authentication.application.service;

import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.authentication.TokenType;
import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.model.maper.CustomerMapper;
import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.domain.service.CustomerService;
import io.reactivesw.customer.authentication.infrastructure.enums.AccountSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sign up service.
 */
@Service
public class SignUpService {

  /**
   * logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(SignUpService.class);

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
   * sign up with email.
   *
   * @param email    email
   * @param password password
   * @return Customer
   */
  public SignInResult signUpWithEmail(String email, String password) {
    LOG.debug("Enter: email: {}", email);

    Customer customer = customerService.createWithEmail(email, password);
    customer.setSource(AccountSource.EMAIL.getValue());

    String token = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId());

    SignInResult signInResult = new SignInResult(CustomerMapper.modelToView(customer), token);

    LOG.debug("Exit: SignInResult: {}", signInResult);
    return signInResult;
  }

  /**
   * set customer service.
   *
   * @param customerService
   */
  protected void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

  /**
   * set jet util.
   *
   * @param jwtUtil
   */
  protected void setJwtUtil(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }
}
