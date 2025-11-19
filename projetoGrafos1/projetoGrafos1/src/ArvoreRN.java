public class ArvoreRN {
    Node root, nulo;

    public ArvoreRN(){
        nulo = new Node();
        nulo.color = Color.BLACK;
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

    void insert(int data){
        Node oldNode, p;
        oldNode = nulo;
        p = root;
        

        while(p!= nulo){
            oldNode = p;
            if(data < p.data){
                p = p.left;
            } else {
                p = p.right;
            }
        }
        Node newNode = new Node();
        newNode.data = data;
        newNode.left = nulo;
        newNode.right = nulo;
        newNode.parent = oldNode;
        newNode.color = Color.RED;

        if(oldNode == nulo){
            root = newNode;
        } else if(data < oldNode.data){
            oldNode.left = newNode;
        } else {
            oldNode.right = newNode;
        }
        rebuildPropertiesAfterInsert(newNode);
    }

    public void rebuildPropertiesAfterInsert(Node z){
        while(z.parent.color == Color.RED){
            if(z.parent == z.parent.parent.left){
                Node y = z.parent.parent.right;
                if(y.color == Color.RED){ //case 1
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else { 
                    if(z == z.parent.right){ //case 2
                        z = z.parent;
                        rotateLeft(z);
                    }// case 3
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if(y.color == Color.RED){
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if(z == z.parent.left){
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rotateLeft(z.parent.parent);
                }
            }

        }
        root.color = Color.BLACK;
    }
    public void print(){
        inOrder(this.root, "        ");
    }
    public void inOrder(Node p, String espaco){
        if (p != nulo){
            inOrder(p.left, espaco + "        ");
            System.out.println(espaco + p.data + " " + p.color);
            inOrder(p.right, espaco + "        ");
        }
    }
}