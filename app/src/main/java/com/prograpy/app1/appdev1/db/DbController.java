package com.prograpy.app1.appdev1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;

public class DbController {

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;


    private static final String DATABASE_NAME = "Db_MoziMozi";
    private static final String DATABASE_TABLE = "Tbl_MyProduct";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    private static boolean isDbUpdate = false;

    private static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";

    private static String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE
                    + " ("
                    + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY"
                    +")";

    private static final String[] COLUMS = {
            KEY_PRODUCT_ID
    };


    public DbController(Context ctx) {
        this.mCtx = ctx;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion >= newVersion)
                return;
            isDbUpdate = true;
        }
    }


    private DbController open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();

        return this;
    }

    private void close() {
        mDb.close();
        mDbHelper.close();
    }

    /**
     * 상품 아이디 내부 디비 저장
     * @param context
     * @param p_id 저장할 아이디
     * @return
     */
    public static boolean addProductId(Context context, int p_id){
        boolean result = false;
        DbController dbController = new DbController(context);
        dbController.open();
        result = dbController.addProductId(p_id);
        dbController.close();
        dbController = null;
        return result;
    }

    /**
     * 모든 데이터 삭제
     * @param context
     */
    public static void deleteAll(Context context){
        DbController dbController = new DbController(context);
        dbController.open();
        dbController.deleteAll();
        dbController.close();
        dbController = null;
    }


    /**
     * 상품 아이디로 내부 디비 삭제
     * @param context
     * @param p_id 상품 아이디
     * @return 결과값
     */
    public static boolean deleteProductId(Context context, int p_id){
        boolean result = false;
        DbController dbController = new DbController(context);
        dbController.open();
        result = dbController.deleteProductId(p_id);
        dbController.close();
        dbController = null;
        return result;
    }

    /**
     * 중복체크
     * @param context
     * @param p_id 상품 아이디
     * @return 결과값
     */
    public static boolean isOverlapData(Context context, int p_id) {
        boolean result = true;
        DbController dbController = new DbController(context);
        dbController.open();
        result = dbController.checkOverlapList(p_id);

        dbController.close();
        dbController = null;

        return result;
    }

    private boolean checkOverlapList(int p_id) {
        String where =  KEY_PRODUCT_ID + "=" + p_id;

        boolean isOverlapPOI = false;

        Cursor mCursor = mDb.query(true, DATABASE_TABLE, COLUMS, where, null, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                isOverlapPOI = true;
            }
        }
        mCursor.close();
        return isOverlapPOI;
    }


    private boolean addProductId(int p_id){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PRODUCT_ID, p_id);

        return mDb.insert(DATABASE_TABLE, null, initialValues) > 0;
    }


    private boolean deleteAll(){
        return mDb.delete(DATABASE_TABLE, "", null) > 0;
    }

    private boolean deleteProductId(int p_id){
        String where =  KEY_PRODUCT_ID + "=" + p_id;

        return mDb.delete(DATABASE_TABLE, where, null) > 0;
    }
}
