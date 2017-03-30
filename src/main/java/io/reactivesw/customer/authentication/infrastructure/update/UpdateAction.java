package io.reactivesw.customer.authentication.infrastructure.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.reactivesw.customer.authentication.application.model.action.UpdatePassword;

/**
 * configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property =
    "action")
@JsonSubTypes( {
    @JsonSubTypes.Type(value = UpdatePassword.class, name = AuthUpdateActionUtils.UPDATE_PASSWORD)})
public interface UpdateAction {
  /**
   * get action name.
   *
   * @return String.
   */
  String getActionName();
}
