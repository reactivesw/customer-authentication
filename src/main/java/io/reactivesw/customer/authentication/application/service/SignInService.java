package io.reactivesw.customer.authentication.application.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.authentication.TokenType;
import io.reactivesw.customer.authentication.application.model.FbSignInRequest;
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
 * sign in service.
 */
@Service
public class SignInService {

  /**
   * LOG.
   */
  private final static Logger LOG = LoggerFactory.getLogger(SignInService.class);

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
   *
   */
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
    LOG.debug("Enter: email: {}", email);
    Customer customer = customerService.getByEmail(email);

    Boolean pwdResult = PasswordUtil.checkPassword(password, customer.getPassword());
    if (!pwdResult) {
      throw new PasswordErrorException("password or email not correct.");
    }

    String token = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId());

    SignInResult result = new SignInResult(CustomerMapper.modelToView(customer), token);

    cacheSignInStatus(result);

    LOG.debug("Enter: email: {}", email);
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
    LOG.debug("Enter: gToken: {}", gToken);

    GoogleIdToken token = verifyToken(gToken);
    String googleId = token.getPayload().getSubject();
    Customer customer = customerService.getOrCreateWithExternalId(googleId);

    String customerToken = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId());

    SignInResult result = new SignInResult(CustomerMapper.modelToView(customer),
        customerToken);

    cacheSignInStatus(result);

    LOG.debug("Exit: customer: {}", customer);
    return result;
  }

  /**
   * sign in with
   *
   * @param request auth response get from facebook by front end.
   * @return sign in result.
   */
  public SignInResult signInWithFacebook(FbSignInRequest request) {
    LOG.debug("enter. response from facebook: {}", request);
    //TODO Verify access token
    Customer customer = customerService.getOrCreateWithExternalId(request.getUserID());
    String customerToken = jwtUtil.generateToken(TokenType.CUSTOMER, customer.getId());

    SignInResult result = new SignInResult(CustomerMapper.modelToView(customer),
        customerToken);

    cacheSignInStatus(result);

    LOG.debug("exit. customer: {}", customer);
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
    LOG.debug("Enter: gToken: {}", token);

    GoogleIdToken idToken = verifier.verify(token);

    if (idToken == null) {
      LOG.debug("google token verify failed. gToken: {}", token);
      throw new ParametersException("Google's id token is not correct.");
    }

    LOG.debug("Exit: googleIdToken: {}", idToken);
    return idToken;
  }


  /**
   * set jet util.
   *
   * @param jwtUtil
   */
  protected void setJwtUtil(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
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
   * set app config.
   *
   * @param appConfig
   */
  protected void setAppConfig(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * set redis template.
   *
   * @param redisTemplate
   */
  protected void setRedisTemplate(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * Cache Sign in result.
   *
   * @param signInResult sign in result.
   */
  private void cacheSignInStatus(SignInResult signInResult) {
    LOG.debug("enter. SignInResult: {}", signInResult);
    Assert.notNull(signInResult, "signInResult can not be null.");
    Assert.notNull(signInResult.getToken(), "token can not be null.");

    String customerId = signInResult.getCustomerView().getId();
    String mapKey = StatusService.AUTH_KEY + customerId;
    String fieldKey = jwtUtil.parseToken(signInResult.getToken()).getTokenId();

    long curTime = System.currentTimeMillis();
    SignInStatus signInStatus = new SignInStatus(curTime, curTime, appConfig.expiresIn);
    redisTemplate.boundHashOps(mapKey).put(fieldKey, signInStatus);

    LOG.debug("exit. SignInStatus: {}", signInStatus);
  }
}
