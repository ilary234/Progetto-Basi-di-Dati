package view.amministratore.api;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MultiLineRenderer extends DefaultTableCellRenderer{

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