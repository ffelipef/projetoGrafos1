Catálogo de Itens de RPG – Estruturas de Dados II

Projeto desenvolvido como requisito para a disciplina de Estrutura de Dados II, com foco na implementação, visualização e comparação de árvores binárias de busca balanceadas, aplicadas a um cenário temático de inventário de RPG.

👥 Equipe
Felipe de Freitas da Silva

Benjamin Yuji Suzuki

🛠️ Tecnologias Utilizadas

Linguagem: Java (JDK 8 ou superior)

Interface Gráfica: Java Swing (biblioteca nativa)

IDE Recomendada: Visual Studio Code ou IntelliJ IDEA

🌳 Estruturas Implementadas
1. Árvore Rubro-Negra (Red-Black Tree)

Implementação completa contendo:

Regras de balanceamento;

Rotações à esquerda e à direita;

Correções pós-inserção;

Visualização gráfica com cores indicativas das propriedades da árvore.

⚔️ Funcionalidades do Catálogo RPG

Visualização Gráfica Completa:
Os nós são representados visualmente em tela, com cores e formatação especial para facilitar a compreensão da estrutura.

Indicação de Raridade:
Itens classificados como “Lendários” recebem uma borda dourada na visualização.

Controle de Duplicatas:
Ao inserir um item com ID já existente:

Nenhum novo nó é criado;

Um contador interno é incrementado;

O contador é exibido em verde no nó correspondente.

🚀 Como Executar o Projeto

Certifique-se de ter o Java instalado (JDK 8 ou superior).

1. Clonar o Repositório ou Baixar o Arquivo .ZIP
git clone https://github.com/ffelipef/projetoGrafos1.git
cd SEU-REPOSITORIO

2. Acessar a Pasta do Código
Abra o terminal e entre na pasta src onde estão os códigos fontes:
cd projetoGrafos1\projetoGrafos1\projetoGrafos1\src

4. Compilar
Compile todos os arquivos Java da pasta:
javac *.java

4. Executar
Rode o arquivo principal (App):
java App

Nota: Uma janela será aberta exibindo a árvore gerada com os dados de teste inseridos no código.
