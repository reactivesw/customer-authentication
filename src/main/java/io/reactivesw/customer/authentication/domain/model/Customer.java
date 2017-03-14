package io.reactivesw.customer.authentication.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * Created by umasuo on 17/2/10.
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  protected String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected ZonedDateTime lastModifiedAt;

  /**
   * version.
   */
  @Version
  @Column(name = "version")
  private Integer version;

  /**
   * email.
   */
  @Column(name = "email", unique = true)
  private String email;

  /**
   * password.
   */
  @Column(name = "password")
  private String password;


  /**
   * external id, like facebook id.
   */
  @Column(name = "external_id", unique = true)
  private String externalId;

  /**
   * customer source, like sign up by email, facebook, google.
   */
  @Column(name = "source")
  private String source;
}
