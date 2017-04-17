package io.reactivesw.customer.authentication.infrastructure.enums;

/**
 * Event status.
 */
public enum EventStatus {
  /**
   * new created events.
   */
  CREATED(0),
  /**
   * events being publishing.
   */
  PENDING(1);

  /**
   * int value;
   */
  private int value;

  /**
   * constructor.
   *
   * @param value int value
   */
  EventStatus(int value) {
    this.value = value;
  }

  /**
   * get value.
   *
   * @return int
   */
  public int getValue() {
    return value;
  }
}
