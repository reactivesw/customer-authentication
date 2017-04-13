package io.reactivesw.customer.authentication.infrastructure.repository;

import io.reactivesw.customer.authentication.domain.model.EventMessage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * customer repository.
 */
@Repository
public interface EventRepository extends CrudRepository<EventMessage, String>,
    JpaSpecificationExecutor<EventMessage> {

}
