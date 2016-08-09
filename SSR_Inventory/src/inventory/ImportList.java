package inventory;

import javafx.scene.control.ListCell;

/**
 * Created by SSR on 8/9/2016.
 */
public class ImportList extends ListCell<ImportList>{
    String date,title,source;
    boolean verified;
    int total;

    public ImportList(){this("","","",false,0);}

    public ImportList(String date, String title, String source, boolean verified, int total) {
        this.date = date;
        this.title = title;
        this.source = source;
        this.verified = verified;
        this.total = total;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
