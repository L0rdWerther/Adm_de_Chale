import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuncionarioForm extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cargoField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JButton salvarButton;

    public FuncionarioForm() {
        setTitle("Cadastro de Funcionário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        nomeField = new JTextField(20);
        cpfField = new JTextField(20);
        cargoField = new JTextField(20);
        telefoneField = new JTextField(20);
        emailField = new JTextField(20);
        salvarButton = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("CPF:"));
        add(cpfField);
        add(new JLabel("Cargo:"));
        add(cargoField);
        add(new JLabel("Telefone:"));
        add(telefoneField);
        add(new JLabel("Email:"));
        add(emailField);
        add(salvarButton);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFuncionario();
            }
        });
    }

    private void salvarFuncionario() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cargoField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO Funcionario (nomeFuncionario, cpfFuncionario, cargoFuncionario, telefoneFuncionario, emailFuncionario) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nome);
                statement.setString(2, cpf);
                statement.setString(3, cargo);
                statement.setString(4, telefone);
                statement.setString(5, email);
                statement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Funcionário salvo com sucesso no banco de dados!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar funcionário no banco de dados: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        FuncionarioForm form = new FuncionarioForm();
        form.setVisible(true);
    }
}