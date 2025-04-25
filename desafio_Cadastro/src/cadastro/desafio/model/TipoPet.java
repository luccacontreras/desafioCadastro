package cadastro.desafio.model;

public enum TipoPet {
    NAO_INFORMADO(0, "NÃO INFORMADO"),
    CACHORRO(1, "Cachorro"),
    GATO(2, "Gato");

    private int valor;
    private String nomeTipoPet;

    TipoPet(int valor, String nomeTipoPet) {
        this.valor = valor;
        this.nomeTipoPet = nomeTipoPet;
    }

    public static TipoPet tipoPetPorNome(String nomeTipoPet) {
        if (nomeTipoPet == null || nomeTipoPet.isEmpty()) {
            return NAO_INFORMADO;
        }
        for (TipoPet TipoPet : values()) {
            if (TipoPet.getNomeTipoPet().equalsIgnoreCase(nomeTipoPet.trim())) {
                return TipoPet;
            }
        }
        return NAO_INFORMADO;

    }

    public int getValor() {
        return valor;
    }

    public String getNomeTipoPet() {
        return nomeTipoPet;
    }

}



