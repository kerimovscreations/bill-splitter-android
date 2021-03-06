package com.kerimovscreations.billsplitter.fragments.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kerimovscreations.billsplitter.R;
import com.kerimovscreations.billsplitter.adapters.recyclerView.GroupMembersRVAdapter;
import com.kerimovscreations.billsplitter.models.GroupMember;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupMemberPickerBottomSheetDialogFragment extends BottomSheetDialogFragment {

    View mView;

    @BindView(R.id.top_pointer_view)
    View mTopPointerView;
    @BindView(R.id.rvList)
    RecyclerView mRVList;

    GroupMembersRVAdapter mAdapter;
    ArrayList<GroupMember> mList = new ArrayList<>();

    boolean mLogoutBtnVisible = false;

    private OnClickListener mListener;

    public interface OnClickListener {
        void onSelect(GroupMember person);

        void onRemove();
    }

    public void setClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public static GroupMemberPickerBottomSheetDialogFragment getInstance(ArrayList<GroupMember> list) {
        GroupMemberPickerBottomSheetDialogFragment fragment= new GroupMemberPickerBottomSheetDialogFragment();
        fragment.mList.addAll(list);
        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bottom_sheet_dialog_group_member_picker, container, false);
        ButterKnife.bind(this, mView);

        initVars();

        return mView;
    }

    void initVars() {
        mAdapter = new GroupMembersRVAdapter(getContext(), mList);
        mAdapter.setOnItemClickListener(position -> {
            if (mListener != null) {
                mListener.onSelect(mList.get(position));
                dismiss();
            }
        });

        mRVList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRVList.setAdapter(mAdapter);
    }

    /**
     * UI
     */

    /**
     * Click handlers
     */

    @OnClick(R.id.remove_layout)
    void onRemove() {
        if(mListener != null){
            mListener.onRemove();
            dismiss();
        }
    }

    @OnClick(R.id.cancel_btn)
    void onCancel() {
        dismiss();
    }
}
