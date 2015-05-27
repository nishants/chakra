package chakra.web.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestTest {
  private String requsetJson = "{\"content\": \"hello\"}";

  @Test
  public void shouldParseRequestContent() throws IOException {
    Request deserialized = new ObjectMapper().readValue(requsetJson, Request.class);
    assertThat(deserialized.getContent().toString(), is("hello"));
  }

}