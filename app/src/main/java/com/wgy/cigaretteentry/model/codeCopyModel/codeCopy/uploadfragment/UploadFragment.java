package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.CaseListAdapter;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.ListPresenter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment implements UploadFragmentContract.IView{
//    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private UploadCaseListAdapter adapter;
    private UploadFragmentContract.Presenter presenter;
    private AlertDialog alertDialog;

    private boolean dialogLock = false;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (null!=alertDialog){
                    alertDialog.dismiss();
                    dialogLock = false;
                }
            }
            super.handleMessage(msg);
        };
    };
    Timer timer = new Timer();

    public UploadFragment() {
        // Required empty public constructor
    }

    public static UploadFragment newInstance() {
        UploadFragment fragment = new UploadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_upload, container, false);
        initView(layout);
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.register();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unRegister();
    }

    private void initView(View layout){
        listView = (ListView)layout.findViewById(R.id.listview);
        if (getActivity()!=null) {
            presenter=new UploadPresenter(this);
            adapter = new UploadCaseListAdapter(getActivity(),presenter);
            listView.setAdapter(adapter);
            presenter.start();
        }
    }

    private void createDialog(){
        if (null !=getActivity()) {
            if(!dialogLock) {
                LinearLayout de = (LinearLayout) getActivity().getLayoutInflater()
                        .inflate(R.layout.upload_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setView(de);
                alertDialog = builder.create();
                alertDialog.show();
                //需要一个定时器
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        // 需要做的事:发送消息
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                };
                timer.schedule(task, 1000);
                dialogLock=true;
            }
        }
    }
    public void search(String num){
        presenter.search(num);
    }
    @Override
    public void setPresenter(UploadFragmentContract.Presenter presenter) {

    }

    @Override
    public void updateListView(ArrayList<Case> cases) {
        if (cases!=null){
            adapter.setCases(cases);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void upload(int index) {

    }

    @Override
    public void onSuccess() {
        createDialog();
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(),"上传失败，清重新尝试",Toast.LENGTH_SHORT).show();
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
    }
}
