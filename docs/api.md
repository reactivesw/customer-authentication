# Customer-Authentication Api Design

## Introduction

TODO

## View Model

### SignIn

| field name | field type | comments |
|-|-|-|
| email | String | NotNull, email pattern |
| password | String | NotNull, a digit must occur at least once, a lower case letter must occur at least once,  no whitespace allowed in the entire string, at least eight places, end of string |
| anonymousId| String | anonymousId if exist

### SignInResult

| field name | field type | comments |
|-|-|-|
| customerView | CustomerView | |
| token | String | |

### CustomerView

| field name | field type | comments |
|-|-|-|
| id | String | |
| createdAt | ZonedDateTime | |
| lastModifiedAt | ZOnedDateTime | |
| version | Integer | |
| email | String | |
| externalId | String | |
| source | String | |

### GoogleSignInRequest

| field name | field type | comments |
|-|-|-|
| token | String | not null|
| anonymousId| String | anonymousId if exist

### FbSignInRequest

| field name | field type | comments |
|-|-|-|
| accessToken | String | not null|
| expiresIn | long | not null |
| signedRequest | String | not null|
| userID | String | not null|
| anonymousId| String | anonymousId if exist

### SignUp

| field name | field type | comments |
|-|-|-|
| email | String | NotNull, email pattern |
| password | String | NotNull, a digit must occur at least once, a lower case letter must occur at least once,  no whitespace allowed in the entire string, at least eight places, end of string |


## API

### get anonymous token

* Url : {customer-authentication service path}/anonymous
* method : GET
* response : String

### sign in with email

* Url : {customer-authentication service path}/signin
* method : POST
* request body :

  | name | type | required |
  |-|-|-|
  | signIn | SignIn | required |

* response : SignInResult

### sign in with google

* Url : {customer-authentication service path}/signin/google
* method : POST
* request body :

  | name | type | required |
  |-|-|-|
  | request | GoogleSignInRequest | require |

* response : SignInResult

### sign in with facebook

* Url : {customer-authentication service path}/signin/facebook
* method : POST
* request body :

  | name | type | required |
  |-|-|-|
  | request | FbSignInRequest | require |

* response : SignInResult

### sign up with email

* Url : {customer-authentication service path}/signup
* method : POST
* request body :

  | name | type | required |
  |-|-|-|
  | signUp | Signup | required |

* response : void

### get sign in status

* Url : {customer-authentication service path}/status
* method : GET
* request param :

  | name | type | required |
  |-|-|-|
  | token | String | required |

* response : customerId in String

### Update customer

* Url : {customer-authentication service path}/{customerId}
* method : PUT
- Payload: customerId --NotNull in path, UpdateRequest --NotNull in body
- Response: CustomerView

### Actions
#### UpdatePassword
- name: updatePassword
Used to change password of the customer.
```java
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
```
