package com.hog.newto.pf2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbGateway {
    public static DbGateway gw;
    private SQLiteDatabase db;

    private DbGateway(Context ctx){
        DatabaseHog helper = new DatabaseHog(ctx);
        db = helper.getWritableDatabase();
    }

    public static DbGateway getInstance(Context ctx){
        if(gw == null)
            gw = new DbGateway(ctx);
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}

