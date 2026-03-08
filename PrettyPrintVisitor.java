import syntaxtree.*;
import syntaxtree.Node;
import visitor.*;

public class PrettyPrintVisitor extends GJDepthFirst<Void,Void>{

    int indent = 0;
    private void printIndent() {
        for (int i = 0; i < indent; ++i) {
            System.out.print("  ");
        }
    }
    private void printClassName(Node x) {
        printIndent();
        System.out.println(x.getClass().getSimpleName());
    }

    @Override
    public Void visit(Goal n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(MainClass n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(ClassDeclaration n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(ClassExtendsDeclaration n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(MethodDeclaration n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(VarDeclaration n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(Statement n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(Expression n, Void argu) {
        printClassName(n);
        indent++;
        super.visit(n, argu);
        indent--;
        return null;
    }

    @Override
    public Void visit(Identifier n, Void argu) {
        printIndent();
        System.out.println("Identifier: " + n.f0.tokenImage);
        return null;
    }

    @Override
    public Void visit(IntegerLiteral n, Void argu) {
        printIndent();
        System.out.println("IntegerLiteral: " + n.f0.tokenImage);
        return null;
    }




}
