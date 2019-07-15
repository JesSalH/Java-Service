package my3counters;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Marcos Cervantes
 * esto es un servicio
 */
public class My3Counters extends Application 
{
    
    
    //Esto crea a saco una función start que lanza un servicio 
    //(creando un objeto service1 que cuenta hasta 10 y actualiza un mensaje
    @Override
    public void start(Stage primaryStage) 
    {        
        Service<Void> service1 = new Service<Void>()
        {
            @Override
            protected Task<Void> createTask() 
            {
                return new Task<Void>() 
                {
                    @Override
                    protected Void call() throws Exception 
                    {
                        for (int progress = 0; progress <= 10; progress++) 
                        {
                            Thread.sleep(1000);
                            updateMessage("" + progress);
                        }
                        updateMessage("");
                        return null;
                        } 
                };
            }
        };
        
        // Creamos label y boton
        Label lblProgress = new Label("");
        Button btnStart = new Button("From 1 to 10");
        
        
        //aquí hace el binding de la label con el message del service
        lblProgress.textProperty().bind(service1.messageProperty());
        
        
        //esto será cuando termine de ejecutarse el call del service
        service1.setOnSucceeded(e -> 
        {
            //volvemos a activar el boton start
            btnStart.setDisable(false);
            
            //ponemos la etiqueta en blanco
            lblProgress.setText("");
        });
        
        
        //Esto es un evento que se crea cuando hacemos click en el boton start
        btnStart.setOnAction(e -> 
        {
            //deshabilitamos boton
            btnStart.setDisable(true);
            
            //llamamos al call del service1
            service1.start();
        });
        
        
        //esto es para inicializar la app dandole un diseño básico
        VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(lblProgress, btnStart);
        
        Scene scene = new Scene(vb, 300, 400);        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

  
    //lanzamos todo
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}