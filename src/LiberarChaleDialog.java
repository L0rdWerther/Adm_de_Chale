import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LiberarChaleDialog extends JDialog {
    private JComboBox<String> chaleComboBox;
    private JButton liberarButton;

    public LiberarChaleDialog() {
        setTitle("Liberar Chalé");
        setSize(300, 200);
        setLayout(new BorderLayout());

        chaleComboBox = new JComboBox<>();
        liberarButton = new JButton("Liberar");

        // Carregar chalés disponíveis
        loadAvailableChales();

        add(chaleComboBox, BorderLayout.CENTER);
        add(liberarButton, BorderLayout.SOUTH);

        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedChale = (String) chaleComboBox.getSelectedItem();
                if (selectedChale != null) {
                    int chaleId = Integer.parseInt(selectedChale.split(" - ")[0]);
                    liberarChale(chaleId);
                    JOptionPane.showMessageDialog(LiberarChaleDialog.this, "Chalé liberado com sucesso!");
                    dispose();
                }
            }
        });
    }

    private void loadAvailableChales() {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT codChale, localizacao FROM Chale WHERE status = 'ocupado'")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int codChale = resultSet.getInt("codChale");
                String localizacao = resultSet.getString("localizacao");
                chaleComboBox.addItem(codChale + " - " + localizacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar chalés disponíveis: " + e.getMessage());
        }
    }

    private void liberarChale(int chaleId) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("CALL liberar_chale(?)")) {
            statement.setInt(1, chaleId);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao liberar chalé: " + e.getMessage());
        }
    }
}