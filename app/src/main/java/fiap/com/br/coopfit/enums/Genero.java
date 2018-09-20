package fiap.com.br.coopfit.enums;

public enum Genero {

    FEMININO(1, "Feminino"),
    MASCULINO(2, "Masculino");

    private Integer codigo;
    private String descricao;

    private Genero(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
    public static Genero toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for(Genero genero : Genero.values()) {
            if (codigo.equals(genero.getCodigo())) {
                return genero;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
