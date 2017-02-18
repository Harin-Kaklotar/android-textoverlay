/*
 * Copyright 2016 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.textoverlay.two.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.textoverlay.two.R;
import com.example.textoverlay.two.activity.base.BaseActivity;

import saschpe.android.textoverlay.service.TextOverlayService;

public class MainActivity extends BaseActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
    }

    public void setText(View view) {
        TextOverlayService.setText(this, editText.getText().toString());
    }

    public void startOtherActivity(View view) {
        startActivity(new Intent(this, OtherActivity.class));
    }
}
