import syntaxtree.Goal;
import visitor.GJDepthFirst;

public class PrettyPrinter<R,A> extends GJDepthFirst<R,A> {
    int indent = 0;
    public void printClassName(Node x){
        for (int i = 0; i < indent; ++i){
            System.out.print("  ");
        }
        System.out.println(x.getClass().getSimpleName());
        indent++;
    }

    public R visit(Goal n, A argu){
        R _ret=null;
        printClassName(n);
        // ndf0.accept(this, argu)
        indent--;
    }
}
