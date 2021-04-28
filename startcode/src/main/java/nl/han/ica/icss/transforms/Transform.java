package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.AST;

public interface Transform {
    void apply(AST ast);
}
