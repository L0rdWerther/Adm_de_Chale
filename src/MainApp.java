import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    private JButton clienteButton;
    private JButton chaleButton;
    private JButton hospedagemButton;
    private JButton funcionarioButton;
    private JButton hospedagemServicoButton;
    private JButton servicoButton;
    private JButton funcionarioServico;
    private JButton liberarChaleButton;

    public MainApp() {
        setTitle("Main Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        clienteButton = new JButton("Abrir Cliente");
        chaleButton = new JButton("Abrir Chale");
        hospedagemButton = new JButton("Abrir Hospedagem");
        funcionarioButton = new JButton("Abrir Funcionário");
        servicoButton = new JButton("Abrir Serviço");
        funcionarioServico = new JButton("Abrir Funcionario Servico");
        hospedagemServicoButton = new JButton("Abrir Hospedagem Serviço");
        liberarChaleButton = new JButton("Liberar Chalé");

        add(clienteButton);
        add(chaleButton);
        add(hospedagemButton);
        add(funcionarioButton);
        add(hospedagemServicoButton);
        add(servicoButton);
        add(funcionarioServico);
        add(liberarChaleButton);

        liberarChaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiberarChaleDialog liberarChaleDialog = new LiberarChaleDialog();
                liberarChaleDialog.setVisible(true);
            }
        });

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteTable clienteTable = new ClienteTable();
                clienteTable.setVisible(true);
            }
        });

        chaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChaleTable chaleTable = new ChaleTable();
                chaleTable.setVisible(true);
            }
        });

        hospedagemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HospedagemTable hospedagemTable = new HospedagemTable();
                hospedagemTable.setVisible(true);
            }
        });

        funcionarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuncionarioTable funcionarioTable = new FuncionarioTable();
                funcionarioTable.setVisible(true);
            }
        });

        hospedagemServicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HospedagemServicoTable hospedagemServicoTable = new HospedagemServicoTable();
                hospedagemServicoTable.setVisible(true);
            }
        });

        servicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServicoTable servicoTable = new ServicoTable();
                servicoTable.setVisible(true);
            }
        });

        funcionarioServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FuncionarioServicoTable funcionarioServicoTable = new FuncionarioServicoTable();
                funcionarioServicoTable.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        DatabaseUtil.executeSchema("schema.sql");

        MainApp mainApp = new MainApp();
        mainApp.setVisible(true);
    }
}