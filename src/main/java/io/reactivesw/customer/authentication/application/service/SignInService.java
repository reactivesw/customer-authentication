package io.reactivesw.customer.authentication.application.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.authentication.TokenType;
import io.reactivesw.customer.authentication.application.model.SignInResult;
import io.reactivesw.customer.authentication.application.model.SignInStatus;
import io.reactivesw.customer.authentication.application.model.maper.CustomerMapper;
import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.domain.service.CustomerService;
import io.reactivesw.customer.authentication.infrastructure.configuration.AppConfig;
import io.reactivesw.customer.authentication.infrastructure.configuration.GoogleConfig;
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil;
import io.reactivesw.exception.ParametersException;
import io.reactivesw.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
  private final static Logger logger = LoggerFactory.getLogger(SignInService.class);

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
   * google client config
   */
  private transient GoogleConfig googleConfig;

  @Autowired
  private transient AppConfig appConfig;


  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * default config.
   */
  @Autowired
  public SignInService(GoogleConfig googleConfig) {
    this.googleConfig = googleConfig;
    this.verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, JSON_FACTORY)
        .setAudience(Collections.singletonList(googleConfig.getGoogleId()))
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
    logger.debug("Enter: email: {}", email);
    Customer customer = customerService.getByEmail(email);

    Boolean pwdResult = PasswordUtil.checkPassword(password, customer.getPassword());
    if (!pwdResult) {
      throw new PasswordErrorException("password or email not correct.");
    }

    String token = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId());

    SignInResult result = new SignInResult(CustomerMapper.modelToView(customer), token);

    cacheSignInStatus(result);

    logger.debug("Enter: email: {}", email);
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
    logger.debug("Enter: gToken: {}", gToken);

    GoogleIdToken token = verifyToken(gToken);
    String googleId = token.getPayload().getSubject();
    Customer customer = customerService.getByExternalId(googleId);
    if (customer == null) {
      customer = createWithGooglePayload(token.getPayload());
    }

    String customerToken = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId());

    SignInResult result = new SignInResult(CustomerMapper.modelToView(customer),
        customerToken);

    cacheSignInStatus(result);

    logger.debug("Exit: customer: {}", customer);
    return result;
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
    logger.debug("Enter: gToken: {}", token);

    GoogleIdToken idToken = verifier.verify(token);

    if (idToken == null) {
      logger.debug("google token verify failed. gToken: {}", token);
      throw new ParametersException("Google's id token is not correct.");
    }

    logger.debug("Exit: googleIdToken: {}", idToken);
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

    customer.setExternalId(id);

    logger.debug("create new customer with external info. customerEntity: {}", customer);
    return customerService.createWithSample(customer);
  }


  protected void setJwtUtil(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  protected void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

  protected void setAppConfig(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  protected void setRedisTemplate(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * Cache Sign in result.
   *
   * @param signInResult sign in result.
   */
  private void cacheSignInStatus(SignInResult signInResult) {
    logger.debug("Enter: SignInResult: {}", signInResult);
    Assert.notNull(signInResult);
    Assert.notNull(signInResult.getToken());

    String customerId = signInResult.getCustomerView().getId();
    String mapKey = StatusService.AUTH_KEY + customerId;
    String fieldKey = jwtUtil.parseToken(signInResult.getToken()).getTokenId();

    long curTime = System.currentTimeMillis();
    SignInStatus signInStatus = new SignInStatus(curTime, curTime, appConfig.expiresIn);
    redisTemplate.boundHashOps(mapKey).put(fieldKey, signInStatus);

    logger.debug("Exit: SignInStatus: {}", signInStatus);
  }
}
