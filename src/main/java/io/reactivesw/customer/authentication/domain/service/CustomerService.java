package io.reactivesw.customer.authentication.domain.service;

import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.infrastructure.repository.CustomerRepository;
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil;
import io.reactivesw.exception.AlreadyExistException;
import io.reactivesw.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by umasuo on 17/2/10.
 */
@Service
public class CustomerService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

  /**
   * customer repository.
   */
  @Autowired
  private transient CustomerRepository customerRepository;

  /**
   * get customer by id.
   *
   * @param id String
   * @return Customer
   */
  public Customer getById(String id) {
    logger.debug("Enter: id: {}", id);

    Customer entity = this.customerRepository.findOne(id);
    if (entity == null) {
      logger.warn("customer not exist: id:{}", id);
      throw new NotExistException("customer not exist. id: " + id);
    }

    logger.debug("Exit: id: {}, customer: {}", id, entity);
    return entity;
  }

  /**
   * get customer by customer name.
   *
   * @param email
   * @return
   */
  public Customer getByEmail(String email) {
    logger.debug("Enter: email: {}", email);

    Customer entity = this.customerRepository.findOneByEmail(email);
    if (entity == null) {
      logger.debug("customer not exist: email:{}", email);
      throw new NotExistException("customer not exist. email:" + email);
    }

    logger.debug("Exit: customer:{}", email, entity);
    return entity;
  }

  /**
   * get customer by customer name.
   *
   * @param externalId String
   * @return Customer
   */
  public Customer getByExternalId(String externalId) {
    logger.debug("Enter: externalId: {}", externalId);

    return this.customerRepository.findByExternalId(externalId);
  }

  /**
   * create new customer with email and password.
   *
   * @param email    String
   * @param password String
   * @return Customer
   */
  public Customer createWithEmail(String email, String password) {
    logger.debug("Enter: email:{}", email);

    this.isCustomerExist(email);
    Customer entity = new Customer();
    entity.setEmail(email);
    entity.setPassword(PasswordUtil.hashPassword(password));

    return this.customerRepository.save(entity);
  }

  /**
   * create customer with external info.
   * used for sign up.
   *
   * @param customer CustomerEntity
   * @return CustomerEntity
   */
  public Customer createWithSample(Customer customer) {
    logger.debug("Enter: sample: {}", customer);
    return this.customerRepository.save(customer);
  }

  /**
   * check if the mail is unique.
   *
   * @param email
   */
  private void isCustomerExist(String email) {
    logger.debug("Enter: email: {}", email);
    Assert.notNull(email);

    Customer existValue = this.customerRepository.findOneByEmail(email);
    if (existValue != null) {
      logger.warn("error: customer already exist. email: {}", email);
      throw new AlreadyExistException("Customer already exist. email: " + email);
    }
  }
}
