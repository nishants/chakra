package chakra.compiler;

import chakra.runner.ReadablePrintStream;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadablePrintStreamTest {

  private ReadablePrintStream out;

  @Before
  public void setUp() throws Exception {
    out = new ReadablePrintStream(new ByteArrayOutputStream());
  }

  @Test
  public void shouldReturnTextFlushed(){
    out.print("i am");
    out.print(" batman");
    out.println();
    out.print("hello");
    assertThat(out.read(), is(new String[]{"i am batman", "hello"}));
  }

}