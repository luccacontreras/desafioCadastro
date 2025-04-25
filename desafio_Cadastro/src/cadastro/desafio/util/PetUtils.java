package cadastro.desafio.util;

import cadastro.desafio.model.Pet;
import cadastro.desafio.model.PetFileManager;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PetUtils {

    public static void listarPets(List<Pet> listaPets) {
        listaPets.sort(Comparator.comparing(Pet::getNomeSobrenome));
        for (int i = 0; i < listaPets.size(); i++) {
            Pet p = listaPets.get(i);
            System.out.print((i + 1) + " - ");
            System.out.println(p.getNomeSobrenome() + " - " +
                    p.getTipoPet().getNomeTipoPet() + " - " +
                    p.getTipoDeSexo().getSexo() + " - " +
                    p.getEnderecoCompleto().toString() + " - " +
                    p.getIdade() + " anos" + " - " +
                    p.getPeso() + "kg" + " - " +
                    p.getRaca());
        }
    }

    public static void excluirPet(Scanner sc, List<Pet> pets) throws IOException {
        PetUtils.listarPets(pets);
        int numero;
        while (true) {
            System.out.println("Digite o número do pet que deseja deletar: ");
            String line = sc.nextLine().trim();
            try {
                numero = Integer.parseInt(line);
                if (numero >= 1 && numero <= pets.size()) break;
                else System.out.println("Número inválido, digite entre 1 e " + pets.size());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, digite um número.");
            }
        }
        Pet alvo = pets.get(numero - 1);
        System.out.println("Confirma a exclusão de '" + alvo.getNomeSobrenome() + "'? (SIM/NÃO): ");
        if (sc.nextLine().trim().equalsIgnoreCase("SIM")) {
            pets.remove(alvo);
            PetFileManager.deletarPet(alvo);
            System.out.println("Pet deletado com sucesso!");
        } else {
            System.out.println("Exclusão cancelada! \\n");
        }
    }
}
