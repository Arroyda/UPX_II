package gpac;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

class Conta extends Pedido {

    public Conta(int numeroPedido, String nomePedido, int clienteID, float preco, String descricao,
                 LocalDate dataCadastro, LocalDate dataEntrega, float total, andamentoPedido status) {
        super(numeroPedido, nomePedido, clienteID, preco, descricao, dataCadastro, dataEntrega, total, status);
    }

    // Implementação do método para exibir informações usando JLabel
    @Override
    public void exibirInformacoes() {
        JFrame frame = new JFrame("Informações do Pedido");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblNumeroPedido = new JLabel("Número do Pedido: " + getNumeroPedido());
        lblNumeroPedido.setBounds(20, 20, 400, 20);
        frame.add(lblNumeroPedido);

        JLabel lblNomePedido = new JLabel("Nome do Pedido: " + getNomePedido());
        lblNomePedido.setBounds(20, 50, 400, 20);
        frame.add(lblNomePedido);

        JLabel lblClienteID = new JLabel("ID do Cliente: " + getClienteID());
        lblClienteID.setBounds(20, 80, 400, 20);
        frame.add(lblClienteID);

        JLabel lblPreco = new JLabel("Preço: R$ " + getPreco());
        lblPreco.setBounds(20, 110, 400, 20);
        frame.add(lblPreco);

        JLabel lblDescricao = new JLabel("Descrição: " + getDescricao());
        lblDescricao.setBounds(20, 140, 400, 20);
        frame.add(lblDescricao);

        JLabel lblDataCadastro = new JLabel("Data de Cadastro: " + getDataCadastro());
        lblDataCadastro.setBounds(20, 170, 400, 20);
        frame.add(lblDataCadastro);

        JLabel lblDataEntrega = new JLabel("Data de Entrega: " + getDataEntrega());
        lblDataEntrega.setBounds(20, 200, 400, 20);
        frame.add(lblDataEntrega);

        JLabel lblTotal = new JLabel("Total: R$ " + getTotal());
        lblTotal.setBounds(20, 230, 400, 20);
        frame.add(lblTotal);

        JLabel lblStatus = new JLabel("Status: " + getStatus());
        lblStatus.setBounds(20, 260, 400, 20);
        frame.add(lblStatus);

        frame.setVisible(true);
    }
}