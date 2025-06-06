package view.amministratore.inserzioni.comunicazioni;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.amministratore.ControllerAmm;
import model.Comunicazione;
import view.amministratore.api.MultiLineRenderer;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class ComunicazioniPanel implements WorkPanel {

    private static final String PANEL_NAME = "Comunicazioni";
    private static final int BUTTON_SIZE = 15;

    private final ControllerAmm controller;
    private final JPanel mainPanel;
    private JTable comunicazioni;
    private TableModel tableModel;

    public ComunicazioniPanel(final ControllerAmm controller){
        this.controller = controller;

        var buttonsPanel = new JPanel(new BorderLayout());
        var buttons = new JPanel(new GridLayout(1, 3));
        var nuovo = GenericButton.getGenericButton("Nuovo +", BUTTON_SIZE, "Nuovo");
        var modifica = GenericButton.getGenericButton("Modifica", BUTTON_SIZE, "Modifica");
        var elimina = GenericButton.getGenericButton("Elimina", BUTTON_SIZE, "Elimina");
        var actionListener = new ButtonListener();
        nuovo.addActionListener(actionListener);
        modifica.addActionListener(actionListener);
        elimina.addActionListener(actionListener);
        buttons.add(nuovo);
        buttons.add(modifica);
        buttons.add(elimina);
        buttonsPanel.add(buttons, BorderLayout.EAST);

        this.tableModel = new TableModel();
        this.comunicazioni = new JTable(tableModel);
        this.comunicazioni.setDefaultRenderer(Object.class, new MultiLineRenderer());
        var scrollTable = new JScrollPane(this.comunicazioni);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        modifica.setEnabled(false);
        elimina.setEnabled(false);

        this.comunicazioni.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = this.comunicazioni.getSelectedRow() != -1;
            modifica.setEnabled(selected);
            elimina.setEnabled(selected);
        });

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);
        this.updateComunicazioni();
    }

    public void updateComunicazioni() {
        this.tableModel.setComunicazioni(this.controller.getComunicazioni());
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
                    new NewComunicazione(controller);
                    updateComunicazioni();
                    break;
                case "Modifica":
                    var code = (int) tableModel.getCode(comunicazioni.getSelectedRow());
                    var titolo = (String) comunicazioni.getValueAt(comunicazioni.getSelectedRow(), 0);
                    var descrizione = (String) comunicazioni.getValueAt(comunicazioni.getSelectedRow(), 1);
                    new ChangeComunicazione(controller, code, titolo, descrizione);
                    updateComunicazioni();
                    break;
                case "Elimina":
                    var response = JOptionPane.showConfirmDialog(controller.getFrame(), "Sei sicuro di voler eliminare?", 
                        "Conferma", JOptionPane.YES_NO_OPTION);
                    if (response ==  0) {
                        var cod = (int) tableModel.getCode(comunicazioni.getSelectedRow());
                        controller.deleteComunicazione(cod);
                        updateComunicazioni();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private class TableModel extends AbstractTableModel {

        private static final int COLUMN = 4;

        private final List<String> nomiColonne;
        private List<Comunicazione> comunicazioni;

        public TableModel() {
            nomiColonne = List.of("Titolo", "descrizione", "Pubblicato", "Impiegato");
        }

        private void setComunicazioni(List<Comunicazione> comunicazioni) {
            this.comunicazioni = comunicazioni;
        }

        @Override
        public int getRowCount() {
            return this.comunicazioni.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN;
        }

        public String getColumnName(int columnIndex) {
            return this.nomiColonne.get(columnIndex);
        }

        public int getCode(int rowIndex){
            return this.comunicazioni.get(rowIndex).getCodComunicazione();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var comunicazione = this.comunicazioni.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> comunicazione.getTitolo();
                case 1 -> comunicazione.getDescrizione();
                case 2 -> comunicazione.getDataPubblicazione();
                case 3 -> comunicazione.getCodImpiegato();
                default -> null;
            };
        }

    }

}
