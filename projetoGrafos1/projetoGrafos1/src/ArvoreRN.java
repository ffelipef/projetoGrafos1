public class ArvoreRN {
    Node root;
    Node nulo; // O nó sentinela (TNULL)

    public ArvoreRN() {
        nulo = new Node();
        nulo.color = Color.BLACK;
        nulo.left = null;
        nulo.right = null;
        root = nulo;
    }

    // Rotações não mudam lógica, apenas tipagem se necessário
    void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != nulo) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == nulo) {
            root = y;
        } else {
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        }
        y.left = x;
        x.parent = y;
    }

    void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != nulo) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == nulo) {
            root = y;
        } else {
            if (x == x.parent.right) {
                x.parent.right = y;
            } else {
                x.parent.left = y;
            }
        }
        y.right = x;
        x.parent = y;
    }

    // !!! MUDANÇA PRINCIPAL AQUI: Recebe ItemRPG
    void insert(ItemRPG item) {
        Node oldNode = nulo;
        Node p = root;

        while (p != nulo) {
            oldNode = p;
            // !!! MUDANÇA: Comparação pelo ID do item
            if (item.id < p.data.id) {
                p = p.left;
            } else if (item.id > p.data.id) {
                p = p.right;
            } else {
                // !!! MUDANÇA: Regra de Repetição
                // Se o ID for igual, incrementa quantidade e NÃO insere nó novo
                p.data.incrementar();
                System.out.println("Item repetido! Quantidade atualizada: " + p.data.nome);
                return; // Sai do método, não precisa balancear
            }
        }

        // Se chegou aqui, é um item novo
        Node newNode = new Node();
        newNode.data = item; // Guarda o objeto inteiro
        newNode.left = nulo;
        newNode.right = nulo;
        newNode.parent = oldNode;
        newNode.color = Color.RED;

        if (oldNode == nulo) {
            root = newNode;
        } else if (item.id < oldNode.data.id) {
            oldNode.left = newNode;
        } else {
            oldNode.right = newNode;
        }

        // Se o pai for nulo (raiz), apenas pinta de preto e retorna
        if (newNode.parent == nulo) {
            newNode.color = Color.BLACK;
            return;
        }
        
        // Se o avô for nulo, não precisa balancear (caso raro, mas seguro verificar)
        if (newNode.parent.parent == nulo) {
            return;
        }

        rebuildPropertiesAfterInsert(newNode);
    }

    public void rebuildPropertiesAfterInsert(Node z) {
        while (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.color == Color.RED) { // case 1
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) { // case 2
                        z = z.parent;
                        rotateLeft(z);
                    }
                    // case 3
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rotateLeft(z.parent.parent);
                }
            }
            if (z == root) {
                break;
            }
        }
        root.color = Color.BLACK;
    }

    public void print() {
        inOrder(this.root, "");
    }

    public void inOrder(Node p, String espaco) {
        if (p != nulo) {
            inOrder(p.left, espaco + "        ");
            // !!! Visualização melhorada no console
            String corStr = (p.color == Color.RED) ? " [R]" : " [B]";
            System.out.println(espaco + p.data.toString() + corStr);
            inOrder(p.right, espaco + "        ");
        }
    }
}