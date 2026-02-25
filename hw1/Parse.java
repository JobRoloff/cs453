
public class Parse {
    private Lexer lexer;
    private Lexer.Token lookahead;

    public Parse(Lexer lexer){
        this.lexer = lexer;
        this.lookahead = lexer.nextToken();
    }

    private void match(Lexer.TokenType type){
        if (lookahead.type == type){
            lookahead = lexer.nextToken();
        } else {
            error();
        }
    }
    private void error(){
        System.out.println("Parse error in line " + lexer.getLineNumber());
        System.exit(1);
    }

    // GRAMMAR METHODS...
    public void expr(){
        concat();
    }

    private void concat(){
        add();
        concatTail();
    }

    private void concatTail(){
        if (lookahead.type == Lexer.TokenType.NUM || lookahead.type == Lexer.TokenType.DOLLAR || lookahead.type == Lexer.TokenType.LPAREN || lookahead.type == Lexer.TokenType.INC || lookahead.type == Lexer.TokenType.DEC){
            add();
            System.out.print(" _");
            concatTail();
        }
    }

    private void add(){
        prefix();
        addTail();
    }

    private void addTail(){
        if (lookahead.type == Lexer.TokenType.PLUS || lookahead.type == Lexer.TokenType.MINUS){
            String op = (lookahead.type == Lexer.TokenType.PLUS) ? " +" : " -";
            match(lookahead.type);
            prefix();
            System.out.print(op);
            addTail();
        }
    }

    private void prefix(){
        if (lookahead.type == Lexer.TokenType.INC || lookahead.type == Lexer.TokenType.DEC){
            String op = (lookahead.type == Lexer.TokenType.INC) ? " ++_" : " --_";
            match(lookahead.type);
            prefix();
            System.out.print(op);
        } else{postfix();}
    }

    private void postfix(){
        dollar();
        postTail();
    }

    private void postTail(){
        if (lookahead.type == Lexer.TokenType.INC || lookahead.type == Lexer.TokenType.DEC){
            String op = (lookahead.type == Lexer.TokenType.INC) ? " _++" : " _--";
            match(lookahead.type);
            System.out.print(op);
            postTail();
        }
    }

    private void dollar(){
        if (lookahead.type == Lexer.TokenType.DOLLAR){
            match(Lexer.TokenType.DOLLAR);
            dollar();
            System.out.print(" $");
        } else {
            primary();
        }

    }
    private void primary(){
        if (lookahead.type == Lexer.TokenType.NUM){
            System.out.print(" "+ lookahead.value);
            match(Lexer.TokenType.NUM);
        } else if (lookahead.type == Lexer.TokenType.LPAREN){
            match(Lexer.TokenType.LPAREN);
            expr();
            match(Lexer.TokenType.RPAREN);
        } else{ error();}
    }



    public static void main(String[] args) {
        Lexer lexer  = new Lexer(System.in);
        Parse parser = new Parse(lexer);
        parser.expr();

        if (parser.lookahead.type != Lexer.TokenType.EOF){
            parser.error();
        }

        System.out.println("\nExpression parsed successfully");
    }

}
