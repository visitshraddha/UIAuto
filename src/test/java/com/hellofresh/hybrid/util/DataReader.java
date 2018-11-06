package com.hellofresh.hybrid.util;



import java.util.Hashtable;

import com.hellofresh.basetest.Xls_Reader;

public class DataReader {
	
	
	public static boolean isRunnable(Xls_Reader xls,String TestSheet,String TC){
		int rows=xls.getRowCount(TestSheet);
		for (int row = 2; row <= rows; row++) {
			if (xls.getCellData(TestSheet, 0, row).equals(TC)) {
				String runmode=xls.getCellData(TestSheet, "RunMode", row);
				if(runmode.equals("Y"))
				{
					return true;
				}else{
					return false;
				}
			}

		}

		return false;


	}
	

	public static Object[][] getdata(Xls_Reader xls,String TestSheet,String TC){

		int i=1;
		while(!xls.getCellData(TestSheet, 0, i).equals(TC)){
			i++;
		}

		System.out.println("TC Start Row: "+ i);

		int TestCaseStartRow=i;
		int TestCaseHeaderStartRow=i+1;
		int TestCaseDataStartRow=i+2;


		//DataRows count
		int datarows=0;
		while(!xls.getCellData(TestSheet, 0, TestCaseDataStartRow+datarows).equals("")){
			datarows++;
		}

		System.out.println("TC Data Rows: "+ datarows);

		//columns count

		int columns=0;
		while(!xls.getCellData(TestSheet, columns, TestCaseHeaderStartRow).equals("")){
			columns++;
		}

		System.out.println("TC columns count: "+ columns);


		Object[][]data=new Object[datarows][1];
		for (int j = 0; j< datarows; j++) {
			Hashtable<Object,Object> ht= new Hashtable<Object,Object>();
			for (int k = 0; k < columns; k++) {
				ht.put(xls.getCellData(TestSheet, k, TestCaseHeaderStartRow),xls.getCellData(TestSheet, k, TestCaseDataStartRow+j));

			}

			data[j][0]=ht;


		}

		System.out.println("Size of arrary:" + data.length);
		return data;
	}

}
