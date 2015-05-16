package hyuuhit.Curriculum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Curriculum extends Activity {
	private Configuration config;//Configuration��ר�������ֻ��豸�ϵ�������Ϣ����Щ������Ϣ�Ȱ����û��ض��������Ҳ����ϵͳ�Ķ�̬�豸����
	private DisplayMetrics dm;
	private Resources resources;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        resources =getResources();//���res��Դ����  
		config = resources.getConfiguration();//���Configue����  
		dm = resources.getDisplayMetrics();//�����Ļ��������Ҫ�Ƿֱ��ʣ����ص�
		((Button)findViewById(R.id.btn_chinese)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				config.locale = Locale.SIMPLIFIED_CHINESE; //��locale��Ϣ���и�ֵ����ϵͳ���Ը�Ϊ���ļ���
				resources.updateConfiguration(config, dm); //���³����Configue��Ϣ���Ӷ��������л�������
				onCreate(null);
			}
		});
		
		((Button)findViewById(R.id.btn_english)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				config.locale = Locale.US;//��locale��Ϣ���и�ֵ����ϵͳ���Ը�ΪӢ��
				resources.updateConfiguration(config, dm);//���³����Configue��Ϣ���Ӷ��������л���Ӣ��
				onCreate(null);
			}
		});
		
        TextView tv=(TextView)findViewById(R.id.day); //����TextView��Ķ���tv��������ʾ������Ϣ
        Date now=new Date();    //��ȡ��ǰ������
        SimpleDateFormat f=new SimpleDateFormat("yyyy��MM��dd");  //������ʾ�����ڵĸ�ʽΪ"yyyy��MM��dd"
        tv.setText(f.format(now).toString()); //����ǰ���ڶ���ת��Ϊ�ַ��ͣ�����setText������ʾ
        
        
        
        ListView lv = (ListView)findViewById(R.id.ListView01); //����ListView��Ķ���lv
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, 
        		new String[]{getString(R.string.mon),getString(R.string.tue),getString(R.string.wed),
        		getString(R.string.thu),getString(R.string.fri),getString(R.string.sat),
        		getString(R.string.sun)});
        //����һ��ArrayAdapter�����������������������<>��String���͡�������������������һ�������������ģ����ǵ�ǰ��Activity, 
        //�ڶ���������android sdk���Լ����õ�һ�����֣�������ֻ��һ��TextView����������Ǳ�������������ÿһ�����ݵĲ��������view��
        //���ǽ�ÿһ�����ݶ���ʾ�����view���棻������������������Ҫ��ʾ�����ݡ�listView���������������������adapterData�����ÿһ�����ݣ�
        //����һ������ʾ���ڶ���������Ӧ�Ĳ����У��������γ������ǿ�����listView
        lv.setAdapter(aa);  //�����涨�����������������������ΪListView����ʾ
        
        lv.setOnItemClickListener(new OnItemClickListener(){   //����listѡ�����ļ����  

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Curriculum.this,Day.class);  
				i.putExtra("d", arg2); //����arg2��Ӧ����position of the view in the adapter�����б��е������Ŀ��������������d������
				startActivity(i); //ͨ��intent������һ��activity
				
				//setTitle(Integer.toString(arg2));
			}
        	
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.help:
    		new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(R.string.help_text).setIcon(android.R.drawable.ic_menu_help).show();
    		break;    	
    	case R.id.about:
    		new AlertDialog.Builder(this).setTitle(R.string.about).setMessage(R.string.about_text).setIcon(android.R.drawable.ic_menu_info_details).show();
    		break;
    	}
    	return false;
    }
}