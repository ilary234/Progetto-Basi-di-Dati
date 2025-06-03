package view.amministratore.servizi;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.amministratore.ControllerAmm;
import model.Servizio;
import view.amministratore.api.MultiLineRenderer;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class ServicePanel implements WorkPanel{

    private static final String PANEL_NAME = "Service";
    
    private static final int BUTTON_SIZE = 15;

    private final ControllerAmm controller;
    private final JPanel mainPanel;
    private JTable servizi;
    private TableModel tableModel;

    public ServicePanel(final ControllerAmm controller){
        this.controller = controller;
        this.mainPanel = new JPanel(new BorderLayout());
        
        var buttonsPanel = new JPanel(new BorderLayout());
        var buttons = new JPanel();
        var create = GenericButton.getGenericButton("Nuovo +", BUTTON_SIZE, "Nuovo Servizio");
        var statistiche = GenericButton.getGenericButton("Statistiche", BUTTON_SIZE, "Statistiche");
        var actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                switch (button.getActionCommand()) {
                    case "Nuovo Servizio":
                        new NewService(controller, ServicePanel.this);
                        break;
                    case "Statistiche":
                        new ServiziStatistica(controller);
                        break;
                    default:
                        break;
                }
            }
            
        };
        create.addActionListener(actionListener);
        statistiche.addActionListener(actionListener);
        buttons.add(create);
        buttons.add(statistiche);
        buttonsPanel.add(buttons, BorderLayout.EAST);

        this.tableModel = new TableModel();
        this.servizi = new JTable(tableModel);
        this.servizi.setDefaultRenderer(Object.class, new MultiLineRenderer());
        var scrollTable = new JScrollPane(this.servizi);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.updateServizi();

        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);
    }

    private void updateServizi() {
        this.tableModel.setServizi(this.controller.getServizi());
        tableModel.fireTableDataChanged();
    }

    public void addServizio(String partenza, String destinazione, String orario, String categoria) {
        var codice = servizi.getRowCount() > 0 ? (int) servizi.getValueAt(servizi.getRowCount() - 1, 0) + 1 : 1;
        this.controller.addServizio(codice, partenza, destinazione, orario, 0, categoria);
        this.updateServizi();
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

        private static final int COLUMN = 6;

        private final List<String> nomiColonne;
        private List<Servizio> servizi;

        public TableModel() {
            nomiColonne = List.of("Codice", "Categoria", "Partenza", "Destinazione", 
                "Ora Partenza", "Biglietti Venduti");
        }

        private void setServizi(List<Servizio> servizi) {
            this.servizi = servizi;
        }

        @Override
        public int getRowCount() {
            return this.servizi.size();
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
            var servizio = this.servizi.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> servizio.getCodServizio();
                case 1 -> servizio.getCategoriaServizio();
                case 2 -> servizio.getPartenza();
                case 3 -> servizio.getDestinazione();
                case 4 -> servizio.getOrarioPartenza();
                case 5 -> servizio.getBigliettiVenduti();
                default -> null;
            };
        }

    }

}
