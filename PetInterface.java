import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PetInterface extends JFrame {

    private int largura, altura;
    private JButton button;
    private static JTextArea logArea = new JTextArea("", 10, 66);
    static int sizeLog = 0;
    private static String arqPet = "Registros.dat";
    private static ArrayList<Pet> pets = new ArrayList<>();
    private static ArrayList<Tutor> tutores = new ArrayList<>();
    private static final String[] option = {"Cadastrar", "Imprimir cadastro", "Buscar por código de tutor", "Excluir por código de tutor", "Encerrar"};
    static Font f = new Font("Consolas", Font.PLAIN, 13);

    public PetInterface(int largura, int altura) {
        super("CADASTRO DE PETS");
        this.largura = largura;
        this.altura = altura;
        setLayout(new FlowLayout());
        JPanel painel = new JPanel();
        painel.setPreferredSize(new Dimension(largura, altura));
        add(painel);
        pack();
        BufferedImage imgDecor = null;
        try {
            File file = new File("pets.jpg");
            FileInputStream fis = new FileInputStream(file);
            imgDecor = ImageIO.read(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ButtonHandler handler = new ButtonHandler();
        for (int k = 0; k < 5; k++) {
            button = new JButton(option[k]);
            painel.add(button);
            if (k != 4)
                button.addActionListener(handler);
        }

        button.addActionListener(e -> {
            PetInterface.this.dispose();
            System.exit(0);
        });

        painel.add(new JLabel("Log de ações realizadas na sessão."));
        Box box = Box.createHorizontalBox();
        logArea.setFont(f);
        logArea.setEditable(false);
        box.add(new JScrollPane(logArea));
        painel.add(box);

        JPanel decor = new JPanel();
        decor.setLayout(new FlowLayout());
        JLabel imgLabel = new JLabel(new ImageIcon(imgDecor));
        decor.add(imgLabel);
        painel.add(decor);

        recuperaPets();
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand().equals(option[0])) {
                cadastrar();
            }
            if (event.getActionCommand().equals(option[1])) {
                imprimirCadastro();
            }
            if (event.getActionCommand().equals(option[2])) {
                buscaPorCodigoTutor();
            }
            if (event.getActionCommand().equals(option[3])) {
                excluiTutor();
            }
        }
    }

    public static void writeLog(String s) {
        if (sizeLog == 0)
            logArea.append("LOG CRIADO...");
        else {
            logArea.append("\n");
            logArea.append("- " + s);
        }
        sizeLog++;
    }

    private void cadastrar() {
        cadastrarFrame cf = new cadastrarFrame();
        cf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        cf.setSize(largura - 45, 300);
        cf.setLocationRelativeTo(null);
        cf.setVisible(true);
    }

    private class cadastrarFrame extends JFrame {
        private JButton ok;

        public cadastrarFrame() {
            setLayout(new FlowLayout(FlowLayout.LEADING));
            add(new JLabel("Nome do Pet"));
            JTextField nomePet = new JTextField("", 20);
            add(nomePet);
            add(new JLabel("Tipo do Pet"));
            JTextField tipoPet = new JTextField("", 20);
            add(tipoPet);
            add(new JLabel("Data de Nascimento do Pet (dd/mm/aaaa)"));
            JTextField diaPet = new JTextField(2);
            add(diaPet);
            add(new JLabel("/"));
            JTextField mesPet = new JTextField(2);
            add(mesPet);
            add(new JLabel("/"));
            JTextField anoPet = new JTextField(4);
            add(anoPet);
            add(new JLabel("Nome do Tutor"));
            JTextField nomeTutor = new JTextField("", 20);
            add(nomeTutor);
            add(new JLabel("Data de Nascimento do Tutor (dd/mm/aaaa)"));
            JTextField diaTutor = new JTextField(2);
            add(diaTutor);
            add(new JLabel("/"));
            JTextField mesTutor = new JTextField(2);
            add(mesTutor);
            add(new JLabel("/"));
            JTextField anoTutor = new JTextField(4);
            add(anoTutor);
            add(new JLabel("Endereço do Tutor"));
            JTextField enderecoTutor = new JTextField("", 20);
            add(enderecoTutor);
            ok = new JButton("Cadastrar");
            add(ok);

            ok.addActionListener(e -> {
                String nPet = nomePet.getText();
                String tPet = tipoPet.getText();
                String dPet = diaPet.getText();
                String mPet = mesPet.getText();
                String aPet = anoPet.getText();
                String nTutor = nomeTutor.getText();
                String dTutor = diaTutor.getText();
                String mTutor = mesTutor.getText();
                String aTutor = anoTutor.getText();
                String eTutor = enderecoTutor.getText();
                int DPet = 0, MPet = 0, APet = 0, DTutor = 0, MTutor = 0, ATutor = 0;

                if (nPet.length() == 0 || tPet.length() == 0 || dPet.length() == 0 || mPet.length() == 0 || aPet.length() == 0 ||
                    nTutor.length() == 0 || dTutor.length() == 0 || mTutor.length() == 0 || aTutor.length() == 0 || eTutor.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Informação incompleta!");
                    return;
                }

                try {
                    DPet = Integer.parseInt(dPet);
                    MPet = Integer.parseInt(mPet);
                    APet = Integer.parseInt(aPet);
                    DTutor = Integer.parseInt(dTutor);
                    MTutor = Integer.parseInt(mTutor);
                    ATutor = Integer.parseInt(aTutor);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida!");
                    return;
                }

                LocalDate nascPet = LocalDate.of(APet, MPet, DPet);
                LocalDate nascTutor = LocalDate.of(ATutor, MTutor, DTutor);
                Pet pet = new Pet(nPet, tPet, nascPet);
                pets.add(pet);
                Tutor tutor = new Tutor(tutores.size() + 1, nTutor, nascTutor, eTutor, pet);
                tutores.add(tutor);
                String msg = String.format("Pet cadastrado: %s, Tipo: %s, Nascimento: %s, Tutor: %s, Nascimento: %s, Endereço: %s.",
                        nPet, tPet, nascPet.toString(), nTutor, nascTutor.toString(), eTutor);
                writeLog(msg);
                gravaPets(); // Atualiza o arquivo.
                JOptionPane.showMessageDialog(null, "Pet cadastrado com sucesso!");
                setVisible(false); // Fecha o frame de cadastro.
            });
        }
    }

    private static void recuperaPets() {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(arqPet));
            pets = new ArrayList<>();
            tutores = new ArrayList<>();
            Object obj;
            while ((obj = inputStream.readObject()) != null) {
                if (obj instanceof Pet) {
                    pets.add((Pet) obj);
                } else if (obj instanceof Tutor) {
                    tutores.add((Tutor) obj);
                }
            }
            writeLog("Arquivo recuperado: " + pets.size() + " cadastros.");
        } catch (EOFException eofEx) {
            writeLog("Arquivo recuperado: " + pets.size() + " cadastros.");
        } catch (FileNotFoundException ex) {
            writeLog("Arquivo ainda não criado.");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void gravaPets() {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(arqPet));
            for (Pet pet : pets) {
                outputStream.writeObject(pet);
            }
            for (Tutor tutor : tutores) {
                outputStream.writeObject(tutor);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void imprimirCadastro() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "Nome Pet", "Tipo Pet", "Nascimento Pet", "Idade Pet", "Cod. Tutor", "Nome Tutor", "Nascimento Tutor", "Idade Tutor", "Endereço Tutor"));
        for (Pet pet : pets) {
            Tutor tutor = findTutor(pet);
            if (tutor != null && !tutor.isDeletado()) {
                sb.append(String.format("%-20s %-20s %-20s %-20d %-20s %-20s %-20s %-20d %-20s\n",
                        pet.getNome(), pet.getTipo(), pet.getNascimento().toString(), pet.getIdade(), tutor.getCodTutor(), tutor.getNome(), tutor.getNascimento().toString(), tutor.getIdade(), tutor.getEndereco()));
            } else {
                sb.append(String.format("%-20s %-20s %-20s %-20d %-20s %-20s %-20s %-20s %-20s\n",
                        pet.getNome(), pet.getTipo(), pet.getNascimento().toString(), pet.getIdade(), "N/A", "N/A", "N/A", "N/A", "N/A"));
            }
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setFont(f);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Cadastro de Pets", JOptionPane.INFORMATION_MESSAGE);
        writeLog("Impressão do cadastro de pets.");
    }

    private void buscaPorCodigoTutor() {
        String codTutorStr = JOptionPane.showInputDialog(this, "Código do tutor: ");
        if (codTutorStr == null || codTutorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código do tutor não fornecido!");
            return;
        }
        int codTutor;
        try {
            codTutor = Integer.parseInt(codTutorStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código do tutor inválido!");
            return;
        }
        for (Tutor tutor : tutores) {
            if (tutor.getCodTutor() == codTutor) {
                Pet pet = tutor.getPet();
                String msg = String.format("Tutor encontrado: %s, Tipo: %s, Nascimento: %s, Tutor: %s, Nascimento: %s, Endereço: %s.",
                        pet.getNome(), pet.getTipo(), pet.getNascimento().toString(), tutor.getNome(), tutor.getNascimento().toString(), tutor.getEndereco());
                JOptionPane.showMessageDialog(this, msg);
                writeLog(msg);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Tutor não encontrado!");
    }

    private void excluiTutor() {
        String codTutorStr = JOptionPane.showInputDialog(this, "Código do tutor: ");
        if (codTutorStr == null || codTutorStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código do tutor não fornecido!");
            return;
        }
        int codTutor;
        try {
            codTutor = Integer.parseInt(codTutorStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código do tutor inválido!");
            return;
        }
        for (Tutor tutor : tutores) {
            if (tutor.getCodTutor() == codTutor) {
                tutor.setDeletado(true);
                for (Pet pet : pets) {
                    if (pet.getNome().equals(tutor.getPet().getNome())) {
                        pets.remove(pet);
                        break;
                    }
                }
                gravaPets(); // Atualiza o arquivo.
                JOptionPane.showMessageDialog(this, "Tutor e pets excluídos com sucesso!");
                writeLog("Tutor e pets excluídos: " + tutor);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Tutor não encontrado!");
    }

    private Tutor findTutor(Pet pet) {
        for (Tutor tutor : tutores) {
            if (tutor.getPet().equals(pet)) {
                return tutor;
            }
        }
        return null;
    }
}
