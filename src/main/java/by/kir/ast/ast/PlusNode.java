package by.kir.ast.ast;

public class PlusNode extends BiFunctionNode {

    public PlusNode(AstNode left, AstNode right) {
        super(left, right);
    }

    @Override
    public Integer calculate() {
        return left.calculate() + right.calculate();
    }
}
