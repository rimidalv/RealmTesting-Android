package realm.rimidalv.com.realm;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by rimidalv on 11/01/15.
 */
public class Tag extends RealmObject {
    private String name;
    private Date date;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
//    private RealmList<Record> records;
}
