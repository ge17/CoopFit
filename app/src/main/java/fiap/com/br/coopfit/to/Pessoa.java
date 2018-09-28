package fiap.com.br.coopfit.to;

import java.io.Serializable;
import java.util.Date;

import fiap.com.br.coopfit.enums.Genero;
import fiap.com.br.coopfit.enums.TipoUsuario;

public class Pessoa implements Serializable {

    private Long id;

    private String nome;

    private Date nascimento;

    private Genero genero;

    private byte[] foto;

    private String email;

    private String senha;

    private TipoUsuario tipo;

    private Date cadastro;

    private Date alteracao;

    private boolean notificacao;

    private double altura;

    private double peso;

    private String observacao;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Date getCadastro() {
        return cadastro;
    }

    public void setCadastro(Date cadastro) {
        this.cadastro = cadastro;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isNotificacao() {
        return notificacao;
    }

    public void setNotificacao(boolean notificacao) {
        this.notificacao = notificacao;
    }
}
