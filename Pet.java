import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

public class Pet {
    private String nome;
    private String tipo;

    public String getNome(){
        return this.nome;
    }

    public void setNome(String novoNome){
        this.nome = novoNome;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String novoTipo){
        this.nome = novoTipo;
    }
}
