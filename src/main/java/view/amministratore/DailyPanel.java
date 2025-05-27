package view.amministratore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import controller.amministratore.ControllerAmm;
import model.Volanda;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class DailyPanel implements WorkPanel{

    private static final String PANEL_NAME = "Daily";
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    private final ControllerAmm controller;
    private final List<String> giornaliere;
    private final JPanel mainPanel;
    private JScrollPane scrollList;
    private JTable volande;
    private TableModel tableModel;
    private JButton selected;
    
    public DailyPanel(final ControllerAmm controller) {
        this.controller = controller;
        this.giornaliere = new ArrayList<>();

        this.mainPanel = new JPanel(new BorderLayout());

        var buttonsPanel = new JPanel(new BorderLayout());
        var dailyButtons = new JPanel(new GridLayout(1, 2));
        var volandeButtons = new JPanel(new GridLayout(1, 2));
        var create = GenericButton.getGenericButton("Nuova +", BUTTON_SIZE, "Nuova Giornaliera");
        var delete = GenericButton.getGenericButton("Elimina", BUTTON_SIZE, "Elimina Giornaliera");
        var createVolanda = GenericButton.getGenericButton("Nuova Volanda +", BUTTON_SIZE, "Nuova Volanda");
        var deleteVolanda = GenericButton.getGenericButton("Elimina Volanda", BUTTON_SIZE, "Elimina Volanda");
        var actionListener = new ButtonListener();
        create.addActionListener(actionListener);
        delete.addActionListener(actionListener);
        createVolanda.addActionListener(actionListener);
        deleteVolanda.addActionListener(actionListener);
        dailyButtons.add(create);
        dailyButtons.add(delete);
        volandeButtons.add(createVolanda);
        volandeButtons.add(deleteVolanda);
        buttonsPanel.add(dailyButtons, BorderLayout.WEST);
        buttonsPanel.add(volandeButtons, BorderLayout.EAST);

        this.tableModel = new TableModel();
        this.volande = new JTable(tableModel);
        this.volande.setDefaultRenderer(Object.class, new MultiLineRenderer());
        var scrollTable = new JScrollPane(this.volande);
        scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.scrollList = new JScrollPane();
        this.createGiornaliere();
        
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
        this.mainPanel.add(scrollList, BorderLayout.WEST);
        this.mainPanel.add(scrollTable, BorderLayout.CENTER);


    }

    private void createGiornaliere() {
        var listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));

        this.giornaliere.clear();
        this.giornaliere.addAll(this.controller.getGiornaliere());
        
        for (var i = 0; i < this.giornaliere.size(); i++) {
            var date = this.giornaliere.get(i);
            var dateButton = GenericButton.getGenericButton("- " + date, TEXT_SIZE, date);
            dateButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    var button = (JButton) e.getSource();
                    GenericButton.setBackgroundVisible(selected, Color.LIGHT_GRAY, false);
                    selected = button;
                    GenericButton.setBackgroundVisible(selected, Color.LIGHT_GRAY, true);
                    tableModel.setVolande(controller.getVolande(button.getActionCommand()));
                    tableModel.fireTableDataChanged();
                }
                
            });
            if (i == 0) {
                this.selected = dateButton;
                GenericButton.setBackgroundVisible(this.selected, Color.LIGHT_GRAY, true);
            }
            listPanel.add(dateButton);
        };
        
        this.scrollList.setViewportView(listPanel);
        this.scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.tableModel.setVolande(this.controller.getVolande(this.giornaliere.getFirst()));
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
                case "Nuova Giornaliera":
                    break;
                case "Elimina Giornaliera":
                    var date = selected.getActionCommand();
                    controller.deleteGiornaliera(date);
                    createGiornaliere();
                    break;
                case "Nuova Volanda":
                break;
                case "Elimina Volanda":
                    var day = selected.getActionCommand();
                    var numeroVolanda = volande.getSelectedRow() + 1;
                    controller.deleteVolanda(day, numeroVolanda);
                    tableModel.fireTableDataChanged();
                break;
                default:
                    break;
            }
        }

    }

    
    private class TableModel extends AbstractTableModel {

        private static final int COLUMN = 9;

        private final List<String> nomiColonne;
        private List<Volanda> volande;

        public TableModel() {
            nomiColonne = List.of("Volanda", "Servizio", "Note", 
                "Fornitore", "Prezzo", "Km", "Autista", "Mezzo", "Committente");
        }

        private void setVolande(List<Volanda> volande) {
            this.volande = volande;
        }

        @Override
        public int getRowCount() {
            return this.volande.size();
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
            var volanda = this.volande.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> volanda.getNumeroVolanda();
                case 1 -> volanda.getCodServizio();
                case 2 -> volanda.getNote();
                case 3 -> volanda.getFornitore();
                case 4 -> volanda.getPrezzo();
                case 5 -> volanda.getKm();
                case 6 -> volanda.getAutista();
                case 7 -> volanda.getMezzo();
                case 8 -> volanda.getCommittente();
                case 9 -> null;
                default -> null;
            };
        }

    }

    private static class MultiLineRenderer extends DefaultTableCellRenderer{

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            var textArea = new javax.swing.JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setText(value == null ? "" : value.toString());
            textArea.setOpaque(true);
            if (isSelected) {
                textArea.setBackground(table.getSelectionBackground());
                textArea.setForeground(table.getSelectionForeground());
            } else {
                textArea.setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
            }
            // Calcola l'altezza preferita
            int colWidth = table.getColumnModel().getColumn(column).getWidth();
            textArea.setSize(colWidth, Short.MAX_VALUE);
            int maxHeight = textArea.getPreferredSize().height;
            for (int col = 0; col < table.getColumnCount(); col++) {
                Object cellValue = table.getValueAt(row, col);
                var tempArea = new javax.swing.JTextArea();
                tempArea.setLineWrap(true);
                tempArea.setWrapStyleWord(true);
                tempArea.setText(cellValue == null ? "" : cellValue.toString());
                tempArea.setSize(table.getColumnModel().getColumn(col).getWidth(), Short.MAX_VALUE);
                maxHeight = Math.max(maxHeight, tempArea.getPreferredSize().height);
            }
            if (table.getRowHeight(row) != maxHeight) {
                table.setRowHeight(row, maxHeight);
            }
            return textArea;
        }
    }


}
