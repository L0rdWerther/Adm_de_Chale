public class HospedagemServico {
    private int codHospedagem;
    private int codServico;
    private int quantidade;

    // Construtor
    public HospedagemServico(int codHospedagem, int codServico, int quantidade) {
        this.codHospedagem = codHospedagem;
        this.codServico = codServico;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getCodHospedagem() { return codHospedagem; }
    public void setCodHospedagem(int codHospedagem) { this.codHospedagem = codHospedagem; }
    public int getCodServico() { return codServico; }
    public void setCodServico(int codServico) { this.codServico = codServico; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    // toString
    @Override
    public String toString() {
        return "HospedagemServico{" +
                "codHospedagem=" + codHospedagem +
                ", codServico=" + codServico +
                ", quantidade=" + quantidade +
                '}';
    }
}