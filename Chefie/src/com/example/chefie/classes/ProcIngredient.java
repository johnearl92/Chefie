package com.example.chefie.classes;

import java.io.Serializable;

public class ProcIngredient implements Serializable{
	private String procIngred;
	private boolean check;
	public ProcIngredient(String val){
		this.procIngred = val;
		this.check=false;
	}
	public String getProcIngred() {
		return procIngred;
	}
	public void setProcIngred(String procIngred) {
		this.procIngred = procIngred;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
}
