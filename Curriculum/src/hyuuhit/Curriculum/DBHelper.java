package hyuuhit.Curriculum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//SQliteOpenHelper��һ�������࣬���������ݿ�Ĵ����Ͱ汾�Ĺ���
public class DBHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="events.db";
	private static final int DATABASE_VERSION=1;  //�������ݿ�汾
	
	public DBHelper(Context ctx){  //���ݿ�ļ̳кͶ���
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {  //���ݿ��һ�δ���ʱ�����
		// TODO Auto-generated method stub
		arg0.execSQL("create table event (day_no integer not null,class_no integer not null,class_name text not null,others text,primary key (day_no,class_no,class_name))");
		Log.i("database", "create event");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) { //���ݿ��ļ��汾�ŷ����仯ʱ���� 
		// TODO Auto-generated method stub
		
	}
	
}
