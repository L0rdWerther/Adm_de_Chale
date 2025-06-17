import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class HospedagemServicoTable extends JFrame {
    private JTable table;
    private JButton addButton;

    public HospedagemServicoTable() {
        setTitle("Hospedagem Serviços");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        table = new JTable();
        addButton = new JButton("Adicionar Hospedagem Serviço");

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        loadData();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HospedagemServicoForm form = new HospedagemServicoForm();
                form.setVisible(true);
            }
        });
    }

    private void loadData() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM HospedagemServico";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                Vector<String> columnNames = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnName(i));
                }

                Vector<Vector<Object>> data = new Vector<>();
                while (resultSet.next()) {
                    Vector<Object> vector = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        vector.add(resultSet.getObject(i));
                    }
                    data.add(vector);
                }

                table.setModel(new DefaultTableModel(data, columnNames));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }
    }
}