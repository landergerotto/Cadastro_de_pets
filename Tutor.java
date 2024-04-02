import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Tutor {
    private int codTutor;
    private String nome;
    private LocalDate nascimento;
    private String endereco;
    private ArrayList<Pet> pets;

    public Tutor(int codTutor, String nome, LocalDate nasc, String endereco)
    {
        this.codTutor = codTutor;
        this.nome = nome;
        this.nascimento = nasc;
        this.endereco = endereco;

        this.pets = new ArrayList<Pet>();
    }

    public Tutor(int codTutor, String nome, int yyyy, int mm, int dd, String endereco)
    {
        this.codTutor = codTutor;
        this.nome = nome;
        this.nascimento = LocalDate.of(yyyy, mm, dd);
        this.endereco = endereco;

        this.pets = new ArrayList<Pet>();
    }

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

    public void AddPet(Pet pet) {
        this.pets.add(pet);
    }

    public void RemovePet(Pet pet) {
        this.pets.remove(pet);
    }

    @Override
    public String toString() {
        String name = String.format("Nome...........: %s\n", this.nome);  
        String date = String.format("Data nascimento: %s\n", this.nascimento.toString());
        String ende = String.format("Endereço.......: %s\n", this.endereco);
        String str1 = name + date + ende;
        String str2 = "";

        for(Pet pet : this.pets)
        {
            String petStr = String.format("- Nome do pet: %s; Tipo: %s.\n", pet.getNome(), pet.getTipo());
            str2 = str2 + petStr;
        }

        return str1 + str2;
    }
}
