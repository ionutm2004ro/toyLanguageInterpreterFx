package FX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stopStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FX.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Toy Language Interpreter");
            primaryStage.setScene(new Scene(root, 620, 540));
            primaryStage.setMinHeight(540);
            primaryStage.setMinWidth(620);

            WindowController cont = loader.getController();

            cont.init();
            primaryStage.show();

            stopStage=primaryStage;
    }


}
