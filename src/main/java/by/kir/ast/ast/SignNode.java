package by.kir.ast.ast;

import by.kir.ast.Token;
import by.kir.ast.TokenType;

public class SignNode implements AstNode {

    private Token token;
    private SignType signType;

    public SignNode(Token token) {
        this.token = token;
        if (token.getTokenType() != TokenType.SIGH) {
            throw new UnsupportedOperationException();
        }
        signType = SignType.parseSrc(token.getSrc());
    }

    public Token getToken() {
        return token;
    }

    public SignType getSignType() {
        return signType;
    }

    @Override
    public Integer calculate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Node{" + token.getSrc() + "}";
    }
}
