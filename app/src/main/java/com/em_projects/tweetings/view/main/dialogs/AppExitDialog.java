package com.em_projects.tweetings.view.main.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.em_projects.tweetings.R;
import com.em_projects.tweetings.config.Dynamic;


public class AppExitDialog extends DialogFragment implements View.OnClickListener {

    private Button okButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_app_exit, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setCancelable(false);

        okButton = (Button) view.findViewById(R.id.okButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.okButton == view.getId()) {
            Dynamic.Companion.setUuid(null);
            System.exit(0);
        }
        dismiss();
    }
}