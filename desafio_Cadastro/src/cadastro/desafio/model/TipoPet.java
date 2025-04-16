package cadastro.desafio.model;

public enum TipoPet {
    CACHORRO(1, "Cachorro"),
    GATO(2, "Gato");

    private int valor;
    private String nomeTipoPet;

    TipoPet(int valor, String nomeTipoPet) {
        this.valor = valor;
        this.nomeTipoPet = nomeTipoPet;
    }

    public static TipoPet tipoPetPorNome(String nomeTipoPet) {
        for (TipoPet TipoPet : values()) {
            if (TipoPet.getNomeTipoPet().equalsIgnoreCase(nomeTipoPet.trim())) {
                return TipoPet;

            }
        } return null;

    }

    public int getValor() {
        return valor;
    }

    public String getNomeTipoPet() {
        return nomeTipoPet;
    }

}



