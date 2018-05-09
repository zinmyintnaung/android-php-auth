<?php

/**
 * Description: Web end written in Yii framework
 * @author Zin Myint Naung <zinmyintnaung@gmail.com>
 */
class MobileController extends CController  
{
	/**
	* Since we are calling URL as
	* http://yourdomain.com.sg/mobile/mobile/mobileLogin
	* It should call actionMobileLogin method!
	*/
	
	public function actionMobileLogin(){
		
		private $_identity;
		
		$user_name = $_POST['user_name'];
		$user_pass = $_POST['user_password'];
		
		//You should have UserIdentity class written to match user name and password from the DB a
		$this->_identity = new UserIdentity($user_name,$user_pass); //set local
		
		if(!$this->_identity->authenticate()){ //check local
			echo 'Failed'; //check this value in android script to confirm failure
		}else{
			echo 'Successful'; //check this value in android script to confirm success
		}
	}
}