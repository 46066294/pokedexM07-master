package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Aplicacio que carrega 10 noms de pokemons amb la seva id
 * Permet seleccionar pokemons i veure les seves
 * caracteristiques i tambe la seva imatge
 *
 * Prestacions:
 *  -Fent click a la imatge, podrem fer zoom amb la roda del mouse
 *  -MenuItemFile Load Pokemons: Tornar a carregar tants pokemons com volguem (max 718)
 *  -MenuItemFile Load One Pokemon: Carrega un pokemon per id o per nom
 *  -MenuItemEdit Delete: Elimina un pokemon per nom o per id
 *  -MenuItemHelp About: Mostra dades del programa
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MCPokedex v1.0");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("General_CSS.css");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setMaxHeight(600);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(400);
        primaryStage.setMinWidth(400);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
