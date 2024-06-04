import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;

public class Tutor implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codTutor;
    private String nome;
    private LocalDate nascimento;
    private String endereco;
    private ArrayList<Pet> pets;
    private boolean deletado;

    public Tutor(int codTutor, String nome, LocalDate nascimento, String endereco) {
        this.codTutor = codTutor;
        this.nome = nome;
        this.nascimento = nascimento;
        this.endereco = endereco;
        this.pets = new ArrayList<>();
        this.deletado = false;
    }

    public Tutor(int codTutor, String nome, int yyyy, int mm, int dd, String endereco) {
        this(codTutor, nome, LocalDate.of(yyyy, mm, dd), endereco);
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

    public void RemoveAllPets() {
        this.pets.clear();
    }

    public boolean isDeletado() {
        return deletado;
    }

    public void setDeletado(boolean deletado) {
        this.deletado = deletado;
    }

    @Override
    public String toString() {
        String cod = String.format("Cod. do tutor: %d\n", this.codTutor);  
        String name = String.format("  Nome...........: %s\n", this.nome);  
        String date = String.format("  Data nascimento: %s\n", this.nascimento.toString());
        String ende = String.format("  Endereço.......: %s\n", this.endereco);
        String str1 = cod + name + date + ende;
        String str2 = "";

        for (Pet pet : this.pets) {
            String petStr = String.format("  - Nome do pet: %s; Tipo: %s; Idade: %d anos\n", pet.getNome(), pet.getTipo(), pet.getIdade());
            str2 = str2 + petStr;
        }

        return str1 + str2;
    }
}
