package com.dxy.commerce.product.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Iterator;

public class JsonConvert {
    public static void main(String[] args) {
        String json = "{'z':'z','x':'x','c':{'v':'v','b':'b','n':'n'}}";
        String jsonArray =  "[{'z':'z', 'x':'x', 'c':{'v':'v','b':'b','n':'n'}}]";

        //json的key转大写
        System.out.println(toUpperCaseObject(json));
        //json数组的key转大写
        System.out.println(toUpperCaseArray(jsonArray).toString());
    }

    //json的key转大写
    //toUpperCase()换成toLowerCase() 转小写
    public static JSONObject toUpperCaseObject(String json) {
        JSONObject jSONArray1 = JSONObject.fromObject(json);
        JSONObject jSONArray2 = new JSONObject();
        Iterator it = jSONArray1.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object object = jSONArray1.get(key);
            if (object.getClass().toString().endsWith("JSONObject")) {
                jSONArray2.accumulate(key.toUpperCase(), toUpperCaseObject(object.toString()));
            } else if (object.getClass().toString().endsWith("JSONArray")) {
                jSONArray2.accumulate(key.toUpperCase(), toUpperCaseArray(jSONArray1.getJSONArray(key).toString()));
            }else{
                jSONArray2.accumulate(key.toUpperCase(), object);
            }
        }
        return jSONArray2;
    }

    //json数组的key转大写
    public static JSONArray toUpperCaseArray(String jsonArray) {
        JSONArray jSONArray1 = JSONArray.fromObject(jsonArray);
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray1.size(); i++) {
            Object jArray = jSONArray1.getJSONObject(i);
            if (jArray.getClass().toString().endsWith("JSONObject")) {
                jSONArray2.add(toUpperCaseObject( jArray.toString()));
            } else if (jArray.getClass().toString().endsWith("JSONArray")) {
                jSONArray2.add(toUpperCaseArray(jArray.toString()));
            }
        }
        return jSONArray2;
    }
}
