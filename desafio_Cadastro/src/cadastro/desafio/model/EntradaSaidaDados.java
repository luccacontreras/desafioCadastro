package cadastro.desafio.model;

import java.io.*;

public class EntradaSaidaDados {


    public void incluirOpcoes() {
        try (FileWriter fw = new FileWriter("formulario.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("1 - Qual o nome e sobrenome do pet?\n" +
                        "\n" +
                        "2 - Qual o tipo do pet (Cachorro/Gato)?\n" +
                        "\n" +
                        "3 - Qual o sexo do animal?\n" +
                        "\n" +
                        "4 - Qual endereço e bairro que ele foi encontrado?\n" +
                        "\n" +
                        "5 - Qual a idade aproximada do pet?\n" +
                        "\n" +
                        "6 - Qual o peso aproximado do pet?\n" +
                        "\n" +
                        "7 - Qual a raça do pet?");
                bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lerOpcoes() {
        try (FileReader fr = new FileReader("formulario.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
