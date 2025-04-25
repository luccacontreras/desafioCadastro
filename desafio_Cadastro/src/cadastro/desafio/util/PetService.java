package cadastro.desafio.util;

import cadastro.desafio.exceptions.NomeSobrenomeException;
import cadastro.desafio.exceptions.PesoException;
import cadastro.desafio.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetService {

    public static Pet cadastrarNovoPet(Scanner sc, EntradaSaidaDados entradaSaida) throws NomeSobrenomeException, PesoException {
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
        return petCadastrado;
    }

    public static Pet alterarPet(Scanner sc, List<Pet> pets) throws IOException {
        System.out.println("Selecione o tipo de animal para buscar: ");
        System.out.println("1 - Cachorro");
        System.out.println("2 - Gato");
        int tipoBusca;
        try {
            tipoBusca = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Usando valor padrão (1).");
            tipoBusca = 1;
        }
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
            resultado = aplicarFiltroAdicional(sc, filtrados);


        }

        PetUtils.listarPets(resultado);

        Pet alvo = selecionarPet(sc, resultado);
        alterarDadosPet(sc, alvo);

        return alvo;
    }

    private static List<Pet> aplicarFiltroAdicional(Scanner sc, List<Pet> filtrados) {
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
        for (Pet p : filtrados) {
            if (FiltroPetUtils.aplicaFiltro(p, crit, val)) {
                temp.add(p);
            }
        }

        return temp;
    }

    private static Pet selecionarPet(Scanner sc, List<Pet> pets) {
        int numeroAlterar;
        Pet alvo;

        while (true) {
            System.out.println("Digite o número do pet que deseja alterar os dados: ");
            String linha = sc.nextLine().trim();
            try {
                numeroAlterar = Integer.parseInt(linha);
                if (numeroAlterar >= 1 && numeroAlterar <= pets.size()) {
                    alvo = pets.get(numeroAlterar - 1);
                    break;
                } else {
                    System.out.println("Número inválido! Digite um valor entre 1 e " + pets.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }

        return alvo;
    }

    private static void alterarDadosPet(Scanner sc, Pet alvo) {
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
        double novaIdade = EntradaUtils.lerDoubleOuNaoInformado(sc, "", alvo.getIdade());
        if (novaIdade != alvo.getIdade()) alvo.setIdade(novaIdade);

        System.out.println("O peso atual é [" + alvo.getPeso() + "]");
        System.out.println("Caso queira alterar, digite o novo peso, senão pressione 'Enter'");
        double novoPeso = EntradaUtils.lerDoubleOuNaoInformado(sc, "", alvo.getPeso());
        if (novoPeso != alvo.getPeso()) alvo.setPeso(novoPeso);

        System.out.println("A raça atual é [" + alvo.getRaca() + "]");
        System.out.println("Caso queira alterar, digite a nova raça, senão pressione 'Enter'");
        String novaRaca = sc.nextLine().trim();
        if (!novaRaca.isEmpty()) alvo.setRaca(novaRaca);
    }

    public static void filtrarEListarPets(Scanner sc, List<Pet> pets) {
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
    }


}
