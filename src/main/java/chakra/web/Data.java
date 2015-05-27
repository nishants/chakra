package chakra.web;

import lombok.Getter;
import lombok.Setter;

//Represents a web request or response
public class Data<T> {
  @Setter
  @Getter
  protected T content;
}
