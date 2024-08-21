import java.io.*;
import java.util.*;
// ALUNO JOÃO GABRIEL VIEIRA SILVA

class Pessoa implements Serializable {
    private String nome;
    private String email;

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(email, pessoa.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

public class Main {
    private static final String pessoas_dat = "pessoas.dat";

    public static void main(String[] args) {
        Set<Pessoa> pessoas = lerPessoas();

        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\nMENU:");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println("1. Salvar uma pessoa");
            System.out.println("2. Listar todas as pessoas");
            System.out.println("3. Deletar uma pessoa pelo e-mail");
            System.out.println("4. Sair");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();
            scanner.nextLine(); // lixo

            switch (escolha) {
                case 1:
                    System.out.print("Diga-me o nome da pessoa: ");
                    String nome = scanner.nextLine();
                    System.out.print("Diga-me o e-mail da pessoa: ");
                    String email = scanner.nextLine();
                    Pessoa novaPessoa = new Pessoa(nome, email);
                    pessoas.add(novaPessoa);
                    salvarPessoas(pessoas);
                    break;
                case 2:
                    System.out.println("\nLista de pessoas:");
                    for (Pessoa p : pessoas) {
                        System.out.println("Nome: " + p.getNome() + ", E-mail: " + p.getEmail());
                    }
                    break;
                case 3:
                    System.out.print("Digite o e-mail da pessoa a ser deletada: ");
                    String emailMorto = scanner.nextLine();
                    Pessoa pessoaDeletada = new Pessoa("", emailMorto);
                    if (pessoas.remove(pessoaDeletada)) {
                        salvarPessoas(pessoas);
                        System.out.println("Pessoa deletada com sucesso!");
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } 
        while (escolha != 4);
    }

    private static Set<Pessoa> lerPessoas() {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(pessoas_dat);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (Set<Pessoa>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashSet<>();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void salvarPessoas(Set<Pessoa> pessoas) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(pessoas_dat);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(pessoas);
            System.out.println("Pessoas salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar as pessoas.");
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
