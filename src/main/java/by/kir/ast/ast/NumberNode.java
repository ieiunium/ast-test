package by.kir.ast.ast;

public class NumberNode implements AstNode {

    private Integer value;

    public NumberNode(Integer value) {
        this.value = value;
    }

    @Override
    public Integer calculate() {
        return value;
    }

    @Override
    public String toString() {
        return "Node(" + String.valueOf(value) + ")";
    }
}
