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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
