package by.kir.ast;

import by.kir.ast.ast.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CalcEngine {

    public Integer eval(String in) {


        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < in.length(); ) {

            final char value = in.charAt(i);
            final TokenType tokenType = getTokenType(value);
            validate(value);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(value));

            for (i = i + 1; i < in.length(); i++) {
                final char character = in.charAt(i);
                final TokenType charTokenType = getTokenType(character);
                if (charTokenType == tokenType && tokenType == TokenType.NUMBER) {
                    stringBuilder.append(character);
                } else {
                    tokens.add(new Token(tokenType, stringBuilder.toString()));
                    stringBuilder = null;
                    break;
                }
            }
            if (stringBuilder != null) {
                tokens.add(new Token(tokenType, stringBuilder.toString()));
            }
        }
        List<AstNode> collect = tokens
                .stream()
                .filter(s -> !s.getSrc().trim().equals(""))
                .map(token -> {
                    if (token.getTokenType() == TokenType.SIGH) {
                        return new SignNode(token);
                    } else if (token.getTokenType() == TokenType.NUMBER) {
                        return new NumberNode(Integer.valueOf(token.getSrc()));
                    } else {
                        throw new UnsupportedOperationException();
                    }
                })
                .collect(Collectors.toList());
        List<AstNode> compiledTree = compile(collect);

        System.out.println(collect);
        return compiledTree.stream().findFirst().get().calculate();
    }

    private List<AstNode> compile(final List<AstNode> in) {

        LinkedList<AstNode> astNodes = new LinkedList<>(in);
        while (true) {
            Tuple2<Integer, Integer> bracesIndexes = getBraceIndexes(astNodes);
            final int indexOfLeftBrace = bracesIndexes._1;
            final int indexOfRightBrace = bracesIndexes._2;
            if (indexOfLeftBrace > indexOfRightBrace) {
                throw new IllegalArgumentException();
            }
            if (indexOfLeftBrace != -1 && indexOfRightBrace != -1) {
                List<AstNode> compile = compilePlain(
                        new LinkedList<>(
                                astNodes.subList(indexOfLeftBrace, indexOfRightBrace + 1)
                        )
                );
                for (int i = indexOfRightBrace; i >= indexOfLeftBrace; i--) {
                    astNodes.remove(i);
                }
                astNodes.addAll(indexOfLeftBrace, compile);
            } else {
                astNodes = compilePlain(astNodes);
            }

            if (astNodes.size() == 1) {
                return astNodes;
            }
        }
    }

    private LinkedList<AstNode> compilePlain(LinkedList<AstNode> astNodes) {
        if (astNodes.getFirst() instanceof SignNode &&
                ((SignNode) astNodes.getFirst()).getSignType() == SignType.LEFT_BRACE) {
            astNodes.removeFirst();
        }
        if (astNodes.getLast() instanceof SignNode &&
                ((SignNode) astNodes.getLast()).getSignType() == SignType.RIGHT_BRACE) {
            astNodes.removeLast();
        }
        while (true) {
            int indexOfSlashOrStar = -1;
            int indexOfPlusOrMinus = -1;
            for (int i = 0; i < astNodes.size(); i++) {
                AstNode astNode = astNodes.get(i);
                if (astNode instanceof SignNode) {
                    SignNode signNode = (SignNode) astNode;
                    if (signNode.getSignType() == SignType.STAR ||
                            signNode.getSignType() == SignType.SLASH) {
                        indexOfSlashOrStar = i;
                        break;
                    }
                }
                if (indexOfPlusOrMinus == -1) {
                    if (astNode instanceof SignNode) {
                        SignNode signNode = (SignNode) astNode;
                        if (signNode.getSignType() == SignType.PLUS ||
                                signNode.getSignType() == SignType.MINUS) {
                            indexOfPlusOrMinus = i;
                        }
                    }
                }
            }
            if (indexOfSlashOrStar != -1) {
                int j = indexOfSlashOrStar;
                AstNode astNode = astNodes.get(j);
                SignNode signNode = (SignNode) astNode;
                if (signNode.getSignType() == SignType.STAR) {
                    StarNode starNode = new StarNode(astNodes.get(j - 1), astNodes.get(j + 1));
                    astNodes.set(j, starNode);
                    astNodes.remove(j + 1);
                    astNodes.remove(j - 1);
                } else if (signNode.getSignType() == SignType.SLASH) {
                    SlashNode slashNode = new SlashNode(astNodes.get(j - 1), astNodes.get(j + 1));
                    astNodes.set(j, slashNode);
                    astNodes.remove(j + 1);
                    astNodes.remove(j - 1);
                } else {
                    throw new IllegalArgumentException();
                }
            } else if (indexOfPlusOrMinus != -1) {
                int j = indexOfPlusOrMinus;
                AstNode astNode = astNodes.get(j);
                SignNode signNode = (SignNode) astNode;
                if (signNode.getSignType() == SignType.PLUS) {
                    PlusNode plusNode = new PlusNode(astNodes.get(j - 1), astNodes.get(j + 1));
                    astNodes.set(j, plusNode);
                    astNodes.remove(j + 1);
                    astNodes.remove(j - 1);
                } else if (signNode.getSignType() == SignType.MINUS) {
                    MinusNode minusNode = new MinusNode(astNodes.get(j - 1), astNodes.get(j + 1));
                    astNodes.set(j, minusNode);
                    astNodes.remove(j + 1);
                    astNodes.remove(j - 1);
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                break;
            }
        }
        return astNodes;
    }

    private Tuple2<Integer, Integer> getBraceIndexes(List<AstNode> collect) {
        int indexOfLeftBrace = -1;
        int indexOfRightBrace = -1;
        for (int i = 0; i < collect.size(); i++) {
            if (indexOfLeftBrace == -1) {
                int index = collect.size() - 1 - i;
                AstNode astNode = collect.get(index);
                if (astNode instanceof SignNode) {
                    SignNode signNode = (SignNode) astNode;
                    if (signNode.getSignType() == SignType.LEFT_BRACE) {
                        indexOfLeftBrace = index;
                    }
                }
            }
            if (indexOfRightBrace == -1) {
                AstNode astNode = collect.get(i);
                if (astNode instanceof SignNode) {
                    SignNode signNode = (SignNode) astNode;
                    if (signNode.getSignType() == SignType.RIGHT_BRACE) {
                        indexOfRightBrace = i;
                    }
                }
            }
        }
        return Tuple2.of(indexOfLeftBrace, indexOfRightBrace);
    }

    private void validate(char value) {
        if (!isDigit(value) && !isSign(value) && value != ' ') {
            throw new RuntimeException("Char " + value + " not allowed");
        }
    }

    private TokenType getTokenType(char value) {

        if (isDigit(value)) {
            return TokenType.NUMBER;
        } else if (isSign(value)) {
            return TokenType.SIGH;
        } else {
            return TokenType.OTHER;
        }
    }

    private boolean isDigit(char value) {
        return Character.isDigit(value);
    }

    public static boolean isSign(char value) {

        return isPlus(value) ||
                isMinus(value) ||
                isSlash(value) ||
                isStar(value) ||
                isLeftBrace(value) ||
                isRightBrace(value);
    }

    private static boolean isRightBrace(char value) {
        return value == ')';
    }

    private static boolean isLeftBrace(char value) {
        return value == '(';
    }

    private static boolean isPlus(char value) {
        return value == '+';
    }

    private static boolean isMinus(char value) {
        return value == '-';
    }

    private static boolean isStar(char value) {
        return value == '*';
    }

    private static boolean isSlash(char value) {
        return value == '/';
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    static class Tuple2<T1, T2> {
        T1 _1;
        T2 _2;
    }
}