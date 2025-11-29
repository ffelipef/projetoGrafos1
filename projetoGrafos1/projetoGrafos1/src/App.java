import javax.swing.JFrame;
public class App {    
    public static void main(String[] args) {
        // 1. Criar a árvore e inserir dados (O "Backend")
        ArvoreRN arvore = new ArvoreRN();

        System.out.println("Gerando Catálogo de Itens RPG...");
        
        // Inserindo itens (Misturando IDs para forçar o balanceamento da árvore)
        arvore.insert(new ItemRPG(50, "Espada Longa", "Comum"));
        arvore.insert(new ItemRPG(25, "Adaga Velha", "Comum"));
        arvore.insert(new ItemRPG(75, "Cajado Arcano", "Raro"));
        arvore.insert(new ItemRPG(10, "Poção Cura", "Consumível"));
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));
        arvore.insert(new ItemRPG(60, "Anel de Fogo", "Lendário")); // Teste visual Lendário
        arvore.insert(new ItemRPG(85, "Botas Velozes", "Raro"));
        arvore.insert(new ItemRPG(5, "Pedra Lixo", "Comum"));
        arvore.insert(new ItemRPG(80, "Arco Élfico", "Lendário")); // Teste visual Lendário
        arvore.insert(new ItemRPG(90, "Elmo de Ferro", "Comum"));
        arvore.insert(new ItemRPG(15, "Capa Sombria", "Raro"));
        arvore.insert(new ItemRPG(35, "Luvas de Couro", "Comum"));
        arvore.insert(new ItemRPG(55, "Amuleto da Vida", "Lendário")); // Teste visual Lendário
        arvore.insert(new ItemRPG(70, "Martelo de Guerra", "Raro"));
        arvore.insert(new ItemRPG(95, "Cinto de Força", "Comum"));
        // Teste de Repetição
        arvore.insert(new ItemRPG(50, "Espada Longa", "Comum")); // Vai aumentar qtd para 2
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));
         // Vai aumentar qtd para 2

        // 2. Configurar a Janela (O "Frontend")
        JFrame frame = new JFrame("Visualizador Árvore Rubro-Negra - Catálogo RPG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600); // Largura x Altura da janela
        
        // 3. Adicionar nosso painel de desenho
        VisualizadorArvore visualizador = new VisualizadorArvore(arvore);
        frame.add(visualizador);
        
        // 4. Mostrar
        frame.setVisible(true);
        
        System.out.println("Janela aberta! Verifique a visualização.");

        System.out.println("\n--- Teste de Remoção ---");
        arvore.delete(50);
        arvore.delete(5);
        arvore.delete(25);

        frame.repaint();
    }
}