package chakra.spikes;

import chakra.compiler.InMemoryJavaFile;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DynamicTestRunner {
  private static final String ATestClass =
      "   package chakra;                                                      " +
      "   import org.junit.Test;                                               " +
      "   import static org.hamcrest.CoreMatchers.is;                          " +
      "   import static org.junit.Assert.assertThat;                           " +
      "   import a.AClass;                                                     " +
      "   public class ATestClass {                                            " +
      "     @Test                                                              " +
      "     public void testMe() {                                             " +
      "       assertThat(1, is(new a.AClass().getOne()));                      " +
      "     }                                                                  " +
      "     @Test                                                              " +
      "     public void testMeAgain() {                                        " +
      "       assertThat(1, is(new AClass().getTwo()));                        " +
      "     }                                                                  " +
      "   }                                                                    ";

    private static final String AClass =
      "   package a;                                                     " +
      "   public class AClass {                                          " +
      "     public int getOne() {                                        " +
      "       return 1;                                                  " +
      "     }                                                            " +
      "     public int getTwo() {                                        " +
      "       return 2;                                                  " +
      "     }                                                            " +
      "   }                                                              ";


  @Test
  public void shouldRuntATestFile() throws Exception {
    final Map<String, Boolean> testsRan = new HashMap<String, Boolean>();
    RunNotifier notifier = new RunNotifier(){
      private String thisTestName = "null";

      @Override
      public void fireTestStarted(final Description description) throws StoppedByUserException {
        thisTestName= description.getMethodName();
      }

      @Override
      public void fireTestFailure(Failure failure) {
        testsRan.put(thisTestName, false);
        thisTestName = null;
      }

      @Override
      public void fireTestFinished(final Description description) {
        if(thisTestName != null){
          testsRan.put(thisTestName, true);
        }
      }

      };

    new MockitoJUnitRunner(
        compiledTestClassFrom(
            new InMemoryJavaFile("a.AClass", AClass),
            new InMemoryJavaFile("chakra.ATestClass", ATestClass)
        )).run(notifier);

    Thread.sleep(5000);
    assertThat(testsRan.size(), is(2));
    assertThat(testsRan.get("testMe"), is(true));
    assertThat(testsRan.get("testMeAgain"), is(false));
  }

  private Class compiledTestClassFrom(InMemoryJavaFile... inMemoryJavaFile) throws Exception {
    return chakra.compiler.Compiler.create().compile(inMemoryJavaFile).getCompiledClasses().get(1);
  }

}
