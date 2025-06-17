public class Servico {
    private int codServico;
    private String descricao;
    private double valor;

    // Construtor
    public Servico(int codServico, String descricao, double valor) {
        this.codServico = codServico;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Getters e Setters
    public int getCodServico() { return codServico; }
    public void setCodServico(int codServico) { this.codServico = codServico; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    // toString
    @Override
    public String toString() {
        return "Servico{" +
                "codServico=" + codServico +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                '}';
    }
}