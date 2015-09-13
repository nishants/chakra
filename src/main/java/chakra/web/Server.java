package chakra.web;

import chakra.compiler.CompilationResult;
import chakra.compiler.Compiler;
import chakra.compiler.InMemoryJavaFile;
import chakra.runner.ExecuteMainResult;
import chakra.runner.ExecuteTestResult;
import chakra.runner.MainRunner;
import chakra.runner.TestRunner;
import chakra.web.request.ExecuteMainRequest;
import chakra.web.request.ExecuteTestsRequest;
import chakra.web.request.JavaFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/runner")
public class Server {
  @RequestMapping(value = "/main", method = POST)
  public Data<ExecuteMainResult> runMainMethod(@RequestBody Data<ExecuteMainRequest> request) {
    ExecuteMainRequest requestContent = request.getContent();
    CompilationResult compilationResult = null;
    try {
      compilationResult = Compiler.create().compile(toSourceCode(requestContent.getJavaFiles()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if(compilationResult.getError() != null){
      return error("Failed to compile");
    }
    return response(MainRunner.run(requestContent.getMainClass(), compilationResult.getCompiledClasses()));
  }

  @RequestMapping(value = "/test", method = POST)
  public Data<ExecuteTestResult> runtTests(@RequestBody Data<ExecuteTestsRequest> request) {
    ExecuteTestsRequest requestContent = request.getContent();
    List<JavaFile> filesToCompile = new ArrayList<JavaFile>();
    filesToCompile.addAll(requestContent.getMain());
    filesToCompile.addAll(requestContent.getTest());


    CompilationResult compiled = null;
    try {
      compiled = Compiler.create().compile(toSourceCode(filesToCompile.toArray(new JavaFile[0])));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response(TestRunner.run(findMatchingClasses(compiled.getCompiledClasses(), requestContent.getTest())));
  }

  private List<Class> findMatchingClasses(List<Class> compiledClasses, List<JavaFile> files) {
    List<Class> classes = new ArrayList<Class>();
    for(JavaFile file: files){
      for(Class claz : compiledClasses){
        if(file.getClassName().equals(claz.getName())){
          classes.add(claz);
        }
      }
    }

    return classes;
  }

  private InMemoryJavaFile[] toSourceCode(JavaFile[] javaFiles) throws Exception {
    InMemoryJavaFile[] sourceCode = new InMemoryJavaFile[javaFiles.length];
    for (int i = 0; i < javaFiles.length; i++) {
      sourceCode[i] = new InMemoryJavaFile(
          javaFiles[i].getClassName(),
          javaFiles[i].getJavaCode());
    }
    return sourceCode;
  }

  private <T> Data<T> error(String error) {
    return new Data<T>(null, error);
  }
  private <T> Data<T> response(T okay) {
    return new Data<T>(okay, null);
  }
}
