package chakra.web;

import chakra.compiler.CompilationResult;
import chakra.compiler.Compiler;
import chakra.compiler.SourceCode;
import chakra.runner.MainRunner;
import chakra.web.request.ExecuteMainRequest;
import chakra.web.request.JavaFile;
import chakra.runner.ExecuteMainResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/runner")
public class Runner {
  @RequestMapping(value = "/main", method = POST)
  public Data<ExecuteMainResult> runMainMethod(@RequestBody Data<ExecuteMainRequest> request) throws Exception {
    ExecuteMainRequest requestContent = request.getContent();
    CompilationResult compilationResult = Compiler.create().compile(toSourceCode(MainRunner.preprocess(requestContent.getJavaFiles())));
    return response(MainRunner.run(requestContent.getMainClass(), compilationResult.getCompiledClasses()));
  }

  private SourceCode[] toSourceCode(JavaFile[] javaFiles) throws Exception {
    SourceCode[] sourceCode = new SourceCode[javaFiles.length];
    for (int i = 0; i < javaFiles.length; i++) {
      sourceCode[i] = new SourceCode(
          javaFiles[i].getClassName(),
          javaFiles[i].getJavaCode());
    }
    return sourceCode;
  }

  private <T> Data<T> response(T okay) {
    return new Data<T>(okay);
  }
}
