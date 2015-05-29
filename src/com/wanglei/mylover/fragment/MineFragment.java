package com.wanglei.mylover.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.loginorregister.LoginActivity;
import com.wanglei.mylover.R;
import com.wanglei.mylover.adapter.PersonQuestionAdapter;
import com.wanglei.mylover.base.BaseFragment;
import com.wanglei.mylover.utils.SharePrefUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseFragment implements OnClickListener {

	private View view;
	private TextView publish_question;
	private TextView answer_question;
	private TextView text;
	private GridView mGridView;
	private List<String> list;
	private PersonQuestionAdapter adapter;
	private CircleImageView civ_exitName;
	@Override
	public View initView(LayoutInflater inflater) {

		view = inflater.inflate(R.layout.activity_persional_center, null);
		text = (TextView) view.findViewById(R.id.title_person);
		answer_question = (TextView) view.findViewById(R.id.answer_question);
		publish_question = (TextView) view.findViewById(R.id.publish_question);
		civ_exitName = (CircleImageView) view.findViewById(R.id.user_photo);
		mGridView = (GridView) view.findViewById(R.id.question_item);
		list = new ArrayList<String>();
		adapter = new PersonQuestionAdapter(ct, list);
		mGridView.setAdapter(adapter);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		text.setText("个人中心");
		publish_question.setOnClickListener(this);
		answer_question.setOnClickListener(this);
		civ_exitName.setOnClickListener(this);
		publish_question.setBackgroundResource(R.drawable.personal_press);
		for (int i = 1; i < 9; i++) {
			list.add("发布的问题" + i);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.publish_question:
			publish_question.setBackgroundResource(R.drawable.personal_press);
			answer_question.setBackgroundResource(R.drawable.personal_normal);
			list.clear();
			for (int i = 1; i < 9; i++) {

				list.add("发布的问题" + i);
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.answer_question:
			answer_question.setBackgroundResource(R.drawable.personal_press);
			publish_question.setBackgroundResource(R.drawable.personal_normal);
			list.clear();
			for (int i = 1; i < 5; i++) {

				list.add("回答的问题" + i);
			}
			adapter.notifyDataSetChanged();
			break;
			
			case R.id.user_photo:
				SharePrefUtil.saveBoolean(getActivity(), "islogin", false);//
				Intent it = new Intent(getActivity(),LoginActivity.class);
				startActivity(it);
				break;
		}
	}

}
