package cadastro.desafio.model;

import cadastro.desafio.exceptions.IdadeException;
import cadastro.desafio.exceptions.NomeSobrenomeException;
import cadastro.desafio.exceptions.PesoException;

public class Pet {
    private String nomeSobrenome;
    private TipoPet tipoDePet;
    private TipoSexo tipoDeSexo;
    private Endereco enderecoCompleto;
    private double idade;
    private double peso;
    private String raca;
    private String nomeArquivo;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
    public String toString() {
        String idadeStr = (idade <= VALOR_INVALIDO) ? DADO_NAO_INFORMADO : (idade % 1 == 0 ? String.format("%.0f", idade) : String.format("%.1f", idade + " anos"));
        String pesoStr = (peso <= VALOR_INVALIDO) ? DADO_NAO_INFORMADO : (peso % 1 == 0 ? String.format("%.0f", peso) : String.format("%.1f", peso + "kg"));
        String enderecoStr = (enderecoCompleto == null) ? DADO_NAO_INFORMADO + ", " + DADO_NAO_INFORMADO + ", " + DADO_NAO_INFORMADO : enderecoCompleto.toString();
        return "1 - " + nomeSobrenome + "\n" +
                "2 - " + tipoDePet.getNomeTipoPet() + "\n" +
                "3 - " + tipoDeSexo.getSexo() + "\n" +
                "4 - " + enderecoStr + "\n" +
                "5 - " + idadeStr + "\n" +
                "6 - " + pesoStr + "\n" +
                "7 - " + raca;
    }

    public Pet() {
    }

    public String getNomeSobrenome() {
        return nomeSobrenome;
    }


    public TipoPet getTipoPet() {
        return tipoDePet;
    }

    public TipoSexo getTipoDeSexo() {
        return tipoDeSexo;
    }

    public Endereco getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public double getIdade() {
        return idade;
    }

    public double getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setNomeSobrenome(String nomeSobrenome) {
        String[] partes = nomeSobrenome.trim().split("\\s+", 2);
        if (nomeSobrenome.isEmpty()) {
            this.nomeSobrenome = DADO_NAO_INFORMADO;
        } else if (partes.length < 2) {
            throw new NomeSobrenomeException("Formato inválido, digite nome e sobrenome do seu pet");
        } else if (!nomeSobrenome.matches("^[\\p{L}\\s]+$")) {
            throw new NomeSobrenomeException("O nome completo NÃO poderá conter caracteres especiais, somente letras de A-Z");
        } else {
            this.nomeSobrenome = nomeSobrenome;
        }
    }

    public void setTipoPet(TipoPet tipoDePet) {
        this.tipoDePet = tipoDePet;
    }

    public void setTipoDeSexo(TipoSexo tipoDeSexo) {
        this.tipoDeSexo = tipoDeSexo;
    }

    public void setEnderecoCompleto(Endereco enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public void setIdade(double idade) {
        if (idade <= 0) {
            this.idade = -1;
        } else if (idade > 20) {
            throw new IdadeException("Idade inválida, por favor revise o valor informado!");
        } else {
            this.idade = idade;
        }
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            this.peso = -1;
        } else if (peso > 60 || peso < 0.5) {
            throw new PesoException("Peso inválido, por favor revise o valor informado!");
        } else {
            this.peso = peso;
        }
    }

    public void setRaca(String raca) {
        if (raca == null || raca.isEmpty()) {
            this.raca = DADO_NAO_INFORMADO;
        } else if (!raca.matches("^[\\p{L}\\s]+$")) {
            throw new IllegalArgumentException("Raça inválida! Use apenas letras, não são permitidos números ou caracteres especiais");
        } else {
            this.raca = raca;
        }
    }

    public static final String DADO_NAO_INFORMADO = "NÃO INFORMADO";
    private static final double VALOR_INVALIDO = -1.0;
}
