package io.reactivesw.customer.authentication.domain.service.update;

import io.reactivesw.customer.authentication.application.model.action.UpdatePassword;
import io.reactivesw.customer.authentication.domain.model.Customer;
import io.reactivesw.customer.authentication.infrastructure.update.AuthUpdateActionUtils;
import io.reactivesw.customer.authentication.infrastructure.update.UpdateAction;
import io.reactivesw.customer.authentication.infrastructure.util.PasswordUtil;
import io.reactivesw.exception.AuthFailedException;
import io.reactivesw.model.Updater;
import org.springframework.stereotype.Service;

/**
 * update password service.
 */
@Service(value = AuthUpdateActionUtils.UPDATE_PASSWORD)
public class UpdatePasswordService implements Updater<Customer, UpdateAction> {

  /**
   * handle the action.
   *
   * @param customer     Customer entity
   * @param updateAction UpdateAction
   */
  @Override
  public void handle(Customer customer, UpdateAction updateAction) {
    UpdatePassword action = (UpdatePassword) updateAction;

    String oldPwd = action.getOldPassword();
    boolean isOldValid = PasswordUtil.checkPassword(oldPwd, customer.getPassword());
    if (!isOldValid) {
      throw new AuthFailedException("Old password is not correct.");
    }

    String newPwd = action.getNewPassword();
    customer.setPassword(PasswordUtil.hashPassword(newPwd));
  }
}
