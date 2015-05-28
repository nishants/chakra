package chakra.runner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.InetAddress;

import static org.mockito.Mockito.mock;

public class SecurityManagerTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void shouldNotAllowSocketConnections(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkConnect("", 1);
  }

  @Test
  public void shouldNotAllowSocketConnections2(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkConnect("", 1, new Object());
  }

  @Test
  public void shouldNotAllowAcceptingSocketConnections(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkAccept("", 1);
  }

  @Test
  public void shouldNotAllowThreadAccess(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkAccess(new Thread());
  }

  @Test
  public void shouldNotAllowThreadGroupAccess(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkAccess(mock(ThreadGroup.class));
  }

  @Test
  public void checkCreateClassLoader(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkCreateClassLoader();
  }
  @Test
  public void checkDelete(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkDelete("");
  }

  @Test
  public void checkExec(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkExec("any-command");
  }


  @Test
  public void checkExit(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkExit(0);
  }


  @Test
  public void checkLink(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkLink("any-library");
  }

  @Test
  public void checkListen(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkListen(1);
  }

  @Test
  public void checkMulticast(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkMulticast(mock(InetAddress.class));
  }


  @Test
  public void checkMulticastD(){
    thrown.expect(ViolationException.class);
    new SecurityManager().checkMulticast(mock(InetAddress.class), new Byte("1"));
  }

//  @Test
//  public void checkPackageAccess(){
//    thrown.expect(ViolationException.class);
//    new SecurityManager().checkPackageAccess("chakra.web");
//  }
//
//  @Test
//  public void checkPackageDefinition(){
//    thrown.expect(ViolationException.class);
//    //What about base package
//    new SecurityManager().checkPackageDefinition("chakra.web");
//  }

}