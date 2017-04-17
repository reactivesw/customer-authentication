package io.reactivesw.customer.authentication.application.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * google sign in request.
 */
@Data
public class GoogleSignInRequest {

  /**
   * 包括应用用户的访问口令。
   */
  @NotNull
  private String token;

  /**
   * anonymous id if exist.
   */
  private String anonymousId;

}
