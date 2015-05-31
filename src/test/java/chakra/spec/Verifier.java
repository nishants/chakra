package chakra.spec;

import chakra.Application;
import chakra.spec.support.Contract;
import chakra.spec.support.Response;
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

import static chakra.spec.support.Contract.loadFrom;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class Verifier {

  @Value("${local.server.port}")
  private int port;

  private RestTemplate template;
  private final String mainRunnerSpecsJson = "/api-spec/main-runner.json";
  private final String testRunnerSpecsJson = "/api-spec/test-runner.json";
  private List<Contract> mainRunnerContracts;
  private List<Contract> testRunnerContracts;

  @Before
  public void setUp() throws Exception {
    template = new TestRestTemplate();
    mainRunnerContracts = loadFrom(mainRunnerSpecsJson);
    testRunnerContracts = loadFrom(testRunnerSpecsJson);
  }

  @Test
  public void verifyMainRunner() {
    for (Contract contract : mainRunnerContracts) {
      if (!contract.isSkipped()) {
        ResponseEntity<Map> response = post(atUrl("runner/main"), contract.getRequest());
        assertThat(contract.getName(), response.getHeaders().get("Access-Control-Allow-Origin"), is(asList("*")));
        assertThat(contract.getName(), response.getStatusCode(), is(HttpStatus.OK));
        assertThat(contract.getName(), response.getBody(), is(contract.getResponse()));
      }
    }
  }

  @Test
  public void verifyTestRunner() {
    for (Contract contract : testRunnerContracts) {
      if (!contract.isSkipped()) {
        ResponseEntity<Map> response = post(atUrl("runner/test"), contract.getRequest());
        assertThat(contract.getName(), response.getStatusCode(), is(HttpStatus.OK));
        assertThat(contract.getName(), response.getHeaders().get("Access-Control-Allow-Origin"), is(asList("*")));
        removeAndAssertErrorMessages(contract, response);
        assertThat(contract.getName(), response.getBody(), is(contract.getResponse()));
      }
    }
  }

  private void removeAndAssertErrorMessages(Contract contract, ResponseEntity<Map> response) {
    List<String> messagesInResponse = Response.popMessagesFrom(response.getBody());
    List<String> messagesInContract = Response.popMessagesFrom(contract.getResponse());

    for (int i = 0; i < messagesInContract.size(); i++) {
      String actual = messagesInResponse.get(i);
      String expected = messagesInContract.get(i);
      assertThat(contract.getName(), actual.indexOf(expected) , is(not(-1)));
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
