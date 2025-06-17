public class FuncionarioServico {
    private int codFuncionario;
    private int codServico;

    public FuncionarioServico(int codFuncionario, int codServico) {
        this.codFuncionario = codFuncionario;
        this.codServico = codServico;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public int getCodServico() {
        return codServico;
    }

    public void setCodServico(int codServico) {
        this.codServico = codServico;
    }
}