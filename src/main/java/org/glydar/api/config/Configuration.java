package org.glydar.api.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author YoshiGenius
 */
public class Configuration {

    public static Configuration loadConfiguration(File file) {
        Configuration c = new Configuration();
        if (!c.load(file)) {
            return null;
        }
        return c;
    }

    private HashMap<String, String> keys = new HashMap<>();

    public boolean load(File file) {
        if (file == null || !file.getName().endsWith(".cfg")) {
            return false;
        } else {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    if (line == null || line.trim().equals("") || line.startsWith("#")) {
                        continue;
                    }
                    String[] values = line.split("=", 2);
                    if (values.length != 2) {
                        continue;
                    }
                    keys.put(values[0].trim().toLowerCase(), values[1].trim());
                }
                scanner.close();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public String getString(String key) {
        return keys.get(key.trim().toLowerCase()).trim();
    }

    public boolean contains(String key) {
        return keys.containsKey(key.trim());
    }

    public Configuration set(String key, Object value) {
        String toValue = value.toString();
        if (key == null || key.equals("") || toValue.equals("")) {
            return this;
        } else {
            keys.put(key.trim().toLowerCase(), toValue.trim());
            return this;
        }
    }

    public Configuration save(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            for (String key : keys.keySet()) {
                String value = keys.get(key);
                output.write(key + '=' + value + '\n');
            }
            output.close();
        } catch (Exception e) {
            return this;
        }
        return this;
    }

}