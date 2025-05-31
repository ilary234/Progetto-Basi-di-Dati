package view.amministratore.mezzi;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.amministratore.ControllerAmm;
import model.Mezzo;
import view.amministratore.api.MultiLineRenderer;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class TransportPanel implements WorkPanel{

    private static final String PANEL_NAME = "Transport";

    private static final int BUTTON_SIZE = 15;

    private final ControllerAmm controller;
    private final JPanel mainPanel;
    private JTable mezzi;
    private TableModel tableModel;

    public TransportPanel(final ControllerAmm controller){
        this.controller = controller;
        this.mainPanel = new JPanel(new BorderLayout());
        
        var buttonsPanel = new JPanel(new BorderLayout());
        var create = GenericButton.getGenericButton("Nuovo +", BUTTON_SIZE, "Nuovo Mezzo");
        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new NewTransport(controller, TransportPanel.this);
            }
            
        });
        buttonsPanel.add(create, BorderLayout.EAST);

        this.tableModel = new TableModel();
        this.mezzi = new JTable(tableModel);
        this.mezzi.setDefaultRenderer(Object.class, new MultiLineRenderer());
        this.mezzi.getColumnModel().getColumn(0).setPreferredWidth(15);
        this.mezzi.getColumnModel().getColumn(2).setPreferredWidth(40);
        this.mezzi.getColumnModel().getColumn(6).setPreferredWidth(60);
        this.mezzi.getColumnModel().getColumn(7).setPreferredWidth(40);
        var scrollTable = new JScrollPane(this.mezzi);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.updateMezzi();

        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);
    }

    public void updateMezzi() {
        this.tableModel.setMezzi(this.controller.getMezzi());
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

    private class TableModel extends AbstractTableModel {

        private static final int COLUMN = 13;

        private final List<String> nomiColonne;
        private List<Mezzo> mezzi;

        public TableModel() {
            nomiColonne = List.of("N°", "Targa", "Euro", "Immatricolazione", 
                "Revisione", "Assicurazione", "CDPD", "PAX", "Km Totali", "Carrozzeria", "Modello", "Telaio", "Licenza Europea");
        }

        private void setMezzi(List<Mezzo> mezzi) {
            this.mezzi = mezzi;
        }

        @Override
        public int getRowCount() {
            return this.mezzi.size();
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
            var mezzo = this.mezzi.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> mezzo.getNumeroMezzo();
                case 1 -> mezzo.getTarga();
                case 2 -> mezzo.getEuro();
                case 3 -> mezzo.getAnnoImmatricolazione();
                case 4 -> mezzo.getDataRevisione();
                case 5 -> mezzo.getAssicurazione();
                case 6 -> mezzo.isCDPD();
                case 7 -> mezzo.getPAX();
                case 8 -> mezzo.getKmTotali();
                case 9 -> mezzo.getCarrozzeria();
                case 10 -> mezzo.getModello();
                case 11 -> mezzo.getTelaio();
                case 12 -> mezzo.getNumeroLicenzaEuropea();
                default -> null;
            };
        }

    }

}
