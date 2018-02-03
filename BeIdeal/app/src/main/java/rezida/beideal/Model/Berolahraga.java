package rezida.beideal.Model;

/**
 * Created by rezida on 17/01/2018.
 */

public class Berolahraga {
    private String berolahragaId;
    private String nama;
    private String jumlah;
    private String total;
    private String tgl;

    public Berolahraga() {
    }

    public Berolahraga(String berolahragaId, String nama, String jumlah, String total, String tgl) {
        this.berolahragaId = berolahragaId;
        this.nama = nama;
        this.jumlah = jumlah;
        this.total = total;
        this.tgl = tgl;
    }

    public String getBerolahragaId() {
        return berolahragaId;
    }

    public String getNama() {
        return nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getTotal() {
        return total;
    }

    public String getTgl() {
        return tgl;
    }
}
