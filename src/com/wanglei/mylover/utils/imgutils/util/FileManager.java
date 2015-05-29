package com.wanglei.mylover.utils.imgutils.util;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "NetEasyNews/data/image";
		} else {
			return CommonUtil.getRootFilePath() + "NetEasyNews/data/image";
		}
	}
}
