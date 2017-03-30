package io.reactivesw.customer.authentication.application.model.action;

import io.reactivesw.customer.authentication.infrastructure.update.AuthUpdateActionUtils;
import io.reactivesw.customer.authentication.infrastructure.update.UpdateAction;
import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Update Password action.
 */
@Data
public class UpdatePassword implements UpdateAction, Serializable {

  /**
   * auth generated serial version id.
   */
  private static final long serialVersionUID = -8922327407691480136L;

  /**
   * old password.
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  @NotNull
  private String oldPassword;

  /**
   * new password.
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  @NotNull
  private String newPassword;

  /**
   * return action name.
   *
   * @return String name
   */
  @Override
  public String getActionName() {
    return AuthUpdateActionUtils.UPDATE_PASSWORD;
  }
}
