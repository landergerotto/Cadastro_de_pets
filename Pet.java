import java.time.LocalDate;
import java.time.Period;
import java.io.Serializable;

public class Pet implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String tipo;
    private LocalDate nascimento;

    public Pet(String nome, String tipo, LocalDate nascimento) {
        this.nome = nome;
        this.tipo = tipo;
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public int getIdade() {
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return String.format("Nome: %s, Tipo: %s, Idade: %d anos", nome, tipo, getIdade());
    }
}
