package cadastro.desafio.main;

import cadastro.desafio.exceptions.NomeSobrenomeException;
import cadastro.desafio.exceptions.PesoException;
import cadastro.desafio.model.EntradaSaidaDados;
import cadastro.desafio.model.Pet;
import cadastro.desafio.model.PetFileManager;
import cadastro.desafio.util.PetService;
import cadastro.desafio.util.PetUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicializar arquivo de formulário
        inicializarFormulario();

        // Configurar entrada/saída de dados
        EntradaSaidaDados entradaSaida = new EntradaSaidaDados();
        entradaSaida.incluirOpcoes();

        // Carregar pets existentes
        List<Pet> pets = carregarPets();

        //Processar menu principal
        processarMenu(sc, entradaSaida, pets);

        sc.close();
    }

    private static void inicializarFormulario() {
        File file = new File("formulario.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Pet> carregarPets() {
        List<Pet> pets = new ArrayList<>();
        try {
            pets = PetFileManager.carregarTodosPets();
        } catch (IOException e) {
            System.out.println("Erro ao carregar pets: " + e.getMessage());
        }
        return pets;
    }

    private static void processarMenu(Scanner sc, EntradaSaidaDados entradaSaida, List<Pet> pets) {
        int opcoes;

        do {
            exibirMenuPrincipal();
            opcoes = Integer.parseInt(sc.nextLine().trim());

            try {
                switch (opcoes) {
                    case 1: // Cadastrar pet
                        Pet novoPet = PetService.cadastrarNovoPet(sc, entradaSaida);
                        PetFileManager.salvarPet(novoPet);
                        System.out.println("Pet cadastrado com sucesso!");
                        pets = PetFileManager.carregarTodosPets(); // Recarregar lista
                        break;

                    case 2: // Alterar pet
                        Pet petAlterado = PetService.alterarPet(sc, pets);
                        PetFileManager.salvarPet(petAlterado);
                        System.out.println("Pet alterado com sucesso!");
                        pets = PetFileManager.carregarTodosPets(); // Recarregar lista
                        break;

                    case 3: // Deletar pet
                        PetUtils.excluirPet(sc, pets);
                        pets = PetFileManager.carregarTodosPets(); // Recarregar lista
                        break;

                    case 4: // Listar todos os pets
                        PetUtils.listarPets(pets);
                        break;

                    case 5: // Listar pets por critério
                        PetService.filtrarEListarPets(sc, pets);
                        break;

                    case 6: // Sair
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida! Por gentileza, digite apenas os números do menu acima.");
                }
            } catch (NomeSobrenomeException | PesoException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro de I/O: " + e.getMessage());
            }

        } while (opcoes != 6);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("1. Cadastrar um novo pet");
        System.out.println("2. Alterar os dados do pet cadastrado");
        System.out.println("3. Deletar um pet cadastrado");
        System.out.println("4. Listar todos os pets cadastrados");
        System.out.println("5. Listar pets por algum critério (idade, nome, raça)");
        System.out.println("6. Sair");
        System.out.println("------------------------------------------------------");
        System.out.println("Digite a opção desejada: ");
    }
}