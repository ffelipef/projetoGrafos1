public class Arvore234 {
    private No raiz;

    public Arvore234() { raiz = new No(); }
    public No getRaiz() { return raiz; }

    // --- BUSCA ---
    public int buscar(long chave) {
        No atual = raiz;
        while (true) {
            int indice = atual.encontrarItem(chave);
            if (indice != -1) return (int) atual.itens[indice];
            else if (atual.ehFolha()) return -1;
            else atual = obterProximoFilho(atual, chave);
        }
    }

    // --- INSERÇÃO (Top-Down Split) ---
    public void inserir(long valor) {
        No atual = raiz;
        if (buscar(valor) != -1) return; // Ignora duplicatas

        while (true) {
            if (atual.estaCheio()) {
                dividir(atual);
                atual = atual.pai;
                atual = obterProximoFilho(atual, valor);
            } else if (atual.ehFolha()) break;
            else atual = obterProximoFilho(atual, valor);
        }
        atual.inserirItem(valor);
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

    // ==========================================================
    // --- LÓGICA DE REMOÇÃO COMPLETA (DELETE / MERGE / BORROW) ---
    // ==========================================================
    
    public void deletar(long chave) {
        remover(raiz, chave);
        
        // Se a raiz ficar vazia após um merge, o filho vira a nova raiz
        // Isso reduz a altura da árvore
        if (raiz.totalItens == 0 && !raiz.ehFolha()) {
            raiz = raiz.filhos[0];
            raiz.pai = null;
        }
    }

    private void remover(No no, long chave) {
        int idx = no.encontrarItem(chave);

        if (idx != -1) { 
            // CASO 1: A chave está neste nó
            if (no.ehFolha()) {
                // Caso 1a: É folha, apenas remove.
                no.removerItemNaPosicao(idx);
            } else {
                // Caso 1b: É nó interno. Substituir pelo sucessor.
                long chaveSucessora = obterSucessor(no, idx);
                no.itens[idx] = chaveSucessora; // Substitui valor
                remover(no.filhos[idx + 1], chaveSucessora); // Remove o sucessor lá embaixo
            }
        } else {
            // CASO 2: A chave não está aqui, precisamos descer
            if (no.ehFolha()) {
                return; // Chave não existe na árvore
            }

            // Descobre para qual filho devemos ir (como na busca)
            boolean flagUltimoFilho = false;
            int i = 0;
            while (i < no.totalItens && no.itens[i] < chave) i++;
            if (i == no.totalItens) flagUltimoFilho = true;
            
            No filho = no.filhos[i];

            // --- GARANTIA DE NÃO-UNDERFLOW (PREEMPTIVE MERGE/BORROW) ---
            // Se o filho para onde vamos tem só 1 item, precisamos "engordá-lo" antes de entrar
            if (filho.totalItens < 2) {
                preencherFilho(no, i);
                
                // Após o preenchimento, o índice do filho pode ter mudado se houve merge
                if (flagUltimoFilho && i > no.totalItens) i--; 
            }

            // Chamada recursiva para o filho apropriado
            remover(no.filhos[i], chave);
        }
    }

    // Pega o menor valor da subárvore à direita (Sucessor In-Order)
    private long obterSucessor(No no, int idx) {
        No atual = no.filhos[idx + 1];
        while (!atual.ehFolha()) atual = atual.filhos[0];
        return atual.itens[0];
    }

    // Tenta consertar um filho que tem poucas chaves (Underflow preventivo)
    private void preencherFilho(No pai, int idx) {
        // Tenta pegar emprestado do irmão esquerdo
        if (idx != 0 && pai.filhos[idx - 1].totalItens >= 2) {
            pegarEmprestadoAnterior(pai, idx);
        }
        // Tenta pegar emprestado do irmão direito
        else if (idx != pai.totalItens && pai.filhos[idx + 1].totalItens >= 2) {
            pegarEmprestadoProximo(pai, idx);
        }
        // Se ninguém pode emprestar, faz MERGE (Fusão)
        else {
            if (idx != pai.totalItens) {
                fundir(pai, idx); // Funde com o próximo
            } else {
                fundir(pai, idx - 1); // Funde com o anterior
            }
        }
    }

    // Rotação à Direita: Pai desce para filho, irmão esquerdo sobe para pai
    private void pegarEmprestadoAnterior(No pai, int idx) {
        No filho = pai.filhos[idx];
        No irmao = pai.filhos[idx - 1];

        // Abre espaço no filho e desce a chave do pai
        for (int i = filho.totalItens - 1; i >= 0; i--) filho.itens[i + 1] = filho.itens[i];
        if (!filho.ehFolha()) {
            for (int i = filho.totalItens; i >= 0; i--) filho.filhos[i + 1] = filho.filhos[i];
        }
        filho.itens[0] = pai.itens[idx - 1];

        // Se não for folha, o filho herda o último filho do irmão
        if (!filho.ehFolha()) filho.conectarFilho(0, irmao.desconectarFilho(irmao.totalItens));
        
        // Sobe a chave do irmão para o pai
        pai.itens[idx - 1] = irmao.itens[irmao.totalItens - 1];
        
        filho.totalItens++;
        irmao.totalItens--;
    }

    // Rotação à Esquerda: Pai desce para filho, irmão direito sobe para pai
    private void pegarEmprestadoProximo(No pai, int idx) {
        No filho = pai.filhos[idx];
        No irmao = pai.filhos[idx + 1];

        // Pai desce para o final do filho
        filho.itens[filho.totalItens] = pai.itens[idx];

        // Filho herda o primeiro filho do irmão
        if (!filho.ehFolha()) filho.conectarFilho(filho.totalItens + 1, irmao.desconectarFilho(0));

        // Irmão sobe sua primeira chave para o pai
        pai.itens[idx] = irmao.itens[0];

        // Ajusta o array do irmão (move tudo para a esquerda)
        for (int i = 1; i < irmao.totalItens; i++) irmao.itens[i - 1] = irmao.itens[i];
        if (!irmao.ehFolha()) {
            for (int i = 1; i <= irmao.totalItens; i++) irmao.filhos[i - 1] = irmao.filhos[i];
        }

        filho.totalItens++;
        irmao.totalItens--;
    }

    // Merge: Junta (Filho Esq) + (Pai) + (Filho Dir) em um único nó
    private void fundir(No pai, int idx) {
        No filho = pai.filhos[idx];
        No irmao = pai.filhos[idx + 1];

        // Desce a chave do pai para o meio do filho (que será o nó resultante)
        filho.itens[1] = pai.removerItemNaPosicao(idx); 

        // Copia os itens do irmão para o filho
        for (int i = 0; i < irmao.totalItens; i++) {
            filho.itens[i + 2] = irmao.itens[i];
        }

        // Copia os filhos do irmão para o filho
        if (!filho.ehFolha()) {
            for (int i = 0; i <= irmao.totalItens; i++) {
                filho.conectarFilho(i + 2, irmao.filhos[i]);
            }
        }

        filho.totalItens += 1 + irmao.totalItens;

        // Ajusta os filhos do pai (remove a referência ao irmão que sumiu)
        for (int i = idx + 1; i < pai.totalItens + 1; i++) { // Note o +1 pois removemos item
            pai.filhos[i] = pai.filhos[i+1];
        }
        // Limpa referências extras
        pai.filhos[pai.totalItens + 1] = null; 
    }
}