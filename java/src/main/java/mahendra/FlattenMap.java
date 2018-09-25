package mahendra;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.HashMap;
import java.util.Map;

public class FlattenMap {

    Map<String, Object> flatten(Map<String, Object> dict) {
        final Map<String, Object> result = new HashMap<>();
        if (dict != null && !dict.isEmpty()) {
            for (Map.Entry<String, Object> entry : dict.entrySet()) {
                addKey(result, "", entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private void addKey(final Map<String, Object> target, final String prefix, final String key, final Object value) {
        if (value instanceof HashMap) {
            for (Map.Entry<String, Object> entry : ((HashMap<String, Object>) value).entrySet()) {
                addKey(target, makeKey(prefix, key), entry.getKey(), entry.getValue());
            }
        } else {
            target.put(makeKey(prefix, key), value);
        }
    }

    private String makeKey(final String prefix, final String key) {
        if (prefix == null || prefix.isEmpty()) {
            return key;
        } else {
            return String.format("%s.%s", prefix, key);
        }
    }
}
