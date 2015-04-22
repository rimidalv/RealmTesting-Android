package realm.rimidalv.com.realm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {

                Realm realm = DBHelper.getRealm(MainActivity.this);

                realm.beginTransaction();
                RealmQuery<Tag> allRecords = realm.where(Tag.class);
                for (int i = 0; i < 10000; i++) {

                    String fileName = "name " + i;
                    allRecords = allRecords.notEqualTo("name", fileName);
                    Tag record = realm.where(Tag.class).equalTo("name", fileName).findFirst();
                    if (record == null) {
//                        record = new Tag();
//                        realm.copyToRealm(record);
                        Tag tag = realm.createObject(Tag.class);
                        tag.setName(fileName);
                        tag.setDate(new Date());

                    }
                    Log.d("TAG", "MainActivity run i : " + i);
                }
                allRecords.findAll().clear();

                realm.commitTransaction();
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                realm.close();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                File file  = new File(getCacheDir(), "file");
                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(file);
                    byte buf[] = new byte[]{1};
                    for (int i = 0; i < 1000000; i++) {
                        os.write(buf);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(os != null){
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
