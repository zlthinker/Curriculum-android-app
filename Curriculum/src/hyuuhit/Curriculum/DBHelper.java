package hyuuhit.Curriculum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//SQliteOpenHelper是一个抽象类，来管理数据库的创建和版本的管理
public class DBHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="events.db";
	private static final int DATABASE_VERSION=1;  //定义数据库版本
	
	public DBHelper(Context ctx){  //数据库的继承和定义
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {  //数据库第一次创建时候调用
		// TODO Auto-generated method stub
		arg0.execSQL("create table event (day_no integer not null,class_no integer not null,class_name text not null,others text,primary key (day_no,class_no,class_name))");
		Log.i("database", "create event");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) { //数据库文件版本号发生变化时调用 
		// TODO Auto-generated method stub
		
	}
	
}
