import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {    
    public static void main(String[] args) {

        ArvoreRN arvore = new ArvoreRN();

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
        arvore.insert(new ItemRPG(90, "Elmo de Ferro", "Comum"));
        arvore.insert(new ItemRPG(50, "Espada Longa", "Comum")); // Vai aumentar qtd para 2
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));
        arvore.insert(new ItemRPG(30, "Escudo Torre", "Raro"));


        JFrame frame = new JFrame("Gerenciador de Itens RPG - Árvore Rubro-Negra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());

        VisualizadorArvore visualizador = new VisualizadorArvore(arvore);
        frame.add(visualizador, BorderLayout.CENTER);

        JPanel painelControle = new JPanel();
        painelControle.setBackground(java.awt.Color.LIGHT_GRAY); 
        painelControle.setBorder(BorderFactory.createTitledBorder("Controles do Mestre"));
        painelControle.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JTextField txtId = new JTextField(5);
        txtId.setBorder(BorderFactory.createTitledBorder("ID"));
        
        JTextField txtNome = new JTextField(10);
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome do Item"));

        String[] raridades = {"Comum", "Raro", "Lendário"};
        JComboBox<String> cmbRaridade = new JComboBox<>(raridades);
        cmbRaridade.setBorder(BorderFactory.createTitledBorder("Raridade"));

        // botões
        JButton btnInserir = new JButton("Inserir Item");
        btnInserir.setBackground(new java.awt.Color(100, 200, 100));
        btnInserir.setForeground(java.awt.Color.BLACK);
        
        JButton btnRemover = new JButton("Remover (pelo ID)");
        btnRemover.setBackground(new java.awt.Color(200, 100, 100)); 
        btnRemover.setForeground(java.awt.Color.WHITE);

        JButton btnLimpar = new JButton("Limpar Árvore");

        painelControle.add(txtId);
        painelControle.add(txtNome);
        painelControle.add(cmbRaridade);
        painelControle.add(btnInserir);
        painelControle.add(Box.createHorizontalStrut(20));
        painelControle.add(btnRemover);
        painelControle.add(btnLimpar);

        frame.add(painelControle, BorderLayout.SOUTH);

        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nome = txtNome.getText();
                    if (nome.isEmpty()) nome = "Item Genérico";
                    String raridade = (String) cmbRaridade.getSelectedItem();

                    arvore.insert(new ItemRPG(id, nome, raridade));
                    visualizador.repaint();
                    
                    txtId.setText("");
                    txtId.requestFocus();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID deve ser um número!");
                }
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    arvore.delete(id); 
                    visualizador.repaint();
                    txtId.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Digite um ID válido.");
                }
            }
        });

        btnLimpar.addActionListener(e -> {
            arvore.root = arvore.nulo;
            visualizador.repaint();
        });

        frame.setVisible(true);

        System.out.println("Janela aberta! Verifique a visualização.");     
    }
}