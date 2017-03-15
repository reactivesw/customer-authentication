package io.reactivesw.customer.authentication.application.service

import io.reactivesw.authentication.JwtUtil
import io.reactivesw.authentication.Token
import io.reactivesw.customer.authentication.application.model.SignInResult
import io.reactivesw.customer.authentication.domain.model.Customer
import io.reactivesw.customer.authentication.domain.service.CustomerService
import io.reactivesw.customer.authentication.infrastructure.configuration.AppConfig
import io.reactivesw.customer.authentication.infrastructure.configuration.GoogleConfig
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil
import org.springframework.data.redis.core.BoundHashOperations
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

/**
 * Created by umasuo on 17/2/21.
 */
class SignInServiceTest extends Specification {
    SignInService signInService

    JwtUtil jwtUtil = Mock(JwtUtil)

    CustomerService customerService = Mock(CustomerService)

    GoogleConfig googleConfig

    AppConfig appConfig

    RedisTemplate redisTemplate = Mock(RedisTemplate)
    BoundHashOperations hashOperations = Mock(BoundHashOperations)

    def rawPassword = "password"

    def password = PasswordUtil.hashPassword(rawPassword)

    def email = "test@test.com"

    def customerId = "customerId"

    Customer customer

    def token


    def setup() {
        googleConfig = new GoogleConfig(googleId: "GOOGLE_ID")
        appConfig = new AppConfig(expiresIn: 7200000)
        signInService = new SignInService(googleConfig)
        signInService.setJwtUtil(jwtUtil)
        signInService.setCustomerService(customerService)
        signInService.setAppConfig(appConfig)
        signInService.setRedisTemplate(redisTemplate)


        customer = new Customer(id: customerId, email: email, password: password)

        token = new Token(tokenId: "tokenId", subjectId: customerId);
    }

    def "Test 1.1: sign in with email"() {
        when:
        customerService.getByEmail(email) >> customer
        jwtUtil.generateToken(_, _) >> token
        jwtUtil.parseToken(_) >> token
        redisTemplate.boundHashOps(_) >> hashOperations
        SignInResult result = signInService.signInWithEmail(email, rawPassword)
        then:
        noExceptionThrown()
    }
}
