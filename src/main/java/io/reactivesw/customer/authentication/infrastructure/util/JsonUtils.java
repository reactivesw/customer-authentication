package io.reactivesw.customer.authentication.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JsonUtils for serialize or deserialize mode.
 */
public final class JsonUtils {


  /**
   * logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
  /**
   * Object mapper.
   */
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * default private constructor.
   */
  private JsonUtils() {

  }

  /**
   * deserialize object.
   *
   * @param value
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T deserialize(String value, Class<T> clazz) {
    if (StringUtils.isBlank(value)) {
      throw new IllegalArgumentException(
          "Blank string cannot be deserialized to class");
    }

    try {
      return OBJECT_MAPPER.readValue(value, clazz);
    } catch (IOException ex) {
      LOG.debug("Error deserializing string: {}", value, ex);
      throw new IllegalArgumentException(
          "Error deserializing string: " + value, ex);
    }
  }

  /**
   * deserialize object.
   *
   * @param stringValue
   * @param typeReference
   * @param <T>
   * @return
   */
  public static <T> T deserialize(String stringValue, TypeReference<T> typeReference) {
    if (StringUtils.isBlank(stringValue)) {
      throw new IllegalArgumentException(
          "Blank string cannot be deserialized to class");
    }

    try {
      return OBJECT_MAPPER.readValue(stringValue, typeReference);
    } catch (IOException ex) {
      LOG.debug("Error deserializing string: {}", stringValue, ex);
      throw new IllegalArgumentException(stringValue, ex);
    }
  }

  /**
   * serialize object.
   *
   * @param obj
   * @return
   */
  public static String serialize(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Null Object cannot be serialized");
    }

    try {
      return OBJECT_MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      LOG.debug("Error serializing object", e);
      throw new IllegalArgumentException("Error serializing object: " + e.getMessage());
    }
  }

  /**
   * valid json string.
   *
   * @param string
   * @return
   */
  public static boolean isJSONValid(String string) {
    boolean result = false;
    try {
      OBJECT_MAPPER.readTree(string);
      result = true;
    } catch (IOException e) {
      result = false;
    }
    return result;
  }

  /**
   * valid json string with class type.
   *
   * @param value
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> boolean isJSONValid(String value, Class<T> clazz) {
    boolean result = false;
    if (!StringUtils.isBlank(value)) {
      try {
        OBJECT_MAPPER.readValue(value, clazz);
        result = true;
      } catch (IOException e) {
        // do nothing
        LOG.debug("string is not valid json. string: {}", value);
      }
    }
    return result;
  }
}
