import java.io.*;

/**
 * a class that reads a file's tokens
 */
public class Lexer {
    private PushbackReader reader;
   private int lineNumber = 1;

   public enum TokenType{
    NUM, PLUS, MINUS, INC, DEC, DOLLAR, LPAREN, RPAREN, EOF
   }
    public static class Token{
        TokenType type;
        String value;
        int line;

        Token(TokenType type, String value, int line){
            this.type = type;
            this.value = value;
            this.line = line;
        }
    } 

    public Lexer(InputStream in){ this.reader = new PushbackReader(new InputStreamReader(in), 2); }
    public int getLineNumber(){return lineNumber;}

    public Token nextToken(){
        try{
            int c = skipWhitespaceAndComments();
            if (c == -1) return new Token(TokenType.EOF, null, lineNumber);

            if (Character.isDigit(c)){
                return new Token(TokenType.NUM, String.valueOf((char)c), lineNumber);
            }

            switch (c){
                case '$': return new Token(TokenType.DOLLAR, "$", lineNumber);
                case '(': return new Token(TokenType.LPAREN, "(", lineNumber);
                case ')': return new Token(TokenType.RPAREN, ")", lineNumber);

                case '+': {
                    int n = reader.read();
                    if (n == '+') return new Token(TokenType.INC, "++", lineNumber);
                    if (n != -1) reader.unread(n);
                    return new Token(TokenType.PLUS, "+", lineNumber);
                }
                case '-':{
                    int n = reader.read();
                    if (n == '-') return new Token(TokenType.DEC, "--", lineNumber);
                    if (n != -1) reader.unread(n);
                    return new Token(TokenType.MINUS, "-", lineNumber);
                }
                default:
                    System.out.println("Parse error in line" + lineNumber);
                    System.exit(1);
            }
        } catch (IOException e){
            System.out.println("Parse error in line " + lineNumber);
            System.exit(1);
        }
        // TODO throw some sort of a lexer error?
        return null;
    }

    private int skipWhitespaceAndComments() throws IOException{
        int c = reader.read();
        while (c != -1){
            if (c == '\n') {
                lineNumber++;
            } else if (c == '#'){
                while (c != -1 && c != '\n') c = reader.read();
                if (c=='\n') lineNumber++;
            } else if (!Character.isWhitespace(c)){
                return c;
            }
            c = reader.read();
        }
        return -1;
    }

}
