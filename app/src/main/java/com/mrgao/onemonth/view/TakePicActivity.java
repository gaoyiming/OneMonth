package com.mrgao.onemonth.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mrgao.onemonth.R;
import com.mrgao.onemonth.ResultActivity;
import com.mrgao.onemonth.helper.CustomHelper;

import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;

import java.util.ArrayList;

public class TakePicActivity extends TakePhotoActivity {
    private CustomHelper customHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_take_pic, null);
        setContentView(contentView);
        customHelper = CustomHelper.of(contentView);
    }

    public void onClick(View view) {
        customHelper.onClick(view, getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(this, OcrActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }
}
