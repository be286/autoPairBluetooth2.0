package com.itme.ActivityClass;

import com.imte.utils.ClsUtils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class bluetoothReceiver extends BroadcastReceiver{

	String PIN = "82737550";
	public bluetoothReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction(); 
		BluetoothDevice btDevice=null;
		btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) 
		{
			if(btDevice.getName().contains("iMate"))
			{
				
				
				try {
					
					//1.ȷ�����
					ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
					//2.��ֹ����㲥
					Log.i("zbh", "isOrderedBroadcast:"+isOrderedBroadcast()+",isInitialStickyBroadcast:"+isInitialStickyBroadcast());
					abortBroadcast();//�����ֻ���������㲥�ģ�������ֹ���ˡ�
					//3.���...
					boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, PIN);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
			
			
		}else if("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)){
			
			String stateExtra = BluetoothAdapter.EXTRA_STATE;
			int state = intent.getIntExtra(stateExtra, -1);
			if(state==BluetoothAdapter.STATE_ON){
				BluetoothAdapter.getDefaultAdapter().startDiscovery();
			}
			
		}else if(BluetoothDevice.ACTION_FOUND.equals(action)){
			Log.e("�����豸:", "["+btDevice.getName()+"]"+":"+btDevice.getAddress());
			if(btDevice.getName().contains("iMate"))//iMate�豸����ж������һ���ѵ����Ǹ��ᱻ���ԡ�
			{
				
				if (btDevice.getBondState() == BluetoothDevice.BOND_NONE) {  
					
					Log.e("zbh", "attemp to bond:"+"["+btDevice.getName()+"]");
					try {
						ClsUtils.createBond(btDevice.getClass(), btDevice);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
			}
		}
	}
}
