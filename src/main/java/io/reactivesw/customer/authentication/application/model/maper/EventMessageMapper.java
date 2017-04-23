package io.reactivesw.customer.authentication.application.model.maper;

import io.reactivesw.customer.authentication.application.model.event.SignInEvent;
import io.reactivesw.customer.authentication.domain.model.EventMessage;
import io.reactivesw.customer.authentication.infrastructure.configuration.EventConfig;
import io.reactivesw.customer.authentication.infrastructure.enums.EventStatus;
import io.reactivesw.message.client.utils.serializer.JsonSerializer;

/**
 * Event message mapper.
 */
public final class EventMessageMapper {

  /**
   * json serializer.
   */
  private transient static JsonSerializer jsonSerializer = new JsonSerializer();

  /**
   * default private constructor.
   */
  private EventMessageMapper() {

  }

  /**
   * to entity.
   *
   * @param event
   * @return EventMessage
   */
  public static EventMessage toEntity(SignInEvent event, EventConfig config) {
    EventMessage eventMessage = new EventMessage();

    eventMessage.setStatus(EventStatus.CREATED);
    eventMessage.setCreatedAt(System.currentTimeMillis());
    eventMessage.setVersion(config.getSignInTopicVersion());
    eventMessage.setTopic(config.getSignInTopicName());

    eventMessage.setPayload(jsonSerializer.serialize(event));

    return eventMessage;
  }

}
