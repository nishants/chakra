package chakra.spec;

import chakra.Application;
import chakra.spec.support.ApiSpec;
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

import java.util.List;
import java.util.Map;

import static chakra.spec.support.ApiSpec.loadFrom;
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
  private final String specsJson = "/api-spec/main-runner.json";
  private List<ApiSpec> specs;

  @Before
  public void setUp() throws Exception {
    template = new TestRestTemplate();
    specs = loadFrom(specsJson);
  }

  public static final String SimpleMainClass =
      "   public class SimpleMainClass{public static void main(String[] args){}}                                                ";

  @Test
  public void testMainSpecs() {
    for (ApiSpec spec : specs) {
      ResponseEntity<Map> response = post(atUrl("runner/main"), spec.getRequest());
      assertThat(spec.getName(), response.getStatusCode(), is(HttpStatus.OK));
      assertThat(spec.getName(), response.getBody(), is(spec.getResponse()));
    }
  }

  private ResponseEntity<Map> post(String url, Object request) {
    return template.postForEntity(
        url,
        request,
        Map.class
    );
  }

  private String atUrl(String url) {
    return String.format("http://localhost:%d/%s/", port, url);
  }

}