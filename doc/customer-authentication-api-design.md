# Customer-Authentication Api Design

## Introduction

TODO

## View Model

### SignIn

| field name | field type | comments |
|-|-|-|
| email | String | NotNull, email pattern |
| password | String | NotNull, pattern is "^(?=.*[0-9])(?=.*[a-z])(?=\S+$).{8,}$" |

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
| password | String | NotNull, pattern is "^(?=.*[0-9])(?=.*[a-z])(?=\S+$).{8,}$" |


## API

### get anonymous token

* Url : {customer-authentication service path}/anonymous
* method : GET
* response : String

### sign in with email

* Url : {customer-authentication service path}/signin
* method : POST
* request body : signIn - SignIn - require
* response : SignInResult

### sign in with google

* Url : {customer-authentication service path}/signin/google
* method : POST
* request param: gToken - String - require
* response : SignInResult

### sign up with email

* Url : {customer-authentication service path}/signup
* method : POST
* request body : signUp - SignUp - require
* response : void

### get sign in status

* Url : {customer-authentication service path}/status
* method : GET
* request param : token - String - require
* response : String