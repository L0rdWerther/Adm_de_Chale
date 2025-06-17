import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospedagemServicoForm extends JFrame {
    private JTextField codHospedagemField;
    private JTextField codServicoField;
    private JTextField quantidadeField;
    private JButton salvarButton;

    public HospedagemServicoForm() {
        setTitle("Cadastro de Hospedagem Serviço");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        codHospedagemField = new JTextField(20);
        codServicoField = new JTextField(20);
        quantidadeField = new JTextField(20);
        salvarButton = new JButton("Salvar");

        add(new JLabel("Código da Hospedagem:"));
        add(codHospedagemField);
        add(new JLabel("Código do Serviço:"));
        add(codServicoField);
        add(new JLabel("Quantidade:"));
        add(quantidadeField);
        add(salvarButton);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarHospedagemServico();
            }
        });
    }

    private void salvarHospedagemServico() {
        int codHospedagem = Integer.parseInt(codHospedagemField.getText());
        int codServico = Integer.parseInt(codServicoField.getText());
        int quantidade = Integer.parseInt(quantidadeField.getText());

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO HospedagemServico (codHospedagem, codServico, quantidade) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, codHospedagem);
                statement.setInt(2, codServico);
                statement.setInt(3, quantidade);
                statement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Hospedagem Serviço salvo com sucesso no banco de dados!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar hospedagem serviço no banco de dados: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        HospedagemServicoForm form = new HospedagemServicoForm();
        form.setVisible(true);
    }
}