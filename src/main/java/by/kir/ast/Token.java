package by.kir.ast;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {

    private final TokenType tokenType;
    private final String src;
}
