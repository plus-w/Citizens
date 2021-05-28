package com.example.citizens.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.citizens.R;

public class DeleteLabelDialogFragment extends DialogFragment {

    private DeleteNewsLabelDialogListener listener;
    private String deleteLabelName;

    public DeleteLabelDialogFragment(String deleteLabelName) {
        super();
        this.deleteLabelName = deleteLabelName;
    }

    public String getDeleteLabelName() {
        return deleteLabelName;
    }

    public void setDeleteLabelName(String deleteLabelName) {
        this.deleteLabelName = deleteLabelName;
    }

    public interface DeleteNewsLabelDialogListener {
        public void onDeleteNewsLabelDialogConfirmClick(DeleteLabelDialogFragment dialogFragment);
        public void onDeleteNewsLabelDialogCancelClick(DeleteLabelDialogFragment dialogFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteNewsLabelDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement DeleteDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("是否删除 " + deleteLabelName + " 标签？")
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDeleteNewsLabelDialogConfirmClick(DeleteLabelDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        listener.onDeleteNewsLabelDialogCancelClick(DeleteLabelDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
