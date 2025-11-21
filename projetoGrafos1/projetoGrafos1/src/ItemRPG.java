class ItemRPG {
    int id;
    String nome;
    String raridade;
    int quantidade;

    public ItemRPG(int id, String nome, String raridade) {
        this.id = id;
        this.nome = nome;
        this.raridade = raridade;
        this.quantidade = 1;
    }

    public void incrementar() {
        this.quantidade++;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " (Qtd: " + quantidade + ")";
    }
}