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
        
        /*���϶�ֻ�����ÿγ̱��Textview��ʾ���ܼ�*/
        
        DBHelper dbh=new DBHelper(this);  //����һ���������ݿ��ı���
        
        SQLiteDatabase db=dbh.getWritableDatabase();  //�����������Androidϵͳ���Զ�����һ�����ݿ�

        Cursor cursor;
        int a;
        
        
        // ����һ��List������������б����ÿһ��map��Ϣ
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            
            cursor=db.query("event", new String[]{"class_name","others"}, "day_no="+Integer.toString(d)+" and class_no="+Integer.toString(i), null, null, null, "class_name");
              //cursor�ǲ�ѯ���ݱ��ָ�룬��������ڽ���ѯ��db������ݿ�����ݷŵ�һ��Cursor�У�����7������
            //��һ������Ϊ���ݿ��б������
            //�ڶ���������������Ҫ���ص����ݰ�������Ϣ
            //������������selection��Ҫ�������е���������Ϊnull
            //���߸�������orderBy,�����������������ķ���ֵ������ʽ��������Ҫ��������Ϊnull
            
            a=cursor.getCount();   //aΪ�γ̵���Ŀ��a=0��û�пγ̣���ʾΪ���գ�
            cursor.moveToFirst();  //��ָ���Ƶ�ָ�����ݿ�һ��ʼ�ĵط�
            Log.i("search", Integer.toString(i)+Integer.toString(a));
            
            switch(i){
            case 0:
            	if(a==0)
            		curGroupMap.put(NAME, getString(R.string.c0)+"("+getString(R.string.none)+")");  //curGroupMap�ǹ����һ��Map������put��������������ݣ��ڼ��ڣ��գ�
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
//            	curGroupMap.put(IS_EVEN, "��");
//            }
            //curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even" : "This group is odd");
            
            
            //������*������Ŀγ̵�list
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < a; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, cursor.getString(0));  //���ÿγ̵����ƺ͸�����Ϣ
                curChildMap.put(IS_EVEN, cursor.getString(1));////////////////////////
                cursor.moveToNext();
                //curChildMap.put(NAME, "Child " + j);
                //curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even" : "This child is odd");
            }
            childData.add(children);
        }
        //forѭ����������ѭ��5��
        
        // Set up our adapter���ο�http://www.2cto.com/kf/201402/281809.html
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
		//menuinfo�ö����ṩ��ѡ�ж���ĸ�����Ϣ
		int type=ExpandableListView.getPackedPositionType(info.packedPosition); //������ѡ��������ͣ�Child,Group��
		//��Ӧ
		if(type==ExpandableListView.PACKED_POSITION_TYPE_GROUP){  //ѡ��group��

			//�����ڼ����б���
			switch(ExpandableListView.getPackedPositionGroup(info.packedPosition)){ //��getPackedPositionGroup����������ѡ�����
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
			menu.setHeaderTitle(day+"_"+c);  //����menu�ı���
			menu.add(0, 1, 0, R.string.group_insert);//����һ������γ̵Ŀ�
			/*��һ��int���͵�group ID���������������������Խ������˵����Ϊһ�飬�Ա���õ�����ķ�ʽ������Ĳ˵���ť��
		             �ڶ���int���͵�item ID���������������Ŀ��š���������ǳ���Ҫ��һ��item ID��Ӧһ��menu�е�ѡ��ں���ʹ�ò˵���ʱ�򣬾Ϳ����item ID���ж���ʹ�õ����ĸ�ѡ�
		             ������int���͵�order ID������������ǲ˵������ʾ˳��Ĭ����0����ʾ�˵�����ʾ˳����ǰ���add����ʾ˳������ʾ��
		             ���ĸ�String���͵�title��������ʾѡ������ʾ�����֡�*/
		}
		
		else{  //�����γ��б���
			ExpandableListView elv= (ExpandableListView)findViewById(R.id.elv);
			String s=(String)((Map<String,String>)elv.getExpandableListAdapter().getChild(ExpandableListView.getPackedPositionGroup(info.packedPosition), ExpandableListView.getPackedPositionChild(info.packedPosition))).get(NAME);
			menu.setHeaderTitle(s);  //s��Ϊ�ַ��Ͳ���������ѡ�����������ƣ��˴����γ���������Ϊmenu�ı���
			menu.add(0, 2, 0, R.string.del);//����һ��ɾ���Ŀ�
			
		}
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item){//�������ֻ��onCreateContextMenu �����Ĳ˵���ѡ��ʱ�Żᱻ����
		switch(item.getItemId()){   //getItemId()������õ��������½�menuʱ���õ�Id����
		case 1:
			c_no=ExpandableListView.getPackedPositionGroup(((ExpandableListContextMenuInfo)item.getMenuInfo()).packedPosition);
			Intent i=new Intent(Day.this,Insert.class);  //�½�intent����ʵ��Activity����ת
			i.putExtra("d", d);
			i.putExtra("c", c_no);
			startActivity(i);    //���ò���γ̵�Activity
			break;
		case 2:
			
			ExpandableListContextMenuInfo info=(ExpandableListContextMenuInfo)item.getMenuInfo();
			ExpandableListView elv= (ExpandableListView)findViewById(R.id.elv);
			String s=(String)((Map<String,String>)elv.getExpandableListAdapter().getChild(ExpandableListView.getPackedPositionGroup(info.packedPosition), ExpandableListView.getPackedPositionChild(info.packedPosition))).get(NAME);
			
			int l=ExpandableListView.getPackedPositionGroup(((ExpandableListContextMenuInfo)item.getMenuInfo()).packedPosition);
			DBHelper dbh=new DBHelper(this);
			SQLiteDatabase db=dbh.getWritableDatabase();
			
			try{
				db.execSQL("delete from event where day_no="+Integer.toString(d)+" and class_no="+Integer.toString(l)+" and class_name='"+s+"'"); //ִ��SQL���ɾ���γ�
				Toast.makeText(this,R.string.del_suc, Toast.LENGTH_SHORT).show();
				/*Toast ��һ�� View ��ͼ�����ٵ�Ϊ�û���ʾ��������Ϣ�� Toast ��Ӧ�ó����ϸ�����ʾ��Ϣ���û�������Զ�����ý��㣬��Ӱ���û�������Ȳ�������Ҫ���� һЩ���� / ��ʾ��
                Toast ����Ĵ�����ʽ��ʹ�þ�̬���� Toast.makeText.�˴�ʹ�õ���Ĭ�ϵ���ʾ��ʽ*/

			}
			catch(Exception e){
				Toast.makeText(this,R.string.del_fail, Toast.LENGTH_SHORT).show();//�����쳣ʱ��ʾɾ��ʧ��
			}
			finally{
				
			}
			
			//elv.getExpandableListAdapter().notifyAll();
			
			return true;
		}
		
		return false;
		
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ //�˷������ڳ�ʼ���˵�������menu�������Ǽ���Ҫ��ʾ��Menuʵ��
    	super.onCreateOptionsMenu(menu);//����ѡ��˵�(optionsMenu), �ò˵��ڵ�� menu ���� ����ڶ�Ӧ��Activity�ײ���ʾ����
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);   //ʹ��inflate�������Ѳ����ļ��еĶ���Ĳ˵� ���ظ�menu����,menu�Ĳ��ֿɼ�menu.xml
    	return true;  //����true����ʾ��menu,false ����ʾ
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	//ֻҪ�˵��еĲ˵����������ᴥ��onOptionsItemSelected(MenuItem item)
        //item������Ϊ������Ĳ˵����ô��Ҫ�ڴ˷������ж��ĸ�Item������ˣ��Ӷ�ʵ�ֲ�ͬ�Ĳ�����
    	switch(item.getItemId()){
    	case R.id.help: //���help�˵���
    		new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(R.string.help_text).setIcon(android.R.drawable.ic_menu_help).show();
    		//AlertDialog��Dialog��һ��ֱ�����࣬AlertDialogҲ��Androidϵͳ������õĶԻ���֮һ��
    		//һ��AlertDialog�������������ϵ�Button�����Զ�һ��AlertDialog������Ӧ����Ϣ������title��massage��setSingleChoiceItems��setPositiveButton��setNegativeButton�ȵ�
    		break;    	
    	case R.id.about://���about�˵���
    		new AlertDialog.Builder(this).setTitle(R.string.about).setMessage(R.string.about_text).setIcon(android.R.drawable.ic_menu_info_details).show();
    		break;
    	}
    	return false;
    }
    
}
