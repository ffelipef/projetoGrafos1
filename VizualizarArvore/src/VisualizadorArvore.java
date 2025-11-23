import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class VisualizadorArvore extends JPanel {
    private final ArvoreRN arvore;
    private final int RAIO = 20; // Tamanho da bolinha
    private final int DISTANCIA_Y = 60; // Distância vertical entre pais e filhos

    public VisualizadorArvore(ArvoreRN arvore) {
        this.arvore = arvore;
        this.setBackground(new java.awt.Color(240, 240, 240)); // Fundo cinza claro
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Ativa antialiasing para as bolinhas ficarem redondinhas (sem serrilhado)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arvore.root != null && arvore.root != arvore.nulo) {
            // Começa a desenhar do meio da tela (largura/2) na posição Y=30
            desenharNo(g2d, arvore.root, getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void desenharNo(Graphics2D g, Node no, int x, int y, int xOffset) {
        // 1. Desenhar as linhas primeiro (para ficarem atrás das bolinhas)
        g.setColor(java.awt.Color.GRAY);
        if (no.left != arvore.nulo) {
            g.drawLine(x, y, x - xOffset, y + DISTANCIA_Y);
            desenharNo(g, no.left, x - xOffset, y + DISTANCIA_Y, xOffset / 2);
        }
        if (no.right != arvore.nulo) {
            g.drawLine(x, y, x + xOffset, y + DISTANCIA_Y);
            desenharNo(g, no.right, x + xOffset, y + DISTANCIA_Y, xOffset / 2);
        }

        // 2. Desenhar a Bolinha (O Nó)
        if (no.color == Color.RED) {
            g.setColor(new java.awt.Color(220, 50, 50)); // Vermelho suave
        } else {
            g.setColor(new java.awt.Color(50, 50, 50)); // Preto suave
        }
        g.fillOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);

        // 3. Desenhar a Borda (Efeito RPG: Dourado se for Lendário)
        if (no.data.raridade.equalsIgnoreCase("Lendário")) {
            g.setColor(new java.awt.Color(255, 215, 0)); // Dourado (Gold)
            g.setStroke(new BasicStroke(3)); // Borda grossa
        } else {
            g.setColor(java.awt.Color.DARK_GRAY);
            g.setStroke(new BasicStroke(1)); // Borda fina
        }
        g.drawOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);

        // 4. Escrever o ID e Nome dentro/perto do nó
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Centralizar o ID dentro da bolinha
        String idTexto = String.valueOf(no.data.id);
        FontMetrics fm = g.getFontMetrics();
        int textoX = x - fm.stringWidth(idTexto) / 2;
        int textoY = y + fm.getAscent() / 2 - 2;
        g.drawString(idTexto, textoX, textoY);

        // Escrever o nome do item e a quantidade abaixo da bolinha (em preto)
        g.setColor(java.awt.Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        String nomeCurto = no.data.nome.length() > 10 ? no.data.nome.substring(0, 10) + "." : no.data.nome;
        g.drawString(nomeCurto, x - RAIO, y + RAIO + 15);
        
        if (no.data.quantidade > 1) {
            g.setColor(new java.awt.Color(0, 100, 0)); // Verde escuro para quantidade
            g.drawString("x" + no.data.quantidade, x + RAIO - 5, y - RAIO + 5);
        }
    }
}
