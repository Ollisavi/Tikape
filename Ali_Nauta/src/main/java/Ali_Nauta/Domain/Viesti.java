package Ali_Nauta.Domain;

import java.sql.Timestamp;


public class Viesti {

    private int id;
    private int ketjuId;
    private String viesti;
    private String nimim;
    private String aika;

    public Viesti(int id, int ketjuId, String viesti, String aika) {
        this.id = id;
        this.ketjuId = ketjuId;
        this.viesti = viesti;
        this.aika = aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public void setNimim(String nimim) {
        this.nimim = nimim;
    }


    public String getAika() {
        return aika;
    }

    public int getKetjuId() {
        return ketjuId;
    }

    public String getViesti() {
        return viesti;
    }

    public String getNimim() {
        return nimim;
    }
}
