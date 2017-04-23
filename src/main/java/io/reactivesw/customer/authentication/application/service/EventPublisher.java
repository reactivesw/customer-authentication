package io.reactivesw.customer.authentication.application.service;

import io.reactivesw.customer.authentication.domain.model.EventMessage;
import io.reactivesw.customer.authentication.infrastructure.configuration.EventConfig;
import io.reactivesw.customer.authentication.infrastructure.enums.EventStatus;
import io.reactivesw.customer.authentication.infrastructure.repository.EventRepository;
import io.reactivesw.customer.authentication.infrastructure.util.EventTopics;
import io.reactivesw.message.client.core.DefaultProducerFactory;
import io.reactivesw.message.client.core.Message;
import io.reactivesw.message.client.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * EventMessage publisher.
 */
@Service
public class EventPublisher {

  /**
   * logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(EventPublisher.class);

  /**
   * event repository.
   */
  @Autowired
  private transient EventRepository eventRepository;

  /**
   * producer map.
   */
  private transient final Map<String, Producer> producerMap = new ConcurrentHashMap<>();

  /**
   * default constructor.
   */
  @Autowired
  public EventPublisher(EventConfig eventConfig) {
    Producer signinProducer = DefaultProducerFactory.createGoogleProducer(
        eventConfig.getGoogleCloudProjectId(), EventTopics.SIGN_IN);

    producerMap.put(EventTopics.SIGN_IN, signinProducer);
  }

  /**
   * executor.
   * Executes each 200 ms.
   */
  @Scheduled(fixedRate = 200)
  public void executor() {

    List<EventMessage> events = getEvents();

    events.stream().forEach(
        event -> {
          Message message = Message.build(event.getId(), event.getSequenceNumber(),
              event.getCreatedAt(), event.getVersion(), event.getExpire(), event.getPayload());

          publishEvent(event.getTopic(), message);
          eventRepository.delete(event.getId());
        }
    );
  }

  /**
   * publish an event to a topic.
   *
   * @param topicName topic name
   * @param message   event
   */
  private void publishEvent(String topicName, Message message) {
    LOG.info("Publish message. topicName: {}, message: {}.", topicName, message);
    Producer producer = producerMap.get(topicName);
    if (producer == null) {
      // The topic may be deleted or changed name.
      LOG.error("Producer not found. topicName: {}.", topicName);
    } else {
      LOG.debug("Publish event. topicName: {}, event: {}.", topicName, message);
      producer.publish(message);
    }
  }

  /**
   * get events from db, and set their status to PENDING.
   *
   * @return List of Event
   */
  @Transactional
  private List<EventMessage> getEvents() {

    //fetch the first page and fetch 10 event each time. TODO change this to configuration.
    Page<EventMessage> page = eventRepository.findAll(isAvailable(), new PageRequest(0, 10));
    List<EventMessage> events = page.getContent();

    LOG.debug("Fetch events. eventCount: {}, countInDb: {}.", events.size(), page
        .getTotalElements());

    events.stream().forEach(
        event -> {
          if (event.getStatus() == EventStatus.CREATED) {
            //if the event is in "CREATED" status, then change it to "PENDING"
            event.setStatus(EventStatus.PENDING);
            eventRepository.save(events);
          }
        }
    );

    // Log the event data.
    LOG.trace("Fetch events. events: {}", Arrays.toString(events.toArray()));
    return events;
  }


  /**
   * specification for fetch Events.
   *
   * @return Specification
   */
  public static Specification<EventMessage> isAvailable() {
    return new Specification<EventMessage>() {
      /**
       * predicate builder.
       */
      public Predicate toPredicate(Root<EventMessage> root, CriteriaQuery<?> query,
                                   CriteriaBuilder builder) {
        // Fetch events for two kind of conditions.
        return builder.or(
            builder.and(
                // Condition1: Fetch event whose status is "PENDING", but created 1 minutes ago.
                builder.lessThan(root.get("createdAt"), System.currentTimeMillis() - 60000),
                builder.equal(root.get("status"), EventStatus.PENDING.getValue())
            ),
            // Condition2: Fetch event whose status is "CREATED"
            builder.equal(root.get("status"), EventStatus.CREATED.getValue())
        );
      }
    };
  }
}
