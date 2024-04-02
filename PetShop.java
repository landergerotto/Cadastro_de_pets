import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class PetShop {

    public static ArrayList<Tutor> tutores = new ArrayList<Tutor>();
    public static int lastid = 0;

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

    public static void Cadastrar() {
        System.out.println("CHAMOI");
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Digite nome do tutor (vazio encerra cadastro tutor):");
        System.out.print("> ");
        String nome = scanner.nextLine();
    
        // System.out.println("Digite dia (dd), mês (mm) e ano (aaaa) de nascimento do tutor: \n" +
        //         "(separados por espaços)");
        // System.out.print("> ");
        // String data = scanner.nextLine();
        // if (data.isEmpty()) {
        //     scanner.close();
        //     return;
        // }
    
        // String[] dateParts = data.split(" ");
        // int ano, mes, dia;
        // try {
        //     ano = Integer.parseInt(dateParts[0]);
        //     mes = Integer.parseInt(dateParts[1]);
        //     dia = Integer.parseInt(dateParts[2]);
        // } catch (NumberFormatException e) {
        //     System.out.println("Invalid date format.");
        //     scanner.close();
        //     return;
        // }
    
        // System.out.println("Digite endereço do tutor/pet: ");
        // System.out.print("> ");
        // String endereco = scanner.nextLine();
        // if (endereco.isEmpty()) {
        //     scanner.close();
        //     return;
        // }
    
        // scanner.close();
    
        // int codTutor = lastid;
        // lastid++;
    
        // Tutor tutor = new Tutor(codTutor, nome, ano, mes, dia, endereco);
        // tutores.add(tutor);
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
                Cadastrar();
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
