package restaurante2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import restaurante2.Usuario;
import restaurante2_controller.Menu;
import restaurante2_controller.Inventario_controller;
import restaurante2_db.Database_usuario;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Inventario_controller.cargarInventarioDesdeArchivo();

        // Crear los elementos de la UI
        Label labelUsuario = new Label("Usuario:");
        TextField campoUsuario = new TextField();
        Label labelPassword = new Label("Contraseña:");
        PasswordField campoPassword = new PasswordField();
        Button botonLogin = new Button("Iniciar Sesión");
        Label mensajeError = new Label();

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(labelUsuario, campoUsuario, labelPassword, campoPassword, botonLogin, mensajeError);

        // Acción del botón de login
        botonLogin.setOnAction(e -> {
            String nombreUsuario = campoUsuario.getText();
            String password = campoPassword.getText();

            Usuario u = autenticarUsuario(nombreUsuario, password);

            if (u != null) {
                abrirMenu(primaryStage, u); // Abre la ventana del menú
            } else {
                mensajeError.setText("❌ Usuario o contraseña incorrectos.");
            }
        });

        // Configuración de la escena y la ventana
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Sistema de Restaurante - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para autenticar usuario
    public static Usuario autenticarUsuario(String nombreUsuario, String password) {
        Usuario usuario = Database_usuario.buscarUsuario(nombreUsuario);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    // Método para abrir el menú después de iniciar sesión
    private void abrirMenu(Stage primaryStage, Usuario usuario) {
        Menu.mostrarMenu(primaryStage, usuario);
    }

    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX
    }
}
