package mahendra;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryDb<K, V> implements Transaction<K, V> {
    final String _filename;
    final Map<K, V> _map;

    public InMemoryDb(final String filename) {
        _filename = filename;
        _map = loadData(_filename);
    }

    private Map<K, V> loadData(final String filename) {
        // TODO
        return new HashMap<>();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("filename", _filename)
                .add("data", _map)
                .toString();
    }

    @Override
    public V get(K key) {
        return _map.get(key);
    }

    @Override
    public void set(K key, V val) {
        _map.put(key, val);
    }

    @Override
    public V delete(K key) {
        return _map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return _map.containsKey(key);
    }

    @Override
    public void commit(Transaction<K, V> childTransaction) {
        for (K key: childTransaction.getDeletes()) {
            _map.remove(key);
        }
        for (Map.Entry<K, V> entry: childTransaction.getAdds().entrySet()) {
            _map.put(entry.getKey(), entry.getValue());
        }
    }

    // these methods are not relevant for DB, they are only meant for transaction.  They are
    @Override
    public void rollback() {
        throw new IllegalStateException("Unexpected method called!");
    }

    @Override
    public void commit() {
        throw new IllegalArgumentException("Unexpected method called!");
    }

    @Override
    public Map<K, V> getAdds() {
        throw new IllegalStateException("Unexpected method called!");
    }

    @Override
    public Set<K> getDeletes() {
        throw new IllegalStateException("Unexpected method called!");
    }

    Transaction<K, V> beingTransaction(Transaction<K, V> parent) {
        return new TransactionImpl(parent);
    }

    Transaction<K, V> beingTransaction() {
        return new TransactionImpl(this);
    }

    @Override
    public void close() throws Exception {
        commit();
    }
}
