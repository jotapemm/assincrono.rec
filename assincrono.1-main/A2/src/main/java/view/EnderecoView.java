package view;

import java.util.List;
import java.util.Scanner;

import model.Endereco;
import service.ViaCepService;

public class EnderecoView {
    private Scanner scanner;
    private ViaCepService viaCepService;

    public EnderecoView() {
        this.scanner = new Scanner(System.in);
        this.viaCepService = new ViaCepService();
    }

    public void exibirMenuEndereco() {
        System.out.println("\n=== MENU ENDERECOS ===");
        System.out.println("1. Cadastrar Endereco");
        System.out.println("2. Listar Enderecos");
        System.out.println("3. Buscar Endereco por ID");
        System.out.println("4. Buscar Enderecos por Cidade");
        System.out.println("5. Buscar Enderecos por Estado");
        System.out.println("6. Atualizar Endereco");
        System.out.println("7. Deletar Endereco");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opcao: ");
    }

    public Endereco lerDadosEndereco() {
        System.out.println("\n=== CADASTRAR ENDERECO ===");

        // ⭐ MODIFICADO: Agora usa ViaCEP
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
                System.out.print("\nDigite o numero: ");
                String numero = scanner.nextLine();
                endereco.setNumero(numero);

            } else {
                System.out.println("CEP invalido ou nao encontrado! Tente novamente.");
            }
        }

        return endereco;
    }

    public void exibirEnderecos(List<Endereco> enderecos) {
        System.out.println("\n=== LISTA DE ENDERECOS ===");
        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereco cadastrado.");
        } else {
            for (Endereco endereco : enderecos) {
                System.out.println("----------------------------------------");
                System.out.println("ID: " + endereco.getId());
                System.out.println(endereco.toString());
                System.out.println("----------------------------------------");
            }
        }
    }

    public void exibirEndereco(Endereco endereco) {
        if (endereco != null) {
            System.out.println("\n=== DADOS DO ENDERECO ===");
            System.out.println("ID: " + endereco.getId());
            System.out.println(endereco.toString());
        } else {
            System.out.println("Endereco nao encontrado.");
        }
    }

    public int lerIdEndereco() {
        System.out.print("Digite o ID do endereco: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return -1;
        }
    }

    public String lerCidade() {
        System.out.print("Digite a cidade: ");
        return scanner.nextLine();
    }

    public String lerEstado() {
        System.out.print("Digite o estado: ");
        return scanner.nextLine();
    }

    public Endereco lerDadosAtualizacaoEndereco() {
        System.out.println("\n=== ATUALIZAR ENDERECO ===");

        // ⭐ MODIFICADO: Agora usa ViaCEP
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
                System.out.print("\nDigite o numero: ");
                String numero = scanner.nextLine();
                endereco.setNumero(numero);

            } else {
                System.out.println("CEP invalido ou nao encontrado! Tente novamente.");
            }
        }

        return endereco;
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