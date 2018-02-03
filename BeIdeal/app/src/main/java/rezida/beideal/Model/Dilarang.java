package rezida.beideal.Model;

/**
 * Created by rezida on 16/01/2018.
 */

public class Dilarang {
    private String user_id;
    private String user_name;
    private String jk_id;

    public Dilarang() {
    }

    public Dilarang(String user_id, String user_name, String jk_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.jk_id = jk_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getJk_id() {
        return jk_id;
    }
}
