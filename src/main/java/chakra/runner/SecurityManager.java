package chakra.runner;

import java.net.InetAddress;

public class SecurityManager extends java.lang.SecurityManager{
  @Override public void checkConnect(String host, int port) {throw new ViolationException();}
  @Override public void checkConnect(String host, int port, Object context) {throw new ViolationException();}
  @Override public void checkAccess(Thread t) {throw new ViolationException();}
  @Override public void checkAccess(ThreadGroup t) {throw new ViolationException();}
  @Override public void checkCreateClassLoader() {throw new ViolationException();}
  @Override public void checkDelete(String file) {throw new ViolationException();}
  @Override public void checkExec(String cmd) {throw new ViolationException();}
  @Override public void checkExit(int status) {throw new ViolationException();}
  @Override public void checkLink(String any) {throw new ViolationException();}
  @Override public void checkAccept(String host, int port) {throw new ViolationException();}
  @Override public void checkListen(int port) {throw new ViolationException();}
  @Override public void checkMulticast(InetAddress any) {throw new ViolationException();}
  @Override public void checkMulticast(InetAddress maddr, byte ttl) {throw new ViolationException();}
//  @Override public void checkPackageAccess(String pkg) {throw new ViolationException();}
  }
