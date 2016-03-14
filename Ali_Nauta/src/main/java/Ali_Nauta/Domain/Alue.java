package Ali_Nauta.Domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 3.3.2016.
 */
public class Alue {

    private int id;
    private String otsikko;
    private int viestienMaara;
    private String viimViestinAika;

    public Alue(int id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
    }

    public int getId() {
        return id;
    }

    public int getViestienMaara() {
        return viestienMaara;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getViimViestinAika() {
        return viimViestinAika;
    }

    public void setViestienMaara(int viestienMaara) {
        this.viestienMaara = viestienMaara;
    }

    public void setViimViestinAika(String viimViestinAika) {
        this.viimViestinAika = viimViestinAika;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
}
