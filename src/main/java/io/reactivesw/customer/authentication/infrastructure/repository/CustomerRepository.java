package io.reactivesw.customer.authentication.infrastructure.repository;

import io.reactivesw.customer.authentication.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * customer repository.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>,
    CrudRepository<Customer, String> {

  /**
   * find customer by email.
   *
   * @param email
   * @return
   */
  Customer findOneByEmail(String email);

  /**
   * find customer by external id.
   *
   * @param externalId
   * @return
   */
  Customer findByExternalId(String externalId);
}
