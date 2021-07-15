package util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {
    private static final LoggerManager logger = new LoggerManager("JSONReader");
    public JSONReader() {
        super();
    }

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static JSONObject initJson(String inputConfigName) {
        JSONObject jsonObject = null;
        String path = System.getProperty("user.dir");
        File file = new File(path + inputConfigName);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            Reader r = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(r);
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }

            jsonObject = new JSONObject(contents.toString());
        } catch (Exception e) {
            logger.error(e);
        }

        return jsonObject;
    }

    public static void writeJSONToTXT(JSONObject json, String fileName) {
        try {
            String path = System.getProperty("user.dir");
            Writer w = new OutputStreamWriter(new FileOutputStream(path + fileName), "UTF-8");
            BufferedWriter fout = new BufferedWriter(w);
            fout.write(json.toString());
            fout.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static boolean readBoolean(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName))
                return itemInfo.getBoolean(attName);
        } catch (JSONException e) {
            logger.error(e);
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static boolean readBoolean(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            boolean data = json.getBoolean(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return false;
        }
    }

    public static boolean[] readBooleanArr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                boolean[] temp = new boolean[arr.length()];
                int k = 0;
                while (k < arr.length()) {
                    temp[k] = arr.getInt(k) == 1;
                    // EggBreakerModule.loggerManager.info(temp[k]);
                    ++k;
                }
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        }
        return new boolean[0];
    }

    public static boolean[] readBooleanArr(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONArray arr = json.getJSONArray(attribute);
            boolean[] data = new boolean[arr.length()];
            int k = 0;
            while (k < arr.length()) {
                data[k] = arr.getInt(k) == 1;
                ++k;
            }

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return new boolean[0];
        }
    }

    public static int readInt(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName))
                return itemInfo.getInt(attName);
        } catch (JSONException e) {
            logger.error(e);
        }
        return 0;
    }

    public static int readInt(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            int data = json.getInt(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return 0;
        }
    }

    public static int[] readIntArr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                int[] temp = new int[arr.length()];
                int k = 0;
                while (k < arr.length()) {
                    temp[k] = arr.getInt(k);
                    ++k;
                }
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        }
        return new int[0];
    }

    public static List<Integer> readIntList(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                List<Integer> temp = new LinkedList<>();
                int k = 0;
                while (k < arr.length()) {
                    temp.add(arr.getInt(k));
                    ++k;
                }
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        }
        return new LinkedList<>();
    }

    public static int[] readIntArr(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONArray arr = json.getJSONArray(attribute);
            int[] data = new int[arr.length()];
            int k = 0;
            while (k < arr.length()) {
                data[k] = arr.getInt(k);
                ++k;
            }

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return new int[0];
        }
    }

    public static double[] readDoubleArr(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONArray arr = json.getJSONArray(attribute);
            double[] data = new double[arr.length()];
            int k = 0;
            while (k < arr.length()) {
                data[k] = arr.getDouble(k);
                ++k;
            }

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return new double[0];
        }
    }

    public static long readLong(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName))
                return itemInfo.getLong(attName);
        } catch (JSONException e) {
            logger.error(e);
        }
        return 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static long readLong(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            long data = json.getLong(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return 0;
        }
    }

    public static long[] readLongArr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                long[] temp = new long[arr.length()];
                int k = 0;
                while (k < arr.length()) {
                    temp[k] = arr.getLong(k);
                    ++k;
                }
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        }
        return new long[0];
    }

    public static long[] readLongArr(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONArray arr = json.getJSONArray(attribute);
            long[] data = new long[arr.length()];
            int k = 0;
            while (k < arr.length()) {
                data[k] = arr.getLong(k);
                ++k;
            }

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return new long[0];
        }
    }

    public static int[][] readArrArr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                int[][] tempArr = new int[arr.length()][0];

                int k = 0;
                while (k < arr.length()) {
                    JSONArray array = arr.getJSONArray(k);
                    int[] temp = new int[array.length()];

                    int i = 0;
                    while (i < array.length()) {
                        temp[i] = array.getInt(i);
                        ++i;
                    }
                    tempArr[k] = temp;
                    ++k;
                }
                return tempArr;
            }
        } catch (JSONException e) {
            logger.error(e);
        }

        return new int[0][0];
    }

    public static String readStr(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            String data = json.getString(attribute);
            if (removeAfterRead) {
                json.remove(attribute);
            }

            return data;
        } catch (JSONException var4) {
            logger.error(new Object[] { var4 });
            return "";
        }
    }

    public static String readStr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName))
                return itemInfo.getString(attName);
        } catch (JSONException e) {
            logger.error(e);
        }
        return "";
    }

    public static String[] readStrArr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                String[] temp = new String[arr.length()];
                int k = 0;
                while (k < arr.length()) {
                    temp[k] = arr.getString(k);
                    ++k;
                }
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        }
        return new String[0];
    }

    public static List<String> readStrList(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                List<String> temp = new LinkedList<>();
                int k = 0;
                while (k < arr.length()) {
                    temp.add(arr.getString(k));
                    k++;
                }
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        }
        return new LinkedList<>();
    }

    public static String[] readStrArr(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONArray arr = json.getJSONArray(attribute);
            String[] data = new String[arr.length()];
            int k = 0;
            while (k < arr.length()) {
                data[k] = arr.getString(k);
                ++k;
            }

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return new String[0];
        }
    }

    public static JSONObject readJSONObject(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName))
                return itemInfo.getJSONObject(attName);
        } catch (JSONException e) {
            logger.error(e);
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static JSONObject readJSONObject(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONObject data = json.getJSONObject(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return null;
        }
    }

    public static long readDate(JSONObject itemInfo, String attName) {
        Date date = readDateTime(itemInfo, attName);

        if (date == null)
            return 0;
        else
            return date.getTime();
    }

    public static long readDate(JSONObject json, String attribute, boolean removeAfterRead) {
        Date date = readDateTime(json, attribute, removeAfterRead);

        if (date == null)
            return 0;
        else
            return date.getTime();
    }

    public static Date readDateTime(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                String str = itemInfo.getString(attName);
                return df.parse(str);
            }
        } catch (JSONException | ParseException e) {
            logger.error(e);
        }
        return null;
    }

    public static Date readDateTime(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            String data = json.getString(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return df.parse(data);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static Date[] readDateTimeArr(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                int length = arr.length();
                Date[] temp = new Date[length];
                int i = 0;
                while (i < length) {
                    temp[i] = df.parse( (String) arr.get(i));

                    ++i;
                }

                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }
        return null;
    }

    public static Date[] readDateTimeArr(JSONObject itemInfo, String attName, boolean removeAfterRead) {
        try {
            if (itemInfo.has(attName)) {
                JSONArray arr = itemInfo.getJSONArray(attName);
                int length = arr.length();
                Date[] temp = new Date[length];
                int i = 0;
                while (i < length) {
                    temp[i] = df.parse( (String) arr.get(i));

                    ++i;
                }
                if (removeAfterRead)
                    itemInfo.remove(attName);
                return temp;
            }
        } catch (JSONException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }
        return null;
    }

    public static double readDouble(JSONObject itemInfo, String attName) {
        try {
            if (itemInfo.has(attName))
                return itemInfo.getDouble(attName);
        } catch (JSONException e) {
            logger.error(e);
        }

        return 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static double readDouble(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            double data = json.getDouble(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return 0;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public static JSONArray readJSONArray(JSONObject json, String attribute, boolean removeAfterRead) {
        try {
            JSONArray data = json.getJSONArray(attribute);

            if (removeAfterRead)
                json.remove(attribute);
            return data;
        } catch (JSONException e) {
            logger.error(e);
            return null;
        }
    }

    public static JSONObject getJSONObjectFromJSONArray(JSONArray jsonArr, int index) {
        try {
            return jsonArr.getJSONObject(index);
        } catch (JSONException e) {
            logger.error(e);
            return null;
        }
    }

    public static void remove(JSONObject json, String attName) {
        try {
            json.remove(attName);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}

