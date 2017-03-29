# Customer-Authentication Api Design

## Introduction

TODO

## View Model

### SignIn

| field name | field type | comments |
|-|-|-|
| email | String | NotNull, email pattern |
| password | String | NotNull, a digit must occur at least once, a lower case letter must occur at least once,  no whitespace allowed in the entire string, at least eight places, end of string |

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
* request param :

  | name | type | required |
  |-|-|-|
  | gToken | String | require |

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