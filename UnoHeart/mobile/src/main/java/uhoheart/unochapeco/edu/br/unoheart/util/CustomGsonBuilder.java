package uhoheart.unochapeco.edu.br.unoheart.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Eduardo on 16/01/2017.
 */

public class CustomGsonBuilder {
    private static Gson gson;

    public static Gson getInstance() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, (JsonSerializer<Date>) new JsonSerializer<Date>() {
                        public JsonElement serialize(Date t, java.lang.reflect.Type type, JsonSerializationContext jsc) {
                            if (t != null) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                return new JsonPrimitive(simpleDateFormat.format(t));
                            } else {
                                return null;
                            }
                        }
                    })
                    .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) new JsonDeserializer<Date>() {
                        public Date deserialize(JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) {
                            if (je.getAsJsonPrimitive().getAsString() != null) {
                                SimpleDateFormat simpleDateFormat = null;
                                if (je.getAsString().trim().length() > 10) {
                                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                } else {
                                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                }
                                try {
                                    return simpleDateFormat.parse(je.getAsString().trim());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                return null;
                            }
                            return null;
                        }
                    })
                    .registerTypeAdapter(Timestamp.class, (JsonSerializer<Timestamp>) new JsonSerializer<Timestamp>() {
                        public JsonElement serialize(Timestamp t, java.lang.reflect.Type type, JsonSerializationContext jsc) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            return new JsonPrimitive(simpleDateFormat.format(t));
                        }
                    })
                    .registerTypeAdapter(Timestamp.class, (JsonDeserializer<Timestamp>) new JsonDeserializer<Timestamp>() {
                        public Timestamp deserialize(JsonElement je, java.lang.reflect.Type type, JsonDeserializationContext jdc) {
                            if (je.getAsJsonPrimitive().getAsString() != null) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                System.out.println(je.getAsString());

                                try {
                                    return new Timestamp(simpleDateFormat.parse(je.getAsString().trim()).getTime());
                                } catch (ParseException ex) {
                                    Logger.getLogger(CustomGsonBuilder.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                            return null;
                        }
                    })
                    .create();
        }
        return gson;
    }
}
