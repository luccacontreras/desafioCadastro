package cadastro.desafio.main;

import cadastro.desafio.exceptions.NomeSobrenomeException;
import cadastro.desafio.exceptions.PesoException;
import cadastro.desafio.model.*;

import java.io.File;
import java.io.IOException;
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


        int opcoes = 0;

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

                        System.out.println("Responda a 4ª pergunta (Rua): ");
                        String rua = sc.nextLine();

                        System.out.println("Ainda na 4ª pergunta (número da Rua): ");
                        int numero = sc.nextInt();
                        sc.nextLine();

                        System.out.println("Finalmente, 4ª pergunta (Cidade): ");
                        String cidade = sc.nextLine();

                        Endereco endereco = new Endereco(rua, numero, cidade);
                        petCadastrado.setEnderecoCompleto(endereco);

                        System.out.println("Responda a 5ª pergunta (Idade): ");
                        System.out.println("Obs: Se o pet tiver menos de 1 ano, informe a idade em meses, Ex: 0.5 (caso tenha 5 meses de vida)");
                        petCadastrado.setIdade(sc.nextInt());
                        sc.nextLine();

                        System.out.println("Responda a 6ª pergunta (Peso): ");
                        petCadastrado.setPeso(sc.nextDouble());
                        sc.nextLine();

                        System.out.println("Responda a 7ª e última pergunta (Raça): ");
                        petCadastrado.setRaca(sc.nextLine());

                        PetFileManager.salvarPet(petCadastrado);
                        System.out.println("Pet cadastrado com sucesso!");
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
