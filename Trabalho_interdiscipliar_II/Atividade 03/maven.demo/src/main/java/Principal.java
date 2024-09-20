import java.util.List;
import java.util.Scanner;
import dao.UsuarioDAO;
import model.Usuario;

import static spark.Spark.*;

public class Principal {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean sair = false;

        while (!sair) {
            exibirMenu();
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    inserirUsuario();
                    break;
                case 3:
                    excluirUsuario();
                    break;
                case 4:
                    atualizarUsuario();
                    break;
                case 5:
                    sair = true;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n==== MENU ====");
        System.out.println("1. Listar usuarios");
        System.out.println("2. Inserir usuario");
        System.out.println("3. Excluir usuario");
        System.out.println("4. Atualizar usuario");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static void listarUsuarios() {
        System.out.println("\n==== Listar usuarios ===");
        List<Usuario> usuarios = usuarioDAO.get();
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private static void inserirUsuario() {
        System.out.println("\n==== Inserir usuario ===");
        System.out.print("Codigo: ");
        int codigo = sc.nextInt();
        sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Sexo (M/F): ");
        char sexo = sc.nextLine().charAt(0);

        Usuario usuario = new Usuario(codigo, login, senha, sexo);
        if (usuarioDAO.insert(usuario)) {
            System.out.println("Usuario inserido com sucesso!");
        } else {
            System.out.println("Falha ao inserir usuario.");
        }
    }

    private static void excluirUsuario() {
        System.out.println("\n==== Excluir usuario ===");
        System.out.print("Informe o codigo do usuario: ");
        int codigo = sc.nextInt();
        if (usuarioDAO.delete(codigo)) {
            System.out.println("Usuario excluido.");
        } else {
            System.out.println("Falha ao excluir usuario.");
        }
    }

    private static void atualizarUsuario() {
        System.out.println("\n==== Atualizar usuario ===");
        System.out.print("Informe o codigo do usuario: ");
        int codigo = sc.nextInt();
        sc.nextLine();

        Usuario usuario = usuarioDAO.get(codigo);
        if (usuario != null) {
            System.out.print("Novo login: ");
            String login = sc.nextLine();
            if (!login.isEmpty()) {
                usuario.setLogin(login);
            }
            System.out.print("Nova senha: ");
            String senha = sc.nextLine();
            if (!senha.isEmpty()) {
                usuario.setSenha(senha);
            }
            System.out.print("Novo sexo (M/F): ");
            String sexoStr = sc.nextLine();
            if (!sexoStr.isEmpty()) {
                usuario.setSexo(sexoStr.charAt(0));
            }

            if (usuarioDAO.update(usuario)) {
                System.out.println("Usuario atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar usuario.");
            }
        } else {
            System.out.println("Usuario nao encontrado.");
        }
    }
}