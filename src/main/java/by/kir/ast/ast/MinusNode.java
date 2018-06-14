package by.kir.ast.ast;

public class MinusNode extends BiFunctionNode {
    public MinusNode(AstNode left, AstNode right) {
        super(left, right);
    }

    @Override
    public Integer calculate() {
        return left.calculate() - right.calculate();
    }
}
