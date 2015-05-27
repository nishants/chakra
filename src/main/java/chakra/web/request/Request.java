package chakra.web.request;

import lombok.Getter;
import lombok.Setter;

public class Request<T> {
  @Setter
  @Getter
  protected T content;
}
