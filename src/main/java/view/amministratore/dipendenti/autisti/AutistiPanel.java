package view.amministratore.dipendenti.autisti;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.amministratore.ControllerAmm;
import model.Autista;
import view.amministratore.api.MultiLineRenderer;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class AutistiPanel implements WorkPanel {
    
    private static final String PANEL_NAME = "Autisti";
    private static final int BUTTON_SIZE = 15;

    private final ControllerAmm controller;
    private final JPanel mainPanel;
    private JTable autisti;
    private TableModel tableModel;

    public AutistiPanel(final ControllerAmm controller){
        this.controller = controller;

        var buttonsPanel = new JPanel(new BorderLayout());
        var buttons = new JPanel(new GridLayout(1, 3));
        var nuovo = GenericButton.getGenericButton("Nuovo +", BUTTON_SIZE, "Nuovo");
        var modifica = GenericButton.getGenericButton("Aggiungi KB", BUTTON_SIZE, "Aggiungi");
        var statistiche = GenericButton.getGenericButton("Statistiche", BUTTON_SIZE, "Statistiche");
        var actionListener = new ButtonListener();
        nuovo.addActionListener(actionListener);
        modifica.addActionListener(actionListener);
        statistiche.addActionListener(actionListener);
        buttons.add(nuovo);
        buttons.add(modifica);
        buttons.add(statistiche);
        buttonsPanel.add(buttons, BorderLayout.EAST);

        this.tableModel = new TableModel();
        this.autisti = new JTable(tableModel);
        this.autisti.setDefaultRenderer(Object.class, new MultiLineRenderer());
        var scrollTable = new JScrollPane(this.autisti);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        modifica.setEnabled(false);
        statistiche.setEnabled(false);

        this.autisti.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = this.autisti.getSelectedRow() != -1;
            modifica.setEnabled(selected && ((int) autisti.getValueAt(autisti.getSelectedRow(), 5) == 0));
            statistiche.setEnabled(selected);
        });

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);
        this.updateAutisti();
    }

    public void updateAutisti() {
        this.tableModel.setImpiegati(this.controller.getAutisti());
        tableModel.fireTableDataChanged();
    }

    public void updateResidenza(String residenza) {
        var code = (int) autisti.getValueAt(autisti.getSelectedRow(), 0);
        this.controller.updateResidenza(code, residenza);
    }

    public void updateTelefono(String telefono) {
        var code = (int) autisti.getValueAt(autisti.getSelectedRow(), 0);
        this.controller.updateTelefono(code, telefono);
    }

    public void updatePassword(String password) {
        var code = (int) autisti.getValueAt(autisti.getSelectedRow(), 0);
        this.controller.updatePassword(code, password);
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getName() {
        return PANEL_NAME;
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var button = (JButton) e.getSource();
            switch (button.getActionCommand()) {
                case "Nuovo":
                    new NewAutista(controller);
                    updateAutisti();
                    break;
                case "Aggiungi":
                    var CF = (String) autisti.getValueAt(autisti.getSelectedRow(), 0); 
                    new AddKB(controller, CF);
                    updateAutisti();
                    break;
                case "Statistiche":
                    var cf = (String) autisti.getValueAt(autisti.getSelectedRow(), 0); 
                    new AutistiStatistic(controller, cf);
                    break;
                default:
                    break;
            }
        }

    }

    private class TableModel extends AbstractTableModel {

        private static final int COLUMN = 10;

        private final List<String> nomiColonne;
        private List<Autista> autisti;

        public TableModel() {
            nomiColonne = List.of( "CF", "Nome", "Cognome", "Patente", "Scadenza CQC", "KB", 
            "Data nascita", "Luogo nascita", "Residenza", "Telefono");
        }

        private void setImpiegati(List<Autista> autisti) {
            this.autisti = autisti;
        }

        @Override
        public int getRowCount() {
            return this.autisti.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN;
        }

        public String getColumnName(int columnIndex) {
            return this.nomiColonne.get(columnIndex);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var autista = this.autisti.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> autista.getCF();
                case 1 -> autista.getNome();
                case 2 -> autista.getCognome();
                case 3 -> autista.getNumeroPatente();
                case 4 -> autista.getScadenzaCQC();
                case 5 -> autista.getNumeroKB();
                case 6 -> autista.getDataNascita();
                case 7 -> autista.getLuogoNascita();
                case 8 -> autista.getResidenza();
                case 9 -> autista.getTelefono();
                default -> null;
            };
        }

    }

}
