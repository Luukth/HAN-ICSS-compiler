package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;
import java.util.Objects;

public class BoolLiteral extends Literal {
    public boolean value;

    public BoolLiteral(boolean value) {
        this.value = value;
    }
    public BoolLiteral(String text) {
        this.value = text.equals("TRUE");
    }
    @Override
    public String getNodeLabel() {
        String textValue = value ? "TRUE" : "FALSE";
        return "Bool Literal (" + textValue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BoolLiteral that = (BoolLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
