package unitTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import main.Main;

import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import controllers.MainController;

public class SearchInvoiceTest {

    /*private static GuiTest controller;
    
    @BeforeClass
    public static void setUpClass() {
            FXTestUtils.launchApp(Main.class);

            controller = new GuiTest() {
                @Override
                protected Parent getRootNode() {
                    try {
                        return FXMLLoader.load(MainController.class.getResource("Main.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

    @Test(expected = ParseException.class)
    public void searchForInvoice_should_throwParseException() {
        ((TextField)GuiTest.find("#searchInvoiceDateFrom")).setText("wrong format");
        controller.click("#searchForInvoice");
        assertTrue(GuiTest.find("#messageLabelSuche").isVisible());
    }*/
}