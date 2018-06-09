package model;

/**
 *
 * @author lw005973
 */
public class Eleitores {
    private int cEleitores;
    private String nomeLeitor;
    //private Char? cpf;
    private String senha;

    public int getcEleitores() {
        return cEleitores;
    }

    public void setcEleitores(int cEleitores) {
        this.cEleitores = cEleitores;
    }

    public String getNomeLeitor() {
        return nomeLeitor;
    }

    public void setNomeLeitor(String nomeLeitor) {
        this.nomeLeitor = nomeLeitor;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
