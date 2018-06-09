package model;

import java.sql.Blob;

/**
 *
 * @author lw005973
 */
public class Candidatos {
    private int cCandidato;
    private int numCandidato;
    private String nomeCandidato;
    private Partidos cPartido;
    private Blob fotoCandidato;

    public int getcCandidato() {
        return cCandidato;
    }

    public void setcCandidato(int cCandidato) {
        this.cCandidato = cCandidato;
    }

    public int getNumCandidato() {
        return numCandidato;
    }

    public void setNumCandidato(int numCandidato) {
        this.numCandidato = numCandidato;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public Partidos getcPartido() {
        return cPartido;
    }

    public void setcPartido(Partidos cPartido) {
        this.cPartido = cPartido;
    }

    public Blob getFotoCandidato() {
        return fotoCandidato;
    }

    public void setFotoCandidato(Blob fotoCandidato) {
        this.fotoCandidato = fotoCandidato;
    }
    
}
