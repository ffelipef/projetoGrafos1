import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
// NÃO importe java.awt.Color aqui para evitar conflito com seu Enum Color

public class VisualizadorArvore extends JPanel {
    private final ArvoreRN arvore;
    private final int RAIO = 20; 
    private final int DISTANCIA_Y = 60; 

    public VisualizadorArvore(ArvoreRN arvore) {
        this.arvore = arvore;
        // CORREÇÃO: Usando java.awt.Color
        this.setBackground(new java.awt.Color(240, 240, 240)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arvore.root != null && arvore.root != arvore.nulo) {
            desenharNo(g2d, arvore.root, getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void desenharNo(Graphics2D g, Node no, int x, int y, int xOffset) {
        // Linhas (Cinza)
        g.setColor(java.awt.Color.GRAY); 
        if (no.left != arvore.nulo) {
            g.drawLine(x, y, x - xOffset, y + DISTANCIA_Y);
            desenharNo(g, no.left, x - xOffset, y + DISTANCIA_Y, xOffset / 2);
        }
        if (no.right != arvore.nulo) {
            g.drawLine(x, y, x + xOffset, y + DISTANCIA_Y);
            desenharNo(g, no.right, x + xOffset, y + DISTANCIA_Y, xOffset / 2);
        }

        // Lógica da Cor do Nó (Aqui usamos SEU Enum Color)
        if (no.color == Color.RED) {
            // Tinta da interface (java.awt.Color)
            g.setColor(new java.awt.Color(220, 50, 50)); // Vermelho bonito
        } else {
            g.setColor(new java.awt.Color(50, 50, 50)); // Preto bonito
        }
        g.fillOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);

        // Borda (Lendário = Dourado)
        if (no.data.raridade.equalsIgnoreCase("Lendário")) {
            g.setColor(new java.awt.Color(255, 215, 0)); 
            g.setStroke(new BasicStroke(3));
        } else {
            g.setColor(java.awt.Color.DARK_GRAY);
            g.setStroke(new BasicStroke(1));
        }
        g.drawOval(x - RAIO, y - RAIO, 2 * RAIO, 2 * RAIO);

        // Texto ID (Branco)
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String idTexto = String.valueOf(no.data.id);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(idTexto, x - fm.stringWidth(idTexto) / 2, y + fm.getAscent() / 2 - 2);

        // Texto Nome (Preto)
        g.setColor(java.awt.Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        String nomeCurto = no.data.nome.length() > 10 ? no.data.nome.substring(0, 10) + "." : no.data.nome;
        g.drawString(nomeCurto, x - RAIO, y + RAIO + 15);
        
        // Quantidade (Verde)
        if (no.data.quantidade > 1) {
            g.setColor(new java.awt.Color(0, 100, 0)); 
            g.drawString("x" + no.data.quantidade, x + RAIO - 5, y - RAIO + 5);
        }
    }
}