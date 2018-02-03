package rezida.beideal.Model;

/**
 * Created by rezida on 15/01/2018.
 */

public class Progress {
    private String progressId;
    private String berat;
    private String tgl;

    public Progress() {
    }

    public Progress(String progressId, String berat, String tgl) {
        this.progressId = progressId;
        this.berat = berat;
        this.tgl = tgl;
    }

    public String getProgressId() {
        return progressId;
    }

    public String getBerat() {
        return berat;
    }

    public String getTgl() {
        return tgl;
    }
}
