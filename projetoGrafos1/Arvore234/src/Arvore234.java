public class Arvore234 {
    private No raiz;
    public Arvore234() { raiz = new No(); }
    public No getRaiz() { return raiz; }

    public int buscar(long chave) {
        No atual = raiz;
        while (true) {
            int indice = atual.encontrarItem(chave);
            if (indice != -1) return (int) atual.itens[indice];
            else if (atual.ehFolha()) return -1;
            else atual = obterProximoFilho(atual, chave);
        }
    }

    public boolean inserir(long valor) {
        No atual = raiz;
        if (buscar(valor) != -1) return false;

        while (true) {
            if (atual.estaCheio()) {
                dividir(atual);
                atual = atual.pai;
                atual = obterProximoFilho(atual, valor);
            } else if (atual.ehFolha()) break;
            else atual = obterProximoFilho(atual, valor);
        }
        atual.inserirItem(valor);
        return true;
    }

    private void dividir(No esteNo) {
        long itemB, itemC;
        No pai, filho2, filho3;
        int itemIndex;

        itemC = esteNo.removerItem();
        itemB = esteNo.removerItem();
        filho2 = esteNo.desconectarFilho(2);
        filho3 = esteNo.desconectarFilho(3);
        No novoDireita = new No();

        if (esteNo == raiz) {
            raiz = new No();
            pai = raiz;
            raiz.conectarFilho(0, esteNo);
        } else pai = esteNo.pai;

        itemIndex = pai.inserirItem(itemB);
        int n = pai.totalItens;
        for (int i = n - 1; i > itemIndex; i--) {
            No temp = pai.desconectarFilho(i);
            pai.conectarFilho(i + 1, temp);
        }
        pai.conectarFilho(itemIndex + 1, novoDireita);
        novoDireita.inserirItem(itemC);
        novoDireita.conectarFilho(0, filho2);
        novoDireita.conectarFilho(1, filho3);
    }

    private No obterProximoFilho(No no, long valor) {
        for (int i = 0; i < no.totalItens; i++) {
            if (valor < no.itens[i]) return no.filhos[i];
        }
        return no.filhos[no.totalItens];
    }

    public void deletar(long chave) {
        remover(raiz, chave);
        
        if (raiz.totalItens == 0 && !raiz.ehFolha()) {
            raiz = raiz.filhos[0];
            raiz.pai = null;
        }
    }

    private void remover(No no, long chave) {
        int idx = no.encontrarItem(chave);

        if (idx != -1) { 
            if (no.ehFolha()) {
                no.removerItemNaPosicao(idx);
            } else {
                long chaveSucessora = obterSucessor(no, idx);
                no.itens[idx] = chaveSucessora;
                remover(no.filhos[idx + 1], chaveSucessora);
            }
        } else {
            if (no.ehFolha()) {
                return; 
            }

            boolean flagUltimoFilho = false;
            int i = 0;
            while (i < no.totalItens && no.itens[i] < chave) i++;
            if (i == no.totalItens) flagUltimoFilho = true;
            
            No filho = no.filhos[i];

            if (filho.totalItens < 2) {
                preencherFilho(no, i);
                
                if (flagUltimoFilho && i > no.totalItens) i--; 
            }
            remover(no.filhos[i], chave);
        }
    }

    private long obterSucessor(No no, int idx) {
        No atual = no.filhos[idx + 1];
        while (!atual.ehFolha()) atual = atual.filhos[0];
        return atual.itens[0];
    }

    private void preencherFilho(No pai, int idx) {
        if (idx != 0 && pai.filhos[idx - 1].totalItens >= 2) {
            pegarEmprestadoAnterior(pai, idx);
        }
        else if (idx != pai.totalItens && pai.filhos[idx + 1].totalItens >= 2) {
            pegarEmprestadoProximo(pai, idx);
        }
        else {
            if (idx != pai.totalItens) {
                fundir(pai, idx);
            } else {
                fundir(pai, idx - 1);
            }
        }
    }

    private void pegarEmprestadoAnterior(No pai, int idx) {
        No filho = pai.filhos[idx];
        No irmao = pai.filhos[idx - 1];

        for (int i = filho.totalItens - 1; i >= 0; i--) filho.itens[i + 1] = filho.itens[i];
        if (!filho.ehFolha()) {
            for (int i = filho.totalItens; i >= 0; i--) filho.filhos[i + 1] = filho.filhos[i];
        }
        filho.itens[0] = pai.itens[idx - 1];

        if (!filho.ehFolha()) filho.conectarFilho(0, irmao.desconectarFilho(irmao.totalItens));
        
        pai.itens[idx - 1] = irmao.itens[irmao.totalItens - 1];
        
        filho.totalItens++;
        irmao.totalItens--;
    }
    private void pegarEmprestadoProximo(No pai, int idx) {
        No filho = pai.filhos[idx];
        No irmao = pai.filhos[idx + 1];

        filho.itens[filho.totalItens] = pai.itens[idx];

        if (!filho.ehFolha()) filho.conectarFilho(filho.totalItens + 1, irmao.desconectarFilho(0));

        pai.itens[idx] = irmao.itens[0];

        for (int i = 1; i < irmao.totalItens; i++) irmao.itens[i - 1] = irmao.itens[i];
        if (!irmao.ehFolha()) {
            for (int i = 1; i <= irmao.totalItens; i++) irmao.filhos[i - 1] = irmao.filhos[i];
        }

        filho.totalItens++;
        irmao.totalItens--;
    }

    private void fundir(No pai, int idx) {
        No filho = pai.filhos[idx];
        No irmao = pai.filhos[idx + 1];

        filho.itens[1] = pai.removerItemNaPosicao(idx); 

        for (int i = 0; i < irmao.totalItens; i++) {
            filho.itens[i + 2] = irmao.itens[i];
        }

        if (!filho.ehFolha()) {
            for (int i = 0; i <= irmao.totalItens; i++) {
                filho.conectarFilho(i + 2, irmao.filhos[i]);
            }
        }

        filho.totalItens += 1 + irmao.totalItens;

        for (int i = idx + 1; i < pai.totalItens + 1; i++) {
            pai.filhos[i] = pai.filhos[i+1];
        }

        pai.filhos[pai.totalItens + 1] = null; 
    }
}