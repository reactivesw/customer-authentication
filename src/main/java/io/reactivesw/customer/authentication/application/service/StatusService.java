package io.reactivesw.customer.authentication.application.service;

import io.reactivesw.authentication.JwtUtil;
import io.reactivesw.authentication.Token;
import io.reactivesw.customer.authentication.application.model.SignInStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * for check login status, developer account status.s
 */
@Service
public class StatusService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(StatusService.class);

  public final static String AUTH_KEY = "customer:auth:";
  /**
   * redis ops. cache cluster should be used.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * check login status. for auth check.
   * this can only be accessed in internal net work.
   *
   * @param tokenString
   * @return String userId
   */
  public String checkSignInStatus(String tokenString) {
    logger.debug("Enter: tokenString: {}", tokenString);
    Token token = jwtUtil.parseToken(tokenString);
    String customerId = token.getSubjectId();
    String mapKey = AUTH_KEY + customerId;
    String fieldKey = token.getTokenId();

    SignInStatus signInStatus = (SignInStatus) redisTemplate.boundHashOps(mapKey).get(fieldKey);
    logger.debug("SignInStatus: {}", signInStatus);

    String result = null;
    if (signInStatus != null) {
      //be careful, keep all authentication machine in the same time.
      long lifeTime = signInStatus.getLastVisitTime() + signInStatus.getExpiresIn();
      long curTime = System.currentTimeMillis();
      if (curTime < lifeTime) {
        result = customerId;
        //if the customer is now signed in, then update the last visit time, for prolong his/her
        // sign in status.
        this.updateSignInStatus(mapKey, fieldKey, signInStatus);
      }
    }

    logger.debug("Exit: customerId: {}", result);
    return result;
  }

  /**
   * for each time the user visit any service, we update the last visit time of his/her sign in
   * status.
   */
  private void updateSignInStatus(String mapKey, String fieldKey, SignInStatus signInStatus) {
    logger.debug("Enter: mapKey: {}, fieldKey: {}, signInStatus: {}", mapKey, fieldKey,
        signInStatus);

    signInStatus.setLastVisitTime(System.currentTimeMillis());

    redisTemplate.boundHashOps(mapKey).put(fieldKey, signInStatus);

    logger.debug("Enter: mapKey: {}, fieldKey: {}, signInStatus: {}", mapKey, fieldKey,
        signInStatus);
  }
}
