package io.reactivesw.customer.authentication.domain.service;

import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.infrastructure.enums.AccountSource;
import io.reactivesw.customer.authentication.infrastructure.repository.CustomerRepository;
import io.reactivesw.customer.authentication.infrastructure.update.UpdateAction;
import io.reactivesw.customer.authentication.infrastructure.update.UpdaterService;
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil;
import io.reactivesw.exception.AlreadyExistException;
import io.reactivesw.exception.ConflictException;
import io.reactivesw.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * customer service.
 */
@Service
public class CustomerService {

  /**
   * LOG.
   */
  private final static Logger LOG = LoggerFactory.getLogger(CustomerService.class);

  /**
   * customer repository.
   */
  @Autowired
  private transient CustomerRepository customerRepository;

  /**
   * update service.
   */
  @Autowired
  private transient UpdaterService updaterService;

  /**
   * get customer by id.
   *
   * @param id String
   * @return Customer
   */
  public Customer getById(String id) {
    LOG.debug("enter. id: {}", id);

    Customer entity = this.customerRepository.findOne(id);
    if (entity == null) {
      LOG.warn("customer not exist: id:{}", id);
      throw new NotExistException("customer not exist. id: " + id);
    }

    LOG.debug("exit. id: {}, customer: {}", id, entity);
    return entity;
  }


  /**
   * get customer by customer name.
   *
   * @param email
   * @return
   */
  public Customer getByEmail(String email) {
    LOG.debug("enter. email: {}", email);

    Customer entity = customerRepository.findOneByEmail(email);
    if (entity == null) {
      LOG.debug("customer not exist: email:{}", email);
      throw new NotExistException("customer not exist. email:" + email);
    }

    LOG.debug("exit. customer:{}", email, entity);
    return entity;
  }

  /**
   * get customer by customer name.
   *
   * @param externalId String
   * @return Customer
   */
  public Customer getOrCreateWithExternalId(String externalId, AccountSource source) {
    LOG.debug("enter. externalId: {}", externalId);
    Customer customer = customerRepository.findByExternalId(externalId);
    if (customer == null) {
      customer = createWithExternalId(externalId, source);
    }
    return customer;
  }

  /**
   * create new customer with google payload.
   *
   * @param externalId GoogleIdToken.Payload
   * @return CustomerEntity
   */
  private Customer createWithExternalId(String externalId, AccountSource source) {
    Customer customer = new Customer();
    customer.setExternalId(externalId);
    customer.setSource(source.getValue());

    LOG.debug("create new customer with external info. customerEntity: {}", customer);
    return customerRepository.save(customer);
  }

  /**
   * create new customer with email and password.
   *
   * @param email    String
   * @param password String
   * @return Customer
   */
  public Customer createWithEmail(String email, String password) {
    LOG.debug("Enter: email:{}", email);

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
    LOG.debug("Enter: sample: {}", customer);
    return this.customerRepository.save(customer);
  }

  /**
   * update customer.
   *
   * @param customerId customer id
   * @param version    integer version
   * @param actions    list of update action
   * @return Customer
   */
  public Customer update(String customerId, Integer version, List<UpdateAction> actions) {
    LOG.debug("enter. customerId: {}", customerId);
    Customer customer = this.getById(customerId);

    this.validateVersion(version, customer.getVersion());
    actions.stream().forEach(
        action -> updaterService.handle(customer, action)
    );

    return this.customerRepository.save(customer);
  }

  /**
   * check if the mail is unique.
   *
   * @param email
   */
  private void isCustomerExist(String email) {
    LOG.debug("Enter: email: {}", email);
    Assert.notNull(email, "email can not be null.");

    Customer existValue = this.customerRepository.findOneByEmail(email);
    if (existValue != null) {
      LOG.warn("error: customer already exist. email: {}", email);
      throw new AlreadyExistException("Customer already exist. email: " + email);
    }
  }

  /**
   * check the version of the customer.
   *
   * @param inputVersion Integer
   * @param savedVersion Integer
   */
  private void validateVersion(Integer inputVersion, Integer savedVersion) {
    if (!Objects.equals(inputVersion, savedVersion)) {
      LOG.debug("Customer version is not correct. inputVersion:{}, savedVersion:{}",
          inputVersion, savedVersion);
      throw new ConflictException("Customer version is not correct.");
    }
  }
}
