package br.edu.unochapeco.unoheartserver.filter;


import br.edu.unochapeco.unoheartserver.controller.resources.AuthorizationResources;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import java.io.UnsupportedEncodingException;

import java.util.Base64;
import java.util.StringTokenizer;

public class AuthenticationService {

   public boolean authenticate(String authCredentials) throws UnsupportedEncodingException {
       if (authCredentials == null) {
           return false;
       }
       String authorization;
       byte[] decodedBytes = Base64.getDecoder().decode(authCredentials);
       authorization = new String(decodedBytes, Constantes.ENCODING_PADRAO);
       final StringTokenizer tokenizer = new StringTokenizer(authorization, ":");
       String token = "";
       if (tokenizer.hasMoreTokens()) {
           token = tokenizer.nextToken();
       }
       
       boolean authenticationStatus = new AuthorizationResources().containsKey(token);
       return authenticationStatus;
   }
}
