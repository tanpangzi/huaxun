package cn.huaxunchina.cloud.app.activity.score;

import java.util.List;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.model.FiveScoredsInfos;
import cn.huaxunchina.cloud.app.model.ScoredsInfos;
import cn.huaxunchina.cloud.app.view.ScoreView;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

/**
 * 曲线图
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月5日 下午5:52:00 
 *
 */
public class ScoreGraphView extends Fragment {

	private Activity activity; 
	private RelativeLayout rlContainer;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//初始化数据请求 initdata  
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity=activity;
        
	}
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score_graph_view_layout, container,false);
		rlContainer=(RelativeLayout)view.findViewById(R.id.container_layout);
		return view;
	}
	
	
	public void initView(FiveScoredsInfos data){
		if(rlContainer.getChildCount()>0){
			rlContainer.removeAllViews();	
		}
		DisplayMetrics dm = new DisplayMetrics();
		int h_padding = activity.getResources().getDimensionPixelSize(R.dimen.scoreview_height_bottom_padding);
		int w_padding = activity.getResources().getDimensionPixelSize(R.dimen.scoreview_width_bottom_padding);
		int _padding = activity.getResources().getDimensionPixelSize(R.dimen.scoreview_padding);
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels-w_padding;
		int height = dm.heightPixels-h_padding;//DisplayUtil.dip2px(25);
		 
		if(data!=null){
		int[] scoreList = new int[5];
		String[] examNos = new String[5];
		List<ScoredsInfos> info = data.getScores();
		if(info!=null){
			int size = info.size()-1;
			int _count = 4;
			for(int i=size;i>=0;i--){
				ScoredsInfos _score=info.get(i);
				scoreList[_count]=_score.getScore();
				examNos[_count]=_score.getExamNo();
				_count--;
			}
		}
		ScoreView view = new ScoreView(activity, height, width, scoreList,examNos,_padding);
	    rlContainer.addView(view);	
		}
	}
		
		
}
