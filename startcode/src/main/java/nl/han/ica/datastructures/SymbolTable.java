package nl.han.ica.datastructures;

import java.util.HashMap;

public class SymbolTable<K, V> {
    private final IHANLinkedList<HashMap<K, V>> scopes;

    public SymbolTable() {
        this.scopes = new HANLinkedList<>();
    }

    public void pushScope() {
        this.scopes.addFirst(new HashMap<>());
    }

    public void popScope() {
        this.scopes.removeFirst();
    }

    public void putVariable(K key, V value) {
        this.scopes.getFirst().put(key, value);
    }

    public V getVariable(K key) {
        for (HashMap<K, V> scope : scopes) {
            V result = scope.get(key);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public int getTotalAmountOfScopes() {
        return scopes.getSize();
    }
}
