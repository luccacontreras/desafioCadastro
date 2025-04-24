package cadastro.desafio.model;

public enum TipoSexo {
    NAO_INFORMADO(0, "NÃO INFORMADO"),
    MACHO (1, "Macho"),
    FEMEA (1, "Femea");

    private int valor;
    private String sexo;

    TipoSexo(int valor, String sexo) {
        this.valor = valor;
        this.sexo = sexo;
    }

    public static TipoSexo TipoSexoPorNome (String sexo) {
        if (sexo == null || sexo.isEmpty()) {
            return NAO_INFORMADO;
        }
        for (TipoSexo TipoSexo : values()) {
            if (TipoSexo.getSexo().equalsIgnoreCase(sexo.trim())) {
                return TipoSexo;
            }
        } return NAO_INFORMADO;
    }

    public int getValor() {
        return valor;
    }

    public String getSexo() {
        return sexo;
    }
}
