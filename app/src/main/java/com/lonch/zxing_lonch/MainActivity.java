package com.lonch.zxing_lonch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.lonch.zxing_lonch.google.zxing.activity.CaptureActivity;
import com.lonch.zxing_lonch.google.zxing.encoding.EncodingHandler;
import com.lonch.zxing_lonch.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 集成Zxing的扫码
 *
 * @author GodofSwond
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.openCodeBtn)
    Button openCodeBtn;
    @BindView(R.id.textEt)
    EditText textEt;
    @BindView(R.id.createCodeBtn)
    Button createCodeBtn;
    @BindView(R.id.codeIv)
    ImageView codeIv;
    @BindView(R.id.codeTv)
    TextView codeTv;

    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//一定要写
    }

    @OnClick({R.id.openCodeBtn, R.id.createCodeBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openCodeBtn:
                //打开二维码扫描界面
                if (CommonUtil.isCameraCanUse()) {
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(this, "请打开此应用的摄像头权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.createCodeBtn:
                //获取输入的文本信息
                String strTxt = textEt.getText().toString();
                if (null != strTxt && !"".equals(strTxt.trim())) {
                    //根据输入的文本生成对应的二维码并且显示出来
                    try {
                        Bitmap bitmap = EncodingHandler.createQRCode(textEt.getText().toString(), 500);
                        if (null != bitmap) {
                            Toast.makeText(this, "二维码生成成功！", Toast.LENGTH_SHORT).show();
                            codeIv.setImageBitmap(bitmap);
                        }
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "文本信息不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("scan_result");
            //将扫描出的信息显示出来
            codeTv.setText(scanResult);
        }
    }
}
