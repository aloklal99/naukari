package mahendra;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static javafx.scene.input.KeyCode.M;
import static org.junit.Assert.*;

public class FlattenMapTest {
    private static final Logger Logger_ = LoggerFactory.getLogger(FlattenMapTest.class);

    private final FlattenMap flattener_ = new FlattenMap();
    private final Random random_ = new Random();

    @Test
    public void nullOrEmptyGetsBackEmpty() {
        Map<String, Object> result;
        result = flattener_.flatten(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());

        result = flattener_.flatten(new HashMap<String, Object>());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void maps() {
        List<Map<String, Object>> nestedDicts = Lists.newArrayList(createDict(5), createDict(10), createDict(15));
        for (Map<String, Object> aDict : nestedDicts) {
            System.out.println("Before flattening:");
            System.out.println("\t" + aDict);
            System.out.println("After  flattening:");
            System.out.println("\t" + flattener_.flatten(aDict));
        }
    }

    /**
     * This generates a random multi-level dictionary
     * @param count
     * @return
     */
    private Map<String, Object> createDict(int count) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < count; i++) {
            final String key = String.format("k-%d", i);
            final int value = random_.nextInt(100);
            add(result, key, value);
        }
        return result;
    }

    private void add(Map<String, Object> root, String key, int value) {
        final boolean createNewEntry = random_.nextBoolean();
        if (createNewEntry || root.isEmpty()) {
            root.put(key, value);
        } else {
            final String existingKey = chooseParentKey(root);
            final Object existingValue = root.get(existingKey);
            if (existingValue instanceof HashMap) {
                add((Map<String, Object>)existingValue, key, value);
            } else { // create a new map that has existing value and new key/value
                final Map<String, Object> aMap = new HashMap<>(2);
                aMap.put(key, value);
                aMap.put(existingKey, existingValue);  // this creates existingKey within the existingKey map
                root.put(existingKey, aMap);
            }
        }

    }

    private String chooseParentKey(Map<String, Object> root) {
        List<String> keys = new ArrayList<>(root.keySet());
        return keys.get(random_.nextInt(keys.size()));
    }

}