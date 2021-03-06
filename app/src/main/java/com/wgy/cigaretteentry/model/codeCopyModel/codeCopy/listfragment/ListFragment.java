package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo.DetailCaseInfoActivity;
import com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase.TakePhotoForCaseActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 /* {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements ListFragmentContract.IView{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    private ListView listview;
    private CaseListAdapter adapter;
//    private OnFragmentInteractionListener mListener;

    private ListFragmentContract.Presenter presenter;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_list, container, false);
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
        listview = (ListView)layout.findViewById(R.id.listview);
        if (getActivity()!=null) {
            presenter=new ListPresenter(this);
            adapter = new CaseListAdapter(getActivity(),presenter);
            listview.setAdapter(adapter);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.gotoDetail(position);
            }
        });
        if(getActivity()!=null)
            presenter.start();
    }

    @Override
    public void setPresenter(ListFragmentContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void updateListView(ArrayList<Case> cases) {
        if (cases!=null) {
            adapter.setCases(cases);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void gotoTakePhoto(int index) {
        if (getActivity()!=null) {
            Intent intent = new Intent(getActivity(), TakePhotoForCaseActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt(ListFragment.INDEX,index);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void gotoDetail(int index) {
        if(getActivity()!=null) {
            Intent intent = new Intent(getActivity(), DetailCaseInfoActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt(INDEX,index);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }
    }


    @Override
    public void search(String num) {
        presenter.search(num);
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
    public static final String INDEX = "index";
}
