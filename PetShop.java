import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class PetShop {

    public ArrayList<Tutor> tutores = new ArrayList<Tutor>();

    public static void main(String[] args) {
        while(true) {
            PrintOpcoes();
            ScanOpcao();
        }
    }

    public static void PrintOpcoes() {
        System.out.println("***** ESCOLHA UMA OPÇÃO *****\n" + 
            "c: cadastrar tutor + pet(s)\n" +
            "i: imprimir cadastro\n" +
            "b: buscar pets por codigo tutor\n" +
            "e: excluir pets por codigo tutor\n" +
            "x: encerrar."
        );
    } 

    public static void CadastrarTutor() {
        
    }

    public static void ImprimirCadastro() {
        
    }

    public static void BuscarPet() {
        
    }

    public static void ExcluirPet() {
        
    }

    public static void ScanOpcao() {
        Scanner scan = new Scanner(System.in);
        String opcao = scan.nextLine();
        System.out.println("Opção escolhida: " + opcao);
        scan.close();

        switch(opcao) {
            case "c":
                CadastrarTutor();
                break;

            case "i":
                ImprimirCadastro();
                break;

            case "b":
                BuscarPet();
                break;

            case "e":
                ExcluirPet();
                break;

            case "x":
                System.out.println("--- Programa de cadastro encerrado ---");
                System.exit(0);
                break;

            default:
                System.out.println("Opção inválida");
                break;
          }
    } 


}
