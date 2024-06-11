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

    private int largura, altura; // Largura e altura da área útil do frame.
    private JButton button; // Botão apenas com texto: para gerar os vários botões.
    private static JTextArea logArea = new JTextArea("", 10, 66); // Area de log.
    static int sizeLog = 0; // Contador de mensagens do log.
    private static String arqPet = "Pet.dat"; // O arquivo de dados.
    // ArrayList para manter o banco de dados na memória.
    private static ArrayList<Pet> pets = new ArrayList<>();
    // ArrayList para manter os tutores
    private static ArrayList<Tutor> tutores = new ArrayList<>();
    // Variáveis de formulário
    private JTextField nomePet, tipoPet, diaPet, mesPet, anoPet, nomeTutor, contatoTutor, diaTutor, mesTutor, anoTutor;
    // Lista de opções disponibilizadas pelo aplicativo.
    private static final String[] option = {"Cadastrar", "Imprimir cadastro", "Buscar por nome", "Encerrar"};
    static Font f = new Font("Consolas", Font.PLAIN, 13);

    // *** Criação da interface do aplicativo *******************************
    public PetInterface(int largura, int altura) {
        super("CADASTRO DE PETS"); // Título do JFrame.
        this.largura = largura;
        this.altura = altura;
        setLayout(new FlowLayout());
        JPanel painel = new JPanel();
        painel.setPreferredSize(new Dimension(largura, altura));
        add(painel);
        pack();
        BufferedImage imgDecor = null;
        try {
            File file = new File("pets.jpg"); // Arquivo de imagem (na pasta do aplicativo).
            FileInputStream fis = new FileInputStream(file); // Cria o fluxo de entrada.
            imgDecor = ImageIO.read(fis); // Lê imagem do arquivo, colocando na memória (buffer).
        } catch (IOException ex) {
            ex.printStackTrace(); // Caso haja problema.
        }

        ButtonHandler handler = new ButtonHandler(); // ButtonHandler é definido mais abaixo.
        for (int k = 0; k < 4; k++) {
            button = new JButton(option[k]); // Cria novo botão.
            painel.add(button); // Adiciona os botões (plainJButtons) ao JFrame.
            if (k != 3) // Adiciona o respectivo ouvinte de eventos aos três primeiros
                button.addActionListener(handler);
        }

        button.addActionListener(e -> { // Notaçao lambda para ouvinte de evento.
            PetInterface.this.dispose(); // Fecha janela.
            System.exit(0); // Fecha aplicativo.
        });

        painel.add(new JLabel("Log de ações realizadas na sessão.")); // Título da caixa
        Box box = Box.createHorizontalBox(); // Cria uma caixa.
        logArea.setFont(f); // Define a fonte da área de log.
        logArea.setEditable(false); // Trava a edição dessa área.
        box.add(new JScrollPane(logArea)); // Insere a área de log num painel com rolamento.
        painel.add(box); // Adiciona a caixa ao painel.

        JPanel decor = new JPanel(); // Cria o painel que conterá a imagem decorativa.
        decor.setLayout(new FlowLayout()); // Define layout automático.
        JLabel imgLabel = new JLabel(new ImageIcon(imgDecor)); // JLabel com imagem.
        decor.add(imgLabel); // Adicional JLabel com imagem ao painel.
        painel.add(decor); // Adiciona o outro painel ao painel principal.

        recuperaPets(); // Recupera dados do arquivo, se pré-existente.
    }

    // Classe interna para tratamento de evento de botão
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand().equals(option[0])) {
                cadastrar(); // Chama método para cadastramento.
            }
            if (event.getActionCommand().equals(option[1])) {
                imprimirCadastro(); // Chama método para impressão.
            }
            if (event.getActionCommand().equals(option[2])) {
                buscaPet(); // Chama método para busca.
            }
        }
    }

    // Método para escrever na área de log.
    public static void writeLog(String s) {
        if (sizeLog == 0) // Aviso de criação do log.
            logArea.append("LOG CRIADO...");
        else { // Adição de novas ações.
            logArea.append("\n");
            logArea.append("- " + s);
        }
        sizeLog++;
    }

    // *** Cadastramento ****************************************************
    private void cadastrar() { // Usa um objeto cadastrarFrame (abaixo).
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
            nomePet = new JTextField("", 20);
            add(nomePet);
            add(new JLabel("Tipo do Pet"));
            tipoPet = new JTextField("", 20);
            add(tipoPet);
            add(new JLabel("Data de Nascimento do Pet (dd/mm/aaaa)"));
            diaPet = new JTextField(2);
            add(diaPet);
            add(new JLabel("/"));
            mesPet = new JTextField(2);
            add(mesPet);
            add(new JLabel("/"));
            anoPet = new JTextField(4);
            add(anoPet);
            add(new JLabel("Nome do Tutor"));
            nomeTutor = new JTextField("", 20);
            add(nomeTutor);
            add(new JLabel("Data de Nascimento do Tutor (dd/mm/aaaa)"));
            diaTutor = new JTextField(2);
            add(diaTutor);
            add(new JLabel("/"));
            mesTutor = new JTextField(2);
            add(mesTutor);
            add(new JLabel("/"));
            anoTutor = new JTextField(4);
            add(anoTutor);
            add(new JLabel("Contato do Tutor"));
            contatoTutor = new JTextField("", 20);
            add(contatoTutor);
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
                String cTutor = contatoTutor.getText();
                int DPet = 0, MPet = 0, APet = 0, DTutor = 0, MTutor = 0, ATutor = 0;

                if (nPet.length() == 0 || tPet.length() == 0 || dPet.length() == 0 || mPet.length() == 0 || aPet.length() == 0 ||
                    nTutor.length() == 0 || dTutor.length() == 0 || mTutor.length() == 0 || aTutor.length() == 0 || cTutor.length() == 0) {
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
                Tutor tutor = new Tutor(nTutor, nascTutor, cTutor, pet);
                tutores.add(tutor);
                String msg = String.format("Pet cadastrado: %s, Tipo: %s, Nascimento: %s, Tutor: %s, Nascimento: %s, Contato: %s.",
                        nPet, tPet, nascPet.toString(), nTutor, nascTutor.toString(), cTutor);
                writeLog(msg);
                gravaPets(); // Atualiza o arquivo.
                JOptionPane.showMessageDialog(null, "Pet cadastrado com sucesso!");
                setVisible(false); // Fecha o frame de cadastro.
            });
        }
    }

    // *** Recuperação dos dados do arquivo *********************************
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

    // *** Gravação dos dados no arquivo ************************************
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
            writeLog("Arquivo atualizado: " + pets.size() + " cadastros.");
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

    // *** Busca de cadastro de pet *****************************************
    private void buscaPet() {
        String busca = JOptionPane.showInputDialog(this, "Nome do pet: ");
        if (busca == null || busca.length() == 0) {
            JOptionPane.showMessageDialog(this, "Nome não fornecido!");
            return;
        }
        for (Pet pet : pets) {
            if (pet.getNome().equalsIgnoreCase(busca)) {
                Tutor tutor = findTutor(pet);
                String msg = String.format("Pet encontrado: %s, Tipo: %s, Nascimento: %s, Tutor: %s, Nascimento: %s, Contato: %s.",
                        pet.getNome(), pet.getTipo(), pet.getNascimento().toString(), tutor.getNome(), tutor.getNascimento().toString(), tutor.getContato());
                JOptionPane.showMessageDialog(this, msg);
                writeLog(msg);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Pet não encontrado!");
    }

    // Método auxiliar para encontrar o tutor de um pet
    private Tutor findTutor(Pet pet) {
        for (Tutor tutor : tutores) {
            if (tutor.getPet().equals(pet)) {
                return tutor;
            }
        }
        return null; // Caso não encontre o tutor
    }

    // *** Impressão do cadastro de pets ************************************
    private void imprimirCadastro() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", "Nome Pet", "Tipo Pet", "Nascimento Pet", "Idade Pet", "Nome Tutor", "Nascimento Tutor", "Idade Tutor", "Contato Tutor"));
        for (Pet pet : pets) {
            Tutor tutor = findTutor(pet);
            if (tutor.isDeletado)
                continue;

            if (tutor != null) {
                sb.append(String.format("%-15s %-15s %-15s %-15d %-15s %-15s %-15d %-15s\n",
                        pet.getNome(), pet.getTipo(), pet.getNascimento().toString(), pet.getIdade(), tutor.getNome(), tutor.getNascimento().toString(), tutor.getIdade(), tutor.getContato()));
            } else {
                sb.append(String.format("%-15s %-15s %-15s %-15d %-15s %-15s %-15s %-15s\n",
                        pet.getNome(), pet.getTipo(), pet.getNascimento().toString(), pet.getIdade(), "N/A", "N/A", "N/A", "N/A"));
            }
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setFont(f);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Cadastro de Pets", JOptionPane.INFORMATION_MESSAGE);
        writeLog("Impressão do cadastro de pets.");
    }


    // *** Main: instanciando a aplicação ******************************
    public static void main(String[] args) {
        PetInterface petApp = new PetInterface(800, 600);
        petApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        petApp.setLocationRelativeTo(null);
        petApp.setVisible(true);
    }
}
