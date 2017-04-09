package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.addfragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase.TakePhotoForCaseActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements AddFragmentContract.IView{
    private static final String TAG = "AddFragment";
    private EditText year_edit;
    private EditText number_edit;
    private EditText departmentID_edit;
    private EditText userID_edit;
    private EditText date_edit;
    private Button next;
    private Button cancel;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add, container, false);
        initView(layout);
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void setmListener(OnFragmentInteractionListener listener){
        if (this.mListener==null) {
            this.mListener = listener;
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initView(View layout){
        year_edit=(EditText)layout.findViewById(R.id.year_edit);
        number_edit=(EditText)layout.findViewById(R.id.number_edit);
        departmentID_edit=(EditText)layout.findViewById(R.id.department_id_edit);
        userID_edit=(EditText)layout.findViewById(R.id.user_id_edit);
        date_edit=(EditText)layout.findViewById(R.id.date_edit);

        next=(Button)layout.findViewById(R.id.next);
        cancel=(Button)layout.findViewById(R.id.cancel);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity()!=null){
                    Intent intent = new Intent(getActivity(), TakePhotoForCaseActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearView();
                if (mListener!=null) {
                    mListener.gotoListFragment();
                }
            }
        });
    }
    @Override
    public void setPresenter(AddFragmentContract.Presenter presenter) {

    }
    private void clearView(){
        year_edit.setText("");
        number_edit.setText("");
        departmentID_edit.setText("");
        date_edit.setText("");
        userID_edit.setText("");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void gotoListFragment();

    }
}
