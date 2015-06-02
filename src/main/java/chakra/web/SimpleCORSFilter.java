package chakra.web;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    response.addHeader("Access-Control-Allow-Headers", "Content-Type");

    response.setHeader("Access-Control-Max-Age", "3600");

    chain.doFilter(req, res);
    System.out.println("cross origin working.........");
  }

  public void init(FilterConfig filterConfig) {}

  public void destroy() {}

}