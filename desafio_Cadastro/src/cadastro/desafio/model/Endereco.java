package cadastro.desafio.model;

public class Endereco {
    private String rua;
    private int numero;
    private String cidade;

    public Endereco(String rua, int numero, String cidade) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
    }

    public static final Endereco ENDERECO_NAO_INFORMADO = new Endereco(Pet.DADO_NAO_INFORMADO, -1, Pet.DADO_NAO_INFORMADO);

    @Override
    public String toString() {

        String numeroStr = (numero <= 0) ? Pet.DADO_NAO_INFORMADO : String.valueOf(numero);
        String ruaStr = (rua == null || rua.trim().isEmpty()) ? Pet.DADO_NAO_INFORMADO : rua;
        String cidadeStr = (cidade == null || cidade.trim().isEmpty()) ? Pet.DADO_NAO_INFORMADO : cidade;

        return ruaStr + ", " + numeroStr + ", " + cidadeStr;
    }

    public String getCidade() {
        return cidade;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

}
