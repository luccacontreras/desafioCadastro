package cadastro.desafio.util;

import cadastro.desafio.model.Pet;

public class FiltroPetUtils {

    public static boolean aplicaFiltro(Pet p, int criterio, String valor) {
        return switch (criterio) {
            case 1 -> p.getNomeSobrenome().contains(valor);
            case 2 -> p.getTipoDeSexo().getSexo().equalsIgnoreCase(valor);
            case 3 -> Double.parseDouble(valor) == p.getIdade();
            case 4 -> Double.parseDouble(valor) == p.getPeso();
            case 5 -> p.getRaca().contains(valor);
            case 6 -> p.getEnderecoCompleto().toString().contains(valor);
            case 7 -> p.getTipoPet().getNomeTipoPet().equalsIgnoreCase(valor);
            default -> false;
        };
    }
}
