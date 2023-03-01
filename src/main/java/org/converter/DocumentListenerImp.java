package org.converter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.math.BigDecimal;

public class DocumentListenerImp implements DocumentListener {

    private final JTextField textField;
    private final Money money;
    private DocumentListenerImp otherDocumentListener;

    public DocumentListenerImp(JTextField textField, String currencyCode, RateProvider rateProvider) {
        this.textField = textField;
        this.money = new Money("0.00", currencyCode , rateProvider);
    }

    public void setOtherDocumentListener(DocumentListenerImp otherDocumentListener) {
        this.otherDocumentListener = otherDocumentListener;
    }

    public Money getMoney() {
        return money;
    }

    private void calculateExchange() {
        if (!textField.getText().matches("^[0-9]+([.][0-9]+)?$")) {
            otherDocumentListener.textField.getDocument().removeDocumentListener(otherDocumentListener);
            otherDocumentListener.textField.setText("");
            otherDocumentListener.textField.getDocument().addDocumentListener(otherDocumentListener);
            return;
        }
        otherDocumentListener.textField.getDocument().removeDocumentListener(otherDocumentListener);
        money.setAmount(new BigDecimal(textField.getText()));
        otherDocumentListener.getMoney().convertCurrencyFrom(money);
        otherDocumentListener.textField.setText(otherDocumentListener.getMoney().getAmount().toString());
        otherDocumentListener.textField.getDocument().addDocumentListener(otherDocumentListener);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        calculateExchange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        calculateExchange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
