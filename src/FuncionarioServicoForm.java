import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuncionarioServicoForm extends JFrame {
    private JTextField codFuncionarioField;
    private JTextField codServicoField;
    private JButton salvarButton;

    public FuncionarioServicoForm(FuncionarioServicoTable funcionarioServicoTable) {
        setTitle("Cadastro de FuncionarioServico");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        codFuncionarioField = new JTextField(20);
        codServicoField = new JTextField(20);
        salvarButton = new JButton("Salvar");

        add(new JLabel("CodFuncionario:"));
        add(codFuncionarioField);
        add(new JLabel("CodServico:"));
        add(codServicoField);
        add(salvarButton);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFuncionarioServico();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                funcionarioServicoTable.refreshData();
            }
        });
    }

    private void salvarFuncionarioServico() {
        int codFuncionario = Integer.parseInt(codFuncionarioField.getText());
        int codServico = Integer.parseInt(codServicoField.getText());

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO FuncionarioServico (codFuncionario, codServico) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, codFuncionario);
                statement.setInt(2, codServico);
                statement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "FuncionarioServico salvo com sucesso no banco de dados!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar FuncionarioServico no banco de dados: " + ex.getMessage());
        }
    }
}