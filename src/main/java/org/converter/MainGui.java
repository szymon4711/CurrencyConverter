package org.converter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainGui {
    private JPanel mainPanel;
    private JTextField curTextField;
    private JTextField plnTextField;
    private JLabel plnLabel;
    private JLabel curLabel;
    private JLabel label;


    public MainGui(String[] currencyCodeArgs) {
        String currencyCode;
        if (currencyCodeArgs.length == 0)
            currencyCode = "GBP";
        else
            currencyCode = currencyCodeArgs[0];
        RateProvider rateProvider = new RateProvider(currencyCode.toUpperCase());

        DocumentListenerImp documentListenerCur = new DocumentListenerImp(curTextField, currencyCode, rateProvider);
        DocumentListenerImp documentListenerPln = new DocumentListenerImp(plnTextField, "PLN", rateProvider);
        documentListenerCur.setOtherDocumentListener(documentListenerPln);
        documentListenerPln.setOtherDocumentListener(documentListenerCur);
        curTextField.getDocument().addDocumentListener(documentListenerCur);
        plnTextField.getDocument().addDocumentListener(documentListenerPln);

        ImageProvider imageProvider = new ImageProvider();
        try {
            Image image = imageProvider.getImage(documentListenerCur);
            Image plnImage = imageProvider.getImage(documentListenerPln);
            curLabel.setIcon(new ImageIcon(image));
            plnLabel.setIcon(new ImageIcon(plnImage));
        } catch (IOException | NullPointerException e) {
            curLabel.setText(currencyCode);
            plnLabel.setText("PLN");
        }

        label.setText("1 " + documentListenerCur.getMoney().getCurrency().getCurrencyCode()
                + " = " + documentListenerCur.getMoney().getRateProvider().getPlnToCurrencyRate() + " PLN");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CurrencyConverter");
        frame.setContentPane(new MainGui(args).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
