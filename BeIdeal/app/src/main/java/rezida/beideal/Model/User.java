package rezida.beideal.Model;

/**
 * Created by rezida on 09/01/2018.
 */

public class User {
    String userId;
    String nama;
    String tglLahir;
    String berat;
    String tinggi;
    String jk;
    String goldar;
    String Username;
    String password;

    public User() {
    }

    public User(String userId, String nama, String tglLahir, String berat, String tinggi, String jk, String goldar, String username, String password) {
        this.userId = userId;
        this.nama = nama;
        this.tglLahir = tglLahir;
        this.berat = berat;
        this.tinggi = tinggi;
        this.jk = jk;
        this.goldar = goldar;
        Username = username;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getNama() {
        return nama;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public String getBerat() {
        return berat;
    }

    public String getTinggi() {
        return tinggi;
    }

    public String getJk() {
        return jk;
    }

    public String getGoldar() {
        return goldar;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }
}
