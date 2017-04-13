package io.reactivesw.customer.authentication.domain.model;

import io.reactivesw.customer.authentication.infrastructure.enums.EventStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * event.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "event")
@EntityListeners(AuditingEntityListener.class)
public class EventMessage {

  /**
   * uuid.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @Column(name = "created_at")
  private long createdAt;

  /**
   * sequence number.
   */
  private String sequenceNumber;

  /**
   * the event will be expired in expire.
   */
  private long expire;

  /**
   * version.
   */
  private Integer version;

  /**
   * Json string payload.
   */
  private String payload;

  /**
   * topic.
   */
  private String topic;

  /**
   * event status.
   */
  private EventStatus status;
}
