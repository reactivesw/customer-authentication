package io.reactivesw.customer.authentication.application.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.authentication.TokenType;
import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.model.maper.CustomerMapper;
import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.domain.service.CustomerService;
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil;
import io.reactivesw.exception.ParametersException;
import io.reactivesw.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by umasuo on 17/2/10.
 */
@Service
public class SignInService {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInService.class);

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;


  /**
   * HTTP TRANSPORT.
   */
  private transient final HttpTransport TRANSPORT = new NetHttpTransport();

  /**
   * Default JSON factory to use to deserialize JSON.
   */
  private transient final JacksonFactory JSON_FACTORY = new JacksonFactory();

  /**
   * google token verifier.
   */
  private transient GoogleIdTokenVerifier verifier;

  /**
   * customer service.
   */
  @Autowired
  private transient CustomerService customerService;

  /**
   * default config.
   */
  public SignInService() {
    this.verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
        //TODO use config
        .setAudience(Collections.singletonList("131564184321-8o7d2rtmansr22v7hlubvjkqmqgkd08h" +
            ".apps.googleusercontent.com"))
        .build();
  }


  /**
   * login with email and password.
   *
   * @param email    String
   * @param password String
   * @return LoginResult
   */
  public SignInResult signInWithEmail(String email, String password) {
    LOGGER.debug("enter: email: {}", email);
    Customer customer = customerService.getByEmail(email);

    Boolean pwdResult = PasswordUtil.checkPassword(password, customer.getPassword());
    if (!pwdResult) {
      throw new PasswordErrorException("password or email not correct.");
    }

    String token = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId(), 0, "");

    SignInResult result = new SignInResult(CustomerMapper.modelToView(customer), token);

    return result;
  }

  /**
   * login with google token.
   *
   * @param gToken String
   * @return LoginResult
   */
  public SignInResult signInWithGoogle(String gToken) throws GeneralSecurityException,
      IOException {
    LOGGER.debug("enter: gToken: {}", gToken);
    GoogleIdToken token = verifyToken(gToken);
    String googleId = token.getPayload().getSubject();
    Customer customer = customerService.getByExternalId(googleId);
    if (customer == null) {
      customer = createWithGooglePayload(token.getPayload());
    }

    String customerToken = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId(), 0, "");

    SignInResult signInResult = new SignInResult(CustomerMapper.modelToView(customer),
        customerToken);

    LOGGER.debug("exit: customer: {}", customer);
    return signInResult;
  }

  /**
   * verify google token.
   *
   * @param token String
   * @return GoogleIdToken
   * @throws GeneralSecurityException
   * @throws IOException
   */
  private GoogleIdToken verifyToken(String token) throws GeneralSecurityException, IOException {
    LOGGER.debug("enter: gToken: {}", token);

    GoogleIdToken idToken = verifier.verify(token);

    if (idToken == null) {
      LOGGER.debug(" google token verify failed: gToken: {}", token);
      throw new ParametersException("Google's id token is not correct.");
    }

    LOGGER.debug("exist: googleIdToken: {}", idToken);
    return idToken;
  }

  /**
   * create new customer with google payload.
   *
   * @param payload GoogleIdToken.Payload
   * @return CustomerEntity
   */
  private Customer createWithGooglePayload(GoogleIdToken.Payload payload) {
    Customer customer = new Customer();
    String id = payload.getSubject();

    //TODO should we save the email?
    customer.setExternalId(id);

    LOGGER.debug("create new customer with external info. customerEntity: {}", customer);
    return customerService.createWithSample(customer);
  }
}
