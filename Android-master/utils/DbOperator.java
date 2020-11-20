package com.megain.nfctemp.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库管理类，具备增删改查操作。
 * *      增删改 --> 操作一个sql语句，并且有返回值。
 * 查询    --> 1. 返回一个游标类型
 * 2. 返回一个List<Object>
 * 3. 返回一个List<Map<String, Object>>
 * Created by wyb on 2016/5/10.
 */
public class DbOperator {
    protected static DbOperator instance = null;
    private SQLiteDatabase sqliteDatabase;
    DbHelper dbHelper;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    DbOperator(Context context, DbHelper dbHelper) {

        sqliteDatabase = dbHelper.getWritableDatabase();
        this.dbHelper = dbHelper;
    }

    /***
     * 获取本类对象实例
     *
     * @param context 上下文对象
     * @return
     */
    public static DbOperator getInstance(Context context, DbHelper dbHelper) {
        if (instance == null)
            instance = new DbOperator(context, dbHelper);
        return instance;
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (!isUse) {
            if (sqliteDatabase.isOpen()) {
                sqliteDatabase.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
            if (instance != null) {
                instance = null;
            }
            Lg.d("info", " close（）");
        }
    }

    /**
     * 按照SQL语句插入数据
     *
     * @param sql      执行更新操作的sql语句
     * @param bindArgs sql语句中的参数,参数的顺序对应占位符顺序
     * @return result      返回新添记录的行号，与主键id无关
     */
    public Long insertDataBySql(String sql, String[] bindArgs) {
        long result = 0;
        if (sqliteDatabase.isOpen()) {
            SQLiteStatement statement = sqliteDatabase.compileStatement(sql);
            if (bindArgs != null) {
                int size = bindArgs.length;
                for (int i = 0; i < size; i++) {
                    //将参数和占位符绑定，对应
                    statement.bindString(i + 1, bindArgs[i]);
                }
                result = statement.executeInsert();
                statement.close();
            }
        } else {
            Lg.d("info", "数据库已关闭");
        }
        return result;
    }


    /**
     * 按照表名和ContentValues通过insert插入数据
     *
     * @param table  表名
     * @param values 要插入的数据
     * @return result      返回新添记录的行号，与主键id无关
     */
    public Long insertData(String table, ContentValues values) {
        long result = 0;
        if (sqliteDatabase.isOpen()) {
            result = sqliteDatabase.insert(table, null, values);
        }
        return result;
    }

    /**
     * 通过SQL语句更新数据
     *
     * @param sql      执行更新操作的sql语句
     * @param bindArgs sql语句中的参数,参数的顺序对应占位符顺序
     */
    public void updateDataBySql(String sql, String[] bindArgs) {
        if (sqliteDatabase.isOpen()) {
            SQLiteStatement statement = sqliteDatabase.compileStatement(sql);
            if (bindArgs != null) {
                int size = bindArgs.length;
                for (int i = 0; i < size; i++) {
                    statement.bindString(i + 1, bindArgs[i]);
                }
                statement.execute();
                statement.close();
            }
        } else {
            Lg.d("info", "数据库已关闭");
        }
    }

    /**
     * 按照表名和ContentValues通过update更新数据
     *
     * @param table       表名
     * @param values      表示更新的数据
     * @param whereClause 表示SQL语句中条件部分的语句
     * @param whereArgs   表示占位符的值
     * @return
     */
    public int updataData(String table, ContentValues values, String whereClause, String[] whereArgs) {
        int result = 0;
        if (sqliteDatabase.isOpen()) {
            result = sqliteDatabase.update(table, values, whereClause, whereArgs);
        }
        return result;
    }

    public void deleteDataBySql(String sql) {
        try {
            if (sqliteDatabase.isOpen()) {
                sqliteDatabase.execSQL(sql);

            }
            Lg.i("info  deleteDataBySql  ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteDataByTable(String[] whereArgs) {
        int aaa = 199;
        sqliteDatabase = dbHelper.getWritableDatabase();
        try {
            if (sqliteDatabase.isOpen()) {
                // sqliteDatabase.execSQL(sql);
                aaa = sqliteDatabase.delete("History", "CardId" + " = ?", whereArgs);

            }
            Lg.i("info  deleteDataBySql----" + String.valueOf(aaa));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 按照SQL语句删除数据
     *
     * @param sql      执行更新操作的sql语句
     * @param bindArgs sql语句中的参数,参数的顺序对应占位符顺序
     */
    public void deleteDataBySql(String sql, String[] bindArgs) {
        if (sqliteDatabase.isOpen()) {
            //  isUse=true;
            SQLiteStatement statement = sqliteDatabase.compileStatement(sql);
            if (bindArgs != null) {
                int size = bindArgs.length;
                for (int i = 0; i < size; i++) {
                    statement.bindString(i + 1, bindArgs[i]);
                }
                Method[] mm = statement.getClass().getDeclaredMethods();
                for (Method method : mm) {
                    Log.i("info", method.getName());
                    /*
                       反射查看是否能获取executeUpdateDelete方法
                       查看源码可知 executeUpdateDelete是public的方法，但是好像被隐藏了所以不能被调用，
                           利用反射貌似只能在root以后的机器上才能调用，小米是可以，其他机器却不行，所以还是不能用。
                     */
                }
                statement.execute();
                statement.close();
                //  isUse=false;
            }
        } else {
            Lg.d("info", "数据库已关闭");
        }
    }

    /**
     * 按照表名和ContentValues通过delete 删除数据
     *
     * @param table       表名
     * @param whereClause 表示SQL语句中条件部分的语句
     * @param whereArgs   表示占位符的值
     * @return
     */
    public int deleteData(String table, String whereClause, String[] whereArgs) {
        int result = 0;
        if (sqliteDatabase.isOpen()) {
            result = sqliteDatabase.delete(table, whereClause, whereArgs);
        }
        return result;
    }

    /**
     * 通过SQL语句查询数据
     *
     * @param sql           执行查询操作的sql语句
     * @param selectionArgs 查询条件
     * @return 返回查询的游标，可对数据自行操作，需要自己关闭游标
     */
    public Cursor queryData2Cursor(String sql, String[] selectionArgs) {
        if (sqliteDatabase.isOpen()) {
            Cursor cursor = sqliteDatabase.rawQuery(sql, selectionArgs);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }
        return null;
    }

    /**
     * 查询数据
     *
     * @param sql           执行查询操作的sql语句
     * @param selectionArgs 查询条件
     * @param object        Object的对象
     * @return List<Object>       返回查询结果
     */
    public List<Object> queryData2Object(String sql, String[] selectionArgs, Object object) {
        List<Object> mList = new ArrayList<>();
        if (sqliteDatabase.isOpen()) {
            Cursor cursor = sqliteDatabase.rawQuery(sql, selectionArgs);
            Field[] f;
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    f = object.getClass().getDeclaredFields();
                    for (int i = 0; i < f.length; i++) {
                        //为JavaBean 设值
                        invokeSet(object, f[i].getName(), cursor.getString(cursor.getColumnIndex(f[i].getName())));
                    }
                    mList.add(object);
                }
            }
            cursor.close();
        } else {
            Lg.d("info", "数据库已关闭");
        }
        return mList;
    }

    /**
     * 查询数据
     *
     * @param sql           执行查询操作的sql语句
     * @param selectionArgs 查询条件
     * @param object        Object的对象
     * @return List<Map                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>>   返回查询结果
     * @throws Exception
     */
    public List<Map<String, Object>> queryData2Map(String sql, String[] selectionArgs, Object object) {
        List<Map<String, Object>> mList = new ArrayList<>();
        if (sqliteDatabase.isOpen()) {
            Cursor cursor = sqliteDatabase.rawQuery(sql, selectionArgs);
            Field[] f;
            Map<String, Object> map;
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    map = new HashMap<>();
                    f = object.getClass().getDeclaredFields();
                    for (int i = 0; i < f.length; i++) {
                        map.put(f[i].getName(), cursor.getString(cursor.getColumnIndex(f[i].getName())));
                    }
                    mList.add(map);
                }
            }
            cursor.close();
        } else {
            Lg.d("info", "数据库已关闭");
        }
        return mList;
    }

    /**
     * java反射bean的set方法
     *
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行set方法
     *
     * @param object    执行对象
     * @param fieldName 属性
     * @param value     值
     */
    public static void invokeSet(Object object, String fieldName, Object value) {
        Method method = getSetMethod(object.getClass(), fieldName);
        try {
            method.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUse = false;


}