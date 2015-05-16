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
	private Configuration config;//Configuration类专门描述手机设备上的配置信息，这些配置信息既包括用户特定的配置项，也包括系统的动态设备配置
	private DisplayMetrics dm;
	private Resources resources;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        resources =getResources();//获得res资源对象  
		config = resources.getConfiguration();//获得Configue对象  
		dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等
		((Button)findViewById(R.id.btn_chinese)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				config.locale = Locale.SIMPLIFIED_CHINESE; //对locale信息进行赋值，将系统语言改为中文简体
				resources.updateConfiguration(config, dm); //更新程序的Configue信息，从而将语言切换到中文
				onCreate(null);
			}
		});
		
		((Button)findViewById(R.id.btn_english)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				config.locale = Locale.US;//对locale信息进行赋值，将系统语言改为英文
				resources.updateConfiguration(config, dm);//更新程序的Configue信息，从而将语言切换到英文
				onCreate(null);
			}
		});
		
        TextView tv=(TextView)findViewById(R.id.day); //定义TextView类的对象tv，用来显示日期信息
        Date now=new Date();    //获取当前的日期
        SimpleDateFormat f=new SimpleDateFormat("yyyy—MM—dd");  //设置显示的日期的格式为"yyyy—MM—dd"
        tv.setText(f.format(now).toString()); //将当前日期对象转换为字符型，并用setText方法显示
        
        
        
        ListView lv = (ListView)findViewById(R.id.ListView01); //定义ListView类的对象lv
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, 
        		new String[]{getString(R.string.mon),getString(R.string.tue),getString(R.string.wed),
        		getString(R.string.thu),getString(R.string.fri),getString(R.string.sat),
        		getString(R.string.sun)});
        //定义一个ArrayAdapter类的数组适配器，数据类型<>是String类型。里面有三个参数，第一个参数是上下文，就是当前的Activity, 
        //第二个参数是android sdk中自己内置的一个布局，它里面只有一个TextView，这个参数是表明我们数组中每一条数据的布局是这个view，
        //就是将每一条数据都显示在这个view上面；第三个参数就是我们要显示的数据。listView会根据这三个参数，遍历adapterData里面的每一条数据，
        //读出一条，显示到第二个参数对应的布局中，这样就形成了我们看到的listView
        lv.setAdapter(aa);  //将上面定义的数组适配器的内容设置为ListView的显示
        
        lv.setOnItemClickListener(new OnItemClickListener(){   //设置list选项点击的监测器  

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Curriculum.this,Day.class);  
				i.putExtra("d", arg2); //参数arg2对应的是position of the view in the adapter，即列表中点击的条目，将参数保存在d变量中
				startActivity(i); //通过intent调用另一个activity
				
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