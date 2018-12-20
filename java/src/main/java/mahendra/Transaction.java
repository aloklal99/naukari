package mahendra;

import java.util.Map;
import java.util.Set;

interface Transaction<K, V> extends AutoCloseable {
    V get(K key);
    void set(K key, V val);

    /**
     * Return the current value mapped to K, if any
     * @param key
     * @return
     */
    V delete(K key);
    boolean contains(K key);
    void rollback();
    void commit();
    void commit(final Transaction<K, V> childTransaction);
    Map<K, V> getAdds();
    Set<K> getDeletes();
}
