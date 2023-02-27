package org.converter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageProvider {
    public Image getImage(DocumentListenerImp documentListener) {
        try {
            URL url = new URL("https://countryflagsapi.com/png/"
                    + documentListener.getMoney().getCurrency().getCurrencyCode().substring(0, 2).toLowerCase());
            return ImageIO
                    .read(url)
                    .getScaledInstance(50, 30, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.err.println("Cannot access flags from API");
        }
        return null; // TODO make exception
    }
}
