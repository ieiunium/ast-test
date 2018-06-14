package by.kir.ast.ast;

public class SlashNode extends BiFunctionNode {

    public SlashNode(AstNode left, AstNode right) {
        super(left, right);
    }

    @Override
    public Integer calculate() {
        return left.calculate() / right.calculate();
    }
}
