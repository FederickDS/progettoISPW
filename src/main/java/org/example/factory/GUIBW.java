package org.example.factory;

import org.example.view.BW.HomePageBW;
import org.example.view.HomePage;

public class GUIBW extends GUIFactory {
    @Override
    public HomePage createHomePage() {
        return new HomePageBW();
    }
}
