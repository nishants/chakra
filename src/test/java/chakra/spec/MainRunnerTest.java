package chakra.spec;

import chakra.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class MainRunnerTest {

  @Value("${local.server.port}")
  private int port;

  private String base;
  private RestTemplate template;

  @Before
  public void setUp() throws Exception {
    template = new TestRestTemplate();
  }

  @Test
  public void helloWorld(){
    ResponseEntity<Map> response = template.postForEntity(
        url("runner/main"),
        "{ \"content\" : {\"mainClass\":\"a.B.HelloWord\" }}  ",
        Map.class
    );

    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody().get("content").toString(), is("{mainClass=a.B.HelloWord}"));
  }

  private String url(String url) {
    return String.format("http://localhost:%d/%s/", port, url);
  }

}
