package io.reactivesw.customer.authentication.infrastructure.util;

import org.springframework.data.domain.AuditorAware;

/**
 * Zoned data time auditor.
 */
public class ZonedDateTimeAuditorAware implements AuditorAware<String> {

  /**
   * return null auditor.
   *
   * @return
   */
  @Override
  public String getCurrentAuditor() {
    return null;
  }
}