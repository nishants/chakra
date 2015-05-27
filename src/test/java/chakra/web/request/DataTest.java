package chakra.web.request;

import chakra.web.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DataTest {
  private String requsetJson = "{\"content\": \"hello\"}";

  @Test
  public void shouldParseRequestContent() throws IOException {
    Data deserialized = new ObjectMapper().readValue(requsetJson, Data.class);
    assertThat(deserialized.getContent().toString(), is("hello"));
  }

}