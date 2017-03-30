package io.reactivesw.customer.authentication.application.controller;

import io.reactivesw.customer.authentication.application.model.CustomerView;
import io.reactivesw.customer.authentication.application.model.maper.CustomerMapper;
import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.domain.service.CustomerService;
import io.reactivesw.customer.authentication.infrastructure.Router;
import io.reactivesw.customer.authentication.infrastructure.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Customer controller for manager customer's info.
 */
@RestController
public class CustomerController {

  /**
   * logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(CustomerController.class);

  /**
   * customer service.
   */
  @Autowired
  private transient CustomerService customerService;

  /**
   * update customer.
   *
   * @param customerId    customer id
   * @param updateRequest update request
   * @return customer view.
   */
  @PutMapping(Router.CUSTOMER_WITH_ID)
  public CustomerView update(@PathVariable(Router.CUSTOMER_ID) String customerId,
                             @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("customerId:{}, updateRequest: {}", customerId, updateRequest);

    Customer result = customerService.update(customerId, updateRequest.getVersion(), updateRequest
        .getActions());

    LOG.info("result:{}", result);
    return CustomerMapper.modelToView(result);
  }
}
