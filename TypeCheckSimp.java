import visitor.GJDepthFirst;

import java.util.*;
import syntaxtree.*;

// return value type -> Node
// arguments for GJDepthFirst A? Where A is the symbol table
public class TypeCheckSimp extends GJDepthFirst<Node, SymbolTable> {
    
    // start the process of creating a visitor to do a specific operation by extending from the existing one provided in GJDepthFirst

    /**
     * Note the shape of this method is based on the generic version auto generated in GJDepthFirst.java
     * 
     * within GJDepthFirst.java, look for the following method signature:
     * 
     * public R visit(Goal n, A argu) 
     * 
     */
    public Node visit(Goal n, SymbolTable arg){
        // prefix print "visiting goal"
        System.out.println("Visiting Goal");

        // note this line is retained in the generated GJDepthFirstt.visitor
        Node returnNode = n.f0.accept(this, arg);

        // postfix print "Done visiting goal"
        System.out.println("Done visiting goal");
        return returnNode;
    }

    // TODO: in this class, we'll be overriding ALL of 50-ish visit methods found in GJDepthFirst.java
}
