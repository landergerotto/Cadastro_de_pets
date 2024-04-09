import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

// Classe que representa um animal de estimação
public class Pet {
    // Atributos
    private String nome; // Nome do animal de estimação
    private String tipo; // Tipo do animal de estimação (cachorro, gato, etc.)

    // Construtor da classe Pet
    public Pet(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    // Método para obter o nome do animal
    public String getNome(){
        return this.nome;
    }

    // Método para definir um novo nome para o animal
    public void setNome(String novoNome){
        this.nome = novoNome;
    }

    // Método para obter o tipo do animal
    public String getTipo(){
        return this.tipo;
    }

    // Método para definir um novo tipo para o animal
    public void setTipo(String novoTipo){
        this.tipo = novoTipo;
    }
}
