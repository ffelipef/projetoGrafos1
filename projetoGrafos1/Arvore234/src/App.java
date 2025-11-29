import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private Arvore234 arvore = new Arvore234();
    private PainelArvore painelDesenho = new PainelArvore(arvore);
    private JTextField campoValor = new JTextField(10);

    public App() {
        setTitle("Árvore 2-3-4 (Visualização)");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // menu superior
        JPanel painelControles = new JPanel();
        JButton btnInserir = new JButton("Inserir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnDemo = new JButton("Carregar Demo (25 nós)");

        painelControles.add(new JLabel("Valor:"));
        painelControles.add(campoValor);
        painelControles.add(btnInserir);
        painelControles.add(btnBuscar);
        painelControles.add(btnDemo);
        
        add(painelControles, BorderLayout.NORTH);
        add(painelDesenho, BorderLayout.CENTER);

        //acao dos botoes
        btnInserir.addActionListener(e -> {
            try {
                long valor = Long.parseLong(campoValor.getText());
                arvore.inserir(valor);
                painelDesenho.repaint();
                campoValor.setText("");
                campoValor.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um número válido!");
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                long valor = Long.parseLong(campoValor.getText());
                int res = arvore.buscar(valor);
                if(res != -1) JOptionPane.showMessageDialog(this, "Valor " + res + " encontrado!");
                else JOptionPane.showMessageDialog(this, "Valor NÃO encontrado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um número válido!");
            }
        });
        
        btnDemo.addActionListener(e -> {
            int[] dados = {50, 20, 70, 10, 30, 60, 80, 5, 15, 25, 35, 55, 65, 75, 85, 90, 95, 3, 7, 12, 18, 22, 28, 32, 38};
            for(int v : dados) arvore.inserir(v);
            painelDesenho.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}

class PainelArvore extends JPanel {
    private Arvore234 arvore;
    private int raioNo = 20; 
    private int distV = 80; // distqncia vertical entre níveis

    public PainelArvore(Arvore234 arvore) {
        this.arvore = arvore;
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (arvore.getRaiz() != null) {
            // centrealiza a raiz (horizontal)
            desenharNo(g2d, arvore.getRaiz(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void desenharNo(Graphics2D g, No no, int x, int y, int xOffset) {
        // largura = (qtd de itens * tamanho do item) + margem extra
        int larguraNo = (no.totalItens * raioNo) + 20;

        g.setColor(new Color(200, 230, 255));

        g.fillRoundRect(x - larguraNo/2, y, larguraNo, raioNo, 10, 10);


        g.setColor(Color.BLACK);
        g.drawRoundRect(x - larguraNo/2, y, larguraNo, raioNo, 10, 10);

        FontMetrics fm = g.getFontMetrics();
        int alturaTexto = fm.getAscent();

        for (int i = 0; i < no.totalItens; i++) {
            String valorStr = String.valueOf(no.itens[i]);

            // calculo da posição x do texto baseada no raioNo
            int centroItemX = (x - larguraNo/2) + 10 + (i * raioNo) + (raioNo / 2);
            int textoX = centroItemX - (fm.stringWidth(valorStr) / 2);

            // cálculo da posição y centralizada
            int textoY = y + (raioNo / 2) + (alturaTexto / 2) - 2;

            g.drawString(valorStr, textoX, textoY);

            if(i < no.totalItens - 1) {
                int divX = (x - larguraNo/2) + 10 + ((i+1) * raioNo);
                g.drawLine(divX, y, divX, y + raioNo);
            }
        }

        //desenho das conexões e dos filhos recursivamente
        if (!no.ehFolha()) {
            for (int i = 0; i <= no.totalItens; i++) {
                if (no.filhos[i] != null) {
                    int novoX = x - (xOffset * (no.totalItens - 1)) + (i * xOffset * 2);
                    
                    if (no.totalItens == 1) novoX = (i == 0) ? x - xOffset : x + xOffset;
                    if (no.totalItens == 2) novoX = x - xOffset + (i * xOffset); 
                    
                    int novoY = y + distV;

                    g.drawLine(x, y + raioNo, novoX, novoY);
                    
                    desenharNo(g, no.filhos[i], novoX, novoY, xOffset / (no.totalItens + 1)); 
                }
            }
        }
    }
}