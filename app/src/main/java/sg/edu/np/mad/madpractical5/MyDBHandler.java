package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myusers.db";
    private static final String USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        Log.i("Database Operations", "Creating a Table.");
        try {
            String CREATE_USERS_TABLE = "CREATE TABLE " + USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT," +  COLUMN_FOLLOWED + " TEXT" + ")" ;
            db.execSQL(CREATE_USERS_TABLE);

            Log.i("Database Operations", "Table created successfully.");
//          db.close();
        } catch (SQLiteException e) {
            Log.i("Database Operations", "Error creating table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    //  Add a user record
    public void addUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
//      Don't need this. ID is auto create and incremented by SQLite
//      values.put(COLUMN_ID, String.valueOf(user.getID()));
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.getFollowed());
        db.insert(USERS, null, values);
//      db.close();
    }

    //  Get 1 user record based on user name.
    public List<User> getUsers() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + USERS;
        Cursor cursor = db.rawQuery(query, null);

        List<User> userList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt((int)cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString((int)cursor.getColumnIndex(COLUMN_NAME));
                String description = cursor.getString((int)cursor.getColumnIndex(COLUMN_DESCRIPTION));
                boolean followed = cursor.getInt((int)cursor.getColumnIndex(COLUMN_FOLLOWED)) == 1;
                User user = new User(id, name, description, followed);
                userList.add(user);
            } while (cursor.moveToNext());
        }

//      db.close();
        cursor.close();
        return userList;
    }

    //  UPDATE user record
    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, user.getID());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.getFollowed());
        String clause = "id=?";
        String[] args = {String.valueOf(user.getID())};
        db.update(USERS, values, clause, args);
//      db.close();
    }

    @Override
    public void close() {
        Log.i("Database Operations", "Database is closed.");
        super.close();
    }
}

