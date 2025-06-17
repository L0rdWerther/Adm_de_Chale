public class Funcionario {
    private int codFuncionario;
    private String nomeFuncionario;
    private String cpfFuncionario;
    private String cargoFuncionario;
    private String telefoneFuncionario;
    private String emailFuncionario;

    // Construtor
    public Funcionario(int codFuncionario, String nomeFuncionario, String cpfFuncionario, String cargoFuncionario, String telefoneFuncionario, String emailFuncionario) {
        this.codFuncionario = codFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.cpfFuncionario = cpfFuncionario;
        this.cargoFuncionario = cargoFuncionario;
        this.telefoneFuncionario = telefoneFuncionario;
        this.emailFuncionario = emailFuncionario;
    }

    // Getters e Setters
    public int getCodFuncionario() { return codFuncionario; }
    public void setCodFuncionario(int codFuncionario) { this.codFuncionario = codFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
    public String getCpfFuncionario() { return cpfFuncionario; }
    public void setCpfFuncionario(String cpfFuncionario) { this.cpfFuncionario = cpfFuncionario; }
    public String getCargoFuncionario() { return cargoFuncionario; }
    public void setCargoFuncionario(String cargoFuncionario) { this.cargoFuncionario = cargoFuncionario; }
    public String getTelefoneFuncionario() { return telefoneFuncionario; }
    public void setTelefoneFuncionario(String telefoneFuncionario) { this.telefoneFuncionario = telefoneFuncionario; }
    public String getEmailFuncionario() { return emailFuncionario; }
    public void setEmailFuncionario(String emailFuncionario) { this.emailFuncionario = emailFuncionario; }

    // toString
    @Override
    public String toString() {
        return "Funcionario{" +
                "codFuncionario=" + codFuncionario +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", cpfFuncionario='" + cpfFuncionario + '\'' +
                ", cargoFuncionario='" + cargoFuncionario + '\'' +
                ", telefoneFuncionario='" + telefoneFuncionario + '\'' +
                ", emailFuncionario='" + emailFuncionario + '\'' +
                '}';
    }
}