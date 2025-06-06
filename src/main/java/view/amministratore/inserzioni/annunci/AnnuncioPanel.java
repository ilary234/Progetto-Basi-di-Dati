package view.amministratore.inserzioni.annunci;

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
import model.AnnuncioServizio;
import view.amministratore.api.MultiLineRenderer;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class AnnuncioPanel implements WorkPanel {

    private static final String PANEL_NAME = "Annuncio";
    private static final int BUTTON_SIZE = 15;

    private final ControllerAmm controller;
    private final JPanel mainPanel;
    private JTable annunci;
    private TableModel tableModel;

    public AnnuncioPanel(final ControllerAmm controller){
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
        this.annunci = new JTable(tableModel);
        this.annunci.setDefaultRenderer(Object.class, new MultiLineRenderer());
        this.annunci.getColumnModel().getColumn(3).setPreferredWidth(30);
        this.annunci.getColumnModel().getColumn(4).setPreferredWidth(30);
        var scrollTable = new JScrollPane(this.annunci);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        modifica.setEnabled(false);

        this.annunci.getSelectionModel().addListSelectionListener(e -> {
            boolean selected = this.annunci.getSelectedRow() != -1;
            modifica.setEnabled(selected);
        });

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);
        this.updateAnnunci();
    }

    public void updateAnnunci() {
        this.tableModel.setAnnunci(this.controller.getAnnunci());
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
                    new NewAnnuncio(controller);
                    updateAnnunci();
                    break;
                case "Modifica":
                    var code = (int) tableModel.getCode(annunci.getSelectedRow());
                    var titolo = (String) annunci.getValueAt(annunci.getSelectedRow(), 0);
                    var descrizione = (String) annunci.getValueAt(annunci.getSelectedRow(), 1);
                    var prezzoBase = (float) annunci.getValueAt(annunci.getSelectedRow(), 5);
                    var visibile = (boolean) annunci.getValueAt(annunci.getSelectedRow(), 6);
                    var bigliettiDisponibili = (int) annunci.getValueAt(annunci.getSelectedRow(), 7);
                    new ChangeAnnuncio(controller, code, titolo, descrizione, prezzoBase, visibile, bigliettiDisponibili);
                    updateAnnunci();
                    break;
                default:
                    break;
            }
        }

    }

    private class TableModel extends AbstractTableModel {

        private static final int COLUMN = 8;

        private final List<String> nomiColonne;
        private List<AnnuncioServizio> annunci;

        public TableModel() {
            nomiColonne = List.of("Titolo", "descrizione", "Pubblicato", 
                "Impiegato", "Servizio", "Prezzo base", "Visibile", "Biglietti disponibili");
        }

        private void setAnnunci(List<AnnuncioServizio> annunci) {
            this.annunci = annunci;
        }

        @Override
        public int getRowCount() {
            return this.annunci.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN;
        }

        public String getColumnName(int columnIndex) {
            return this.nomiColonne.get(columnIndex);
        }

        public int getCode(int rowIndex){
            return this.annunci.get(rowIndex).getCodComunicazione();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var annuncio = this.annunci.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> annuncio.getTitolo();
                case 1 -> annuncio.getDescrizione();
                case 2 -> annuncio.getDataPubblicazione();
                case 3 -> annuncio.getCodImpiegato();
                case 4 -> annuncio.getCodServizio();
                case 5 -> annuncio.getPrezzoBase();
                case 6 -> annuncio.isVisibile();
                case 7 -> annuncio.getBigliettiDisponibili();
                default -> null;
            };
        }

    }

}
