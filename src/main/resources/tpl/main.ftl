import app.${serverName}.${serverName?cap_first}ServiceApp;

/**
 * @author ebin
 */
public class Main {
    public static void main(String[] args) {
        new ${serverName?cap_first}ServiceApp().start();
    }
}
