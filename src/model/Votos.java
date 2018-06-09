package model;

/**
 *
 * @author lw005973
 */
public class Votos {    
    private Candidatos cCandidato;
    private int voto;

    public Candidatos getcCandidato() {
        return cCandidato;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setcCandidato(Candidatos cCandidato) {
        this.cCandidato = cCandidato;
    }
    
}
