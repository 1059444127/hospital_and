package com.example.android.navigationdrawerexample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class suggestion extends Fragment {
	private static final String[] m={"骨科", "儿科", "眼科", "鼻科", "手科","其他"};
	private TextView textView ;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.suggestion, container,false);
		spinner=(Spinner)view.findViewById(R.id.spinner1);
		adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,m);
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinner.setAdapter(adapter);
		 return view;
	}
}
