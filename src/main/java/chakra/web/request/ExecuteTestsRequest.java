package chakra.web.request;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteTestsRequest {
  private List<JavaFile> main;
  private List<JavaFile> test;
}
