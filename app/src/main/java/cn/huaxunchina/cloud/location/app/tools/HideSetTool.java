package cn.huaxunchina.cloud.location.app.tools;

public class HideSetTool {
	private char[] mchar;

	public HideSetTool(char[] mchar) {
		this.mchar = mchar;
	}

	public boolean getSelectCheck(int i) {
		if (mchar[i] == '1') {
			return true;
		}
		return false;

	}

}
