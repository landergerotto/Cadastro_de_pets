import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

// Classe principal que representa um pet shop
public class PetShop {
    // ArrayList para armazenar os tutores cadastrados
    public static ArrayList<Tutor> tutores = new ArrayList<Tutor>();
    // Variável para armazenar o último ID de tutor cadastrado
    public static int lastid = 1;
    // Objeto Scanner para entrada de dados do usuário
    public static Scanner scanner = new Scanner(System.in);

    // Método principal
    public static void main(String[] args) {
        PopularCadastro(); // Popula o cadastro com alguns dados iniciais
        while(true) {
            PrintOpcoes(); // Exibe as opções do menu
            ScanOpcao(); // Captura e processa a opção do usuário
        }
    }

    // Método para imprimir as opções do menu
    public static void PrintOpcoes() {
        System.out.println("***** ESCOLHA UMA OPÇÃO *****\n" + 
            "c: cadastrar tutor + pet(s)\n" +
            "i: imprimir cadastro\n" +
            "b: buscar pets por codigo tutor\n" +
            "e: excluir pets por codigo tutor\n" +
            "x: encerrar."
        );
    } 

    // Método para cadastrar um novo tutor e seus pets
    public static void Cadastrar() {
        // Solicita informações do tutor
        System.out.println("Digite nome do tutor (vazio encerra cadastro tutor):");
        System.out.print("> ");
        String nome = scanner.nextLine();

        int ano, mes, dia;
        // Solicita a data de nascimento do tutor e valida
        while(true) {
            System.out.println("Digite dia (dd), mês (mm) e ano (aaaa) de nascimento do tutor: \n" +
            "(separados por espaços)");
            System.out.print("> ");

            String data = scanner.nextLine();

            while(data.isEmpty() || data.matches("[a-zA-Z]+")) {
                System.out.println("Data inválida! Por favor, digite corretamente");
                System.out.print("> ");
                data = scanner.nextLine();   
            }
            
            String[] dateParts = data.split(" ");
            
            ano = Integer.parseInt(dateParts[2]);
            mes = Integer.parseInt(dateParts[1]);
            dia = Integer.parseInt(dateParts[0]);

            if(ano < LocalDate.now().getYear() + 1 && ano > 1999 && mes > 0 && mes < 13 && dia > 0 && dia < 32) {   
                break;
            }
            else
                System.out.println("Data inválida!");
        }

        System.out.println("Digite endereço do tutor/pet: ");
        System.out.print("> ");
        String endereco = scanner.nextLine();
        if (endereco.isEmpty()) {
            return;
        }
    
        int codTutor = lastid;
        lastid++;
    
        // Cria um novo tutor com as informações fornecidas
        Tutor tutor = new Tutor(codTutor, nome, ano, mes, dia, endereco);
        
        // Solicita informações dos pets e os associa ao tutor
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

        // Adiciona o tutor cadastrado à lista de tutores
        tutores.add(tutor);
    }

    // Método para imprimir o cadastro de tutores e seus pets
    public static void ImprimirCadastro() {
        System.out.println("--- CADASTRO DE TUTORES E PETS ------------------------------------------------------");
        for (Tutor tutor : tutores) {
            System.out.println(tutor);
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }

    // Método para buscar pets por código de tutor
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

    // Método para excluir todos os pets associados a um tutor
    public static void ExcluirPet() {
        System.out.println("Digite o código do tutor: ");
        System.out.print("> ");
        String id = scanner.nextLine();

        while(id.isEmpty() || id.matches("[a-zA-Z]+")){
            System.out.println("Código de tutor inválido\nPor favor, digite o código do tutor: ");
            System.out.print("> ");
            id = scanner.nextLine();
        }

        for (Tutor tutor : tutores) {
            if(tutor.getCodTutor() == Integer.parseInt(id)) {
                tutor.RemoveAllPets(); // Remove todos os pets do tutor
                tutores.remove(tutor); // Remove o tutor da lista de tutores
                return;
            }
        }

        System.out.println("Tutor não encontrado!!!");
    }

    // Método para capturar a opção do usuário e executar a ação correspondente
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

    // Método para popular o cadastro com alguns dados iniciais
    public static void PopularCadastro() {
        Tutor tuts = new Tutor(lastid, "Maria Antonela", 2000, 12, 12, "R. Bem Ali");
        tuts.AddPet(new Pet("Bob", "Cachorro"));
        tuts.AddPet(new Pet("Luna", "Gato"));
        lastid++;

        Tutor tuts2 = new Tutor(lastid, "Super Marcio", 2000, 06, 10, "R. Bem Longe");
        tuts2.AddPet(new Pet("Kiara", "Gato"));
        tuts2.AddPet(new Pet("Priquito", "Papagaio"));
        lastid++;

        // Adiciona os tutores populados à lista de tutores
        tutores.add(tuts);
        tutores.add(tuts2);
    }
}
