package co.gov.dane.controller.api.rest.filter;


 
 import java.io.IOException;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;
 
 @Component
 public class SimpleCORSFilter implements javax.servlet.Filter
 {
   @Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
   {
     HttpServletResponse response = (HttpServletResponse)res;
     response.setHeader("Access-Control-Allow-Origin", "*");
     response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
     response.setHeader("Access-Control-Max-Age", "3600");
     response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
     chain.doFilter(req, res);
   }
   
   @Override
public void init(FilterConfig filterConfig) {}
   
   @Override
public void destroy() {}
 }
