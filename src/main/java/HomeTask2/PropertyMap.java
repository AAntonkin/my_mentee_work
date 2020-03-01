package HomeTask2;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class PropertyMap extends Properties {

    public class PropertyHolder extends Properties {
        @Override
        public Enumeration keys() {
            Enumeration keysEnum = super.keys();
            Vector<String> keyList = new Vector<String>();
            while (keysEnum.hasMoreElements())
                keyList.add((String) keysEnum.nextElement());
            Collections.sort(keyList);
            return keyList.elements();
        }
    }

    static private PropertyHolder prop = null;

    @SneakyThrows
    public PropertyMap() {
        String propFileName = "project.property";
        File file = new File(propFileName);
        prop = new PropertyHolder();

        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            prop.load(inputStream);
            inputStream.close();
        }
    }

    Map<Integer, String> mapAsIntKeys;
    Map<Integer, String> mapAsStringKeys;


    public Map<Integer, String> toMapAsIntKeys() {
        if (mapAsIntKeys == null)
            mapAsIntKeys = prop.stringPropertyNames().stream().collect(Collectors.toMap(Integer::parseInt, key -> prop.get(key).toString(), (k, v) -> v, HashMap::new));
        return mapAsIntKeys;
    }

    public Map<String, Integer> toMapAsStringKeys() {
        return toMapAsIntKeys().entrySet().stream()
            .filter(entry -> {
                if (entry.getKey().toString().equals(entry.getValue()))
                    throw new CustomException("key[%s] and value[%s] are the same", entry.getKey(), entry.getValue());
                return true;
            })
            .collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> Integer.parseInt(entry.getValue()), (k, v) -> v, HashMap::new));
    }

    @SneakyThrows
    public PropertyMap saveToFile() {
        PropertyHolder properties = new PropertyHolder();
        for (Map.Entry<String, Integer> entry : toMapAsStringKeys().entrySet()) {
            properties.put(entry.getValue().toString(), entry.getKey());
        }
        properties.store(new FileOutputStream("projectSorted.property"), null);
        return this;
    }

    public static <T, K> void print(Map<T, K> map) {
        Function<Object, String> color = (obj) -> {
            String out = "\u001b[30;1m";
            if (obj.getClass() == Integer.class)
                out = "\u001b[31m";
            else if (obj.getClass() == String.class) out = "\u001b[32;1m";
            return out + obj + "\u001b[30;1m [" + obj.getClass().getSimpleName() + "]";
        };
        for (Map.Entry<T, K> entry : map.entrySet()) {
            System.out.println(color.apply(entry.getValue()) + " = " + color.apply(entry.getKey()));
        }
    }

}