package org.example.factory;

import org.example.view.bw.HomePageBW;
import org.example.view.HomePage;

public class GUIBw extends GUIFactory {
    @Override
    public HomePage createHomePage() {
        return new HomePageBW();
    }
}
