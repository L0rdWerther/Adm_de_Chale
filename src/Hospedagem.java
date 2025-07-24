public class Hospedagem {
    private int codChale;
    private String status;
    private String dataInicio;
    private String dataFim;
    private int qtdPessoas;
    private double valorDiaria;
    private int codCliente;
    private double desconto;

    public Hospedagem(int codChale, String status, String dataInicio, String dataFim, int qtdPessoas, double valorDiaria, int codCliente, double desconto) {
        this.codChale = codChale;
        this.status = status;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.qtdPessoas = qtdPessoas;
        this.valorDiaria = valorDiaria;
        this.codCliente = codCliente;
        this.desconto = desconto;
    }
}
