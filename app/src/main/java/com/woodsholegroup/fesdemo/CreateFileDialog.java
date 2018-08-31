package com.woodsholegroup.fesdemo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ltossou on 8/15/2018.
 */

public class CreateFileDialog extends DialogFragment {

    public static final String DIALOG_TAG = "CREATE_FILE_DIALOG";

    private CreateFileListener listener;

    public interface CreateFileListener {
        void onFileContentCreated(@NonNull String content);
    }

    public static CreateFileDialog newInstance() {
        return new CreateFileDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogTheme();
    }

    private void setDialogTheme() {
        @StyleRes int styleRes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            styleRes = android.R.style.Theme_Material_Light_Dialog;
        } else {
            styleRes = android.R.style.Theme_Holo_Light_Dialog;
        }
        setStyle(DialogFragment.STYLE_NORMAL, styleRes);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create_file, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle("Type the file content");

        final EditText contentFileText = view.findViewById(R.id.text_dialog_file_content);
        Button createFileButton = view.findViewById(R.id.button_dialog_file_create);
        createFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentFileText.getText().toString();
                if (content.isEmpty()) {
                    contentFileText.setError("Required");
                } else {
                    if (listener != null) {
                        listener.onFileContentCreated(content);
                    }
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = ((CreateFileListener) context);
        } catch (ClassCastException e) {
            throw new IllegalStateException("Parent activity must implement CreateDialogListener interface");
        }
    }
}
