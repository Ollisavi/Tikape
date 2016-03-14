package Ali_Nauta.Domain;

/**
 * Created by jan on 3.3.2016.
 */
public class ViestiKetju {
    private int id;
    private int alueId;
    private String otsikko;
    private String viimeisinViesti;
    private int viestienMaara;

    public ViestiKetju(int id, int alueId, String otsikko) {
        this.id = id;
        this.alueId = alueId;
        this.otsikko = otsikko;
    }

    public void setAlueId(int alueId) {
        this.alueId = alueId;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setViestienMaara(int viestienMaara) {
        this.viestienMaara = viestienMaara;
    }

    public void setViimeisinViesti(String viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public Integer getAlueId() {
        return alueId;
    }

    public int getId() {
        return id;
    }

    public int getViestienMaara() {
        return viestienMaara;
    }

    public String getViimeisinViesti() {
        return viimeisinViesti;
    }
}
