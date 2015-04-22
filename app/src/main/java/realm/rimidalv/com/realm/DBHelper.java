package realm.rimidalv.com.realm;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

import io.realm.Realm;

/**
 * Created by rimidalv on 17/01/15.
 */
public class DBHelper {

    public static Realm getRealm(Context context) {
        String currentRecordPath = context.getCacheDir().getAbsolutePath();//StorageSettings.getInstance(context).getCurrentRecordPath();
        String fileName = "database.realm";
        File writeableFolder = new File(currentRecordPath);
        if (!writeableFolder.exists()) {
            writeableFolder.mkdirs();
        } else {
            if (!writeableFolder.isDirectory()) {
                writeableFolder.delete();
                writeableFolder.mkdirs();
            }
        }

        if (writeableFolder.exists() && writeableFolder.isDirectory()) {
            return Realm.getInstance(writeableFolder, fileName);
        } else {
            Toast.makeText(context, "Can't open database path: \'" + writeableFolder + "\'", Toast.LENGTH_LONG).show();
            return Realm.getInstance(context, fileName);
        }
    }
}
