package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.in.mobilepay.R;
import co.in.mobilepay.view.model.PurchaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PurchaseItemsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PurchaseItemsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PurchaseItemsFragment newInstance(int columnCount) {
        PurchaseItemsFragment fragment = new PurchaseItemsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        List<PurchaseModel> purchaseModels = new ArrayList<>(3);
        PurchaseModel purchaseModel = new PurchaseModel(1,"Saravana Stores","T.Nagar","9952471553","000014","Jan-2-2016","Readymades","3","1000");
        purchaseModels.add(purchaseModel);
        purchaseModels.add(purchaseModel);
        purchaseModels.add(purchaseModel);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
          //  recyclerView.setAdapter(new MyItemRecyclerViewAdapter(purchaseModels, mListener));
         //   recyclerView.setAdapter(new MyItemRecyclerViewAdapter(context,purchaseModels, mListener));
        }
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}
