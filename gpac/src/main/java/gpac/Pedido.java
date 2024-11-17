package gpac;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

public abstract class Pedido {
    
	// Variáveis
	private int numeroPedido;           // Número do pedido (int)
	private String nomePedido;          // Nome do pedido (String)
	private int clienteID;              // ID do cliente (int)
	private float preco;                // Preço do pedido (float)
	private String descricao;           // Descrição do pedido (String)
	private LocalDate dataCadastro;     // Data de cadastro (LocalDate)
	private LocalDate dataEntrega;      // Data de entrega (LocalDate)
	private float total;                // Total do pedido (float)
	private andamentoPedido status;     // Status do pedido (enum andamentoPedido)


    
    
	// Getters e Setters
    public int getNumeroPedido() {
        return numeroPedido;
    }
    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getNomePedido() {
        return nomePedido;
    }
    public void setNomePedido(String nomePedido) {
        this.nomePedido = nomePedido;
    }

    public int getClienteID() {
        return clienteID;
    }
    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public float getPreco() {
        return preco;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }
    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }

    public andamentoPedido getStatus() {
        return status;
    }
    public void setStatus(andamentoPedido status) {
        this.status = status;
    }

    // Construtor
    public Pedido(int numeroPedido, String nomePedido, int clienteID, float preco, String descricao,
                  LocalDate dataCadastro, LocalDate dataEntrega, float total, andamentoPedido status) {
        this.numeroPedido = numeroPedido;
        this.nomePedido = nomePedido;
        this.clienteID = clienteID;
        this.preco = preco;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.dataEntrega = dataEntrega;
        this.total = total;
        this.status = status;
    }

    // Método abstrato para exibir informações
    public abstract void exibirInformacoes();
}

enum andamentoPedido {
    EM_PROGRESSO, ATRASO, CONCLUIDO;
}
