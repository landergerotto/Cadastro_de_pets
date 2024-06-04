import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PetInterface extends JFrame {
    private static ArrayList<Tutor> tutores = new ArrayList<>();
    private static int lastid = 1;
    private static final String FILE_NAME = "cadastro.dat";

    private JTextField nomeTutorField;
    private JTextField dataTutorField;
    private JTextField enderecoField;
    private JTextField nomePetField;
    private JTextField tipoPetField;
    private JTextField dataPetField;
    private JTextField buscarField;
    private JTextArea outputArea;

    public PetInterface() {
        super("Cadastro de Pets");

        carregarDados();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        panel.add(new JLabel("Nome do Tutor:"));
        nomeTutorField = new JTextField();
        panel.add(nomeTutorField);

        panel.add(new JLabel("Data de Nascimento do Tutor (dd mm aaaa):"));
        dataTutorField = new JTextField();
        panel.add(dataTutorField);

        panel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        panel.add(enderecoField);

        panel.add(new JLabel("Nome do Pet:"));
        nomePetField = new JTextField();
        panel.add(nomePetField);

        panel.add(new JLabel("Tipo do Pet:"));
        tipoPetField = new JTextField();
        panel.add(tipoPetField);

        panel.add(new JLabel("Data de Nascimento do Pet (dd mm aaaa):"));
        dataPetField = new JTextField();
        panel.add(dataPetField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
        panel.add(cadastrarButton);

        JButton imprimirButton = new JButton("Imprimir Cadastro");
        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirCadastro();
            }
        });
        panel.add(imprimirButton);

        JButton excluirButton = new JButton("Excluir Tutor");
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        panel.add(excluirButton);

        panel.add(new JLabel("Buscar Tutor (Código):"));
        buscarField = new JTextField();
        panel.add(buscarField);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar();
            }
        });
        panel.add(buscarButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
    }

    private void cadastrar() {
        String nomeTutor = nomeTutorField.getText();
        String dataTutor = dataTutorField.getText();
        String endereco = enderecoField.getText();

        if (nomeTutor.isEmpty() || dataTutor.isEmpty() || endereco.isEmpty()) {
            outputArea.setText("Todos os campos do tutor devem ser preenchidos.");
            return;
        }

        String[] tutorDateParts = dataTutor.split(" ");
        if (tutorDateParts.length != 3) {
            outputArea.setText("Data de nascimento do tutor inválida. Use o formato dd mm aaaa.");
            return;
        }

        int diaTutor, mesTutor, anoTutor;
        try {
            diaTutor = Integer.parseInt(tutorDateParts[0]);
            mesTutor = Integer.parseInt(tutorDateParts[1]);
            anoTutor = Integer.parseInt(tutorDateParts[2]);
        } catch (NumberFormatException e) {
            outputArea.setText("Data de nascimento do tutor inválida. Use o formato dd mm aaaa.");
            return;
        }

        LocalDate nascimentoTutor = LocalDate.of(anoTutor, mesTutor, diaTutor);
        int codTutor = lastid++;
        Tutor tutor = new Tutor(codTutor, nomeTutor, nascimentoTutor, endereco);

        String nomePet = nomePetField.getText();
        String tipoPet = tipoPetField.getText();
        String dataPet = dataPetField.getText();

        if (!nomePet.isEmpty() && !tipoPet.isEmpty() && !dataPet.isEmpty()) {
            String[] petDateParts = dataPet.split(" ");
            if (petDateParts.length != 3) {
                outputArea.setText("Data de nascimento do pet inválida. Use o formato dd mm aaaa.");
                return;
            }

            int diaPet, mesPet, anoPet;
            try {
                diaPet = Integer.parseInt(petDateParts[0]);
                mesPet = Integer.parseInt(petDateParts[1]);
                anoPet = Integer.parseInt(petDateParts[2]);
            } catch (NumberFormatException e) {
                outputArea.setText("Data de nascimento do pet inválida. Use o formato dd mm aaaa.");
                return;
            }

            LocalDate nascimentoPet = LocalDate.of(anoPet, mesPet, diaPet);
            Pet pet = new Pet(nomePet, tipoPet, nascimentoPet);
            tutor.AddPet(pet);
        }

        tutores.add(tutor);
        salvarDados();
        outputArea.setText("Tutor e Pet(s) cadastrados com sucesso.");
        limparCampos();
    }

    private void imprimirCadastro() {
        StringBuilder output = new StringBuilder("--- CADASTRO DE TUTORES E PETS ---\n");
        for (Tutor tutor : tutores) {
            if (!tutor.isDeletado()) {
                output.append(tutor).append("\n");
            }
        }
        output.append("-------------------------------");
        outputArea.setText(output.toString());
    }

    private void buscar() {
        String codTutorStr = buscarField.getText();
        if (codTutorStr.isEmpty()) {
            outputArea.setText("Digite o código do tutor para buscar.");
            return;
        }

        int codTutor;
        try {
            codTutor = Integer.parseInt(codTutorStr);
        } catch (NumberFormatException e) {
            outputArea.setText("Código inválido.");
            return;
        }

        for (Tutor tutor : tutores) {
            if (tutor.getCodTutor() == codTutor && !tutor.isDeletado()) {
                outputArea.setText(tutor.toString());
                return;
            }
        }

        outputArea.setText("Tutor não encontrado.");
    }

    private void excluir() {
        String codTutorStr = buscarField.getText();
        if (codTutorStr.isEmpty()) {
            outputArea.setText("Digite o código do tutor para excluir.");
            return;
        }

        int codTutor;
        try {
            codTutor = Integer.parseInt(codTutorStr);
        } catch (NumberFormatException e) {
            outputArea.setText("Código inválido.");
            return;
        }

        for (Tutor tutor : tutores) {
            if (tutor.getCodTutor() == codTutor) {
                tutor.setDeletado(true);
                salvarDados();
                outputArea.setText("Tutor e pets excluídos com sucesso.");
                return;
            }
        }

        outputArea.setText("Tutor não encontrado.");
    }

    private void limparCampos() {
        nomeTutorField.setText("");
        dataTutorField.setText("");
        enderecoField.setText("");
        nomePetField.setText("");
        tipoPetField.setText("");
        dataPetField.setText("");
        buscarField.setText("");
    }

    private static void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tutores = (ArrayList<Tutor>) ois.readObject();
            lastid = tutores.size() + 1;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados não encontrado, um novo arquivo será criado.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tutores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PetInterface();
        });
    }
}
