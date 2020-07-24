package org.ga4gh.registry.testutils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.ga4gh.registry.model.RegistryModel;
import org.ga4gh.registry.model.Standard;
import org.ga4gh.registry.testutils.deserializer.StandardDeserializer;
import org.ga4gh.registry.util.serialize.serializers.VariableDepthSerializer;

public class JsonResponseLoader {

    private static Map<Class<? extends RegistryModel>, String> modelDirectories = Map.ofEntries(
        Map.entry(Standard.class, "standards")
    );

    private static Map<Class<? extends RegistryModel>, Class<? extends JsonDeserializer<? extends RegistryModel>>> deserializers = Map.ofEntries(
        Map.entry(Standard.class, StandardDeserializer.class)
    );

    //private static Map<Class<? extends RegistryModel>, Class<? extends VariableDepthSerializer<? extends RegistryModel>>> serializers = Map.ofEntries(
        // Map.entry(Standard.class, v)
    //)

    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static String resourcePath(Class<?> modelClass, String method, String number) {
        String modelName = modelDirectories.get(modelClass);
        return "/responses/" + modelName + "/" + method + capitalize(modelName) + number + ".json";
    }

    public static String load(Class<?> modelClass, String method, String number) throws IOException {
        String filename = JsonResponseLoader.class.getResource(resourcePath(modelClass, method, number)).getFile();
        FileInputStream fs = new FileInputStream(filename);
        byte[] bytes = fs.readAllBytes();
        String json = new String(bytes, "UTF-8");
        fs.close();

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        // module.addDeserializer(modelClass, deserializers.get(modelClass).getDeclaredConstructor().newInstance());
        // module.addSerializer(modelClass, s)
        
        /*
        FileInputStream fs = new FileInputStream(filename);
        byte[] bytes = fs.readAllBytes();
        String data = new String(bytes, "UTF-8");
        data = data.replaceAll("\n", "");
        fs.close();
        return data;
        */
    }
    
}