package view.amministratore.api;

import javax.swing.text.DocumentFilter;

public class MaxLenghtDocumentFilter extends DocumentFilter {

    private final int maxLenght;

    public MaxLenghtDocumentFilter(final int maxLenght) {
        this.maxLenght = maxLenght;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (fb.getDocument().getLength() + string.length() <= maxLenght) {
            super.insertString(fb, offset, string, attr);
        }
    }
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
        if (fb.getDocument().getLength() - length + (text != null ? text.length() : 0) <= maxLenght) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

}
