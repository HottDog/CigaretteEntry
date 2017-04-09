package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.deletefragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.Case;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteFragment extends Fragment implements DeleteFragmentContract.IView{

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private DeleteCaseListAdapter adapter;
    private DeleteFragmentContract.Presenter presenter;

    public DeleteFragment() {
        // Required empty public constructor
    }

    public static DeleteFragment newInstance() {
        DeleteFragment fragment = new DeleteFragment();
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
        View layout = inflater.inflate(R.layout.fragment_delete, container, false);
        initView(layout);
        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        listView=(ListView)layout.findViewById(R.id.listview);
        if (getActivity()!=null) {
            adapter = new DeleteCaseListAdapter(getActivity());
            listView.setAdapter(adapter);
            presenter=new DeletePresenter(this);
            presenter.start();
        }
    }

    @Override
    public void setPresenter(DeleteFragmentContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void updateListView(ArrayList<Case> cases) {
        if (cases!=null){
            adapter.setCases(cases);
            adapter.notifyDataSetChanged();
        }
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
