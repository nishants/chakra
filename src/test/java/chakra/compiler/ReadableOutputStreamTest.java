package chakra.compiler;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadableOutputStreamTest {
  @Test
  public void shouldReturnTextFlushed(){
    ReadablePrintStream out = new ReadablePrintStream(new ByteArrayOutputStream());
    out.print("i am");
    out.print(" batman");
    out.println();
    out.print("hello");
    assertThat(out.read(), is(new String[]{"i am batman", "hello"}));
  }

}