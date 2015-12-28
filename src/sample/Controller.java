package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Clase controladora on es troba la llogica general
 */
public class Controller {

    @FXML
    ImageView imgView;
    @FXML
    ImageView imgZoomView;
    @FXML
    ImageView imgViewGrass;
    @FXML
    ImageView logo;
    @FXML
    ListView listView;
    @FXML
    TextArea textArea = null;
    @FXML
    ScrollPane scrollPane;
    @FXML
    DoubleProperty zoom;

    protected String pathImg;
    protected ObservableList<String> olist;
    protected Image image = new Image(Main.class.getResourceAsStream("pokedex.png"));
    protected Image pokeLogo = new Image(Main.class.getResourceAsStream("pokemon_logo.png"));
    protected Image grass = new Image(Main.class.getResourceAsStream("grass.png"));

    Connect connect = new Connect();

    /**
     * Zoom al pokemon amb la roda del mouse
     */
    public void zoom() {
        Stage stage = new Stage();

        zoom = new SimpleDoubleProperty(200);
        InvalidationListener listener = new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                System.out.println("Zoom=" + zoom.doubleValue());
                imgZoomView.setFitWidth(zoom.get() * 4);
                imgZoomView.setFitHeight(zoom.get() * 3);
            }
        };
        zoom.addListener(listener);

        scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.addEventFilter(ScrollEvent.ANY,
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        if (event.getDeltaY() > 0) {
                            zoom.set(zoom.get() * 1.1);
                        } else if (event.getDeltaY() < 0) {
                            zoom.set(zoom.get() / 1.1);
                        }
                        event.consume();
                    }
                });
        scrollPane.viewportBoundsProperty().addListener(
                new ChangeListener<Bounds>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Bounds> bounds,
                            Bounds oldBounds, Bounds newBounds) {
                        System.out.println("oldBounds=" + oldBounds
                                + " newBounds=" + newBounds);
                    }
                });

        imgZoomView = new ImageView(new Image(pathImg)) {
            {
                setPreserveRatio(true);
            }
        };
        scrollPane.setContent(imgZoomView);
        Scene scene = new Scene(scrollPane, zoom.get() * 4 + 2,
                zoom.get() * 3 + 2);
        stage.setScene(scene);
        stage.show();
        imgZoomView.setFitWidth(zoom.get() * 4);
        imgZoomView.setFitHeight(zoom.get() * 3);
        scrollPane.setHvalue(scrollPane.getHmax());
        scrollPane.setVvalue(scrollPane.getVmax());

        //imgView.setScaleX(2);
        //imgView.setScaleY(2);
        //imgView.scaleXProperty().bind(scrollPane.vvalueProperty());
        //imgView.scaleYProperty().bind(scrollPane.hvalueProperty());

    }//zoom

    /**Quan s'inicia el programa:
     * Carrega de 3 imatges i 10 noms de pokemons
     * amb la seva id
     */
    public void initialize() {
        imgView.setImage(image);
        imgView.setFitHeight(260);
        imgView.setFitWidth(380);
        imgView.setLayoutX(0);
        imgView.setPreserveRatio(false);
        imgView.setSmooth(true);
        imgView.setCache(true);

        logo.setImage(pokeLogo);
        logo.setFitHeight(50);
        logo.setFitWidth(130);
        logo.setX(15);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setCache(true);

        imgViewGrass.setImage(grass);
        imgViewGrass.setFitHeight(280);
        imgViewGrass.setFitWidth(380);
        imgViewGrass.setX(0);
        imgViewGrass.setPreserveRatio(false);
        imgViewGrass.setSmooth(true);
        imgViewGrass.setCache(true);


        olist = FXCollections.observableArrayList(Connect.nombresPokAL);
        listView.setItems(olist);

        /*
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        HBox box = new HBox();
        box.getChildren().add(imgView);
        root.getChildren().add(box);
        stage.setTitle("ImageView");
        stage.setWidth(411);
        stage.setHeight(392);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        */
    }

    /**
     * Es tanca la aplicacio
     */
    public void sortir(){
        Platform.exit();
    }

    /**
     * Peticio que mostra les dades del pokemon seleccionat
     * @param event Target del pokemon
     */
    public void peticionPoke(Event event) {
        String target = String.valueOf(event.getTarget());
        System.out.println(target);
        String nom = "";
        boolean exit = true;
        int index = 11;

        while(exit){
            String lletra = String.valueOf(target.charAt(index));

            if(lletra.equals("\"")){
                exit = false;
            }
            else{
                nom = nom.concat(lletra);
                index++;
            }
        }

        System.out.println(nom);
        int indice = parserIdPoke(nom);
        String in = String.valueOf(indice);
        String pokemon = "#POKEMON_EMPTY";
        boolean nameExists = false;

        for (int i = 0; i < Connect.nombresPokAL.size(); i++) {
            if(Connect.nombresPokAL.get(i).contentEquals(nom)){
                nameExists = true;
                String peticioPokemon = "http://pokeapi.co/api/v1/pokemon/" + in + "/";
                try {
                    pokemon = Connect.getHTML(peticioPokemon);//peticioPokemons
                    Connect.pokemonPrintJson(pokemon);
                } catch (Exception e) {
                    System.out.println("...Conexio API ha fallat\nError:");
                    e.printStackTrace();
                }
                break;
            }
            indice++;
        }
        if(!nameExists){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Conection Api:\n\thttp://pokeapi.co/api/ --> Failed");
            alert.setContentText("Selection again");
            System.out.println(event.getTarget());

            alert.showAndWait();
        }
        else{
            String text = Connect.returnDescription(pokemon);
            textArea.setText(text);
            nameExists = false;

            //carga de imagen
            String peticioImatge = "http://pokeapi.co/media/img/" + in + ".png";
            image = new Image(peticioImatge);
            pathImg = peticioImatge;
            imgView.setImage(image);
            imgView.setFitHeight(280);
            imgView.setFitWidth(280);
            imgView.setX(60);
            imgView.setPreserveRatio(true);
            imgView.setSmooth(true);
            imgView.setCache(true);

            //img de fondo
            imgViewGrass.setImage(grass);
            imgViewGrass.setFitHeight(280);
            imgViewGrass.setFitWidth(380);
            imgViewGrass.setX(0);
            imgViewGrass.setPreserveRatio(false);
            imgViewGrass.setSmooth(true);
            imgViewGrass.setCache(true);

            olist = FXCollections.observableArrayList(Connect.nombresPokAL);
            listView.setItems(olist);

        }
    }//peticionPoke

    /**
     * Interpreta les dades del pokemon
     * @param nom JSON del pokemon
     * @return La id del pokemon
     */
    private int parserIdPoke(String nom) {
        int index = nom.indexOf("Id: ");
        index = index+4;
        String num = "";
        int returnIdPoke = 0;
        boolean exit = true;
        String lletra = "A";
        while(exit){
            if(index >= nom.length()){
                break;
            }
            lletra = String.valueOf(nom.charAt(index));
            num = num.concat(lletra);
            index++;
            returnIdPoke = Integer.parseInt(num);
        }
        return returnIdPoke;
    }

    /**
     * Carrega noms de pokemons amb la seva id
     * @param actionEvent
     */
    public void loadPoke(ActionEvent actionEvent) {
        //Dialog --> ControlFX
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Pokemons");
        dialog.setHeaderText("There are 718 Pokemon");
        dialog.setContentText("Please enter how many pokemons:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }
        int cantidad = Integer.parseInt(result.get());
        if(cantidad > 718){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("There are 718 Pokemon ... no more");
            alert.setContentText("Please...choose less than 720");
            alert.showAndWait();
        }
        else{
            Connect.setCantidadPokemons(cantidad);
            connect = new Connect();

            olist = FXCollections.observableArrayList(Connect.nombresPokAL);
            listView.setItems(olist);
        }
    }//loadPoke

    /**
     * Carrega el nom i la id de un pokemon escollit
     * @param actionEvent
     */
    public void loadOnePoke(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Pokemons");
        dialog.setHeaderText("There are 718 Pokemon");
        dialog.setContentText("Enter pokemon Id:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("pokemon Id: " + result.get());
        }
        try{
            int id = Integer.parseInt(result.get());
            if(id > 718){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("There are 718 Pokemon ... no more");
                alert.setContentText("Please...choose one of them");
                alert.showAndWait();
                loadOnePoke(actionEvent);
            }
            else{
                Connect.conexioApi(id);

                olist = FXCollections.observableArrayList(Connect.nombresPokAL);
                listView.setItems(olist);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }//loadOnePoke

    /**
     * Mostra dades del desenvolupament programa
     */
    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ABOUT");
        alert.setHeaderText("Practica Pokedex Desktop M07");
        alert.setContentText("MCPokedex v1.0\nDeveloped by Marc Cano");
        alert.showAndWait();
    }

    /**
     * Elimina un pokemon escollit
     * @param event
     */
    public void delete(Event event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Pokemon");
        dialog.setHeaderText("You are deleting a Pokemon!!");
        dialog.setContentText("Enter pokemon Name or pokemon Id:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("pokemon Name: " + result.get());
        }
        String pokeName = result.get();

        for(int i = 0; i < Connect.nombresPokAL.size(); i++){
            if(Connect.nombresPokAL.get(i).contains(pokeName)){
                Connect.nombresPokAL.remove(i);
                olist = FXCollections.observableArrayList(Connect.nombresPokAL);
                listView.setItems(olist);
            }
        }


        for(int i = 0; i < Connect.nombresPokAL.size(); i++){
            System.out.println(Connect.nombresPokAL.get(i));
        }
    }
}//Controller
