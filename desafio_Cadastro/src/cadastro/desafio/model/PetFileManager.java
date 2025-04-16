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

public class PetFileManager extends Pet {
    private static final String DIRETORIO_BASE = "C:\\Users\\lucca\\OneDrive\\Área de Trabalho\\desafioCadastro\\desafio_Cadastro\\src\\cadastro\\desafio\\petsCadastrados";

    public static void salvarPet (Pet pet) throws IOException {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String dataHora = agora.format(formatterData);

        Path diretorio = Paths.get(DIRETORIO_BASE);
        if (!Files.exists(diretorio)) {
            Files.createDirectory(diretorio);
        }

        String nomeArquivo = dataHora + "-" + pet.getNomeSobrenome().toUpperCase().replace(" ", "");
        String caminhoCompleto = DIRETORIO_BASE + "\\" + nomeArquivo;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoCompleto))) {
            writer.write(pet.toString());
        }

    }
}
