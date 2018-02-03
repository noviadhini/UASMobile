package rezida.beideal.Model;

/**
 * Created by rezida on 16/01/2018.
 */

public class Asupan {
    private String asupanId;
    private String nama;
    private String jumlah;
    private String total;
    private String tgl;

    public Asupan() {
    }

    public Asupan(String asupanId, String nama, String jumlah, String total, String tgl) {
        this.asupanId = asupanId;
        this.nama = nama;
        this.jumlah = jumlah;
        this.total = total;
        this.tgl = tgl;
    }

    public String getAsupanId() {
        return asupanId;
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
