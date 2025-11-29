public class Arvore234 {
    private No raiz;

    public Arvore234() {
        raiz = new No();
    }

    public No getRaiz() { return raiz; }

    //kigica de busca
    public int buscar(long chave) {
        No atual = raiz;
        while (true) {
            int indice = atual.encontrarItem(chave);
            if (indice != -1) return (int) atual.itens[indice];
            else if (atual.ehFolha()) return -1; 
            else atual = obterProximoFilho(atual, chave);
        }
    }

    // insercao de nos (logica)
    public void inserir(long valor) {
        No atual = raiz;

        if (buscar(valor) != -1) {
            System.out.println("Valor " + valor + " duplicado ignorado.");
            return;
        }

        while (true) {

            if (atual.estaCheio()) {
                dividir(atual);
                atual = atual.pai;
                atual = obterProximoFilho(atual, valor);
            } else if (atual.ehFolha()) {
                break; // encontrou o local de inserção
            } else {
                atual = obterProximoFilho(atual, valor);
            }
        }
        atual.inserirItem(valor);
    }

    //divisao de nos
    private void dividir(No esteNo) {
        long itemB, itemC;
        No pai, filho2, filho3;
        int itemIndex;

        itemC = esteNo.removerItem(); // maior item (fica no novo nó)
        itemB = esteNo.removerItem(); // item do meio (sobe para o pai)
        
        filho2 = esteNo.desconectarFilho(2);
        filho3 = esteNo.desconectarFilho(3);

        No novoDireita = new No();

        if (esteNo == raiz) {
            raiz = new No();
            pai = raiz;
            raiz.conectarFilho(0, esteNo);
        } else {
            pai = esteNo.pai;
        }

        // sobe o item B para o pai
        itemIndex = pai.inserirItem(itemB);
        int n = pai.totalItens;

        // move os filhos do pai p abrir espaço pra nova referência
        for (int i = n - 1; i > itemIndex; i--) {
            No temp = pai.desconectarFilho(i);
            pai.conectarFilho(i + 1, temp);
        }
        pai.conectarFilho(itemIndex + 1, novoDireita);

        novoDireita.inserirItem(itemC);
        novoDireita.conectarFilho(0, filho2);
        novoDireita.conectarFilho(1, filho3);
    }

    // decide p qual filho descer com base no valor
    private No obterProximoFilho(No no, long valor) {
        int i;
        for (i = 0; i < no.totalItens; i++) {
            if (valor < no.itens[i]) return no.filhos[i];
        }
        return no.filhos[i];
    }
}