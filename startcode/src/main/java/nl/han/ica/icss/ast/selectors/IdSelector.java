package nl.han.ica.icss.ast.selectors;

import nl.han.ica.icss.ast.Selector;

import java.util.Objects;

public class IdSelector extends Selector {
    public String id;

    public IdSelector(String id) {
        this.id = id;
    }

    public String getNodeLabel() {
        return "IdSelector " + id;
    }
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IdSelector that = (IdSelector) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
