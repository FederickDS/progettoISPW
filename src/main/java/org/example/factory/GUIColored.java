package org.example.factory;

import org.example.view.colored.HomePageColored;
import org.example.view.HomePage;

public class GUIColored extends GUIFactory {
    @Override
    public HomePage createHomePage() {
        return new HomePageColored();
    }
}
