package eurekademo;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liuyu685
 * @since 2018/8/13
 */
@RestController
public class ConsumerController {

  // 这里注入的restTemplate就是在com.sam.ConsumerApp中通过@Bean配置的实例
  @Autowired private RestTemplate restTemplate;
  @Autowired private EurekaClient eurekaClient;

  public String serviceUrl() {
    InstanceInfo instance = eurekaClient.getNextServerFromEureka("MY-WEB", false);
    return instance.getHomePageUrl();
  }

  @RequestMapping("/hello")
  public String helloConsumer() {
    String url = serviceUrl();
    // 调用hello-service服务，注意这里用的是服务名，而不是具体的ip+port
    restTemplate.getForObject(url, String.class);
    return "hello consumer finish !!!";
  }
}
