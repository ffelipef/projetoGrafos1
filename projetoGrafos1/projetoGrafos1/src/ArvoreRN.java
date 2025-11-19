public class ArvoreRN {
    Node root, nulo;

    public ArvoreRN(){
        nulo = new Node();
        root = nulo;
    }

    void rotateLeft(Node x){
        Node y = x.right;

        x.right = y.left;
        if(y.left != nulo){
            y.left.parent = x;
        }

        y.parent = x.parent;

        if(x.parent == nulo){
            root = y;
        }else {
            if(x == x.parent.left){
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        }
        y.left = x;
        x.parent = y;
    }

    void rotateRight(Node x){
        Node y = x.left;

        x.left = y.right;
        if(y.right != nulo){
            y.right.parent = x;
        }

        y.parent = x.parent;

        if(x.parent == nulo){
            root = y;
        }else {
            if(x == x.parent.right){
                x.parent.right = y;
            } else {
                x.parent.left = y;
            }
        }
        y.right = x;
        x.parent = y;
    }
}
