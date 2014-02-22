package bean;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.AppException;
import tools.Logger;

public class UserEntity extends Entity{
	public String openid;
	public String nickname;
	public String sex;
	public String headimgurl;
	public String hash;
	
	public static UserEntity parse(String res) throws IOException, AppException {
		Logger.i(res);
		UserEntity data = new UserEntity();
		try {
			JSONObject js = new JSONObject(res);
			if (js.getInt("status") == 1) {
				data.error_code = Result.RESULT_OK;
				JSONObject info = js.getJSONObject("info");
				data.openid = info.getString("openid");
				data.sex = info.getString("sex");
				data.headimgurl = info.getString("headimgurl");
				data.nickname = info.getString("nickname");
				if (!info.isNull("_sign")) {
					data.hash = info.getString("_sign");
				}
			}
			else {
				if (!js.isNull("error_code")) {
					try {
						data.error_code = js.getInt("error_code");
					} catch (Exception  e) {
						data.error_code = Integer.valueOf(js.getString("error_code"));
					}
				}
				data.message = js.getString("info");
			}
			
		} catch (JSONException e) {
			Logger.i(res);
			Logger.i(e);
			throw AppException.json(e);
		}
		return data;
	}
}
