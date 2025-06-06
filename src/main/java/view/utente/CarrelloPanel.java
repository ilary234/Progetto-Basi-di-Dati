package view.utente;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import controller.utente.ControllerUtente;
import view.api.GenericButton;

public class CarrelloPanel extends JPanel {
    private static final int COLOR_BUTTONS_PANEL = 0x30A4CF;
    private static final Color SELECTED_COLOR = new Color(0x99C2EA);
    private static final int TEXT_SIZE = 15;

    private JButton carrelloButton;
    private JButton ordiniButton;
    private JButton selected;
    private JPanel switchPanel;
    private JPanel carrelloView;
    private JPanel ordiniView;
    private ControllerUtente controller;
    private UserScene userScene;

    public CarrelloPanel(ControllerUtente controller, UserScene userScene) {
        this.controller = controller;
        this.userScene = userScene;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 500));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        carrelloButton = GenericButton.getGenericButton("Carrello", TEXT_SIZE, "Carrello");
        ordiniButton = GenericButton.getGenericButton("Ordini", TEXT_SIZE, "Ordini");
        carrelloButton.setForeground(Color.WHITE);
        ordiniButton.setForeground(Color.WHITE);
        buttonPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        buttonPanel.add(carrelloButton);
        buttonPanel.add(ordiniButton);

        switchPanel = new JPanel(new CardLayout());

        this.carrelloView = creaCarrelloView();

        switchPanel.add(carrelloView, "Carrello");
        this.ordiniView = creaOrdiniView();
        switchPanel.add(ordiniView, "Ordini");

        selected = carrelloButton;
        GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);

        ActionListener menuListener = e -> {
            JButton button = (JButton) e.getSource();
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, false);
            selected = button;
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);
            CardLayout cl = (CardLayout) switchPanel.getLayout();
            cl.show(switchPanel, button.getActionCommand());
        };
        carrelloButton.addActionListener(menuListener);
        ordiniButton.addActionListener(menuListener);

        add(buttonPanel, BorderLayout.NORTH);
        add(switchPanel, BorderLayout.CENTER);
    }

    public JPanel creaCarrelloView() {
        Integer codOrdine = this.controller.getOrdineAperto();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton pagaButton = GenericButton.getGenericButton("PAGA", TEXT_SIZE, "PAGA");

        if (codOrdine == null) {
            pagaButton.setEnabled(false);
            listaPanel.add(new JLabel("Il carrello è vuoto."));
        } else {
            List<String> dettagliCarrello = this.controller.getDettagliOrdineAperto(); 
            pagaButton.setEnabled(true);
            for (String dettaglio : dettagliCarrello) {
                JPanel dettagliPanel = new JPanel();
                dettagliPanel.setLayout(new FlowLayout());
                JButton meno = GenericButton.getGenericButton("-", TEXT_SIZE, "-");
                JLabel label = new JLabel(dettaglio);
                dettagliPanel.add(meno);
                dettagliPanel.add(label);
                listaPanel.add(dettagliPanel);

                meno.addActionListener(ev -> {
                    int conferma = JOptionPane.showConfirmDialog(
                        this,
                        "Sei sicuro di voler eliminare questo elemento dal carrello?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (conferma == JOptionPane.YES_OPTION) {
                        String[] parti = dettaglio.split(" - ");
                        String titolo = parti[0].trim();
                        String categoria = parti[1].split(" x")[0].trim();
                        this.controller.rimuoviDalCarrello(titolo, categoria);

                        JPanel nuovoCarrelloView = creaCarrelloView();
                        switchPanel.remove(carrelloView);
                        carrelloView = nuovoCarrelloView;
                        switchPanel.add(carrelloView, "Carrello");
                        CardLayout cl = (CardLayout) switchPanel.getLayout();
                        cl.show(switchPanel, "Carrello");
                    }
                });
            }
        }

        panel.add(new JScrollPane(listaPanel), BorderLayout.CENTER);

        double costoTotale = 0;
        if(codOrdine != null) {
            costoTotale = controller.getCostoTotale();
        }
        JLabel costoLabel = new JLabel(String.format("Totale: %.2f €", costoTotale));
        costoLabel.setForeground(Color.WHITE);
        costoLabel.setFont(costoLabel.getFont().deriveFont(java.awt.Font.BOLD));
        JPanel costoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        costoPanel.setOpaque(false);
        costoPanel.add(costoLabel);

        pagaButton.setForeground(Color.WHITE);
        pagaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel pagaPanel = new JPanel();
        pagaPanel.setLayout(new BoxLayout(pagaPanel, BoxLayout.Y_AXIS));
        pagaPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        pagaPanel.add(Box.createVerticalStrut(10));
        pagaPanel.add(costoPanel);
        pagaPanel.add(Box.createVerticalStrut(5));
        pagaPanel.add(pagaButton);

        panel.add(pagaPanel, BorderLayout.SOUTH);

        pagaButton.addActionListener(e -> {
            float prezzoIniziale = controller.getCostoTotale();
            Map<String, Float>  listaCategoriePrezzi = controller.getCategoriePrezzi();
            Map<String, Float> listaSconti = controller.getSconti();
            float prezzoScontato = calcolaCostoTotaleConSconti(listaCategoriePrezzi, listaSconti);

            JPanel confermaPanel = new JPanel();
            confermaPanel.setLayout(new BoxLayout(confermaPanel, BoxLayout.Y_AXIS));
            confermaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel prezzoLabel = new JLabel();
            if(prezzoScontato == prezzoIniziale) {
                prezzoLabel.setText("<html>Totale:<span style='color:green;font-weight:bold;'>" +
                String.format("%.2f €", prezzoIniziale) +
                "</span></html>");
            } else {
                prezzoLabel.setText("<html>Totale: <span style='text-decoration:line-through;color:gray;'>" +
                String.format("%.2f €", prezzoIniziale) +
                "</span> <span style='color:green;font-weight:bold;'>" +
                String.format("%.2f €", prezzoScontato) +
                "</span></html>");
            }
            prezzoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            confermaPanel.add(Box.createVerticalStrut(10));
            confermaPanel.add(prezzoLabel);

            JLabel pagamentoLabel = new JLabel("Seleziona il tipo di pagamento:");
            pagamentoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            confermaPanel.add(pagamentoLabel);

            String[] tipiPagamento = {"Bancomat", "Carta di credito"};
            JComboBox<String> comboPagamento = new JComboBox<>(tipiPagamento);
            comboPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);
            comboPagamento.setSelectedIndex(-1);
            confermaPanel.add(Box.createVerticalStrut(20));
            confermaPanel.add(comboPagamento);

            int result = JOptionPane.showConfirmDialog(
                this,
                confermaPanel,
                "Pagamento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                if (comboPagamento.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(this, "Seleziona un tipo di pagamento!", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    Date data = new java.sql.Date(System.currentTimeMillis());
                    Time orario = new java.sql.Time(System.currentTimeMillis());
                    String tipoPagamento = (String) comboPagamento.getSelectedItem();
                    updateDatiAnnunciServizi();
                    String username = controller.getCredenziali().left;
                    controller.updateDatiOrdine(orario, data, tipoPagamento, username, prezzoScontato);
                    controller.getModel().aggiornaCodOrdineAperto(username);
                    JOptionPane.showMessageDialog(this, "Pagamento effettuato con " + comboPagamento.getSelectedItem() + "!");

                    userScene.changeWorkPanel(new HomePanel(controller, userScene));

                    SwingUtilities.getWindowAncestor(this).dispose();
                }
            }
        });

        return panel;
    }

    private JPanel creaOrdiniView() {
        List<String> ordiniPrecedenti = this.controller.getOrdiniPrecedenti(this.controller.getCredenziali().left);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (ordiniPrecedenti == null || ordiniPrecedenti.isEmpty()) {
            listaPanel.add(new JLabel("Nessun ordine precedente."));
        } else {
            for (String dettaglio : ordiniPrecedenti) {
                JLabel label = new JLabel(dettaglio);
                listaPanel.add(label);
                listaPanel.add(Box.createVerticalStrut(5));
            }
        }

        panel.add(new JScrollPane(listaPanel), BorderLayout.CENTER);
        return panel;
    }

    public void showAsSidePanel(Component parent) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent));
        dialog.setTitle("Carrello e Ordini");
        dialog.setModal(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(this);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private float calcolaCostoTotaleConSconti(Map<String, Float> categoriaTotale, Map<String, Float> categoriaSconto) {
        float totale = 0;
        for (Map.Entry<String, Float> entry : categoriaTotale.entrySet()) {
            String categoria = entry.getKey();
            float totaleCategoria = entry.getValue();
            if (categoriaSconto.containsKey(categoria)) {
                float sconto = categoriaSconto.get(categoria);
                totale += totaleCategoria * sconto;
            } else {
                totale += totaleCategoria;
            }
        }
        return totale;
    }

    private void updateDatiAnnunciServizi() {
        Map<String, Integer> annunci = this.controller.getAnnunciDaModificare();
        for (Map.Entry<String, Integer> entry : annunci.entrySet()) {
            String titolo = entry.getKey();
            int quantita = entry.getValue();
            this.controller.updateBigliettiAnnuncioServizio(titolo, quantita);
            this.controller.updateBigliettiServizio(titolo, quantita);
        }
    }
}
