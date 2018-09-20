package fiap.com.br.coopfit.enums;

public enum TipoUsuario {
	
	PACIENTE(1, "Free"),
	DOUTOR(2, "Premium"),
	ADMINISTRADOR(3, "Administrador");
	
	private Integer codigo;
	private String descricao;
	
	private TipoUsuario(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoUsuario toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for(TipoUsuario tu : TipoUsuario.values()) {
			if (codigo.equals(tu.getCodigo())) {
				return tu;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + codigo);
	}

}
