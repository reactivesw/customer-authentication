package io.reactivesw.customer.authentication.infrastructure.repository;

import io.reactivesw.customer.authentication.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by umasuo on 17/2/10.
 */
public interface CustomerRepository extends JpaRepository<Customer, String>,
    CrudRepository<Customer, String> {

  Customer findOneByCustomerName(String customerName);

  Customer findOneByEmail(String email);

  Customer findByExternalId(String externalId);
}
