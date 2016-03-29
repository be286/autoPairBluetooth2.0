package com.itme.ActivityClass;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.imte.utils.ClsUtils;

public class MainActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private static BluetoothDevice remoteDevice=null;
	private Button btn_autopair=null;
	private Button btn=null;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn_autopair=(Button)findViewById(R.id.btn_autopair);
        btn_autopair.setOnClickListener(this);
        btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
    }
    
    public boolean pair(String strAddr, String strPsw)
	{
		boolean result = false;
		//ȡ�����ֵ�ǰ�豸�Ĺ���
		bluetoothAdapter.cancelDiscovery();
		if (!BluetoothAdapter.checkBluetoothAddress(strAddr))
		{ // ���������ַ�Ƿ���Ч
			Log.e("mylog", "devAdd un effient!");
		}
		if (!bluetoothAdapter.isEnabled())
		{
			bluetoothAdapter.enable();
			return false;
		}
		
		//�������豸��ַ�����һ�����豸����
		BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr.toUpperCase());
		if (device.getBondState() != BluetoothDevice.BOND_BONDED)
		{
			try
			{
				Log.e("mylog", "NOT BOND_BONDED");
				ClsUtils.createBond(device.getClass(), device);//��������Թ㲥
				result = true;
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.e("mylog", "setPiN failed!");
				e.printStackTrace();
			} //

		}
		else
		{
			Log.e("mylog", "HAS BOND_BONDED");
			try
			{
				remoteDevice = device; // ����󶨳ɹ�����ֱ�Ӱ�����豸���󴫸�ȫ�ֵ�remoteDevice
				result = true;
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.e("mylog", "setPiN failed!");
				e.printStackTrace();
			}
		}
		return result;
	}
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_autopair:

			if (!bluetoothAdapter.isEnabled())
			{
				bluetoothAdapter.enable();//�첽�ģ�����ȴ������ֱ�ӷ��ء�
			}else{
				bluetoothAdapter.startDiscovery();
			}
			break;

		case R.id.button1:
			
		ClsUtils.printAllInform(remoteDevice.getClass());
			
		default:
			break;
		}
	}
}