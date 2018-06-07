package com.mrgao.onemonth.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mrgao.onemonth.base.BaseApplication;




/**
 * Created by sll on 2016/1/11.
 */
public class ToastUtils {

  private static Context mContext;

  private ToastUtils() {
  }

  public static void register(Context context) {
    mContext = context;
  }

  public static void showToast(int resId) {
    Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_SHORT).show();
  }

  public static void showToast(String msg) {
    MyToast.showToast(msg);
  }
  static class MyToast{
    private final Context context;
    private static Toast toast;

    public MyToast(Context context){
      this.context=context;
    }

    public static void showToast(String text){

      if(toast==null){
        toast = new Toast(BaseApplication.getContext());
      }
      View inflate=null;
//      inflate = View.inflate(BaseApplication.getContext(), 12, null);
//      TextView content = (TextView) inflate.findViewById(1);
     // content.setText(text);
      toast.setGravity(Gravity.BOTTOM, 0, 200);
      toast.setView(inflate);
      toast.show();
    }
  }
}
