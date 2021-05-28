package com.example.citizens.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.citizens.R;

public class AddLabelDialogFragment extends DialogFragment {

    private AddNewsLabelDialogListener listener;
    private String addLabelName;

    public String getAddLabelName() {
        return addLabelName;
    }

    public interface AddNewsLabelDialogListener {
        public void onAddNewsLabelDialogConfirmClick(AddLabelDialogFragment dialogFragment, String addLabelName);
        public void onAddNewsLabelDialogCancelClick(AddLabelDialogFragment dialogFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddLabelDialogFragment.AddNewsLabelDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement DeleteDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_news_label, null);
        builder.setView(v)
            .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText editText = (EditText) v.findViewById(R.id.edit_text_add_news_label);
                    addLabelName = editText.getText().toString();
                    listener.onAddNewsLabelDialogConfirmClick(AddLabelDialogFragment.this, addLabelName);
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onAddNewsLabelDialogCancelClick(AddLabelDialogFragment.this);
                }
            });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
