package view;

import java.util.List;
import java.util.Scanner;

import model.Endereco;
import model.Usuario;
import service.ViaCepService;

public class UsuarioView {
    private Scanner scanner;
    private ViaCepService viaCepService;

    public UsuarioView() {
        this.scanner = new Scanner(System.in);
        this.viaCepService = new ViaCepService();
    }

    public void exibirMenuUsuario() {
        System.out.println("\n=== MENU PACIENTES ===");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Buscar Paciente por ID");
        System.out.println("4. Buscar Paciente por CPF");
        System.out.println("5. Atualizar Paciente");
        System.out.println("6. Deletar Paciente");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opcao: ");
    }

    public Usuario lerDadosUsuario() {
        System.out.println("\n=== CADASTRAR PACIENTE ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.println("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // ⭐ MODIFICADO: Agora usa ViaCEP
        System.out.println("\n--- Dados do Endereco (Via CEP) ---");
        Endereco endereco = null;
        boolean cepValido = false;

        while (!cepValido) {
            System.out.print("CEP (com ou sem hifen): ");
            String cep = scanner.nextLine();

            // Consulta a API ViaCEP
            endereco = viaCepService.consultarCep(cep);

            if (endereco != null) {
                cepValido = true;
                System.out.println("\n=== Endereco encontrado! ===");
                System.out.println("Estado: " + endereco.getEstado());
                System.out.println("Cidade: " + endereco.getCidade());
                System.out.println("Rua: " + endereco.getRua());
                System.out.println("CEP: " + endereco.getCep());

                // Solicita apenas o número
                System.out.print("\nDigite o numero da residencia: ");
                String numero = scanner.nextLine();
                endereco.setNumero(numero);

            } else {
                System.out.println("CEP invalido ou nao encontrado! Tente novamente.");
            }
        }

        return new Usuario(nome, cpf, idade, email, telefone, endereco);
    }

    public void exibirUsuarios(List<Usuario> usuarios) {
        System.out.println("\n=== LISTA DE PACIENTES ===");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println("----------------------------------------");
                System.out.println(usuario.toString());
                System.out.println("----------------------------------------");
            }
        }
    }

    public void exibirUsuario(Usuario usuario) {
        if (usuario != null) {
            System.out.println("\n=== DADOS DO PACIENTE ===");
            System.out.println(usuario.toString());
        } else {
            System.out.println("Paciente nao encontrado.");
        }
    }

    public int lerIdUsuario() {
        System.out.print("Digite o ID do paciente: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return -1;
        }
    }

    public String lerCpfUsuario() {
        System.out.print("Digite o CPF do paciente: ");
        return scanner.nextLine();
    }

    public Usuario lerDadosAtualizacaoUsuario() {
        System.out.println("\n=== ATUALIZAR PACIENTE ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // ⭐ MODIFICADO: Agora usa ViaCEP
        System.out.println("\n--- Dados do Endereco (Via CEP) ---");
        Endereco endereco = null;
        boolean cepValido = false;

        while (!cepValido) {
            System.out.print("CEP (com ou sem hifen): ");
            String cep = scanner.nextLine();

            // Consulta a API ViaCEP
            endereco = viaCepService.consultarCep(cep);

            if (endereco != null) {
                cepValido = true;
                System.out.println("\n=== Endereco encontrado! ===");
                System.out.println("Estado: " + endereco.getEstado());
                System.out.println("Cidade: " + endereco.getCidade());
                System.out.println("Rua: " + endereco.getRua());
                System.out.println("CEP: " + endereco.getCep());

                // Solicita apenas o número
                System.out.print("\nDigite o numero da residencia: ");
                String numero = scanner.nextLine();
                endereco.setNumero(numero);

            } else {
                System.out.println("CEP invalido ou nao encontrado! Tente novamente.");
            }
        }

        return new Usuario(nome, cpf, idade, email, telefone, endereco);
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirMensagemSucesso() {
        System.out.println("Operacao realizada com sucesso!");
    }

    public void exibirMensagemErro() {
        System.out.println("Erro ao realizar operacao!");
    }

    public boolean confirmarExclusao() {
        System.out.print("Tem certeza que deseja excluir? (s/n): ");
        String resposta = scanner.nextLine().toLowerCase();
        return resposta.equals("s") || resposta.equals("sim");
    }

    public void aguardarEnter() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }
}