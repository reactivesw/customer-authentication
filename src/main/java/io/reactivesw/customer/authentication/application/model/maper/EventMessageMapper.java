package io.reactivesw.customer.authentication.application.model.maper;

import io.reactivesw.customer.authentication.application.model.event.SignInEvent;
import io.reactivesw.customer.authentication.domain.model.EventMessage;
import io.reactivesw.customer.authentication.infrastructure.enums.EventStatus;
import io.reactivesw.customer.authentication.infrastructure.util.EventTopics;
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
  public static EventMessage toEntity(SignInEvent event) {
    EventMessage eventMessage = new EventMessage();

    eventMessage.setStatus(EventStatus.CREATED);
    eventMessage.setCreatedAt(System.currentTimeMillis());
    eventMessage.setVersion(1);//TODO use config
    eventMessage.setTopic(EventTopics.SIGN_IN);

    eventMessage.setPayload(jsonSerializer.serialize(event));

    return eventMessage;
  }

}
