import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

// Classe que representa um tutor de animais de estimação
public class Tutor {
    // Atributos
    private int codTutor; // Código do tutor
    private String nome; // Nome do tutor
    private LocalDate nascimento; // Data de nascimento do tutor
    private String endereco; // Endereço do tutor
    private ArrayList<Pet> pets; // Lista de animais de estimação associados ao tutor

    // Construtor da classe Tutor
    public Tutor(int codTutor, String nome, LocalDate nasc, String endereco) {
        this.codTutor = codTutor;
        this.nome = nome;
        this.nascimento = nasc;
        this.endereco = endereco;
        this.pets = new ArrayList<Pet>(); // Inicializa a lista de pets
    }

    // Construtor alternativo da classe Tutor, que aceita a data de nascimento em partes separadas
    public Tutor(int codTutor, String nome, int yyyy, int mm, int dd, String endereco) {
        this.codTutor = codTutor;
        this.nome = nome;
        this.nascimento = LocalDate.of(yyyy, mm, dd); // Cria um objeto LocalDate a partir das partes
        this.endereco = endereco;
        this.pets = new ArrayList<Pet>(); // Inicializa a lista de pets
    }

    // Getters e setters para os atributos
    public int getCodTutor() {
        return codTutor;
    }

    public void setCodTutor(int codTutor) {
        this.codTutor = codTutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    // Override do método setNascimento para aceitar partes separadas da data
    public void setNascimento(int yyyy, int mm, int dd) {
        this.nascimento = LocalDate.of(yyyy, mm, dd);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    // Método para adicionar um animal de estimação à lista de pets do tutor
    public void AddPet(Pet pet) {
        this.pets.add(pet);
    }

    // Método para remover um animal de estimação da lista de pets do tutor
    public void RemovePet(Pet pet) {
        this.pets.remove(pet);
    }

    // Método para remover todos os animais de estimação da lista do tutor
    public void RemoveAllPets() {
        this.pets.clear();
    }

    // Override do método toString para exibir informações sobre o tutor e seus pets
    @Override
    public String toString() {
        String cod = String.format("Cod. do tutor: %d\n", this.codTutor);  
        String name = String.format("  Nome...........: %s\n", this.nome);  
        String date = String.format("  Data nascimento: %s\n", this.nascimento.toString());
        String ende = String.format("  Endereço.......: %s\n", this.endereco);
        String str1 = cod + name + date + ende;
        String str2 = "";

        // Itera sobre os pets do tutor para gerar a parte da string relacionada aos pets
        for(Pet pet : this.pets) {
            String petStr = String.format("  - Nome do pet: %s; Tipo: %s.\n", pet.getNome(), pet.getTipo());
            str2 = str2 + petStr;
        }

        // Concatena as partes relacionadas ao tutor e aos pets
        return str1 + str2;
    }
}
