package org.converter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.math.BigDecimal;

public class DocumentListenerImp implements DocumentListener {

    private final JTextField textField;
    private DocumentListenerImp otherDocumentListener;
    private final Money money;

    public DocumentListenerImp(JTextField textField, String currencyCode) {
        this.textField = textField;
        this.money = new Money("0.00", currencyCode);
    }

    public void setOtherDocumentListener(DocumentListenerImp otherDocumentListener) {
        this.otherDocumentListener = otherDocumentListener;
    }

    public Money getMoney() {
        return money;
    }

    public JTextField getTextField() {
        return textField;
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
        otherDocumentListener.getMoney().setAmount(money.convertCurrencyTo(otherDocumentListener.getMoney()));
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
