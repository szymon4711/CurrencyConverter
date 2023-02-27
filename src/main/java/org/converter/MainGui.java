package org.converter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainGui {
    private JTextField gbpTextField;
    private JPanel mainPanel;
    private JTextField plnTextField;
    private JLabel plnLabel;
    private JLabel gbpLabel;


    public MainGui() {
        DocumentListenerImp documentListenerGbp = new DocumentListenerImp(gbpTextField, "GBP");
        DocumentListenerImp documentListenerPln = new DocumentListenerImp(plnTextField, "PLN");
        documentListenerGbp.setOtherDocumentListener(documentListenerPln);
        documentListenerPln.setOtherDocumentListener(documentListenerGbp);

        ImageProvider imageProvider = new ImageProvider();
        Image image = imageProvider.getImage(documentListenerGbp);
        Image plnImage = imageProvider.getImage(documentListenerPln);

        gbpLabel.setIcon(new ImageIcon(image));
        plnLabel.setIcon(new ImageIcon(plnImage));
        gbpTextField.getDocument().addDocumentListener(documentListenerGbp);
        plnTextField.getDocument().addDocumentListener(documentListenerPln);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CurrencyConverter");
        frame.setContentPane(new MainGui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
