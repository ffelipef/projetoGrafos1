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

    public boolean ehFolha() { return filhos[0] == null; }
    public boolean estaCheio() { return totalItens == 3; }

    public void conectarFilho(int indiceFilho, No filho) {
        filhos[indiceFilho] = filho;
        if (filho != null) filho.pai = this;
    }

    public No desconectarFilho(int indiceFilho) {
        No temp = filhos[indiceFilho];
        filhos[indiceFilho] = null;
        return temp;
    }

    public int encontrarItem(long chave) {
        for(int i=0; i<totalItens; i++) {
            if(itens[i] == chave) return i;
        }
        return -1;
    }

    public int inserirItem(long valor) {
        totalItens++;
        for (int i = totalItens - 1; i > 0; i--) {
            if (itens[i - 1] > valor) itens[i] = itens[i - 1];
            else {
                itens[i] = valor;
                return i;
            }
        }
        itens[0] = valor;
        return 0;
    }

    public long removerItem() { // Remove o último
        long temp = itens[totalItens - 1];
        itens[totalItens - 1] = 0;
        totalItens--;
        return temp;
    }

    // --- NOVOS MÉTODOS PARA SUPORTAR REMOÇÃO ---
    
    // Remove um item em uma posição específica e ajusta o array
    public long removerItemNaPosicao(int index) {
        long temp = itens[index];
        for (int i = index; i < totalItens - 1; i++) {
            itens[i] = itens[i + 1];
        }
        itens[totalItens - 1] = 0; // Limpa o último
        totalItens--;
        return temp;
    }
}