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

    private void deleteFixup(Node x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rotateLeft(x.parent);
                    w = x.parent.right; 
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {

                    if (w.right.color == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rotateRight(w);
                        w = x.parent.right;
                    }

                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            }
            else {
                Node w = x.parent.left;

                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }

                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        rotateLeft(w);
                        w = x.parent.left;
                    }

                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private void deleteNode(Node z) {
        Node y = z;
        Node x;
        Color yOriginalColor = y.color;

        if (z.left == nulo) {
            x = z.right;
            transplant(z, z.right);
        } 
        else if (z.right == nulo) {
            x = z.left;
            transplant(z, z.left);
        } 
        else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color; 
        }

        if (yOriginalColor == Color.BLACK) {
            deleteFixup(x);
        }
    }

    private void transplant(Node u, Node v) {
        if (u.parent == nulo) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != nulo) {
            node = node.left;
        }
        return node;
    }


    public void delete(int id) {
        Node z = nulo;
        Node x = root;

        while (x != nulo) {
            if (x.data.id == id) {
                z = x;
                break;
            }
            if (x.data.id <= id) {
                x = x.right;
            } else {
                x = x.left;
            }
        }

        if (z == nulo) {
            System.out.println("Item ID " + id + " não encontrado para remoção.");
            return;
        }
        
        if (z.data.quantidade > 1) {
            z.data.quantidade--;
            System.out.println("Item decrementado: " + z.data.nome + " (Qtd: " + z.data.quantidade + ")");
            return;
        }

        System.out.println("Removendo item: " + z.data.nome);
        deleteNode(z);
    }
}