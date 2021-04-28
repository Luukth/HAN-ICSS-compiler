package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * An assignment binds a expression to an identifier.
 *
 */
public class VariableAssignment extends ASTNode {
	
	public VariableReference name;
	public Expression expression;

	@Override
	public String getNodeLabel() {
		return "VariableAssignment (" + name.name + ")";
	}

	@Override
	public ASTNode addChild(ASTNode child) {
		if(name == null) {
			name = (VariableReference) child;
		} else if(expression == null) {
			expression = (Expression) child;
		}

		return this;
	}

	@Override
	public ArrayList<ASTNode> getChildren() {

		ArrayList<ASTNode> children = new ArrayList<>();
		if(name != null)
			children.add(name);
		if(expression != null)
			children.add(expression);
		return children;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		VariableAssignment that = (VariableAssignment) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(expression, that.expression);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, expression);
	}
}
