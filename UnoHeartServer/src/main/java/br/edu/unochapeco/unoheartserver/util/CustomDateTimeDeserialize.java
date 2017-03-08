/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 *
 * @author Eduardo
 */
public class CustomDateTimeDeserialize extends JsonDeserializer<LocalDateTime>{

   @Override
   public LocalDateTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
      return LocalDateTime.parse(jp.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
   }
   
}
