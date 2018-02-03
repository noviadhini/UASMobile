package rezida.beideal.Model;

/**
 * Created by rezida on 16/01/2018.
 */

public class Makanan {
    private String user_id;
    private String user_name;
    private String kalori_id;

    public Makanan() {
    }

    public Makanan(String user_id, String user_name, String kalori_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.kalori_id = kalori_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getKalori_id() {
        return kalori_id;
    }
}
