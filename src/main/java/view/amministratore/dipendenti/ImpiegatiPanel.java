package view.amministratore.dipendenti;

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
import model.Impiegato;
import view.amministratore.api.MultiLineRenderer;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class ImpiegatiPanel implements WorkPanel{

    private static final String PANEL_NAME = "Impiegati";
    private static final int BUTTON_SIZE = 15;

    private final ControllerAmm controller;
    private final JPanel mainPanel;
    private JTable impiegati;
    private TableModel tableModel;

    public ImpiegatiPanel(final ControllerAmm controller){
        this.controller = controller;

        var buttonsPanel = new JPanel(new BorderLayout());
        var buttons = new JPanel(new GridLayout(1, 2));
        var nuovo = GenericButton.getGenericButton("Nuovo +", BUTTON_SIZE, "Nuovo");
        var modifica = GenericButton.getGenericButton("Modifica", BUTTON_SIZE, "Modifica");
        var actionListener = new ButtonListener();
        nuovo.addActionListener(actionListener);
        modifica.addActionListener(actionListener);
        buttons.add(nuovo);
        buttons.add(modifica);
        buttonsPanel.add(buttons, BorderLayout.EAST);

        this.tableModel = new TableModel();
        this.impiegati = new JTable(tableModel);
        this.impiegati.setDefaultRenderer(Object.class, new MultiLineRenderer());
        this.impiegati.getColumnModel().getColumn(0).setPreferredWidth(15);
        var scrollTable = new JScrollPane(this.impiegati);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        modifica.setEnabled(false);

        this.impiegati.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = this.impiegati.getSelectedRow() != -1;
            modifica.setEnabled(selected);
        });

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);
        this.updateImpiegati();
    }

    public void updateImpiegati() {
        this.tableModel.setImpiegati(this.controller.getImpiegati());
        tableModel.fireTableDataChanged();
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
                    new NewImpiegato(controller, ImpiegatiPanel.this);
                    updateImpiegati();
                    break;
                case "Modifica":
                    break;
                default:
                    break;
            }
        }

    }

    private class TableModel extends AbstractTableModel {

        private static final int COLUMN = 8;

        private final List<String> nomiColonne;
        private List<Impiegato> impiegati;

        public TableModel() {
            nomiColonne = List.of("Codice", "CF", "Nome", 
                "Cognome", "Data nascita", "Luogo nascita", "Residenza", "Telefono");
        }

        private void setImpiegati(List<Impiegato> impiegati) {
            this.impiegati = impiegati;
        }

        @Override
        public int getRowCount() {
            return this.impiegati.size();
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
            var impiegato = this.impiegati.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> impiegato.getCodImpiegato();
                case 1 -> impiegato.getCF();
                case 2 -> impiegato.getNome();
                case 3 -> impiegato.getCognome();
                case 4 -> impiegato.getDataNascita();
                case 5 -> impiegato.getLuogoNascita();
                case 6 -> impiegato.getResidenza();
                case 7 -> impiegato.getTelefono();
                default -> null;
            };
        }

    }

}
