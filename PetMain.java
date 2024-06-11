import javax.swing.JFrame;

public class PetMain {
    public static void main(String[] args) {
        PetInterface petApp = new PetInterface(800, 600);
        petApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        petApp.setLocationRelativeTo(null);
        petApp.setVisible(true);
    }
    
}