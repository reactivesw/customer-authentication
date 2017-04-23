# Customer authentication
This doc describes the design of customer service.

## 1. Basic Features
- Get Anonymous Token
- Sin In: email, google, facebook
- Sign Up: email
- SignIn Status Check

## 2. Model Design
- Entity: Customer
```java

  String id;

  ZonedDateTime createdAt;

  ZonedDateTime lastModifiedAt;

  Integer version;

  String email;

  String password;

  String externalId;

  String source;
```
- View model: [View Model](./api.md)

## 3. Workflow
This part describes the workflow of each feature.
### Get Anonymous Token
- generate an anonymous token
- return the token

### Sign In with email

## 4. Event Design
This part describes the events that this service produce or consume.
### 4.1 Producer Of Login Event
This service will produce sign in event.
#### 4.1.1  Topic Name
`reactivesw-customer-login`
#### 4.1.2 Topic Model
```java
  String customerId;

  String anonymousId;
```


