package mahendra;

import java.util.HashMap;
import java.util.Map;

public class FlattenMap {

    /**
     * Given a multi-level dictionary convert it to a flattended dictionary, e.g.
     * {
     *  a: 1,
     *  b: {
     *      b1: 2,
     *      c1: 3,
     *      d1: {
     *          dd: 4
     *      },
     *      e1: 0
     *  },
     *  c: 45
     *  }
     *
     *  Should be converted to:
     *  {
     *      a: 1,
     *      b.b1: 2,
     *      b.c1: 3,
     *      b.d1.dd: 4,
     *      b.e1: 0
     *      c: 45
     *  }
     * @param dict
     * @return
     */
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
