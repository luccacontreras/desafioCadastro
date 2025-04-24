package cadastro.desafio.util;

import cadastro.desafio.model.Endereco;
import cadastro.desafio.model.Pet;

import java.util.Scanner;

public class EntradaUtils {

    public static Endereco lerEndereco(Scanner sc) {
        System.out.println("Responda a 4ª pergunta (Rua): ");
        String rua = sc.nextLine().trim();

        System.out.println("Informe o número:");
        String numeroStr = sc.nextLine().trim();
        int numero = -1;
        if (!numeroStr.isEmpty()) {
            try {
                numero = Integer.parseInt(numeroStr);
            } catch (NumberFormatException e) {
                numero = -1;
            }
        }

        System.out.println("Informe a cidade:");
        String cidade = sc.nextLine().trim();

        boolean todosVazios = rua.isEmpty() && numero == -1 && cidade.isEmpty();

        if (todosVazios) {
            return Endereco.ENDERECO_NAO_INFORMADO;
        }

        return new Endereco(rua.isEmpty() ? Pet.DADO_NAO_INFORMADO : rua, numero, cidade.isEmpty() ? Pet.DADO_NAO_INFORMADO : cidade);
    }

    public static double lerDoubleOuNaoInformado(Scanner sc, String prompt, double valorSentinela) {
        System.out.println(prompt);
        String linha = sc.nextLine().trim();
        if (linha.isEmpty()) {
            return valorSentinela;
        }
        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            return valorSentinela;
        }
    }
}
