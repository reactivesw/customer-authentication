package io.reactivesw.customer.authentication.application.model.maper;

import io.reactivesw.customer.authentication.application.model.CustomerView;
import io.reactivesw.customer.authentication.domain.model.Customer;

/**
 * Created by umasuo on 17/2/10.
 */
public class CustomerMapper {

  /**
   * domain model to model for view.
   *
   * @param customer domain model
   * @return model for view
   */
  public static CustomerView modelToView(Customer customer) {
    CustomerView customerView = null;
    if (customer != null) {
      customerView = new CustomerView();
      customerView.setId(customer.getId());
      customerView.setEmail(customer.getEmail());
      customerView.setCreatedAt(customer.getCreatedAt());
      customerView.setExternalId(customer.getExternalId());
      customerView.setLastModifiedAt(customer.getLastModifiedAt());
      customerView.setSource(customer.getSource());
      customerView.setVersion(customer.getVersion());
    }
    return customerView;
  }
}
