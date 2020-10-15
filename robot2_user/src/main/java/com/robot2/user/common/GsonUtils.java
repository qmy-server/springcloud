package com.robot2.user.common;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GsonUtils {
    public static Gson getMapGson(){
        Gson gson=new GsonBuilder().registerTypeAdapter(Map.class, new JsonDeserializer<Map>() {
            public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException{
                HashMap<String,Object> resultMap=new HashMap<>();
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    resultMap.put(entry.getKey(),entry.getValue());
                }
                return resultMap;
            }
        }).create();
        return gson;
    }
}