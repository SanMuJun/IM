
package com.exampled.san.im.model;

import android.content.Context;

import com.exampled.san.im.model.bean.UserInfo;
import com.exampled.san.im.model.dao.UserAccountDao;
import com.exampled.san.im.model.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by San on 2016/11/8.
 */
//数据模型层全局类
public class Model {

    public  DBManager dbManager;
    public  UserAccountDao userAccountDao;
    private Context mContext;
    private ExecutorService executors= Executors.newCachedThreadPool();

    //创建对象
    private static Model model=new Model();

    //私有化构造器
    private Model(){
    }

    //获取单例对象
    public static Model getInstance(){
        return model;
    }

    //初始化的方法
    public void init(Context context){
        mContext = context;
        //创建用户账号的数据库的操作对象
         userAccountDao = new UserAccountDao(mContext);
        //开启全局监听
        EventListener eventListener = new EventListener(mContext);
    }
    //获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executors;
    }
    //用户登陆成功后的处理
    public void loginSuccess(UserInfo account) {
         //检验
        if(account==null){
            return;
        }
        if (dbManager!=null){
            dbManager.close();
        }
         dbManager = new DBManager(mContext,account.getName() );
    }
    public DBManager getDbManager(){
        return dbManager;
    }
    //获取用户账号的数据库的操作对象
    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
}
