import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class App extends JFrame {
    
    private Arvore234 arvore = new Arvore234();
    private PainelCasino painelDesenho = new PainelCasino(arvore);
    private JTextField campoValor = new JTextField(10);

    public App() {
        setTitle("ROYAL CASSINO 2-3-4 - ALGORITMO DO BANQUEIRO");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelControles = new JPanel();
        painelControles.setBackground(new Color(20, 0, 0));
        painelControles.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(218, 165, 32))); // Borda Dourada

        JLabel lblValor = new JLabel("VALOR DA FICHA ($):");
        lblValor.setForeground(new Color(255, 215, 0)); // Dourado
        lblValor.setFont(new Font("Serif", Font.BOLD, 16));

        campoValor.setBackground(new Color(50, 0, 0));
        campoValor.setForeground(Color.WHITE);
        campoValor.setFont(new Font("Monospaced", Font.BOLD, 16));
        campoValor.setCaretColor(Color.YELLOW);
        campoValor.setBorder(new LineBorder(new Color(218, 165, 32), 2));

        //menu
        JButton btnInserir = criarBotaoCasino("APOSTAR (INSERIR)", new Color(0, 100, 0));
        JButton btnBuscar = criarBotaoCasino("AUDITORIA (BUSCAR)", new Color(0, 50, 150));
        JButton btnDemo = criarBotaoCasino("HIGH ROLLER (DEMO)", new Color(180, 0, 0));
        JButton btnRemover = criarBotaoCasino("SACAR (REMOVER)", new Color(139, 0, 0));

        painelControles.add(lblValor);
        painelControles.add(campoValor);
        painelControles.add(Box.createHorizontalStrut(15));
        painelControles.add(btnInserir);
        painelControles.add(btnBuscar);
        painelControles.add(btnDemo);
        painelControles.add(btnRemover);
        
        add(painelControles, BorderLayout.NORTH);
        add(painelDesenho, BorderLayout.CENTER);

        btnInserir.addActionListener(e -> {
            try {
                long valor = Long.parseLong(campoValor.getText());
                boolean inseriuComSucesso = arvore.inserir(valor);
                
                if (inseriuComSucesso) {
                    painelDesenho.repaint();
                    campoValor.setText("");
                    campoValor.requestFocus();
                    JOptionPane.showMessageDialog(this, 
                        "APOSTA ACEITA:\nA ficha $" + valor + " foi inserida na mesa!");
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "APOSTA REJEITADA:\nA ficha $" + valor + " já está na mesa!", 
                        "Erro de Duplicidade", 
                        JOptionPane.WARNING_MESSAGE);
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Aposta Inválida! Digite um número.");
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                long valor = Long.parseLong(campoValor.getText());
                int res = arvore.buscar(valor);
                if(res != -1) JOptionPane.showMessageDialog(this, "Ficha $" + res + " encontrada na mesa!");
                else JOptionPane.showMessageDialog(this, "Ficha não encontrada no cassino.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor Inválido!");
            }
        });
        
        btnDemo.addActionListener(e -> {
            int[] dados = {50, 20, 70, 10, 30, 60, 80, 5, 15, 25, 35, 55, 65, 75, 85, 90, 95, 3, 7, 12, 18, 22, 28, 32, 38};
            for(int v : dados) arvore.inserir(v);
            painelDesenho.repaint();
        });

        btnRemover.addActionListener(e -> {
            try {
                long valor = Long.parseLong(campoValor.getText());
                arvore.deletar(valor);
                painelDesenho.repaint();
                campoValor.setText("");
                campoValor.requestFocus();
                    JOptionPane.showMessageDialog(this, "APOSTA REMOVIDA:\nA ficha $" + valor + " foi removida da mesa!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor Inválido!");
            }
        });
    }

    private JButton criarBotaoCasino(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setBackground(corFundo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Serif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(218, 165, 32), 2), 
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}

class PainelCasino extends JPanel {
    private Arvore234 arvore;
    private int diametroFicha = 30;
    private int distV = 90;

    public PainelCasino(Arvore234 arvore) {
        this.arvore = arvore;
        this.setBackground(new Color(34, 100, 34)); 
    }

    private int calcularPeso(No no) {
        if (no == null) return 0;
        if (no.ehFolha()) return 1;
        
        int pesoTotal = 0;
        for (int i = 0; i < 4; i++) {
            if (no.filhos[i] != null) {
                pesoTotal += calcularPeso(no.filhos[i]);
            }
        }
        return pesoTotal;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(218, 165, 32));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(5, 5, getWidth()-10, getHeight()-10);
        
        g2d.setColor(new Color(0, 50, 0));
        g2d.setFont(new Font("Serif", Font.BOLD, 100));
        String logo = "♠ ♥ ♣ ♦";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(logo, getWidth()/2 - fm.stringWidth(logo)/2, getHeight()/2);

        if (arvore.getRaiz() != null) {
            int pesoTotal = calcularPeso(arvore.getRaiz());
            int larguraDisponivel = getWidth() - 100;
            desenharMesaRecursivo(g2d, arvore.getRaiz(), 50, 50, larguraDisponivel, pesoTotal);
        }
    }

    private void desenharMesaRecursivo(Graphics2D g, No no, int xInicial, int y, int larguraFaixa, int pesoDesteNo) {
        int xCentro = xInicial + (larguraFaixa / 2);
        int espacoEntreFichas = 4; 
        int larguraMesa = (no.totalItens * diametroFicha) + ((no.totalItens - 1) * espacoEntreFichas) + 24;
        int alturaMesa = diametroFicha + 16;

        g.setColor(new Color(101, 67, 33));
        g.fillRoundRect(xCentro - larguraMesa/2, y, larguraMesa, alturaMesa, 20, 20);

        if (no.estaCheio()) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(3));
        } else {
            g.setColor(new Color(218, 165, 32));
            g.setStroke(new BasicStroke(2));
        }
        g.drawRoundRect(xCentro - larguraMesa/2, y, larguraMesa, alturaMesa, 20, 20);

        for (int i = 0; i < no.totalItens; i++) {
            long valor = no.itens[i];
            int inicioFichasX = xCentro - (larguraMesa / 2) + 12;
            int fichaX = inicioFichasX + (i * (diametroFicha + espacoEntreFichas));
            int fichaY = y + 8;
            
            Color corFicha = (valor % 2 == 0) ? Color.BLACK : new Color(180, 0, 0);
            Color corBorda = (valor % 2 == 0) ? Color.RED : Color.BLACK;
            
            Ellipse2D formaFicha = new Ellipse2D.Double(fichaX, fichaY, diametroFicha, diametroFicha);
            g.setColor(corFicha);
            g.fill(formaFicha);
            
            g.setStroke(new BasicStroke(2));
            g.setColor(corBorda);
            g.draw(formaFicha);

            g.setColor(Color.WHITE);
            Stroke oldStroke = g.getStroke();
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
            g.draw(new Ellipse2D.Double(fichaX + 3, fichaY + 3, diametroFicha - 6, diametroFicha - 6));
            g.setStroke(oldStroke);

            g.setFont(new Font("Arial", Font.BOLD, diametroFicha / 2)); 
            FontMetrics fm = g.getFontMetrics();
            String txt = String.valueOf(valor);
            g.drawString(txt, fichaX + (diametroFicha/2) - (fm.stringWidth(txt)/2), fichaY + (diametroFicha/2) + (diametroFicha/6));
        }

        if (!no.ehFolha()) {
            int cursorX = xInicial;
            
            for (int i = 0; i < 4; i++) {
                if (no.filhos[i] != null) {
                    int pesoFilho = calcularPeso(no.filhos[i]);
                    int larguraParaFilho = (larguraFaixa * pesoFilho) / Math.max(pesoDesteNo, 1);
                    int novoY = y + distV;
                    int xCentroFilho = cursorX + (larguraParaFilho / 2);

                    g.setColor(new Color(255, 215, 0, 100));
                    g.setStroke(new BasicStroke(2));
                    g.drawLine(xCentro, y + alturaMesa, xCentroFilho, novoY);
                    
                    desenharMesaRecursivo(g, no.filhos[i], cursorX, novoY, larguraParaFilho, pesoFilho);
                    cursorX += larguraParaFilho;
                }
            }
        }
    }
}