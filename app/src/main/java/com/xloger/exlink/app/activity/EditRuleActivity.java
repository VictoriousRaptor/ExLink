package com.xloger.exlink.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.xloger.exlink.app.R;
import com.xloger.exlink.app.adapter.WhiteRuleAdapter;
import com.xloger.exlink.app.entity.App;
import com.xloger.exlink.app.util.JSONFile;

import java.util.List;

/**
 * Created on 16/6/27 下午9:36.
 * Author: xloger
 * Email:phoenix@xloger.com
 */
public class EditRuleActivity extends BaseActivity {

    private Context context;
    private App app;
    private List<App> appList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rule);
        context = this;
        int position = getIntent().getIntExtra("position", -1);
        if (position == -1) {
            Toast.makeText(context, "哎呀我好像不知道哪个 App 点击了呢...", Toast.LENGTH_SHORT).show();
            finish();
        }
        appList = JSONFile.INSTANCE.getJson();
        app = appList.get(position);
        initView();
    }

    private void initView() {
        ListView listView = findViewById(R.id.edit_rule_list);
        Button addBtn = findViewById(R.id.edit_rule_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setTest(true);
                JSONFile.INSTANCE.saveJson(appList);
                Intent intent = new Intent(context, StepTwoActivity.class);
                intent.putExtra("title", "步骤二/添加规则");
                startActivity(intent);
            }
        });
        WhiteRuleAdapter adapter = new WhiteRuleAdapter(context, app.getRules(), new WhiteRuleAdapter.WhiteRuleAdapterCallBack() {
            @Override
            public void onDelClick() {
                JSONFile.INSTANCE.saveJson(appList);
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
