package io.reactivesw.customer.authentication.application.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * fb sign in request.
 */
@Data
public class FbSignInRequest {

  /**
   * 包括应用用户的访问口令。
   */
  @NotNull
  private String accessToken;

  /**
   * 表示口令到期且需要更新的 UNIX 时间。
   */
  @NotNull
  private long expiresIn;

  /**
   * 经签名的参数，其中包括应用用户的信息。
   */
  @NotNull
  private String signedRequest;

  /**
   * user ID in face book.
   */
  @NotNull
  private String userID;
}
