package chakra.runner;

import lombok.Getter;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

import java.util.ArrayList;
import java.util.List;

public class TestListener extends RunNotifier {
  @Getter
  private List<TestResult> testResults = new ArrayList<TestResult>();

  private String currentTestName;

  @Override
  public void fireTestStarted(final Description description) throws StoppedByUserException {
    currentTestName = description.getMethodName();
  }

  @Override
  public void fireTestFailure(Failure failure) {
    testResults.add(new TestResult(currentTestName, "failed", failure.getTrace().split("at chakra\\.runner\\.TestRunner\\.run")[0]));
    currentTestName = null;
  }

  @Override
  public void fireTestFinished(final Description description) {
    if (currentTestName != null) {
      testResults.add(new TestResult(currentTestName, "success", ""));
    }
  }
}
