package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;

import java.util.List;
import java.util.stream.Collectors;

public class Generator {

    private final StringBuilder stringBuilder;

    public Generator() {
        this.stringBuilder = new StringBuilder();
    }

    public String generate(AST ast) {
        this.generateNode(ast.root);
        return stringBuilder.toString();
    }

    private void generateNode(ASTNode astNode) {
        for (ASTNode node : astNode.getChildren()) {
            if (node instanceof Stylerule) {
                this.generateSelector(node);

                this.generateDeclaration(node);

                this.stringBuilder.append("}\n\n");
            }
        }

        // Remove one \n character.
        if (this.stringBuilder.length() > 1) {
            this.stringBuilder.delete(this.stringBuilder.length() - 1, this.stringBuilder.length());
        }
    }

    private void generateSelector(ASTNode astNode) {
        Stylerule stylerule = (Stylerule) astNode;

        List<String> selectors = stylerule.selectors.stream()
                .map(ASTNode::toString)
                .collect(Collectors.toList());

        String str = String.join(", ", selectors);
        this.stringBuilder.append(str);

        this.stringBuilder.append(" {\n");
    }

    private void generateDeclaration(ASTNode astNode) {
        for (ASTNode node : astNode.getChildren()) {
            if (node instanceof Declaration) {
                Declaration declaration = (Declaration) node;
                this.stringBuilder.append("  ")
                        .append(declaration.property.name)
                        .append(": ")
                        .append(this.expressionToString(declaration.expression))
                        .append(";\n");
            }
        }
    }

    private String expressionToString(Expression expression) {
        if (expression instanceof PercentageLiteral) {
            return ((PercentageLiteral) expression).value + "%";
        }
        if (expression instanceof PixelLiteral) {
            return ((PixelLiteral) expression).value + "px";
        }
        if (expression instanceof ColorLiteral) {
            return ((ColorLiteral) expression).value + "";
        }

        return "";
    }
}
