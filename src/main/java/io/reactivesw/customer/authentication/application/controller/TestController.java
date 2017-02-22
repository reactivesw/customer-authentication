package io.reactivesw.customer.authentication.application.controller;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by umasuo on 17/2/21.
 */
@RestController
@Configuration
public class TestController {

  @Value("${umasuo.name}")
  private String name;

  @Value("${umasuo.test.uri}")
  private String uri;

  RestTemplate restTemplate = new RestTemplate();
  @ApiOperation("test")
  @GetMapping("/test")
  public String testConfigServer(){
    String nameFromCustomerInfo = restTemplate.getForObject(uri,String.class);
    return name + "    :    " + nameFromCustomerInfo;
  }

  @ApiOperation("test1")
  @GetMapping("/test1")
  public String testConfigServer1(){
    return name;
  }
}
