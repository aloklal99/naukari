package mahendra;

import com.google.common.base.MoreObjects;

import java.util.*;

public class TransactionImpl<K, V> implements Transaction<K, V> {
    private final Map<K, V> _adds = new HashMap<>();
    private final Set<K> _deletes = new HashSet<>();
    private final Transaction<K, V> _parent;

    public TransactionImpl(final Transaction<K, V> parent) {
        _parent = parent;
    }

    @Override
    public Map<K, V> getAdds() {
        return _adds;
    }

    @Override
    public Set<K> getDeletes() {
        return _deletes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("adds", _adds)
                .add("deletes", _deletes)
                .add("parent", _parent)
                .toString();
    }

    @Override
    public V get(K key) {
        if (_deletes.contains(key)) {
            return null;
        } else if (_adds.containsKey(key)) {
            return _adds.get(key);
        } else {
            return _parent.get(key);
        }
    }

    @Override
    public void set(K key, V val) {
        if (_deletes.contains(key)) {
            _deletes.remove(key);
            _adds.put(key, val);
        } else {
            _adds.put(key, val);
        }
    }

    @Override
    public V delete(K key) {
        if (_deletes.contains(key)) {
            return null;
        } else if (_adds.containsKey(key)) {
            return _adds.remove(key);
        } else if (_parent.contains(key)) {
            // delete returns currently mapped value if any
            V val = _parent.get(key);
            _deletes.add(key);
            return val;
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(K key) {
        if (_deletes.contains(key)) {
            return false;
        } else {
            return _adds.containsKey(key) || _parent.contains(key);
        }
    }

    @Override
    public void rollback() {
        _reset();
    }

    @Override
    public void commit() {
        _parent.commit(this);
    }

    @Override
    public void commit(Transaction<K, V> childTransaction) {
        for (K key: childTransaction.getDeletes()) {
            if (_adds.containsKey(key)) {
                _adds.remove(key);
                _deletes.add(key);
            } else if (_deletes.contains(key)) {
                // do nothing, its already there!
            } else if (_parent.contains(key)) {
                _deletes.add(key);
            } else {
                // even parent does not have the key, just ignore it
            }
        }
        for (Map.Entry<K, V> entry: childTransaction.getAdds().entrySet()) {
            if (_deletes.contains(entry.getKey())) {
                _deletes.remove(entry.getKey());
            }
            _adds.put(entry.getKey(), entry.getValue());
        }
    }

    private void _reset() {
        _deletes.clear();
        _adds.clear();
    }

    @Override
    public void close() throws Exception {
        commit();
    }
}
