package br.edu.unochapeco.unoheartserver.filter;

import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestAuthenticationFilter implements javax.servlet.Filter {

   public static final String AUTHENTICATION_HEADER = "Authorization";

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) {

      if (request instanceof HttpServletRequest) {
         HttpServletRequest httpServletRequest = (HttpServletRequest) request;
         String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
         try {
            boolean authenticationStatus = true;
//            boolean authenticationStatus;
//            try {
//               AuthenticationService authenticationService = new AuthenticationService();
//
//               authenticationStatus = authenticationService.authenticate(authCredentials);
//            } catch (UnsupportedEncodingException ex) {
//               Logger.getLogger(RestAuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
//               authenticationStatus = false;
//            }

            if (authenticationStatus) {
               filter.doFilter(request, response);
            } else if (response instanceof HttpServletResponse) {
               HttpServletResponse httpServletResponse = (HttpServletResponse) response;
               String mensagem = httpServletRequest.getRemoteHost() + " sem autorização";

               httpServletResponse.reset();

               httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
               httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);                
               httpServletResponse.setCharacterEncoding(Constantes.ENCODING_PADRAO);
               PrintWriter out = httpServletResponse.getWriter();
               
               Gson g = CustomGsonBuilder.getInstance();
               FilterError error = new FilterError(HttpServletResponse.SC_UNAUTHORIZED, mensagem);
               out.write(g.toJson(error));

               Logger.getLogger(RestAuthenticationFilter.class.getName()).log(Level.SEVERE, mensagem);
            }
         } catch (IOException | ServletException ex) {
            Logger.getLogger(RestAuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
         }
      }

   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {

   }

   @Override
   public void destroy() {

   }
}
