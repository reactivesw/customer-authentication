package io.reactivesw.customer.authentication.application.service

import io.reactivesw.authentication.JwtUtil
import io.reactivesw.customer.authentication.application.model.SignInResult
import io.reactivesw.customer.authentication.domain.model.Customer
import io.reactivesw.customer.authentication.domain.service.CustomerService
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil
import spock.lang.Specification

/**
 * Created by umasuo on 17/2/21.
 */
class SignInServiceTest extends Specification {
    SignInService signInService

    JwtUtil jwtUtil = Mock(JwtUtil)

    CustomerService customerService = Mock(CustomerService)

    def rawPassword = "password"
    def password = PasswordUtil.hashPassword(rawPassword)

    def email = "test@test.com"

    def customerId = "customerId"

    Customer customer

    def token = "this.is.the.token"

    def setup() {
        signInService = new SignInService(jwtUtil: jwtUtil, customerService: customerService)
        customer = new Customer(id: customerId, email: email, password: password)
    }

    def "Test 1.1: sign in with email"() {
        when:
        customerService.getByEmail(email) >> customer
        jwtUtil.generateToken(_, _, _, _) >> token
        SignInResult result = signInService.signInWithEmail(email, rawPassword)
        then:
        noExceptionThrown()
    }
}
