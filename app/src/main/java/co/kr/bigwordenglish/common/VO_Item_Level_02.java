package co.kr.bigwordenglish.common;


//DB를 총괄관리
public class VO_Item_Level_02 {
	String Level2_Index; //인덱스
	String Level2_SubKey; //서브키
	String Level2_C_Name;
	String Level2_Info;
	boolean Level2_Check;
	
	
	public VO_Item_Level_02() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VO_Item_Level_02(String level2_Index, String level2_SubKey,
			String level2_C_Name, String level2_Info, boolean level2_Check) {
		super();
		Level2_Index = level2_Index;
		Level2_SubKey = level2_SubKey;
		Level2_C_Name = level2_C_Name;
		Level2_Info = level2_Info;
		Level2_Check = level2_Check;
	}

	public String getLevel2_Index() {
		return Level2_Index;
	}

	public void setLevel2_Index(String level2_Index) {
		Level2_Index = level2_Index;
	}

	public String getLevel2_SubKey() {
		return Level2_SubKey;
	}

	public void setLevel2_SubKey(String level2_SubKey) {
		Level2_SubKey = level2_SubKey;
	}

	public String getLevel2_C_Name() {
		return Level2_C_Name;
	}

	public void setLevel2_C_Name(String level2_C_Name) {
		Level2_C_Name = level2_C_Name;
	}

	public String getLevel2_Info() {
		return Level2_Info;
	}

	public void setLevel2_Info(String level2_Info) {
		Level2_Info = level2_Info;
	}

	public boolean isLevel2_Check() {
		return Level2_Check;
	}

	public void setLevel2_Check(boolean level2_Check) {
		Level2_Check = level2_Check;
	}
	
	
	
}

