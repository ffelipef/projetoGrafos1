public class No {
    public int totalItens;
    public long[] itens = new long[3];
    public No[] filhos = new No[4];
    public No pai;

    public No() {
        totalItens = 0;
        for (int i = 0; i < 4; i++) filhos[i] = null;
        pai = null;
    }

    public boolean ehFolha() {
        return filhos[0] == null;
    }

    public boolean estaCheio() {
        return totalItens == 3;
    }

    //conecta um filho a um nó e configura o ponteiro do pai
    public void conectarFilho(int indiceFilho, No filho) {
        filhos[indiceFilho] = filho;
        if (filho != null) {
            filho.pai = this;
        }
    }

    // rremove e retorna o último ITEM
    public long removerItem() {
        long temp = itens[totalItens - 1];
        itens[totalItens - 1] = 0;
        totalItens--;
        return temp;
    }
    
    // revome e retorna o último FILHO
    public No desconectarFilho(int indiceFilho) {
        No temp = filhos[indiceFilho];
        filhos[indiceFilho] = null;
        return temp;
    }

    public int inserirItem(long valor) {
        totalItens++;
        for (int i = totalItens - 1; i > 0; i--) {
            if (itens[i - 1] > valor) {
                itens[i] = itens[i - 1]; // deslocamento p direita
            } else {
                itens[i] = valor;
                return i;
            }
        }
        itens[0] = valor;
        return 0;
    }

    public int encontrarItem(long chave) {
        for(int i=0; i<totalItens; i++) {
            if(itens[i] == chave) return i;
        }
        return -1;
    }
}