package by.kir.ast.ast;

public abstract class BiFunctionNode implements AstNode {

    protected final AstNode left;
    protected final AstNode right;

    public BiFunctionNode(AstNode left, AstNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public abstract Integer calculate();
}
