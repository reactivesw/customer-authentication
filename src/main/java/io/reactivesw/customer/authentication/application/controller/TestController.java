package io.reactivesw.customer.authentication.application.controller;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/2/21.
 */
@RestController
@Configuration
public class TestController {

  @Value("${umasuo.name}")
  private String name;

  @ApiOperation("test")
  @GetMapping("/test")
  public String testConfigServer(){
    return name;
  }
}
