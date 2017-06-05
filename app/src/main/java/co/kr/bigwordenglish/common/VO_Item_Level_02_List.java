package co.kr.bigwordenglish.common;


//DB를 총괄관리
public class VO_Item_Level_02_List {
	String Level2_List_Count; //출제횟수
	String Level2_List_English; //영어
	String Level2_List_Korean; //한국어
	String Level2_List_Type; // 난이도
	boolean Level2_List_Favorite; // 즐겨찾기

	String Level2_List_Info1;
	String Level2_List_Info2;
	String Level2_List_Info3;
	String Level2_List_Info4;


	public VO_Item_Level_02_List() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VO_Item_Level_02_List(String level2_List_Count, String level2_List_English, String level2_List_Korean, String level2_List_Type, boolean level2_List_Favorite, String level2_List_Info1, String level2_List_Info2, String level2_List_Info3, String level2_List_Info4) {
		Level2_List_Count = level2_List_Count;
		Level2_List_English = level2_List_English;
		Level2_List_Korean = level2_List_Korean;
		Level2_List_Type = level2_List_Type;
		Level2_List_Favorite = level2_List_Favorite;
		Level2_List_Info1 = level2_List_Info1;
		Level2_List_Info2 = level2_List_Info2;
		Level2_List_Info3 = level2_List_Info3;
		Level2_List_Info4 = level2_List_Info4;
	}

	public String getLevel2_List_Count() {
		return Level2_List_Count;
	}

	public void setLevel2_List_Count(String level2_List_Count) {
		Level2_List_Count = level2_List_Count;
	}

	public String getLevel2_List_English() {
		return Level2_List_English;
	}

	public void setLevel2_List_English(String level2_List_English) {
		Level2_List_English = level2_List_English;
	}

	public String getLevel2_List_Korean() {
		return Level2_List_Korean;
	}

	public void setLevel2_List_Korean(String level2_List_Korean) {
		Level2_List_Korean = level2_List_Korean;
	}

	public String getLevel2_List_Type() {
		return Level2_List_Type;
	}

	public void setLevel2_List_Type(String level2_List_Type) {
		Level2_List_Type = level2_List_Type;
	}

	public boolean isLevel2_List_Favorite() {
		return Level2_List_Favorite;
	}

	public void setLevel2_List_Favorite(boolean level2_List_Favorite) {
		Level2_List_Favorite = level2_List_Favorite;
	}

	public String getLevel2_List_Info1() {
		return Level2_List_Info1;
	}

	public void setLevel2_List_Info1(String level2_List_Info1) {
		Level2_List_Info1 = level2_List_Info1;
	}

	public String getLevel2_List_Info2() {
		return Level2_List_Info2;
	}

	public void setLevel2_List_Info2(String level2_List_Info2) {
		Level2_List_Info2 = level2_List_Info2;
	}

	public String getLevel2_List_Info3() {
		return Level2_List_Info3;
	}

	public void setLevel2_List_Info3(String level2_List_Info3) {
		Level2_List_Info3 = level2_List_Info3;
	}

	public String getLevel2_List_Info4() {
		return Level2_List_Info4;
	}

	public void setLevel2_List_Info4(String level2_List_Info4) {
		Level2_List_Info4 = level2_List_Info4;
	}
}

