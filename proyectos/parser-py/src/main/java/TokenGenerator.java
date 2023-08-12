import lists.Node;
import token.Token;
import token.TokenType;

public class TokenGenerator {
    int lineRead;
    int columnRead;
    TokenType tokenTypeRead;

    public TokenGenerator() {
        this.lineRead = 0;
        this.columnRead = 0;
    }

    public Token generateToken(Validator validator, Accumulator accumulator, Node<Character> nextCharNode) {

        Character nextChar;
        try {
            nextChar = nextCharNode.getContent();
        } catch (NullPointerException e) {
            nextChar = '\0';
        }

        tokenTypeRead = null;
        String currentStrg = accumulator.getString();

        if (currentStrg.equals(' ')) {
            accumulator.empty();
            return null;
        } // ignore spaces

        if (validator.isReservedWord(currentStrg)) {
            tokenTypeRead = TokenType.RESERVED_WORD;
        } else if (validator.isArithmeticOperator(currentStrg)) {

            if (nextChar.equals('=')) // means token will be assignment_op on next loop
                return null;
            if (currentStrg.equals("/") && nextChar.equals('/')) // means token it's gonna be //
                return null;

            tokenTypeRead = TokenType.ARITHMETIC_OP;
        } else if (validator.isComparissionOperator(currentStrg)) {
            tokenTypeRead = TokenType.ARITHMETIC_OP;
        } else if (validator.isAssignmentOperator(currentStrg)) {

            if (nextChar.equals('=') && currentStrg.equals("=")) // means it's gonna be == comparission op
                return null;

            tokenTypeRead = TokenType.ASSIGNMENT_OP;
        } else if (validator.isLogicOperator(currentStrg)) {
            tokenTypeRead = TokenType.LOGIC_OP;
        } else {
            tokenTypeRead = TokenType.ERROR;
        }
        ;

        return new Token(tokenTypeRead, accumulator.getString(), lineRead, columnRead);
    }

    public void updatePointer(Node<Character> currentChar) {
        // update line and column
        if (currentChar.getContent().equals('\n')) {
            lineRead++;
            columnRead = 0;
        } else {
            columnRead++;
        }

    }

}
