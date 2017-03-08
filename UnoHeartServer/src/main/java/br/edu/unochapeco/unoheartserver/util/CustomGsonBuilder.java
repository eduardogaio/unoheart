/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Eduardo
 */
public class CustomGsonBuilder {

    private static Gson gson;

    public static Gson getInstance() {
        if (gson == null) {

            gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) new JsonSerializer<LocalDate>() {

                        @Override
                        public JsonElement serialize(LocalDate t, java.lang.reflect.Type type, JsonSerializationContext jsc) {
                            if (t != null) {
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                return new JsonPrimitive(t.format(dateTimeFormatter));
                            } else {
                                return null;
                            }
                        }
                    })
                    .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) new JsonDeserializer<LocalDate>() {

                        @Override
                        public LocalDate deserialize(JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) {
                            if (je.getAsJsonPrimitive().getAsString() != null) {
                                return LocalDate.parse(je.getAsJsonPrimitive().getAsString().substring(0, 10).trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            } else {
                                return null;
                            }
                        }
                    })
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) new JsonSerializer<LocalDateTime>() {

                        @Override
                        public JsonElement serialize(LocalDateTime t, java.lang.reflect.Type type, JsonSerializationContext jsc) {
                            if (t != null) {
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                                return new JsonPrimitive(t.format(dateTimeFormatter));
                            } else {
                                return null;
                            }
                        }
                    })
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) -> {
                        if (je.getAsJsonPrimitive().getAsString() != null) {
                            return LocalDateTime.parse(je.getAsJsonPrimitive().getAsString().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                        } else {
                            return null;
                        }
                    })
                    .create();
        }
        return gson;
    }

}
