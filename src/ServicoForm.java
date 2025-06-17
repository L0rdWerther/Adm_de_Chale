import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicoForm extends JFrame {
    private JTextField descricaoField;
    private JTextField valorField;
    private JButton salvarButton;

    public ServicoForm() {
        setTitle("Cadastro de Serviço");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        descricaoField = new JTextField(20);
        valorField = new JTextField(20);
        salvarButton = new JButton("Salvar");

        add(new JLabel("Descrição:"));
        add(descricaoField);
        add(new JLabel("Valor:"));
        add(valorField);
        add(salvarButton);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarServico();
            }
        });
    }

    private void salvarServico() {
        String descricao = descricaoField.getText();
        double valor = Double.parseDouble(valorField.getText());

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO Servico (descricao, valor) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, descricao);
                statement.setDouble(2, valor);
                statement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Serviço salvo com sucesso no banco de dados!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar serviço no banco de dados: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        ServicoForm form = new ServicoForm();
        form.setVisible(true);
    }
}