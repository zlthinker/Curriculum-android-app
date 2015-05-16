package hyuuhit.Curriculum;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import hyuuhit.Curriculum.DBHelper;

public class Insert extends Activity {
    /** Called when the activity is first created. */
	public int d;
	public int c_no;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        d=getIntent().getIntExtra("d", 0);
        c_no=getIntent().getIntExtra("c", 0); //d和c用来承接intent传递的参数：周几和第几节
        String day=null;
        String c=null;
        
        switch(d){
		case 0:
			day=getString(R.string.mon);
			break;
		case 1:
			day=getString(R.string.tue);
			break;
		case 2:
			day=getString(R.string.wed);
			break;
		case 3:
			day=getString(R.string.thu);
			break;
		case 4:
			day=getString(R.string.fri);
			break;
		case 5:
			day=getString(R.string.sat);
			break;
		case 6:
			day=getString(R.string.sun);
			break;
		}
        
        switch(c_no){
		case 0:
			c=getString(R.string.c0);
			break;
		case 1:
			c=getString(R.string.c1);
			break;
		case 2:
			c=getString(R.string.c2);
			break;
		case 3:
			c=getString(R.string.c3);
			break;
		case 4:
			c=getString(R.string.c4);
			break;
		}
        setTitle(day+"_"+c);
        
        final EditText et1=(EditText)findViewById(R.id.new_class);
        final EditText et2=(EditText)findViewById(R.id.new_others);
        
        Button ok_b=(Button)findViewById(R.id.insert_ok);
        ok_b.setOnClickListener(new OnClickListener(){       //监听"确认"这个按键

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(et1.length()!=0)       //如果输入不为空
				{
				DBHelper dbh=new DBHelper(Insert.this);
				SQLiteDatabase db=dbh.getWritableDatabase();
				try{
					db.execSQL("insert into event (day_no,class_no,class_name,others) values ('"+Integer.toString(d)+"','"+Integer.toString(c_no)+"','"+(et1.getText())+"','"+(et2.getText())+"')");
					Toast.makeText(Insert.this,R.string.insert_suc, Toast.LENGTH_SHORT).show();
					//添加输入的内容进数据库，并用Toast.makeText方法弹出显示提示用户
				}
				catch(SQLException e){
					Toast.makeText(Insert.this,R.string.insert_fail, Toast.LENGTH_SHORT).show();
				}
				finally{
					
				}
				finish();
				}
				else      //如果输入为空，用Toast.makeText方法弹出提示
				{
					Toast.makeText(Insert.this,R.string.insert_none, Toast.LENGTH_SHORT).show();
				}
			}
        	
        });
        
        Button cancel_b=(Button)findViewById(R.id.insert_cancel);
        cancel_b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();       //点击“取消”按键则直接结束activity
			}
        	
        });
    }
}