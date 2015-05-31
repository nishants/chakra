package chakra.compiler;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ClassLookupTest {
  @Test
  public void shouldLookupClassesFromExternalDependency() throws ClassNotFoundException {
    assertThat(ClassLookup.getClass("org.junit.Test").getName(), is("org.junit.Test"));
  }

}