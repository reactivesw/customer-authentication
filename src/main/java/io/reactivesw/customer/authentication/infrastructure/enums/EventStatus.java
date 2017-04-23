package io.reactivesw.customer.authentication.infrastructure.enums;

/**
 * Event status.
 */
public enum EventStatus {
  /**
   * New created events.
   */
  CREATED(0),
  /**
   * Events being publishing.
   */
  PENDING(1);

  /**
   * Int value;
   */
  private int value;

  /**
   * Constructor.
   *
   * @param value int value
   */
  EventStatus(int value) {
    this.value = value;
  }

  /**
   * Get value.
   *
   * @return int
   */
  public int getValue() {
    return value;
  }
}
