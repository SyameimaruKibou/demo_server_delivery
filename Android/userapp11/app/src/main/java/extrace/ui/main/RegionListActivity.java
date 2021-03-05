package extrace.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

import extrace.loader.RegionListLoader;
import extrace.misc.model.CodeNamePair;
import extrace.ui.misc.RegionListAdapter;

@SuppressWarnings("all")
public class RegionListActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar1 = getSupportActionBar();
		if (actionBar1 != null) actionBar1.hide();
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fm = getSupportFragmentManager();

		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {//显示下面列表PlaceholderFragment！！！！！！！！！！！！！！
			PlaceholderFragment list = new PlaceholderFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends ListFragment {
		private RegionListAdapter mAdapter;
		private RegionListLoader mLoader;

		private int opStatus;
		private String selectCode,selectString;
		Intent mIntent;
		private int Length;

		public PlaceholderFragment() {

		}

		@Override public void onActivityCreated(Bundle savedInstanceState) {//加载菜单列表
			super.onActivityCreated(savedInstanceState);

			// Give some text to display if there is no data.  In a real
			// application this would come from a resource.
			setEmptyText("无行政区域选定!");

			// We have a menu item to show in action bar.
			setHasOptionsMenu(true);//传入true作为参数表明Fragment需要加载菜单项
			//Log.i("onStatusNotify", "YESYES!!!!!!!!!!!!!!!!!!!!!!!!!!!: " );
			// Create an empty adapter we will use to display the loaded data.
			mAdapter = new RegionListAdapter(new ArrayList<CodeNamePair>(), this.getActivity());
			setListAdapter(mAdapter);

			// Start out with a progress indicator.
			//setListShown(false);

			if(mIntent.hasExtra("RegionId")){//显示选中的选项？？？？？？？？表示已经选择了地址
				selectCode = mIntent.getStringExtra("RegionId");
				selectString = mIntent.getStringExtra("RegionString");
				this.getActivity().setTitle(selectString);//显现你选择的地址信息！！！！
			}

			if(mIntent.hasExtra("Length")){
				Length=mIntent.getIntExtra("Length",3);
			}

			opStatus = 0;
			RefreshList(opStatus, "");
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			mIntent = activity.getIntent();
		}

		@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {//创建选择菜单
			// Inflate the menu; this adds items to the action bar if it is present.
			inflater.inflate(R.menu.region_list, menu);
			MenuItem item = menu.findItem(R.id.action_reset);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS
					| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			item = menu.findItem(R.id.action_ok);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS
					| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			switch(id){
				case R.id.action_ok:
					if(opStatus > 2)//选择的地址到达了三层，就okok！
					{
						SelectOk();
					}
					else{
						Toast.makeText(this.getActivity(), "请选择完整的三级行政区!", Toast.LENGTH_SHORT).show();
					}
					return true;
				case R.id.action_reset://重置，重新选择地址，选择次数置0.
					opStatus = 0;
					RefreshList(opStatus, "");
					return true;
				case R.id.action_settings:
					//SettingsActivity a = new SettingsActivity();
					//a.startActivities(null);
					return true;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override public void onListItemClick(ListView l, View v, int position, long id) {
			// Insert desired behavior here.
			CodeNamePair cnp = mAdapter.getItem(position);//地址和code的转换
			selectCode = cnp.getCode();//得到地区的code
			switch(opStatus){
				case 0:
					selectString = cnp.getName();//得到地区名
					opStatus++;	//选择次数增加
					TransNodeCheck(opStatus);
					RefreshList(opStatus, selectCode);
					break;
				case 1:
					selectString += " " + cnp.getName();//地址长度增加
					opStatus++;
					TransNodeCheck(opStatus);
					RefreshList(opStatus, selectCode);
					break;
				case 2:
					opStatus++;
					selectString += " " + cnp.getName();
					TransNodeCheck(opStatus);
				//	Log.i("调用完三层了：",selectString+"这是现在选择的地址");
					RefreshList(opStatus, selectCode);
				//	Log.i("第4层","跳过！！！");
				//	Log.i("开始调用selectOK:","成功！！！！！！！！");
					SelectOk();
					break;
				default:
					break;
			}

//        	selectCode = cnp.getCode();
//        	selectString = cnp.getName();
			this.getActivity().setTitle(selectString);//显示你前面所选择的地址信息

		}

		private void TransNodeCheck(int state){
			if(mIntent.hasExtra("Length")==false) return;
			if(state<Length) return;
			mIntent.putExtra("RegionId",selectCode);
			mIntent.putExtra("RegionString",selectString);
			this.getActivity().setResult(RESULT_OK, mIntent);//返回给上一个调用这个activity的activity
			this.getActivity().finish();
		}

		private void SelectOk()//选好了三级地址
		{
			/*将选中的对象赋值给Intent*/
			mIntent.putExtra("RegionId",selectCode);
			mIntent.putExtra("RegionString",selectString);
		//	Log.i("调用selectOK中:",selectString+"!!!!!!!"+selectCode+"成功！！！！！！！！");
			this.getActivity().setResult(RESULT_OK, mIntent);//返回给上一个调用这个activity的activity
			this.getActivity().finish();
		}

		private void RefreshList(int status, String region_code)
		{
			try {
				mLoader = new RegionListLoader(mAdapter,this.getActivity());
				switch(status)
				{
					case 0:
					//	Log.i("开始查看省份","成功！！！！");
						mLoader.LoadProvinceList();
						break;
					case 1:
						mLoader.LoadCityList(region_code);
						break;
					case 2:
						mLoader.LoadTownList(region_code);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
