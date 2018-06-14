package by.kir.ast.ast;

public class StarNode extends BiFunctionNode {

    public StarNode(AstNode left, AstNode right) {
        super(left, right);
    }

    @Override
    public Integer calculate() {
        return left.calculate() * right.calculate();
    }
}
