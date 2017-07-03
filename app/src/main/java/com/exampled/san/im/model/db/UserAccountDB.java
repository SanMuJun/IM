
package com.exampled.san.im.model.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.exampled.san.im.model.dao.UserAccoutTable;

/**
 * Created by San on 2016/11/9.
 */

public class UserAccountDB extends SQLiteOpenHelper {
    // 构造
    public UserAccountDB(Context context) {
        super(context, "account.db", null, 2);
    }

    // 数据库创建的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库表的语句
        db.execSQL(UserAccoutTable.CREATE_TAB);
    }

    // 数据库更新的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
