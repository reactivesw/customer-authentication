package io.reactivesw.customer.authentication.domain.service;

import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.infrastructure.repository.CustomerRepository;
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil;
import io.reactivesw.exception.AlreadyExistException;
import io.reactivesw.exception.NotExistException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by umasuo on 17/2/10.
 */
@Service
public class CustomerService {

  /**
   * logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

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
    LOGGER.debug("enter: id:{}", id);
    Customer entity = this.customerRepository.findOne(id);
    if (entity == null) {
      LOGGER.warn("customer not exist: id:{}", id);
      throw new NotExistException("customer not exist. id:" + id);
    }
    LOGGER.debug("exit: id:{}, customer:{}", id, entity);
    return entity;
  }

  /**
   * get customer by customer name.
   *
   * @param name
   * @return
   */
  public Customer getByCustomerName(String name) {
    LOGGER.debug("enter: name:{}", name);
    Customer entity = this.customerRepository.findOneByCustomerName(name);
    if (entity == null) {
      LOGGER.debug("customer not exist: name:{}", name);
      throw new NotExistException("customer not exist. name:" + name);
    }
    LOGGER.debug("exit: name: {}, Customer:{}", name, entity);
    return entity;
  }

  /**
   * get customer by customer name.
   *
   * @param email
   * @return
   */
  public Customer getByEmail(String email) {
    LOGGER.debug("enter: email:{}", email);
    String lcEmail = email.toLowerCase(Locale.ENGLISH);
    Customer entity = this.customerRepository.findOneByEmail(lcEmail);
    if (entity == null) {
      LOGGER.debug("customer not exist: email:{}", lcEmail);
      throw new NotExistException("customer not exist. email:" + lcEmail);
    }
    LOGGER.debug("exit: email:{}, customer:{}", email, entity);
    return entity;
  }

  /**
   * get customer by customer name.
   *
   * @param externalId String
   * @return Customer
   */
  public Customer getByExternalId(String externalId) {
    LOGGER.debug("enter: externalId:{}", externalId);

    Customer customer = this.customerRepository.findByExternalId(externalId);

    LOGGER.debug("exit: externalId:{}, customer:{}", externalId, customer);
    return customer;
  }

  /**
   * create new customer with email and password.
   *
   * @param email    String
   * @param password String
   * @return Customer
   */
  public Customer createWithEmail(String email, String password) {

    this.checkEmail(email);
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
    LOGGER.debug("external info: {}", customer);
    return this.customerRepository.save(customer);
  }

  /**
   * check if the mail is unique.
   *
   * @param email
   */
  private void checkEmail(String email) {
    if (StringUtils.isNotBlank(email)) {
      String tmmEmail = email.toLowerCase(Locale.ENGLISH);
      Customer existValue = this.customerRepository.findOneByEmail(tmmEmail);
      if (existValue != null) {
        LOGGER.debug("error: customer already exist. email: {}", email);
        throw new AlreadyExistException("Customer already exist. email: " + tmmEmail);
      }
    }
  }
}
