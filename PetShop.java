import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class PetShop {

    public static ArrayList<Tutor> tutores = new ArrayList<Tutor>();
    public static int lastid = 1;
    public static Scanner scanner = new Scanner(System.in);

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
    
        System.out.println("Digite nome do tutor (vazio encerra cadastro tutor):");
        System.out.print("> ");
        String nome = scanner.nextLine();
    
        System.out.println("Digite dia (dd), mês (mm) e ano (aaaa) de nascimento do tutor: \n" +
                "(separados por espaços)");
        System.out.print("> ");
        String data = scanner.nextLine();
        if (data.isEmpty()) {

            return;
        }
    
        String[] dateParts = data.split(" ");
        int ano, mes, dia;
        try {
            ano = Integer.parseInt(dateParts[2]);
            mes = Integer.parseInt(dateParts[1]);
            dia = Integer.parseInt(dateParts[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid date format.");
            return;
        }
    
        System.out.println("Digite endereço do tutor/pet: ");
        System.out.print("> ");
        String endereco = scanner.nextLine();
        if (endereco.isEmpty()) {
            return;
        }
    
        int codTutor = lastid;
        lastid++;
    
        Tutor tutor = new Tutor(codTutor, nome, ano, mes, dia, endereco);
        
        while(true) {
            System.out.println("Digite nome do pet (vazio encerra cadastro pet): ");
            System.out.print("> ");
            String nomePet = scanner.nextLine();
            if (nomePet.isEmpty()) {
                break;
            }
            
            System.out.println("Digite tipo do pet: ");
            System.out.print("> ");
            String tipoPet = scanner.nextLine();

            while(tipoPet.isEmpty()){
                System.out.println("Tipo de pet inválido\nPor favor, digite tipo do pet: ");
                System.out.print("> ");
                tipoPet = scanner.nextLine();
            }
            
            Pet pet = new Pet(nomePet, tipoPet);   
            tutor.AddPet(pet);
        }

        tutores.add(tutor);
    }

    public static void ImprimirCadastro() {
        System.out.println("--- CADASTRO DE TUTORES E PETS ------------------------------------------------------");
        for (Tutor tutor : tutores) {
            System.out.println(tutor);
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }

    public static void BuscarPet() {
        System.out.println("Digite codigo do tutor a ser localizado");
        System.out.print("> ");
        String cod = scanner.nextLine();

        while(cod.isEmpty() || cod.matches("[a-zA-Z]+")) {
            System.out.println("Código inválido! Por favor, digite o código do tutor a ser localizado: ");
            System.out.print("> ");
            cod = scanner.nextLine();
        }
        
        for (Tutor tutor : tutores) {
            if(tutor.getCodTutor() == Integer.parseInt(cod)) {
                System.out.println("--- Tutor localizado ---");
                System.out.println(tutor);
                return;
            }
        }

        System.out.println("--- Tutor não foi localizado ---");
    }

    public static void ExcluirPet() {
        System.out.println("Digite o código do tutor: ");
        System.out.print("> ");
        String id = scanner.nextLine();

        while(id.isEmpty()){
            System.out.println("Código de tutor inválido\nPor favor, digite o código do tutor: ");
            System.out.print("> ");
            id = scanner.nextLine();
        }

        for (Tutor tutor : tutores) {
            if(tutor.getCodTutor() == Integer.parseInt(id)) {
                tutor.setPets(new ArrayList<Pet>());
                tutores.remove(tutor);
                return;
            }
        }

        System.out.println("Tutor não encontrado!!!");
    }

    public static void ScanOpcao() {
        String opcao = scanner.nextLine();
        System.out.println("Opção escolhida: " + opcao);

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
