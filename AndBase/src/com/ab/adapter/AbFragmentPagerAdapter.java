/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ab.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbFragmentPagerAdapter.java 
 * 描述：一个通用的Fragment适配器
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-11-28 上午10:57:53
 */
public class AbFragmentPagerAdapter extends FragmentPagerAdapter {
	/** The m fragment list. */
	private ArrayList<Fragment> mFragmentList = null;

	public AbFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
		super(fm);
		// TODO Auto-generated constructor stub
		mFragmentList = fragmentList;
	}

	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		if (position < mFragmentList.size()){
			fragment = mFragmentList.get(position);
		}else{
			fragment = mFragmentList.get(0);
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragmentList.size();
	}
}
