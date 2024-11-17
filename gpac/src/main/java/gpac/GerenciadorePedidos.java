package gpac;

import java.math.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public class GerenciadorePedidos {

	// Conexao com o banco de dados
    private static final String DB_URL = "jdbc:mysql://localhost:3306/upxii";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        // Criar a janela principal
        JFrame frame = new JFrame("Gerenciador de Pedidos");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Criar botões para as opções
        JButton inserirButton = new JButton("Inserir Pedido");
        JButton atualizarButton = new JButton("Atualizar Pedido");
        JButton deletarButton = new JButton("Deletar Pedido");
        JButton listarButton = new JButton("Listar Pedidos");
        JButton criarClienteButton = new JButton("Criar Cliente");


        // Labels para mensagens e entradas
        JLabel statusLabel = new JLabel("Status: Aguardando ação.");
        JLabel nomeLabel = new JLabel("Nome do Pedido:");
        JTextField nomeField = new JTextField(20);
        JLabel pedidoIdLabel = new JLabel("ID do Pedido:");
        JTextField pedidoIdField = new JTextField(20);
        JLabel clienteLabel = new JLabel("ID do Cliente:");
        JTextField clienteField = new JTextField(20);
        JLabel precoLabel = new JLabel("Preço:");
        JTextField precoField = new JTextField(20);
        JLabel descricaoLabel = new JLabel("Descrição:");
        JTextField descricaoField = new JTextField(20);

        // Adicionar componentes na janela
        frame.add(nomeLabel);
        frame.add(nomeField);
        frame.add(pedidoIdLabel);
        frame.add(pedidoIdField);
        frame.add(clienteLabel);
        frame.add(clienteField);
        frame.add(precoLabel);
        frame.add(precoField);
        frame.add(descricaoLabel);
        frame.add(descricaoField);
        frame.add(inserirButton);
        frame.add(atualizarButton);
        frame.add(deletarButton);
        frame.add(listarButton);
        frame.add(statusLabel);
        frame.add(criarClienteButton);

        frame.setVisible(true);

        // Ação do botão "Inserir Pedido"
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomePedido = nomeField.getText();
                int clienteID = Integer.parseInt(clienteField.getText());
                float preco = Float.parseFloat(precoField.getText());
                String descricao = descricaoField.getText();

                try (Connection conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    if (conexao == null) {
                        statusLabel.setText("Erro ao conectar ao banco de dados.");
                        return;
                    }

                    // Verificar se o cliente existe
                    String verificarClienteSQL = "SELECT COUNT(*) FROM contas WHERE id = ?";
                    try (PreparedStatement verificarStmt = conexao.prepareStatement(verificarClienteSQL)) {
                        verificarStmt.setInt(1, clienteID);
                        ResultSet rs = verificarStmt.executeQuery();
                        rs.next();
                        if (rs.getInt(1) == 0) {
                            statusLabel.setText("Cliente com ID " + clienteID + " não existe.");
                            return;
                        }
                    }

                    // Inserir o pedido
                    LocalDate dataCadastro = LocalDate.now();
                    LocalDate dataEntrega = dataCadastro.plusDays(7);
                    String status = "EM_PROGRESSO";
                    int numPecas = 1;

                    String sql = "INSERT INTO pedidos (pedido, cliente_id, preço, data_cadastro, data_entrega, descrição, status, num_pecas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, nomePedido);
                        stmt.setInt(2, clienteID);
                        stmt.setFloat(3, preco);
                        stmt.setDate(4, Date.valueOf(dataCadastro));
                        stmt.setDate(5, Date.valueOf(dataEntrega));
                        stmt.setString(6, descricao);
                        stmt.setString(7, status);
                        stmt.setInt(8, numPecas);

                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            // Recuperar o ID do pedido recém-criado
                            ResultSet generatedKeys = stmt.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                int pedidoID = generatedKeys.getInt(1);
                                statusLabel.setText("Pedido inserido com sucesso! ID do Pedido: " + pedidoID);
                            } else {
                                statusLabel.setText("Pedido inserido, mas não foi possível obter o ID.");
                            }
                        } else {
                            statusLabel.setText("Erro ao inserir pedido.");
                        }
                    } catch (SQLException ex) {
                        statusLabel.setText("Erro ao inserir pedido: " + ex.getMessage());
                    }
                } catch (SQLException ex) {
                    statusLabel.setText("Erro de conexão: " + ex.getMessage());
                }
            }
        });

        // Ação do botão "Atualizar Pedido"
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idPedido = Integer.parseInt(clienteField.getText()); // Usando clienteField como exemplo
                String novoNome = nomeField.getText();
                float novoPreco = Float.parseFloat(precoField.getText());

                try (Connection conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "UPDATE pedidos SET pedido = ?, preço = ? WHERE id = ?";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        stmt.setString(1, novoNome);
                        stmt.setFloat(2, novoPreco);
                        stmt.setInt(3, idPedido);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            statusLabel.setText("Pedido atualizado com sucesso!");
                        } else {
                            statusLabel.setText("Pedido não encontrado.");
                        }
                    }
                } catch (SQLException ex) {
                    statusLabel.setText("Erro ao atualizar pedido: " + ex.getMessage());
                }
            }
        });
        
        

     // Ação do botão "Deletar Pedido"
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoIdPedido = pedidoIdField.getText();
                String textoIdCliente = clienteField.getText();

                if (textoIdPedido.isEmpty() && textoIdCliente.isEmpty()) {
                    statusLabel.setText("Por favor, insira o ID do Pedido ou o ID do Cliente para deletar.");
                    return;
                }

                int idPedido = -1;
                int idCliente = -1;

                // Tentar converter os IDs para inteiros
                try {
                    if (!textoIdPedido.isEmpty()) {
                        idPedido = Integer.parseInt(textoIdPedido);
                    }
                    if (!textoIdCliente.isEmpty()) {
                        idCliente = Integer.parseInt(textoIdCliente);
                    }
                } catch (NumberFormatException ex) {
                    statusLabel.setText("ID do Pedido ou Cliente inválido.");
                    return;
                }

                // Verificar qual ID será utilizado para deletar o pedido
                String sql;
                boolean usarIdPedido = idPedido != -1;
                boolean usarIdCliente = idCliente != -1;

                if (usarIdPedido && usarIdCliente) {
                    // Se ambos os IDs forem fornecidos, priorizar o ID do Pedido
                    sql = "DELETE FROM pedidos WHERE id = ?";
                } else if (usarIdPedido) {
                    sql = "DELETE FROM pedidos WHERE id = ?";
                } else if (usarIdCliente) {
                    sql = "DELETE FROM pedidos WHERE cliente_id = ?";
                } else {
                    statusLabel.setText("Nenhum ID válido foi fornecido.");
                    return;
                }

                try (Connection conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                        if (usarIdPedido) {
                            stmt.setInt(1, idPedido);
                        } else {
                            stmt.setInt(1, idCliente);
                        }

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            statusLabel.setText("Pedido deletado com sucesso!");
                        } else {
                            statusLabel.setText("Pedido não encontrado.");
                        }
                    }
                } catch (SQLException ex) {
                    statusLabel.setText("Erro ao deletar pedido: " + ex.getMessage());
                }
            }
        });
        


        // Ação do botão "Listar Pedidos"
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "SELECT * FROM pedidos";
                    try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                        StringBuilder sb = new StringBuilder("<html><body>");
                        while (rs.next()) {
                            sb.append("ID: ").append(rs.getInt("id"))
                              .append(" | Pedido: ").append(rs.getString("pedido"))
                              .append(" | Cliente ID: ").append(rs.getInt("cliente_id"))
                              .append(" | Preço: ").append(rs.getFloat("preço"))
                              .append(" | Descrição: ").append(rs.getString("descrição"))
                              .append(" | Status: ").append(rs.getString("status"))
                              .append("<br>");
                        }
                        sb.append("</body></html>");
                        statusLabel.setText("<html><body>" + sb.toString());
                    }
                } catch (SQLException ex) {
                    statusLabel.setText("Erro ao listar pedidos: " + ex.getMessage());
                }
            }
        });
        
        // Ação do botão "Criar cliente"
        criarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCliente = JOptionPane.showInputDialog(frame, "Digite o nome do cliente:");
                String telefoneCliente = JOptionPane.showInputDialog(frame, "Digite o telefone do cliente:");

                if (nomeCliente == null || telefoneCliente == null || nomeCliente.isEmpty() || telefoneCliente.isEmpty()) {
                    statusLabel.setText("Nome e telefone são obrigatórios para criar um cliente.");
                    return;
                }

                try (Connection conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "INSERT INTO contas (nome, telefone) VALUES (?, ?)";
                    try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, nomeCliente);
                        stmt.setString(2, telefoneCliente);

                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            ResultSet rs = stmt.getGeneratedKeys();
                            if (rs.next()) {
                                int clienteID = rs.getInt(1);
                                statusLabel.setText("Cliente criado com sucesso! ID: " + clienteID);
                            }
                        } else {
                            statusLabel.setText("Erro ao criar cliente.");
                        }
                    }
                } catch (SQLException ex) {
                    statusLabel.setText("Erro ao criar cliente: " + ex.getMessage());
                }
            }
        });

    }
}