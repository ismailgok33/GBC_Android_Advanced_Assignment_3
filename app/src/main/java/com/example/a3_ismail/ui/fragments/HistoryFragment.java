package com.example.a3_ismail.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3_ismail.R;
import com.example.a3_ismail.adapters.ResultAdapter;
import com.example.a3_ismail.data.db.HistoryDatabase;
import com.example.a3_ismail.data.db.ResultDao;
import com.example.a3_ismail.data.models.Result;
import com.example.a3_ismail.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";
    private FragmentHistoryBinding binding;

    private List<Result> resultArrayList = new ArrayList<>();
    private ResultAdapter adapter;
    private ResultDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dao = HistoryDatabase.getDatabase(getContext()).resultDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new ResultAdapter(view.getContext(), this.resultArrayList);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvItems.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvItems.setAdapter(this.adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set the new results to the adapter
        this.resultArrayList.removeAll(resultArrayList);
        this.resultArrayList.addAll(dao.getResults());
        adapter.notifyDataSetChanged();
    }
}