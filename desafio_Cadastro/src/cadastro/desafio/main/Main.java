package cadastro.desafio.main;

import cadastro.desafio.exceptions.NomeSobrenomeException;
import cadastro.desafio.exceptions.PesoException;
import cadastro.desafio.model.*;
import cadastro.desafio.util.EntradaUtils;
import cadastro.desafio.util.FiltroPetUtils;
import cadastro.desafio.util.PetUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        File file = new File("formulario.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        EntradaSaidaDados entradaSaida = new EntradaSaidaDados();
        entradaSaida.incluirOpcoes();

        List<Pet> pets = new ArrayList<>();
        try {
            pets = PetFileManager.carregarTodosPets();
        } catch (IOException e) {
            System.out.println("Erro ao carregar pets: " + e.getMessage());
        }

        int opcoes;

        do {

            System.out.println("1. Cadastrar um novo pet");
            System.out.println("2. Alterar os dados do pet cadastrado");
            System.out.println("3. Deletar um pet cadastrado");
            System.out.println("4. Listar todos os pets cadastrados");
            System.out.println("5. Listar pets por algum critério (idade, nome, raça)");
            System.out.println("6. Sair");
            System.out.println("------------------------------------------------------");
            System.out.println("Digite a opção desejada: ");

            opcoes = sc.nextInt();
            sc.nextLine();

            try {


                switch (opcoes) {

                    case 1:
                        entradaSaida.lerOpcoes();
                        Pet petCadastrado = new Pet();

                        System.out.println("Responda a 1ª pergunta (Nome e Sobrenome):");
                        String respostaNomeSobrenome = sc.nextLine();
                        petCadastrado.setNomeSobrenome(respostaNomeSobrenome);

                        System.out.println("Responda a 2ª pergunta (Cachorro ou Gato): ");
                        String respostaTipo = sc.nextLine();
                        petCadastrado.setTipoPet(TipoPet.tipoPetPorNome(respostaTipo));


                        TipoSexo TipoSexo = null;
                        do {
                            System.out.println("Responda a 3ª pergunta (Macho ou Femea): ");
                            String respostaSexo = sc.nextLine();
                            TipoSexo = TipoSexo.TipoSexoPorNome(respostaSexo);

                            if (TipoSexo == null) {
                                System.out.println("Sexo inválido! Digite apenas 'Macho' ou 'Femea'.");
                            }
                        } while (TipoSexo == null);

                        petCadastrado.setTipoDeSexo(TipoSexo);

                        Endereco endereco = EntradaUtils.lerEndereco(sc);
                        petCadastrado.setEnderecoCompleto(endereco);

                        double idadeInput = EntradaUtils.lerDoubleOuNaoInformado(sc, "Responda a 5ª pergunta (Idade do pet)", -1);
                        petCadastrado.setIdade(idadeInput);

                        double pesoInput = EntradaUtils.lerDoubleOuNaoInformado(sc, "Responda a 6ª pergunta (Peso do pet)", -1);
                        petCadastrado.setPeso(pesoInput);


                        System.out.println("Responda a 7ª e última pergunta (Raça): ");
                        petCadastrado.setRaca(sc.nextLine());

                        PetFileManager.salvarPet(petCadastrado);
                        System.out.println("Pet cadastrado com sucesso!");
                        break;

                    case 2:
                        System.out.println("Selecione o tipo de animal para buscar: ");
                        System.out.println("1 - Cachorro");
                        System.out.println("2 - Gato");
                        int tipoBusca = Integer.parseInt(sc.nextLine().trim());
                        List<Pet> filtrados = new ArrayList<>();
                        for (Pet p : pets) {
                            if (tipoBusca == 1 && p.getTipoPet() == TipoPet.CACHORRO) {
                                filtrados.add(p);
                            } else if (tipoBusca == 2 && p.getTipoPet() == TipoPet.GATO) {
                                filtrados.add(p);
                            }
                        }

                        System.out.println("Deseja filtrar mais algum critério? (S/N)");
                        String maisUmCrit = sc.nextLine().trim();
                        List<Pet> resultado = new ArrayList<>(filtrados);
                        if (maisUmCrit.equalsIgnoreCase("S")) {
                            System.out.println("""
                                Por qual critério deseja buscar?
                                1) Nome/Sobrenome
                                2) Sexo
                                3) Idade
                                4) Peso
                                5) Raça
                                6) Endereço""");

                            int crit = Integer.parseInt(sc.nextLine().trim());
                            System.out.println("Digite o valor para o critério escolhido: ");
                            String val = sc.nextLine();

                            List<Pet> temp = new ArrayList<>();
                            for (Pet p : resultado) {
                                if (FiltroPetUtils.aplicaFiltro(p, crit, val)) {
                                    temp.add(p);
                                }
                            }
                            resultado = temp;
                        } else {
                            resultado = filtrados;
                        }

                        PetUtils.listarPets(resultado);

                        int numeroAlterar;
                        Pet alvo;

                        while (true) {
                            System.out.println("Digite o número do pet que deseja alterar os dados: ");
                            String linha = sc.nextLine().trim();
                            try {
                                numeroAlterar = Integer.parseInt(linha);
                                if (numeroAlterar >= 1 && numeroAlterar <= filtrados.size()) {
                                    alvo = filtrados.get(numeroAlterar - 1);
                                    break;
                                } else {
                                    System.out.println("Número inválido! Digite um valor entre 1 e " + filtrados.size() + ".");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida! Digite um número.");
                            }
                        }

                        System.out.println("O nome atual é [" + alvo.getNomeSobrenome() + "]");
                        System.out.println("Caso queira alterar, digite o novo nome, senão pressione 'Enter'");
                        String novoNome = sc.nextLine().trim();
                        if (!novoNome.isEmpty()) alvo.setNomeSobrenome(novoNome);

                        System.out.println("O endereço atual é [" + alvo.getEnderecoCompleto() + "]");
                        System.out.println("Caso queira alterar, digite um novo endereço (rua, número e cidade), senão pressione 'Enter'");
                        Endereco novoEnd = EntradaUtils.lerEndereco(sc);
                        if (!(novoEnd.getRua().equals(Pet.DADO_NAO_INFORMADO) && novoEnd.getNumero() == -1 && novoEnd.getCidade().equals(Pet.DADO_NAO_INFORMADO))) {
                            alvo.setEnderecoCompleto(novoEnd);
                        }

                        System.out.println("A idade atual é [" + alvo.getIdade() + "]");
                        System.out.println("Caso queira alterar, digite a nova idade, senão pressione 'Enter'");
                        double novaIdade = EntradaUtils.lerDoubleOuNaoInformado(sc,"", alvo.getIdade());
                        if (novaIdade != alvo.getIdade()) alvo.setIdade(novaIdade);

                        System.out.println("O peso atual é [" + alvo.getPeso() + "]");
                        System.out.println("Caso queira alterar, digite o novo peso, senão pressione 'Enter'");
                        double novoPeso = EntradaUtils.lerDoubleOuNaoInformado(sc,"", alvo.getPeso());
                        if (novoPeso != alvo.getPeso()) alvo.setPeso(novoPeso);

                        System.out.println("A raça  atual é [" + alvo.getRaca() + "]");
                        System.out.println("Caso queira alterar, digite a nova raça, senão pressione 'Enter'");
                        String novaRaca = sc.nextLine().trim();
                        if (!novaRaca.isEmpty()) alvo.setRaca(novaRaca);

                        PetFileManager.salvarPet(alvo);
                        System.out.println("Pet alterado com sucesso!");
                        break;

                    case 3:
                        PetUtils.excluirPet(sc, pets);
                        break;

                    case 4:
                        PetUtils.listarPets(pets);
                        break;

                    case 5:
                        System.out.println("""
                                Por qual critério você deseja filtar?
                                1) Nome/Sobrenome
                                2) Sexo
                                3) Idade
                                4) Peso
                                5) Raça
                                6) Endereço
                                7) Tipo de animal""");

                        int crit = Integer.parseInt(sc.nextLine().trim());
                        System.out.println("Digite o critério para o valor escolhido (Exemplo: Caso tenha escolhido opção 2 - Sexo, digite Macho ou Femea): ");
                        String val = sc.nextLine();

                        List<Pet> petFiltrados = new ArrayList<>();
                        for (Pet pet : pets) {
                        if (FiltroPetUtils.aplicaFiltro(pet, crit, val)) {
                            petFiltrados.add(pet);
                        }
                    }
                        PetUtils.listarPets(petFiltrados);
                        break;

                    case 6:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida! Por gentileza, digite apenas os números do menu acima.");


                }
            } catch (NomeSobrenomeException | PesoException e) {
                System.out.println(e.getMessage());

            } catch (IOException e) {
                throw new RuntimeException(e);

            }


        } while (opcoes != 6);

    }
}
