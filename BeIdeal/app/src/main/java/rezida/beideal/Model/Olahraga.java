package rezida.beideal.Model;

/**
 * Created by rezida on 16/01/2018.
 */

public class Olahraga {
    private String user_id;
    private String user_name;
    private String Waktu_id;
    private String jumlah_id;
    private String kalori_id;
    private String jk_id;

    public Olahraga() {
    }

    public Olahraga(String user_id, String user_name, String waktu_id, String jumlah_id, String kalori_id, String jk_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        Waktu_id = waktu_id;
        this.jumlah_id = jumlah_id;
        this.kalori_id = kalori_id;
        this.jk_id = jk_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getWaktu_id() {
        return Waktu_id;
    }

    public String getJumlah_id() {
        return jumlah_id;
    }

    public String getKalori_id() {
        return kalori_id;
    }

    public String getJk_id() {
        return jk_id;
    }
}
