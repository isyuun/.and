
/**
*       interface to access pvs information from java application
*
*/
/* call flow : Other apps such as IUI --> IptvShmService */

package com.lge.sys;

interface ILgIptvShmRemote {
	/*
		one of the following event will be returned.
		
		public static final String PVS_INTENT_WORK_DONE = "com.lge.sys.PVS_DONE";
		public static final String PVS_INTENT_AUTH_FAIL = "com.lge.sys.PVS_FAIL";
		public static final String PVS_INTENT_CHANGED_CONFIG = "com.lge.sys.PVS_CHANGED_CONFIG";
		public static final String SYS_INTENT_REBOOT_IN_10_SEC = "com.lge.sys.SYS_REBOOT_IN_10_SEC";
	*/
	String getLastPvsEvent();
	/* Request firmware to reboot(cold booting) the system.
	   This is normally used for firmware upgrade.
	*/
	void   requestSysReboot();
	/*
		get the latest config file contents.
		@return the contents of /LGU_conf/config.xml file.
	*/
	String getConfigXml();
	/* 
		get the latest pvs return string.
		@return the pvs return string result after provisioning
	*/
	String getPvsReturnString();
	/*
		get the current firmware version information 
		@return yyyymmdd_V.xx.yy.zzzz
			yyyymmdd -> build date
			xx -> Major version
			yy -> minor version
			zzzz -> build version
	*/
	String getFwVersion();
	/*
		get the ethernet mac address of network device.
		@return mac address format is like this 
		        xxxx.yyyy.zzzz (ex: 001c.6277.8888)
	*/
	String getMacAddress();
	/* 
		get the subscriber number
		if the user is invalid one, this must be NOTHING
		@return LGU+ subscriber number
	*/
	String getSubscriberNo();
	/*
		get the region code of user STB which is given during provisioning process
		@return region code
	*/
	String getRegionCode(); 
	/* serverName is the server name in config.xml. e.g)  qos, imcs. 
        * output will be the form of "ip:port"
        * if it has multiple port information such as qos, it will be returned as the form of "ip:port1:port2:port3" 
        */
	String getServerInfo(String serverName);
	/*
		get the network type from pvs return string

		@return 01 --> HFC, 02 -> Optcal LAN
	*/
	String getNetworkType();
	/*
		get the manufacturer ID

		@return LG Electronics --> "lge"
	*/

	String getManufacturerId();
	/*
		get the STB's Model ID

		@return LG Electronics --> "TI320-DU"
	*/
	String getModelId();
	/*
		get the each STB's unique serial number

		@return serial number like "ABCDEF10235"
	*/
	//String getSerialNumber());
}
