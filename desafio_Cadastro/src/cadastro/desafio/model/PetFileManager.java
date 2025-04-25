package cadastro.desafio.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PetFileManager {
    private static final String DIRETORIO_BASE = "C:\\Users\\lucca\\OneDrive\\Área de Trabalho\\desafioCadastro\\desafio_Cadastro\\src\\cadastro\\desafio\\petsCadastrados";


    public static void salvarPet(Pet pet) throws IOException {
        Path diretorio = Paths.get(DIRETORIO_BASE);
        if (!Files.exists(diretorio)) {
            Files.createDirectory(diretorio);
        }

        String nomeArquivo;
        if (pet.getNomeArquivo() != null && !pet.getNomeArquivo().isEmpty()) {
            nomeArquivo = pet.getNomeArquivo();
        } else {
            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
            String dataHora = agora.format(formatterData);
            nomeArquivo = dataHora + "-" + pet.getNomeSobrenome().toUpperCase().replace(" ", "");
            pet.setNomeArquivo(nomeArquivo);
        }

        String caminhoCompleto = DIRETORIO_BASE + "\\" + nomeArquivo;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoCompleto))) {
            writer.write(pet.toString());
        }
    }

    public static List<Pet> carregarTodosPets() throws IOException {
        List<Pet> pets = new ArrayList<>();
        File pasta = new File(DIRETORIO_BASE);
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File f : arquivos) {
                List<String> linhas = Files.readAllLines(f.toPath());
                linhas.removeIf((String::isEmpty));
                if (linhas.size() == 7) {
                    Pet p = converterLinhasEmPet(linhas);
                    p.setNomeArquivo(f.getName());
                    pets.add(p);
                }
            }
        }
        return pets;
    }

    private static Pet converterLinhasEmPet(List<String> linhas) {
        // 1. Nome e Sobrenome
        String nomeSobrenome = linhas.get(0).split(" - ", 2)[1];

        // 2. Tipo de Pet
        String tipoStr = linhas.get(1).split(" - ", 2)[1];
        TipoPet tipo = TipoPet.tipoPetPorNome(tipoStr);

        // 3. Sexo do Pet
        String sexoStr = linhas.get(2).split(" - ", 2)[1];
        TipoSexo sexo = TipoSexo.TipoSexoPorNome(sexoStr);

        // 4. Endereço
        String enderecoStr = linhas.get(3).split(" - ", 2)[1];
        String[] endPartes = enderecoStr.split("\\s*,\\s*");
        String rua = endPartes[0];
        int numero;
        try {
            numero = Integer.parseInt(endPartes[1]);
        } catch (NumberFormatException e) {
            numero = -1;
        }
        String cidade = endPartes.length > 2 ? endPartes[2] : Pet.DADO_NAO_INFORMADO;
        Endereco endereco = new Endereco(rua, numero, cidade);

        // 5. Idade
        String idadeStr = linhas.get(4).split(" - ", 2)[1].replace(" anos", "");
        double idade;
        try {
            idade = Double.parseDouble(idadeStr);
        } catch (NumberFormatException e) {
            idade = -1;
        }

        // 6. Peso
        String pesoStr = linhas.get(5).split(" - ", 2)[1].replace("kg", "");
        double peso;
        try {
            peso = Double.parseDouble(pesoStr);
        } catch (NumberFormatException e) {
            peso = -1;
        }

        // 7. Raça
        String raca = linhas.get(6).split(" - ", 2)[1];

        // Monta o Pet
        Pet p = new Pet();
        p.setNomeSobrenome(nomeSobrenome);
        p.setTipoPet(tipo);
        p.setTipoDeSexo(sexo);
        p.setEnderecoCompleto(endereco);
        p.setIdade(idade);
        p.setPeso(peso);
        p.setRaca(raca);

        return p;
    }

    public static void deletarPet(Pet pet) throws IOException {
        Path caminho = Paths.get(DIRETORIO_BASE, pet.getNomeArquivo());
        if (Files.exists(caminho)) {
            Files.delete(caminho);
            System.out.println("Pet " + pet.getNomeArquivo() + " deletado com sucesso!");
        } else {
            System.out.println("Pet não encontrado!");
        }

    }


}
