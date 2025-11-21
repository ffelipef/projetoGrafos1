public class App {
    public static void main(String[] args) throws Exception {
        ArvoreRN arvore = new ArvoreRN();

        // Inserindo itens normais
        arvore.insert(new ItemRPG(50, "Espada Longa", "Comum"));
        arvore.insert(new ItemRPG(20, "Escudo Quebrado", "Lixo"));
        arvore.insert(new ItemRPG(70, "Cajado Arcano", "Raro"));
        arvore.insert(new ItemRPG(10, "Livro de Magia", "Épico"));
        
        // Testando inserção de item repetido (ID 50)
        System.out.println("--- Tentando inserir item repetido ---");
        arvore.insert(new ItemRPG(50, "Espada Longa (Outra)", "Comum"));

        System.out.println("\n--- Estado da Árvore ---");
        arvore.print();

    
    
    }
}
