import java.time.LocalDate;

public abstract class Pedido {
    
    //Variaveis
    private int numero;
    private String nome;
    private int telefone;
    private int numeroPecas;
    private String descricao;
    private LocalDate data;
    private LocalDate entrega;
    private float total;
    private String formaPagamento;
    private status andamentoPedido;

    
    
    //Getters e Setters dos atributos
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public int getTelefone() {
        return telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    
    
    public int getNumeroPecas() {
        return numeroPecas;
    }
    public void setNumeroPecas(int numeroPecas) {
        this.numeroPecas = numeroPecas;
    }
    

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    
    
    public LocalDate getEntrega() {
        return entrega;
    }
    
    
    public void setEntrega(LocalDate entrega) {
        this.entrega = entrega;
    }
    
    
    public float getTotal() {
        return total;
    }
    

    public void setTotal(float total) {
        this.total = total;
    }
    
    
    public String getFormaPagamento() {
        return formaPagamento;
    }
    
    
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public status getAndamentoPedido() {
        return andamentoPedido;
    }

    public void setAndamentoPedido(status andamentoPedido) {
        this.andamentoPedido = andamentoPedido;
    }


    //Construtor do objeto
    public Pedido(int numero, String nome, int telefone, int numeroPecas, String descricao, LocalDate data,
    LocalDate entrega, float total, String formaPagamento) {
        this.numero = numero;
        this.nome = nome;
        this.telefone = telefone;
        this.numeroPecas = numeroPecas;
        this.descricao = descricao;
        this.data = data;
        this.entrega = entrega;
        this.total = total;
        this.formaPagamento = formaPagamento;
    }

    //Construtor da conta
    public Pedido(int numero2, String nome2, int telefone2) {
    }
    
    
}

enum status{
    EM_PROGRESSO, ATRASO, CONCLUIDO;
}
