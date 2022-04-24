package gov.dane.sipsa.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import gov.dane.sipsa.dao.DaoMaster;

public class DatabaseOpenHelper extends DaoMaster.OpenHelper {

    private Context context;

    private SQLiteDatabase sqliteDatabase;

    private static String DB_PATH;

    private static String DB_NAME;

    public DatabaseOpenHelper(Context context, String path, String name, CursorFactory factory) {
        super(context, name, factory);
        this.context = context;
        this.DB_NAME = name;
        this.DB_PATH = path;
        try {
            createDataBase();
        } catch (Exception ioe) {
            throw new Error("Unable to create database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }




    /** Open Database for Use */
    public void openDatabase() {
        String databasePath = DB_PATH + DB_NAME;
        sqliteDatabase = SQLiteDatabase.openDatabase(databasePath, null,
                (SQLiteDatabase.OPEN_READWRITE));
    }

    /** Close Database after use */
    @Override
    public synchronized void close() {
        if ((sqliteDatabase != null) && sqliteDatabase.isOpen()) {
            sqliteDatabase.close();
        }
        super.close();
    }

    /** Get database instance for use */
    public SQLiteDatabase getSqliteDatabase() {
        return sqliteDatabase;
    }

    /** Create new database if not present */
    public void createDataBase() {
//        sqliteDatabase = null;

        File path = new File(DB_PATH, DB_NAME);
        path.getParentFile().mkdirs();
        sqliteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
       // sqliteDatabase = this.getReadableDatabase();
        // sqliteDatabase.close();
/*
        if (databaseExists()) {
        } else {
            File path = new File(DB_PATH, DB_NAME);
            path.getParentFile().mkdirs();
            sqliteDatabase = SQLiteDatabase.openOrCreateDatabase(path, null);
            sqliteDatabase = this.getReadableDatabase();
            sqliteDatabase.close();
            copyDataBase();
        }
*/
    }


    public SQLiteDatabase getDatabase() {
        return sqliteDatabase;
    }


    /** Check Database if it exists */
    private boolean databaseExists() {
//        SQLiteDatabase sqliteDatabase = null;
        try {
            String databasePath = DB_PATH + "/" + DB_NAME;
            sqliteDatabase = SQLiteDatabase.openDatabase(databasePath, null,SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (sqliteDatabase != null) {
            sqliteDatabase.close();
        }
        return sqliteDatabase != null ? true : false;
    }

    /**
     * Copy existing database file in system
     */
    public void copyDataBase() {

        int length;
        byte[] buffer = new byte[1024];
        String databasePath = DB_PATH + DB_NAME;

        try {
            InputStream databaseInputFile = this.context.getAssets().open(DB_NAME+".sqlite");
            OutputStream databaseOutputFile = new FileOutputStream(databasePath);

            while ((length = databaseInputFile.read(buffer)) > 0) {
                databaseOutputFile.write(buffer, 0, length);
                databaseOutputFile.flush();
            }
            databaseInputFile.close();
            databaseOutputFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}