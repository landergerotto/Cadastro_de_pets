import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDate;

public class PetShop {
    public static ArrayList<Tutor> tutores = new ArrayList<>();
    public static int lastid = 1;
    public static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "cadastro.dat";

    public static void main(String[] args) {
        carregarDados();
        PopularCadastro();

        while (true) {
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
        if (nome.isEmpty()) {
            return;
        }

        int ano, mes, dia;
        while (true) {
            System.out.println("Digite dia (dd), mês (mm) e ano (aaaa) de nascimento do tutor (separados por espaços):");
            System.out.print("> ");
            String data = scanner.nextLine();

            if (!data.matches("\\d{2} \\d{2} \\d{4}")) {
                System.out.println("Data inválida! Por favor, digite corretamente");
                continue;
            }

            String[] dateParts = data.split(" ");
            try {
                dia = Integer.parseInt(dateParts[0]);
                mes = Integer.parseInt(dateParts[1]);
                ano = Integer.parseInt(dateParts[2]);

                LocalDate nascimento = LocalDate.of(ano, mes, dia);
                if (nascimento.isAfter(LocalDate.now()) || ano < 1900) {
                    System.out.println("Data inválida!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Data inválida! Por favor, digite corretamente");
            }
        }

        System.out.println("Digite endereço do tutor/pet:");
        System.out.print("> ");
        String endereco = scanner.nextLine();
        if (endereco.isEmpty()) {
            return;
        }

        int codTutor = lastid++;
        Tutor tutor = new Tutor(codTutor, nome, ano, mes, dia, endereco);

        while (true) {
            System.out.println("Digite nome do pet (vazio encerra cadastro pet):");
            System.out.print("> ");
            String nomePet = scanner.nextLine();
            if (nomePet.isEmpty()) {
                break;
            }

            System.out.println("Digite tipo do pet:");
            System.out.print("> ");
            String tipoPet = scanner.nextLine();
            if (tipoPet.isEmpty()) {
                System.out.println("Tipo de pet inválido");
                continue;
            }

            System.out.println("Digite a data de nascimento do pet (dd mm aaaa):");
            System.out.print("> ");
            String petData = scanner.nextLine();
            if (!petData.matches("\\d{2} \\d{2} \\d{4}")) {
                System.out.println("Data inválida! Por favor, digite corretamente");
                continue;
            }

            String[] petDateParts = petData.split(" ");
            try {
                int diaPet = Integer.parseInt(petDateParts[0]);
                int mesPet = Integer.parseInt(petDateParts[1]);
                int anoPet = Integer.parseInt(petDateParts[2]);
                LocalDate nascPet = LocalDate.of(anoPet, mesPet, diaPet);

                Pet pet = new Pet(nomePet, tipoPet, nascPet);
                tutor.AddPet(pet);
            } catch (Exception e) {
                System.out.println("Data inválida! Por favor, digite corretamente");
            }
        }

        tutores.add(tutor);
        salvarDados();
    }

    public static void ImprimirCadastro() {
        System.out.println("--- CADASTRO DE TUTORES E PETS ------------------------------------------------------");
        for (Tutor tutor : tutores) {
            if (!tutor.isDeletado()) {
                System.out.println(tutor);
            }
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    public static void Buscar() {
        System.out.println("Digite o código do tutor:");
        System.out.print("> ");
        int codTutor = Integer.parseInt(scanner.nextLine());

        for (Tutor tutor : tutores) {
            if (tutor.getCodTutor() == codTutor && !tutor.isDeletado()) {
                System.out.println(tutor);
                return;
            }
        }

        System.out.println("Tutor não encontrado.");
    }

    public static void Excluir() {
        System.out.println("Digite o código do tutor para excluir:");
        System.out.print("> ");
        int codTutor = Integer.parseInt(scanner.nextLine());

        for (Tutor tutor : tutores) {
            if (tutor.getCodTutor() == codTutor) {
                tutor.setDeletado(true);
                salvarDados();
                System.out.println("Tutor e pets excluídos com sucesso.");
                return;
            }
        }

        System.out.println("Tutor não encontrado.");
    }

    public static void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tutores = (ArrayList<Tutor>) ois.readObject();
            lastid = tutores.size() + 1;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados não encontrado, um novo arquivo será criado.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tutores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ScanOpcao() {
        String opcao = scanner.nextLine().toLowerCase();
        switch (opcao) {
            case "c":
                Cadastrar();
                break;
            case "i":
                ImprimirCadastro();
                break;
            case "b":
                Buscar();
                break;
            case "e":
                Excluir();
                break;
            case "x":
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void PopularCadastro() {
        // Método para popular o cadastro com dados fictícios
        // Não necessário para este exemplo
    }
}
