package chakra.runner;

import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TestRunner {

  public static ExecuteTestResult run(List<Class> testClasses) {

    ExecuteTestResult result = new ExecuteTestResult();

    for(Class testClass : testClasses){
      try {
        TestListener listener = new TestListener();
        new MockitoJUnitRunner(testClass).run(listener);
        result.getTests().add(new TestClassReport(testClass.getName(), listener.getTestResults()));
      } catch (InvocationTargetException e) {
        //TODO return error to user
        e.printStackTrace();
      }
    }
    return result;
  }
}
