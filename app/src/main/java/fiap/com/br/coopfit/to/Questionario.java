package fiap.com.br.coopfit.to;

import java.util.Date;

import fiap.com.br.coopfit.enums.TipoSentimento;

public class Questionario {

    private Long id;

    private Integer qtdCopoAgua;

    private TipoSentimento tipoSentimento;

    private Integer estresse;
    private Date data;

    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtdCopoAgua() {
        return qtdCopoAgua;
    }

    public void setQtdCopoAgua(Integer qtdCopoAgua) {
        this.qtdCopoAgua = qtdCopoAgua;
    }

    public TipoSentimento getTipoSentimento() {
        return tipoSentimento;
    }

    public void setTipoSentimento(TipoSentimento tipoSentimento) {
        this.tipoSentimento = tipoSentimento;
    }

    public Integer getEstresse() {
        return estresse;
    }

    public void setEstresse(Integer estresse) {
        this.estresse = estresse;
    }
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
