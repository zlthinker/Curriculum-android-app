package hyuuhit.Curriculum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hyuuhit.Curriculum.DBHelper;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class Day extends Activity {
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";
    public static final String TABLE_NAME="event";
    public int d;
	public String day = null;
	public String c=null;
	public int c_no;
    
    
    private ExpandableListAdapter mAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);

//        Button btn=(Button)findViewById(R.id.btn);
//        btn.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				//TextView tv=(TextView)findViewById(R.id.text1);
//				//setTitle(tv.getText());
//			}
//        	
//        });
        
        ExpandableListView elv=(ExpandableListView)findViewById(R.id.elv);
        d=getIntent().getIntExtra("d", 0);
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
        
        TextView tv=(TextView)findViewById(R.id.day_tv);
        tv.setText(day);
        //setTitle(day);
        
        /*以上都只是设置课程表的Textview显示是周几*/
        
        DBHelper dbh=new DBHelper(this);  //定义一个处理数据库表的变量
        
        SQLiteDatabase db=dbh.getWritableDatabase();  //调用这个方法Android系统会自动生成一个数据库

        Cursor cursor;
        int a;
        
        
        // 创建一个List对象，用来存放列表项的每一行map信息
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            
            cursor=db.query("event", new String[]{"class_name","others"}, "day_no="+Integer.toString(d)+" and class_no="+Integer.toString(i), null, null, null, "class_name");
              //cursor是查询数据表的指针，此语句用于将查询到db这个数据库的内容放到一个Cursor中，包含7个参数
            //第一个参数为数据库中表的名字
            //第二个参数是我们想要返回的数据包含的信息
            //第三个参数是selection，要返回所有的数据则设为null
            //第七个参数是orderBy,用来描述我们期望的返回值的排序方式，若不需要排序则设为null
            
            a=cursor.getCount();   //a为课程的数目，a=0则没有课程，显示为（空）
            cursor.moveToFirst();  //将指针移到指向数据库一开始的地方
            Log.i("search", Integer.toString(i)+Integer.toString(a));
            
            switch(i){
            case 0:
            	if(a==0)
            		curGroupMap.put(NAME, getString(R.string.c0)+"("+getString(R.string.none)+")");  //curGroupMap是构造的一个Map对象，用put这个方法设置内容：第几节（空）
            	else
            		curGroupMap.put(NAME, getString(R.string.c0));
            	break;
            case 1:
            	if(a==0)
            		curGroupMap.put(NAME, getString(R.string.c1)+"("+getString(R.string.none)+")");
            	else
            		curGroupMap.put(NAME, getString(R.string.c1));
            	break;
            case 2:
            	if(a==0)
            		curGroupMap.put(NAME, getString(R.string.c2)+"("+getString(R.string.none)+")");
            	else
            		curGroupMap.put(NAME, getString(R.string.c2));
            	break;
            case 3:
            	if(a==0)
            		curGroupMap.put(NAME, getString(R.string.c3)+"("+getString(R.string.none)+")");
            	else
            		curGroupMap.put(NAME, getString(R.string.c3));
            	break;
            case 4:
            	if(a==0)
            		curGroupMap.put(NAME, getString(R.string.c4)+"("+getString(R.string.none)+")");
            	else
            		curGroupMap.put(NAME, getString(R.string.c4));
            	break;
            }
            //curGroupMap.put(NAME, "Group " + i);
//            if(a==0){
//            	curGroupMap.put(IS_EVEN, "无");
//            }
            //curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
            
            
            //建立第*节下面的课程的list
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < a; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, cursor.getString(0));  //设置课程的名称和附加信息
                curChildMap.put(IS_EVEN, cursor.getString(1));////////////////////////
                cursor.moveToNext();
                //curChildMap.put(NAME, "Child " + j);
                //curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
            }
            childData.add(children);
        }
        //for循环结束，共循环5次
        
        // Set up our adapter，参考http://www.2cto.com/kf/201402/281809.html
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 }
                );
        elv.setAdapter(mAdapter);
        this.registerForContextMenu(elv);
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		ExpandableListContextMenuInfo info=(ExpandableListContextMenuInfo)menuInfo;
		//menuinfo该对象提供了选中对象的附加信息
		int type=ExpandableListView.getPackedPositionType(info.packedPosition); //返回所选择项的类型（Child,Group）
		//响应
		if(type==ExpandableListView.PACKED_POSITION_TYPE_GROUP){  //选择group组

			//长按第几节列表项
			switch(ExpandableListView.getPackedPositionGroup(info.packedPosition)){ //用getPackedPositionGroup方法返回所选择的组
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
			menu.setHeaderTitle(day+"_"+c);  //设置menu的标题
			menu.add(0, 1, 0, R.string.group_insert);//跳出一个插入课程的框
			/*第一个int类型的group ID参数，代表的是组概念，你可以将几个菜单项归为一组，以便更好的以组的方式管理你的菜单按钮。
		             第二个int类型的item ID参数，代表的是项目编号。这个参数非常重要，一个item ID对应一个menu中的选项。在后面使用菜单的时候，就靠这个item ID来判断你使用的是哪个选项。
		             第三个int类型的order ID参数，代表的是菜单项的显示顺序。默认是0，表示菜单的显示顺序就是按照add的显示顺序来显示。
		             第四个String类型的title参数，表示选项中显示的文字。*/
		}
		
		else{  //长按课程列表项
			ExpandableListView elv= (ExpandableListView)findViewById(R.id.elv);
			String s=(String)((Map<String,String>)elv.getExpandableListAdapter().getChild(ExpandableListView.getPackedPositionGroup(info.packedPosition), ExpandableListView.getPackedPositionChild(info.packedPosition))).get(NAME);
			menu.setHeaderTitle(s);  //s作为字符型参数传递所选择的子项的名称，此处即课程名，并设为menu的标题
			menu.add(0, 2, 0, R.string.del);//跳出一个删除的框
			
		}
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item){//这个方法只在onCreateContextMenu 创建的菜单被选中时才会被触发
		switch(item.getItemId()){   //getItemId()方法获得的是上面新建menu时设置的Id参数
		case 1:
			c_no=ExpandableListView.getPackedPositionGroup(((ExpandableListContextMenuInfo)item.getMenuInfo()).packedPosition);
			Intent i=new Intent(Day.this,Insert.class);  //新建intent变量实现Activity的跳转
			i.putExtra("d", d);
			i.putExtra("c", c_no);
			startActivity(i);    //调用插入课程的Activity
			break;
		case 2:
			
			ExpandableListContextMenuInfo info=(ExpandableListContextMenuInfo)item.getMenuInfo();
			ExpandableListView elv= (ExpandableListView)findViewById(R.id.elv);
			String s=(String)((Map<String,String>)elv.getExpandableListAdapter().getChild(ExpandableListView.getPackedPositionGroup(info.packedPosition), ExpandableListView.getPackedPositionChild(info.packedPosition))).get(NAME);
			
			int l=ExpandableListView.getPackedPositionGroup(((ExpandableListContextMenuInfo)item.getMenuInfo()).packedPosition);
			DBHelper dbh=new DBHelper(this);
			SQLiteDatabase db=dbh.getWritableDatabase();
			
			try{
				db.execSQL("delete from event where day_no="+Integer.toString(d)+" and class_no="+Integer.toString(l)+" and class_name='"+s+"'"); //执行SQL语句删除课程
				Toast.makeText(this,R.string.del_suc, Toast.LENGTH_SHORT).show();
				/*Toast 是一个 View 视图，快速的为用户显示少量的信息。 Toast 在应用程序上浮动显示信息给用户，它永远不会获得焦点，不影响用户的输入等操作，主要用于 一些帮助 / 提示。
                Toast 最常见的创建方式是使用静态方法 Toast.makeText.此处使用的是默认的显示方式*/

			}
			catch(Exception e){
				Toast.makeText(this,R.string.del_fail, Toast.LENGTH_SHORT).show();//出现异常时显示删除失败
			}
			finally{
				
			}
			
			//elv.getExpandableListAdapter().notifyAll();
			
			return true;
		}
		
		return false;
		
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ //此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例
    	super.onCreateOptionsMenu(menu);//创建选项菜单(optionsMenu), 该菜单在点击 menu 按键 后会在对应的Activity底部显示出来
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);   //使用inflate方法来把布局文件中的定义的菜单 加载给menu对象,menu的布局可见menu.xml
    	return true;  //返回true则显示该menu,false 则不显示
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	//只要菜单中的菜单项被点击，都会触发onOptionsItemSelected(MenuItem item)
        //item参数即为被点击的菜单项，那么需要在此方法内判断哪个Item被点击了，从而实现不同的操作。
    	switch(item.getItemId()){
    	case R.id.help: //点击help菜单项
    		new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(R.string.help_text).setIcon(android.R.drawable.ic_menu_help).show();
    		//AlertDialog是Dialog的一个直接子类，AlertDialog也是Android系统当中最常用的对话框之一。
    		//一个AlertDialog可以有两个以上的Button，可以对一个AlertDialog设置相应的信息。比如title，massage，setSingleChoiceItems，setPositiveButton，setNegativeButton等等
    		break;    	
    	case R.id.about://点击about菜单项
    		new AlertDialog.Builder(this).setTitle(R.string.about).setMessage(R.string.about_text).setIcon(android.R.drawable.ic_menu_info_details).show();
    		break;
    	}
    	return false;
    }
    
}
