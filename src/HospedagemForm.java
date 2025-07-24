import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospedagemForm extends JFrame {
    private JTextField codChaleField;
    private JTextField statusField;
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JTextField qtdPessoasField;
    private JTextField valorDiariaField;
    private JTextField codClienteField;
    private JTextField descontoField;
    private JButton salvarButton;
    private List<Hospedagem> hospedagens;

    public HospedagemForm(HospedagemTable hospedagemTable) {
        setTitle("Cadastro de Hospedagem");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        codChaleField = new JTextField(20);
        statusField = new JTextField(20);
        dataInicioField = new JTextField(20);
        dataFimField = new JTextField(20);
        qtdPessoasField = new JTextField(20);
        valorDiariaField = new JTextField(20);
        codClienteField = new JTextField(20);
        descontoField = new JTextField(20);
        salvarButton = new JButton("Salvar");
        hospedagens = new ArrayList<>();

        add(new JLabel("Código do Chalé:"));
        add(codChaleField);
        add(new JLabel("Status:"));
        add(statusField);
        add(new JLabel("Data de Início (AAAA-MM-DD):"));
        add(dataInicioField);
        add(new JLabel("Data de Fim (AAAA-MM-DD):"));
        add(dataFimField);
        add(new JLabel("Quantidade de Pessoas:"));
        add(qtdPessoasField);
        add(new JLabel("Valor da Diária:"));
        add(valorDiariaField);
        add(new JLabel("Código do Cliente:"));
        add(codClienteField);
        add(new JLabel("Desconto:"));
        add(descontoField);
        add(salvarButton);

        JButton verHospedesAtivosButton = new JButton("Ver Hóspedes Ativos");
        add(verHospedesAtivosButton);

        verHospedesAtivosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HospedesAtivosViewer().setVisible(true);
            }
        });


        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarHospedagem();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                hospedagemTable.refreshData();
            }
        });
    }

    //transaction example
    private void salvarHospedagem() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            connection.setAutoCommit(false); // Inicia a transação

            try {
                int codChale = Integer.parseInt(codChaleField.getText());
                String status = statusField.getText();
                String dataInicio = dataInicioField.getText();
                String dataFim = dataFimField.getText();
                int qtdPessoas = Integer.parseInt(qtdPessoasField.getText());
                double valorDiaria = Double.parseDouble(valorDiariaField.getText());
                int codCliente = Integer.parseInt(codClienteField.getText());
                double desconto = Double.parseDouble(descontoField.getText());

                // Insere a hospedagem
                String sqlHospedagem = "INSERT INTO Hospedagem (codChale, status, dataInicio, dataFim, qtdPessoas, valorDiaria, codCliente, desconto) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sqlHospedagem)) {
                    statement.setInt(1, codChale);
                    statement.setString(2, status);
                    statement.setDate(3, java.sql.Date.valueOf(dataInicio));
                    statement.setDate(4, java.sql.Date.valueOf(dataFim));
                    statement.setInt(5, qtdPessoas);
                    statement.setDouble(6, valorDiaria);
                    statement.setInt(7, codCliente);
                    statement.setDouble(8, desconto);
                    statement.executeUpdate();
                }

                // Atualiza o status do chalé
                String sqlChale = "UPDATE Chale SET status = 'ocupado' WHERE codChale = ?";
                try (PreparedStatement statement = connection.prepareStatement(sqlChale)) {
                    statement.setInt(1, codChale);
                    statement.executeUpdate();
                }

                connection.commit(); // Confirma a transação
                JOptionPane.showMessageDialog(null, "Hospedagem salva com sucesso no banco de dados!");
            } catch (SQLException ex) {
                connection.rollback(); // Reverte a transação em caso de erro
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao salvar hospedagem no banco de dados: " + ex.getMessage());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao processar os dados: " + ex.getMessage());
        }
    }
}
